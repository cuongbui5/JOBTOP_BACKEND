package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateInterviewSchedule;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.service.InterviewScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-schedule")
public class InterviewScheduleController {
    private final InterviewScheduleService interviewScheduleService;

    public InterviewScheduleController(InterviewScheduleService interviewScheduleService) {
        this.interviewScheduleService = interviewScheduleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllInterviewSchedulesByCreatedBy() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.getAllInterviewSchedules())
        );

    }

    @GetMapping("/application/{id}")
    public ResponseEntity<?> getInterviewScheduleByApplication(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.findAllByApplicationId(id))
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterviewById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.getInterviewById(id))
        );

    }

    @PostMapping
    public ResponseEntity<?> addInterviewSchedule(@RequestBody CreateInterviewSchedule createInterviewSchedule) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.createInterviewSchedule(createInterviewSchedule))
        );
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelInterviewSchedule(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.cancelInterviewSchedule(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterviewSchedule(@RequestBody CreateInterviewSchedule createInterviewSchedule, @PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.updateInterviewSchedule(id,createInterviewSchedule))
        );
    }

}
