package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.EvaluateRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.AiService;
import com.example.jobs_top.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/find-job-by-cv")
    public ResponseEntity<?> findJobByCv() throws JsonProcessingException {


        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                aiService.findJobByCv()
        ));

    }

    @PostMapping("/evaluate/{jobId}")
    public ResponseEntity<?> evaluateFit(@PathVariable Long jobId) {


        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                aiService.evaluateFit(jobId)
        ));

    }
}
