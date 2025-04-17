package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.AddToInterviewRequest;
import com.example.jobs_top.dto.req.ApplyRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.service.ApplicationService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply-job/{jobId}")
    public ResponseEntity<?> applyJob(@PathVariable Long jobId) {

        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        201,
                        Constants.SUCCESS_MESSAGE,
                        applicationService.applyJob(jobId)
                ));
    }
    @PostMapping("/view/{id}")
    public ResponseEntity<?> viewApplication(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                201,
                Constants.SUCCESS_MESSAGE,
                applicationService.viewApplication(id)));
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectApplication(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(200,
                Constants.SUCCESS_MESSAGE,
                applicationService.rejectApplication(id)));
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveApplication(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                applicationService.approveApplication(id)));
    }




    @GetMapping
    public ResponseEntity<?> getAllApplicationsByCompany(@RequestParam(value = "page",defaultValue = "1") int page,
                                                         @RequestParam(value = "size",defaultValue = "5") int size,
                                                         @RequestParam(value = "scheduleId",required = false) Long scheduleId,
                                                         @RequestParam(value = "jobId",required = false) Long jobId,
                                                         @RequestParam(value = "status",required = false) ApplicationStatus status) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                applicationService.getAllApplicationsByFilter(page,size,scheduleId,jobId,status)));
    }


    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getApplicationDetail(@PathVariable Long applicationId) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                applicationService.getApplication(applicationId)));
    }


    @GetMapping("/applied-jobs")
    public ResponseEntity<?> getAppliedJobs(@RequestParam(value = "page",defaultValue = "1") int page,
                                            @RequestParam(value = "size",defaultValue = "5") int size,
                                            @RequestParam(value = "status",required = false) ApplicationStatus status) {

        return ResponseEntity.ok().body(new ApiResponse<>(
                201,
                Constants.SUCCESS_MESSAGE,
                applicationService.getAppliedJobs(page, size, status)));
    }

    @PatchMapping("/add-to-interview/{interviewId}")
    public ResponseEntity<?> addApplicationToInterview(@PathVariable Long interviewId,@RequestBody AddToInterviewRequest addToInterviewRequest){
        applicationService.addToInterview(interviewId,addToInterviewRequest);
        return ResponseEntity.ok().body(new ApiResponse<>(
                201,
                Constants.SUCCESS_MESSAGE,
                null

        ));
    }

    @PatchMapping("/remove-from-interview")
    public ResponseEntity<?> removeFromInterview(@RequestBody AddToInterviewRequest addToInterviewRequest){
        applicationService.removeFromInterview(addToInterviewRequest);
        return ResponseEntity.ok().body(new ApiResponse<>(
                201,
                Constants.SUCCESS_MESSAGE,
                null

        ));
    }


    @PatchMapping("/mark-no-show")
    public ResponseEntity<?> markNoShow(@RequestBody AddToInterviewRequest addToInterviewRequest) {
        applicationService.markNoShowApplicants(addToInterviewRequest);
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                null
        ));
    }









}
