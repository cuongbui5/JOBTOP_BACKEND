package com.example.jobs_top.controller;

import com.example.jobs_top.service.CustomerSupportAssistant;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ChatController {
    private final CustomerSupportAssistant customerSupportAssistant;

    public ChatController(CustomerSupportAssistant customerSupportAssistant) {
        this.customerSupportAssistant = customerSupportAssistant;
    }

    @GetMapping("/chat")
    String chat(@RequestParam String message, @RequestParam String chatId) {
        return customerSupportAssistant.chat(chatId, message);
    }


}
