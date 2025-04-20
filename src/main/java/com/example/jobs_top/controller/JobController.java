package com.example.jobs_top.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.jobs_top.dto.req.UpdateJobStatusRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.dto.res.BaseResponse;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.service.ElasticService;
import com.example.jobs_top.service.JobService;
import com.example.jobs_top.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;


    public JobController(JobService jobService) {
        this.jobService = jobService;

    }

    @GetMapping("/titles")
    public ResponseEntity<?> getAllJobsTitle(){
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        jobService.getAllJobsTitle()
                )
        );

    }


    @GetMapping
    public ResponseEntity<?> getAllJobs(@RequestParam(value = "page",defaultValue = "1") int page,
                                        @RequestParam(value = "size",defaultValue = "5") int size,
                                        @RequestParam(value = "status",required = false) JobStatus status,
                                        @RequestParam(value = "createdBy",required = false) Long createdBy) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        jobService.getAllJobs(page,size,status,createdBy)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id, @RequestParam(value = "view",defaultValue = "true") boolean view, HttpServletRequest request) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        jobService.getJobById(id,request,view)
                )
        );
    }




    @PostMapping("/create")
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        jobService.createJob(job)
                )
        );
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatusJob(@PathVariable Long id, @RequestBody UpdateJobStatusRequest updateJobStatusRequest) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        jobService.updateJobStatus(id,updateJobStatusRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id,@RequestBody Job job) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        jobService.updateJob(id,job)
                )
        );
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable("id") Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",null)
        );


    }

}
