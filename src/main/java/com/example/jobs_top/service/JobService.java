package com.example.jobs_top.service;

import com.example.jobs_top.dto.res.*;
import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.dto.view.JobDetailView;
import com.example.jobs_top.dto.view.RecruiterProfileView;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.repository.RecruiterProfileRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final RecruiterProfileService recruiterProfileService;
    private final RecruiterProfileRepository recruiterProfileRepository;

    public JobService(JobRepository jobRepository, RecruiterProfileService recruiterProfileService, RecruiterProfileRepository recruiterProfileRepository) {
        this.jobRepository = jobRepository;
        this.recruiterProfileService = recruiterProfileService;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }


    @Transactional
    public void approveJob(Long jobId) {
        int updatedRows = jobRepository.updateJobStatus(jobId, JobStatus.APPROVED);
        if (updatedRows == 0) {
            throw new RuntimeException("Job not found or update failed");
        }
    }

    @Transactional
    public void rejectJob(Long jobId) {
        int updatedRows = jobRepository.updateJobStatus(jobId, JobStatus.REJECTED);
        if (updatedRows == 0) {
            throw new RuntimeException("Job not found or update failed");
        }
    }

    @Transactional
    public Job saveJob(Job job) {
        RecruiterProfile recruiterProfile=recruiterProfileService.getRecruiterProfileByUser(RecruiterProfile.class);
        if(job.getId() == null) {
            job.setRecruiterProfile(recruiterProfile);
            job.setStatus(JobStatus.PENDING);
            return jobRepository.save(job);

        }
        Job jobUpdated=jobRepository.findById(job.getId()).get();
        if(!jobUpdated.getRecruiterProfile().equals(recruiterProfile)) {
            throw new RuntimeException("You are not allowed to change the job's recruiterProfile");
        }
        System.out.println(job.getJobType());
        jobUpdated.setTitle(job.getTitle());
        jobUpdated.setStatus(job.getStatus());
        jobUpdated.setDescription(job.getDescription());
        jobUpdated.setExperienceLevel(job.getExperienceLevel());
        jobUpdated.setJobType(job.getJobType());
        jobUpdated.setWorkSchedule(job.getWorkSchedule());
        jobUpdated.setApplicationDeadline(job.getApplicationDeadline());
        jobUpdated.setSalaryMax(job.getSalaryMax());
        jobUpdated.setSalaryMin(job.getSalaryMin());
        jobUpdated.setBenefits(job.getBenefits());
        jobUpdated.setIndustry(job.getIndustry());
        jobUpdated.setLocation(job.getLocation());
        jobUpdated.setRequirements(job.getRequirements());
        jobUpdated.getTags().clear();
        jobUpdated.getTags().addAll(job.getTags());
        jobUpdated.setCity(job.getCity());
        return jobRepository.save(jobUpdated);
    }


    /*public List<JobCountDto> getJobCountByIndustry() {
        return jobRepository.countJobsByIndustry();
    }



    public List<RecruiterJobCountDto> getJobCountByRecruiter() {
        return jobRepository.countJobsByRecruiter();
    }*/
    public List<JobCountDto> getJobCountByLocation() {
        List<Object[]> results = jobRepository.countJobsByLocation();
        return results.stream()
                .map(row -> new JobCountDto((String) row[0], ((Number) row[1]).intValue()))
                .toList();
    }



    public JobDetailView getJobById(Long id) {
        //System.out.println(job.getIndustry().getName());
        return jobRepository.findById(id,JobDetailView.class)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + id));

    }


    public Job findJobByTitleAndCompany(String title, String company) {
        return jobRepository.findByTitleAndRecruiterProfileCompanyName(title, company);
    }


    public PaginatedResponse<?> getAllJobs(int page, int size,String status) {
        Pageable pageable=PageRequest.of(page-1,size);
        Page<JobCardView> jobPage;
        System.out.println(status);
        if (!Objects.equals(status, "")) {
            JobStatus jobStatus = JobStatus.valueOf(status.toUpperCase());
            jobPage = jobRepository.findByStatus(jobStatus,pageable);
        } else {
            jobPage = jobRepository.findAllJobs(pageable);
        }
        return new PaginatedResponse<>(jobPage.getContent(),jobPage.getTotalPages(),page,jobPage.getTotalElements());
    }
    public PaginatedResponse<?> getAllJobsByUser(int page, int size) {

        User user=Utils.getUserFromContext();
        Long recruiterId=recruiterProfileRepository.getRecruiterProfileIdByUserId(user.getId());
        if(recruiterId==null){
            throw new IllegalArgumentException("Bạn phải cập nhật thông tin hồ sơ trước!");
        }

        Pageable pageable=PageRequest.of(page-1,size);
        Page<Job> jobPage = jobRepository.findByRecruiterProfileId(recruiterId,pageable);
        return new PaginatedResponse<>(jobPage.getContent().stream().map(JobDto::new).toList(),jobPage.getTotalPages(),page,jobPage.getTotalElements());
    }






    public void deleteJob(Long id) {
         jobRepository.deleteById(id);
    }

    public PaginatedResponse<?> getAllJobsView(int page,
                                               int size,
                                               Integer datePosted,
                                               String salaryRange,
                                               String exp,
                                               String jobType,
                                               Long companyId,
                                               Long industryId,
                                               String keyword,
                                               String city,
                                               String sortBy) {


        Integer salaryMin = null;
        Integer salaryMax = null;
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
            pageable = PageRequest.of(page - 1, size, Sort.by("updatedAt").ascending());
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.by("updatedAt").descending());
        }






        Page<JobCardView> jobPage = jobRepository.findAllWithFilters(updatedAfter, salaryMin, salaryMax, expEnum, jobTypeEnum, companyId, industryId, keyword, city, pageable);


        return new PaginatedResponse<>(
                jobPage.getContent(),
                jobPage.getTotalPages(),
                page,
                jobPage.getTotalElements()
        );
    }


    public List<JobCardView> getFavoriteJobs() {
        User user= Utils.getUserFromContext();
        return jobRepository.findFavoriteJobsByUserId(user.getId());
    }


    public PaginatedResponse<?> getRelatedJobs(Long id,int page,int size) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        Pageable pageable = PageRequest.of(page-1,size);
        Page<JobCardView> jobPage=jobRepository.findRelatedJobs(id, job.getIndustry().getId(), job.getCity(), job.getJobType(), job.getExperienceLevel(), pageable);

        return new PaginatedResponse<>(
                jobPage.getContent(),
                jobPage.getTotalPages(),
                page,
                jobPage.getTotalElements()
        );
    }
}
