package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateReview;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.InterviewReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-reviews")
public class InterviewReviewController {
    private final InterviewReviewService interviewReviewService;

    public InterviewReviewController(InterviewReviewService interviewReviewService) {
        this.interviewReviewService = interviewReviewService;
    }

    @GetMapping("/interview-slot/{id}")
    public ResponseEntity<?> getReviewBySlotId(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewReviewService.getReviewBySlotId(id))
        );
    }





    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody CreateReview createReview) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewReviewService.save(createReview))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@RequestBody CreateReview createReview, @PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",interviewReviewService.updateReview(id,createReview))
        );
    }
}
