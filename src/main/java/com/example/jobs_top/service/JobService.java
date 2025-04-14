package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.UpdateJobStatusRequest;
import com.example.jobs_top.dto.res.*;
import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyService companyService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ElasticService elasticService;

    public JobService(JobRepository jobRepository, CompanyService companyService, RedisTemplate<String, Object> redisTemplate, ElasticService elasticService) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
        this.redisTemplate = redisTemplate;
        this.elasticService = elasticService;
    }



    public Job createJob(Job job){
        Account account=Utils.getAccount();
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
        Job jobSaved= jobRepository.save(jobUpdated);
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
                                               Integer datePosted,
                                               String salaryRange,
                                               String exp,
                                               String jobType,
                                               Long companyId,
                                               String keyword,
                                               String city,
                                               String sortBy,
                                               List<Long> categoryIds) {


        Integer salaryMin;
        Integer salaryMax;
        if(salaryRange!=null){
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
                    salaryMax = 50_000_000;
                    break;
                default:
                    salaryMin = null;
                    salaryMax = null;
                    break;
            }
        }else {
            salaryMin = null;
            salaryMax = null;
        }


        ZonedDateTime updatedAfter = (datePosted != null)
                ? ZonedDateTime.now(ZoneId.systemDefault()).minusDays(datePosted)
                : null;

        JobType jobTypeEnum = (jobType != null) ? JobType.valueOf(jobType) : null;
        ExperienceLevel expEnum = (exp != null) ? ExperienceLevel.valueOf(exp) : null;
        Pageable pageable;
        if ("date_asc".equals(sortBy)) {
            pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").ascending());
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        }






        Page<JobCardView> jobPage = jobRepository.findAllWithFilters(updatedAfter,
                salaryMin,
                salaryMax,
                expEnum,
                jobTypeEnum,
                companyId,
                keyword,
                city,
                categoryIds,
                pageable);


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
        job.setStatus(updateJobStatusRequest.getStatus());
        Job savedJob = jobRepository.save(job);
        if (updateJobStatusRequest.getStatus() == JobStatus.APPROVED) {
            elasticService.upsertJobDocument(savedJob);
        }

        return savedJob;
    }

    public List<?> getAllJobsTitle() {
        Account account=Utils.getAccount();
        return jobRepository.getAllJobsTitle(account.getId());
    }
}
