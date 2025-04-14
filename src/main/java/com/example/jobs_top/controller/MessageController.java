package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateMessage;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMessages(@RequestParam("conversationId") Long conversationId,
                                            @RequestParam(value = "page",defaultValue = "1") int page,
                                            @RequestParam(value = "size",defaultValue = "10") int size) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",messageService.getAllMessages(conversationId,page,size))
        );

    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody CreateMessage createMessage) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",messageService.createMessage(createMessage))
        );

    }

    @PostMapping("/conversation/{id}")
    public ResponseEntity<?> markMessagesAsRead(@PathVariable Long id) {
        //messageService.markMessagesAsRead(id);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",null)
        );

    }
}
