package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Candidate;
import com.example.jobs_top.service.CandidateService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<?> getCandidateByAccount() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                candidateService.getCandidateByAccount()
        ));
    }



    @PostMapping
    public ResponseEntity<?> saveCandidate(@RequestBody Candidate candidate) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        candidateService.saveCandidate(candidate))
        );

    }
}
