package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateManySlots;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.dto.res.BaseResponse;
import com.example.jobs_top.model.InterviewSlot;
import com.example.jobs_top.service.InterviewSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview-slots")
public class InterviewSlotController {
    private final InterviewSlotService interviewSlotService;

    public InterviewSlotController(InterviewSlotService interviewSlotService) {
        this.interviewSlotService = interviewSlotService;
    }
    @GetMapping("/getByUser")
    public ResponseEntity<?> getAllInterviewSlotsByUser() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewSlotService.getInterviewSlotsByUser())
        );
    }

    @PostMapping("/creates")
    public ResponseEntity<?> createManySlots(@RequestBody CreateManySlots createManySlots) {
        interviewSlotService.createSlots(createManySlots);
        return ResponseEntity.ok().body(
                new BaseResponse(200,"success")
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSlotById(@PathVariable Long id) {
        interviewSlotService.deleteSlotById(id);
        return ResponseEntity.ok().body(
                new BaseResponse(200,"success")
        );
    }

    @GetMapping("/interview-schedule/{id}")
    public ResponseEntity<?> getAllSlotByInterViewScheduleId(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewSlotService.getAllSlotByInterViewScheduleId(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterviewSlotStatus(@PathVariable Long id,@RequestBody InterviewSlot interviewSlot) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewSlotService.updateInterviewSlot(id,interviewSlot.getStatus()))
        );
    }
}
