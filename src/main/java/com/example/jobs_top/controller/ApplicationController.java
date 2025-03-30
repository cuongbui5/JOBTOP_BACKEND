package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.ApplyRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;


    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyJob(@RequestBody ApplyRequest applyRequest) {

        return ResponseEntity.ok().body(new ApiResponse<>(201,"success", applicationService.applyJob(applyRequest)));
    }
    @PostMapping("/view/{id}")
    public ResponseEntity<?> viewApplication(@PathVariable Long id) {

        return ResponseEntity.ok().body(new ApiResponse<>(201,"success", applicationService.viewApplication(id)));
    }


    @GetMapping("/getApplicationsByRecruiter")
    public ResponseEntity<?> getAllApplicationsByRecruiter( @RequestParam(value = "page",defaultValue = "1") int page,
                                                            @RequestParam(value = "size",defaultValue = "5") int size,
                                                            @RequestParam(value = "status",required = false) String status) {
        return ResponseEntity.ok().body(new ApiResponse<>(200,"success",applicationService.getAllApplicationsByRecruiter(page,size,status)));
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getApplicationDetail(@PathVariable Long applicationId) {
        return ResponseEntity.ok().body(new ApiResponse<>(200,"success",applicationService.getApplication(applicationId)));
    }


    @GetMapping("/applied-jobs")
    public ResponseEntity<?> getAppliedJobs() {

        return ResponseEntity.ok().body(new ApiResponse<>(
                201,
                "success",
                applicationService.getAppliedJobs()));
    }



}
