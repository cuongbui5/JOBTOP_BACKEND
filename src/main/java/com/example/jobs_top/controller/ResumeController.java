package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Resume;
import com.example.jobs_top.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<?> createResume(@RequestBody Resume resume) {

        return ResponseEntity.ok().body(
                new ApiResponse<>(201,"success",resumeService.createResume(resume))
        );
    }

    @GetMapping("/byUser")
    public ResponseEntity<?> getAllResumesByUser() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(201,"success",resumeService.getAllResumeByUser())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResume(@PathVariable Long id, @RequestBody Resume resume) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",resumeService.updateResume(id, resume))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",null)
        );
    }
}
