package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateConversation;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.ConversationService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversations")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllConversations() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        conversationService.getConversationsForAccount()
                )
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConversationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        conversationService.getConversationById(id)
                )
        );

    }

    @PostMapping
    public ResponseEntity<?> createConversation(@RequestBody CreateConversation createConversation) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        conversationService.create(createConversation)
                )
        );

    }

}
