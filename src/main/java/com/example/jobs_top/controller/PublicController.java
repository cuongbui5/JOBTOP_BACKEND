package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.UserProfile;
import com.example.jobs_top.service.InterviewReviewService;
import com.example.jobs_top.service.JobService;
import com.example.jobs_top.service.RecruiterProfileService;
import com.example.jobs_top.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final RecruiterProfileService recruiterProfileService;
    private final JobService jobService;
    private final UserProfileService userProfileService;
    private final InterviewReviewService interviewReviewService;

    public PublicController(RecruiterProfileService recruiterProfileService, JobService jobService, UserProfileService userProfileService, InterviewReviewService interviewReviewService) {
        this.recruiterProfileService = recruiterProfileService;
        this.jobService = jobService;
        this.userProfileService = userProfileService;

        this.interviewReviewService = interviewReviewService;
    }
    @GetMapping("/count-jobs-by-location")
    public ResponseEntity<?> getJobCountByLocation() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getJobCountByLocation())
        );
    }

    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getRecruiterProfiles() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Get user profile successfully",
                recruiterProfileService.getAllRecruiterProfiles()
        ));
    }

    @GetMapping("/user-profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Get user profile successfully",
                userProfileService.getUserProfileByUserId(userId)
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
                                        @RequestParam(value = "industryId",required = false) Long industryId,
                                        @RequestParam(value = "keyword",required = false) String keyword,
                                        @RequestParam(value = "city",required = false) String city,
                                        @RequestParam(value = "sortBy",required = false) String sortBy
                                        ) {

        System.out.println(page+" "+size);
        System.out.println(datePosted);
        System.out.println(salaryRange);
        System.out.println(exp);
        System.out.println(jobType);
        System.out.println(companyId);
        System.out.println(industryId);
        System.out.println(keyword);
        System.out.println(city);

        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Get user profile successfully",
                jobService.getAllJobsView(page,size,datePosted,salaryRange,exp,jobType,companyId,industryId,keyword,city,sortBy)
        ));
    }
    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReview(@RequestParam(value = "page",defaultValue = "1") int page,
                                          @RequestParam(value = "size",defaultValue = "5") int size,
                                          @RequestParam(value = "jobId") Long jobId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewReviewService.getAllReview(jobId,page,size))
        );
    }

    @GetMapping("/reviews/stats/{jobId}")
    public ResponseEntity<?> getRatingStatistics(@PathVariable Long jobId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewReviewService.getRatingStatistics(jobId))
        );

    }

    @GetMapping("/related-jobs/{id}")
    public ResponseEntity<?> getRelatedJobs(@PathVariable Long id,
                                            @RequestParam(value = "page",defaultValue = "1") int page,
                                            @RequestParam(value = "size",defaultValue = "5") int size) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Get user profile successfully",
                jobService.getRelatedJobs(id,page,size)
        ));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getJobById(id))
        );
    }

    @GetMapping("/recruiter-profile/{id}")
    public ResponseEntity<?> getRecruiterProfile(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",recruiterProfileService.getRecruiterProfileById(id))
        );
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
