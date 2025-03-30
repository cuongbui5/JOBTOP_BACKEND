package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Industry;
import com.example.jobs_top.model.Tag;
import com.example.jobs_top.service.IndustryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/industries")
public class IndustryController {
    private final IndustryService industriesService;

    public IndustryController(IndustryService industriesService) {
        this.industriesService = industriesService;
    }

    @GetMapping
    public ResponseEntity<?> getIndustries() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",industriesService.getAll())
        );
    }

    @PostMapping("/save")
    public ResponseEntity<?> createIndustry(@RequestBody Industry industry) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(201,"success",industriesService.save(industry))
        );
    }
}
