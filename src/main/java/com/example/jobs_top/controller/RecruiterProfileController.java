package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.dto.res.RecruiterJobCountDto;
import com.example.jobs_top.dto.view.RecruiterProfileView;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.UserProfile;
import com.example.jobs_top.service.RecruiterProfileService;
import com.example.jobs_top.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(RecruiterProfileService recruiterProfileService) {
        this.recruiterProfileService = recruiterProfileService;
    }






    @GetMapping
    public ResponseEntity<?> getRecruiterProfile() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Get user profile successfully",
                recruiterProfileService.getRecruiterProfileByUser(RecruiterProfileView.class)
        ));
    }

    @PostMapping()
    public ResponseEntity<?> saveRecruiterProfile(@RequestBody RecruiterProfile recruiterProfile) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        "Save profile successfully.",
                        recruiterProfileService.saveRecruiterProfile(recruiterProfile))
        );

    }

}
