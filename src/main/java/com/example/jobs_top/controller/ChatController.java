package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.chatbot.CandidateSupportAssistant;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/chatbot")
public class ChatController {
    private final CandidateSupportAssistant candidateSupportAssistant;

    public ChatController(CandidateSupportAssistant candidateSupportAssistant) {
        this.candidateSupportAssistant = candidateSupportAssistant;
    }

    @GetMapping
    public ResponseEntity<?> chat(@RequestParam String message) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        candidateSupportAssistant.chat(message)
                )
        );

    }


}
