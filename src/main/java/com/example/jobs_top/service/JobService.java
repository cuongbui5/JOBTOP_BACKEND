package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateNotification;
import com.example.jobs_top.dto.req.UpdateJobStatusRequest;
import com.example.jobs_top.dto.res.*;
import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.Action;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyService companyService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ElasticService elasticService;
    private final NotificationService notificationService;
    private final AccountPlanService accountPlanService;
    private final AccountRepository accountRepository;


    public JobService(JobRepository jobRepository, CompanyService companyService, RedisTemplate<String, Object> redisTemplate, ElasticService elasticService, NotificationService notificationService, AccountPlanService accountPlanService, AccountRepository accountRepository) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
        this.redisTemplate = redisTemplate;
        this.elasticService = elasticService;
        this.notificationService = notificationService;
        this.accountPlanService = accountPlanService;
        this.accountRepository = accountRepository;
    }


    @Transactional
    public Job createJob(Job job){
        Account account=Utils.getAccount();
        Integer freePost=account.getFreePost();
        if(freePost>0){
            Company company = companyService.getCompanyByAccount(account);
            job.setCompany(company);
            job.setStatus(JobStatus.PENDING);
            job.setViews(0);
            job.setCreatedBy(account.getId());
            account.setFreePost(freePost-1);
            accountRepository.save(account);
            return jobRepository.save(job);
        }

        AccountPlan accountPlan=accountPlanService.findByAccountId(account.getId());

        if(accountPlan.getRemainingPosts()==0||accountPlan.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Gói hiện tại đã không còn hiệu lực hoặc hết lượt đăng");
        }


        accountPlanService.updateRemainingPosts(accountPlan.getId(), Action.DECREASE);
        Company company = companyService.getCompanyByAccount(account);
        job.setCompany(company);
        job.setStatus(JobStatus.PENDING);
        job.setViews(0);
        job.setCreatedBy(account.getId());
        return jobRepository.save(job);


    }

    @Transactional
    public Job updateJob(Long jobId, Job job){
        Job jobUpdated=jobRepository.findById(jobId).orElseThrow(()->new RuntimeException("Job not found"));
        jobUpdated.setTitle(job.getTitle());
        jobUpdated.setDescription(job.getDescription());
        jobUpdated.setExperienceLevel(job.getExperienceLevel());
        jobUpdated.setJobType(job.getJobType());
        jobUpdated.setWorkSchedule(job.getWorkSchedule());
        jobUpdated.setApplicationDeadline(job.getApplicationDeadline());
        jobUpdated.setSalaryMax(job.getSalaryMax());
        jobUpdated.setSalaryMin(job.getSalaryMin());
        jobUpdated.setBenefits(job.getBenefits());
        jobUpdated.setLocation(job.getLocation());
        jobUpdated.setRequirements(job.getRequirements());
        jobUpdated.setCity(job.getCity());
        Job jobSaved = jobRepository.save(jobUpdated);
        if(jobSaved.getStatus()==JobStatus.APPROVED){
            elasticService.upsertJobDocument(jobSaved);
        }
        return jobSaved;
    }



    public List<JobCountDto> getJobCountByLocation() {
        List<Object[]> results = jobRepository.countJobsByLocation();
        return results.stream()
                .map(row -> new JobCountDto((String) row[0], ((Number) row[1]).intValue()))
                .toList();
    }




    public PaginatedResponse<?> getAllJobs(int page, int size,JobStatus status,Long createdBy) {
        Pageable pageable=PageRequest.of(page-1,size,Sort.by("createdAt").descending());
        Page<Job> jobPage=jobRepository.findAllJobs(pageable,status,createdBy);
        List<JobDto> results=jobPage.getContent().stream().map(JobDto::new).toList();
        return new PaginatedResponse<>(
                results,
                jobPage.getTotalPages(),
                page,
                jobPage.getTotalElements());
    }





    public void deleteJob(Long id) {
         jobRepository.deleteById(id);
         elasticService.deleteJobDocument(id);

    }

    public PaginatedResponse<?> getAllJobsView(int page,
                                               int size,
                                               List<Long> categoryIds,
                                               String salaryRange,
                                               List<ExperienceLevel> exps,
                                               List<JobType> jobTypes,
                                               List<Long> companyIds,
                                               String keyword,
                                               List<String> cities,
                                               String sortBy) {

        Integer salaryMin = null;
        Integer salaryMax = null;
        System.out.println(jobTypes);

        if (salaryRange != null) {
            switch (salaryRange) {
                case "below_10m":
                    salaryMin = 0;
                    salaryMax = 10_000_000;
                    break;
                case "10m_20m":
                    salaryMin = 10_000_000;
                    salaryMax = 20_000_000;
                    break;
                case "20m_30m":
                    salaryMin = 20_000_000;
                    salaryMax = 30_000_000;
                    break;
                case "30m_50m":
                    salaryMin = 30_000_000;
                    salaryMax = 50_000_000;
                    break;
                case "above_50m":
                    salaryMin = 50_000_000;
                    salaryMax = 100_000_000; // giới hạn max lớn hơn
                    break;
            }
        }



        Pageable pageable = "date_asc".equals(sortBy)
                ? PageRequest.of(page - 1, size, Sort.by("createdAt").ascending())
                : PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<JobCardView> jobPage = jobRepository.findAllWithFilters(
                categoryIds,
                salaryMin,
                salaryMax,
                exps,
                jobTypes,
                companyIds,
                keyword,
                cities,
                pageable
        );

        return new PaginatedResponse<>(
                jobPage.getContent(),
                jobPage.getTotalPages(),
                page,
                jobPage.getTotalElements()
        );
    }



    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            // Trường hợp có nhiều IP (qua proxy), lấy IP đầu tiên
            ip = ip.split(",")[0];
        }
        return ip;
    }

    @Transactional
    public JobDto getJobById(Long id, HttpServletRequest request,boolean view) {
        String ip = getClientIp(request);
        String redisKey = "job:viewed:" + id + ":" + ip;
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))&&view) {
            job.increaseViews();
            jobRepository.save(job);
            redisTemplate.opsForValue().set(redisKey, true, Duration.ofHours(2));

        }
        return new JobDto(job);

    }

    @Transactional
    public Job updateJobStatus(Long id, UpdateJobStatusRequest updateJobStatusRequest) {
        Job job= jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        JobStatus newStatus=updateJobStatusRequest.getStatus();
        job.setStatus(newStatus);
        Job savedJob = jobRepository.save(job);
        if (updateJobStatusRequest.getStatus() == JobStatus.APPROVED) {
            elasticService.upsertJobDocument(savedJob);
        }else {
            elasticService.deleteJobDocument(savedJob.getId());
            //Account account=Utils.getAccount();
            //AccountPlan accountPlan=accountPlanService.findByAccountId(account.getId());
            //accountPlanService.updateRemainingPosts(accountPlan.getId(),Action.INCREASE);
        }
        String content = JobStatus.APPROVED.equals(newStatus)
                ? String.format("Tin tuyển dụng %s của bạn đã được phê duyệt", job.getTitle())
                : String.format("Tin tuyển dụng %s của bạn bị từ chối", job.getTitle());
        notificationService.createNotification(
                new CreateNotification(job.getCreatedBy(),
                        content,
                        "Admin"));

        return savedJob;
    }

    public List<?> getAllJobsTitle() {
        Account account=Utils.getAccount();
        return jobRepository.getAllJobsTitle(account.getId());
    }
}
