package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.SendJobsRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.EmailService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PostMapping("/jobs-notification")
    public ResponseEntity<?> sendEmail(@RequestBody SendJobsRequest sendJobsRequest) {
        emailService.sendJobEmail(sendJobsRequest.getJobIds(), sendJobsRequest.getCandidateId());
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        null
                )
        );
    }
}
