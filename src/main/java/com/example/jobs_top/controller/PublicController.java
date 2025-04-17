package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobType;
import com.example.jobs_top.service.*;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final CompanyService companyService;
    private final JobService jobService;
    private final CandidateService candidateService;
    private final InterviewReviewService interviewReviewService;
    private final ElasticService elasticService;
    private final ApplicationService applicationService;

    public PublicController(CompanyService companyService, JobService jobService, CandidateService candidateService, InterviewReviewService interviewReviewService, ElasticService elasticService, ApplicationService applicationService) {
        this.companyService = companyService;
        this.jobService = jobService;
        this.candidateService = candidateService;
        this.interviewReviewService = interviewReviewService;
        this.elasticService = elasticService;
        this.applicationService = applicationService;
    }
    @GetMapping("/count-jobs-by-location")
    public ResponseEntity<?> getJobCountByLocation() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getJobCountByLocation())
        );
    }

    @GetMapping("/companies")
    public ResponseEntity<?> getAllCompanies() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                companyService.getAllCompanies()
        ));
    }

    @GetMapping("/user-profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                candidateService.getUserProfileByUserId(userId)
        ));
    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<?> getAllJobs(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
            @RequestParam(value = "salaryRange", required = false) String salaryRange,
            @RequestParam(value = "exps", required = false) List<ExperienceLevel> exps,
            @RequestParam(value = "jobTypes", required = false) List<JobType> jobTypes,
            @RequestParam(value = "companyIds", required = false) List<Long> companyIds,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "cities", required = false) List<String> cities,
            @RequestParam(value = "sortBy", required = false) String sortBy
    ) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                jobService.getAllJobsView(
                        page,
                        size,
                        categoryIds,
                        salaryRange,
                        exps,
                        jobTypes,
                        companyIds,
                        keyword,
                        cities,
                        sortBy
                )
        ));
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReview(@RequestParam(value = "page",defaultValue = "1") int page,
                                          @RequestParam(value = "size",defaultValue = "5") int size,
                                          @RequestParam(value = "jobId") Long jobId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        interviewReviewService.getAllReview(jobId,page,size))
        );
    }

    @GetMapping("/jobs/sematic-search")
    public ResponseEntity<?> sematicSearch(@RequestParam(value = "key") String key) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        elasticService.sematicSearchJobDocument(key)
                )

        );
    }


    @GetMapping("/reviews/stats/{jobId}")
    public ResponseEntity<?> getRatingStatistics(@PathVariable Long jobId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewReviewService.getRatingStatistics(jobId))
        );

    }



    /*@GetMapping("/jobs/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getJobById(id))
        );
    }*/

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success", companyService.getCompanyById(id))
        );
    }

    @GetMapping("/applications/status-stats")
    public ResponseEntity<?> getStatusStats(@RequestParam("jobId") Long jobId) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                applicationService.getApplicationStatistics(jobId)
        ));
    }

    /*

    @GetMapping("/count-jobs-by-industry")
    public ResponseEntity<?> getJobCountByIndustry() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getJobCountByIndustry())
        );
    }


    @GetMapping("/count-jobs-by-recruiter")
    public ResponseEntity<?> getJobCountByRecruiter() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "success",
                jobService.getJobCountByRecruiter()
        ));

    }*/
}
