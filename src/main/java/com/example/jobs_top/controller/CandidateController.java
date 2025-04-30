package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Candidate;
import com.example.jobs_top.model.enums.EducationLevel;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.Gender;
import com.example.jobs_top.model.enums.PositionLevel;
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

    @GetMapping("/search")
    public ResponseEntity<?> searchCandidates(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) PositionLevel positionLevel,
            @RequestParam(required = false) ExperienceLevel experienceLevel,
            @RequestParam(required = false) EducationLevel educationLevel,
            @RequestParam(required = false) Gender gender
    ) {


        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        candidateService.findCandidates(
                                page,
                                size,
                                keyword,
                                city,
                                industry,
                                positionLevel,
                                experienceLevel,
                                educationLevel,
                                gender
                        )
                )
        );
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
