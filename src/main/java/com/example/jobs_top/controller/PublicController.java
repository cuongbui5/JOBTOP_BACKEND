package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
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
    public ResponseEntity<?> getAllJobs(@RequestParam(value = "page",defaultValue = "1") int page,
                                        @RequestParam(value = "size",defaultValue = "5") int size,
                                        @RequestParam(value = "date_posted",required = false) Integer datePosted,
                                        @RequestParam(value = "salary_range",required = false) String salaryRange,
                                        @RequestParam(value = "exp",required = false) String exp,
                                        @RequestParam(value = "job_type",required = false) String jobType,
                                        @RequestParam(value = "companyId",required = false) Long companyId,
                                        @RequestParam(value = "keyword",required = false) String keyword,
                                        @RequestParam(value = "city",required = false) String city,
                                        @RequestParam(value = "sortBy",required = false) String sortBy,
                                        @RequestParam(value = "categoryIds",required = false) List<Long> categoryIds
                                        ) {

        System.out.println(page+" "+size);
        System.out.println(datePosted);
        System.out.println(salaryRange);
        System.out.println(exp);
        System.out.println(jobType);
        System.out.println(companyId);
        System.out.println(keyword);
        System.out.println(city);

        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                jobService.getAllJobsView(page,size,datePosted,
                        salaryRange,exp,jobType,companyId,keyword,city,sortBy,categoryIds)
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
