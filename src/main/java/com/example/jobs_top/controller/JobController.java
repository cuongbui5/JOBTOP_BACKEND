package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.dto.res.BaseResponse;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping("/getByProfile")
    public ResponseEntity<?> getAllJobsByProfile(@RequestParam(value = "page",defaultValue = "1") int page,
                                                 @RequestParam(value = "size",defaultValue = "5") int size) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getAllJobsByUser(page,size))
        );
    }



    @GetMapping("/getFavoriteJobs")
    public ResponseEntity<?> getFavoriteJobs() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getFavoriteJobs())
        );
    }





    @GetMapping
    public ResponseEntity<?> getAllJobs(@RequestParam(value = "page",defaultValue = "1") int page,
                                        @RequestParam(value = "size",defaultValue = "5") int size,
                                        @RequestParam(value = "status",defaultValue = "") String status) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getAllJobs(page,size,status))
        );
    }

    @GetMapping("/getAllJobTitle")
    public ResponseEntity<?> getAllJobTitleByRecruiter() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.getAllJobTitleByRecruiterProfile())
        );
    }


    @PostMapping
    public ResponseEntity<?> saveJob(@RequestBody Job job) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",jobService.saveJob(job))
        );
    }

    @PostMapping("/{jobId}/approve")
    public ResponseEntity<?> approveJob(@PathVariable Long jobId) {
        jobService.approveJob(jobId);
        return ResponseEntity.ok().body(new ApiResponse<>(200,"Success",null));


    }

    @PostMapping("/{jobId}/reject")
    public ResponseEntity<?> rejectJob(@PathVariable Long jobId) {
        jobService.rejectJob(jobId);
        return ResponseEntity.ok().body(new ApiResponse<>(200,"Success",null));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable("id") Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",null)
        );


    }

}
