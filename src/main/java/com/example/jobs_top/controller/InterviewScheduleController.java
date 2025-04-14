package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateInterviewSchedule;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.service.InterviewScheduleService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-schedules")
public class InterviewScheduleController {
    private final InterviewScheduleService interviewScheduleService;

    public InterviewScheduleController(InterviewScheduleService interviewScheduleService) {
        this.interviewScheduleService = interviewScheduleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllInterviewSchedulesByCreatedBy() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        interviewScheduleService.getAllInterviewSchedules())
        );

    }

    @GetMapping("/for-account")
    public ResponseEntity<?> getAllInterviewSchedulesByAccount(@RequestParam(value = "page",defaultValue = "1") int page,
                                                               @RequestParam(value = "size",defaultValue = "5") int size) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        interviewScheduleService.getAllInterviewSchedulesByAccount(page,size))
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getInterviewById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.findInterviewById(id))
        );

    }

    @PostMapping
    public ResponseEntity<?> addInterviewSchedule(@RequestBody CreateInterviewSchedule createInterviewSchedule) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.createInterviewSchedule(createInterviewSchedule))
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterviewSchedule(@RequestBody CreateInterviewSchedule createInterviewSchedule, @PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"Success",interviewScheduleService.updateInterviewSchedule(id,createInterviewSchedule))
        );
    }

}
