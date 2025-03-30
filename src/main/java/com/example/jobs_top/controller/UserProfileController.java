package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.UserProfile;
import com.example.jobs_top.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<?> getUserProfile() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Get user profile successfully",
                userProfileService.getUserProfileByUser()
        ));
    }



    @PostMapping
    public ResponseEntity<?> saveUserProfile(@RequestBody UserProfile userProfile) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        "Save profile successfully.",
                        userProfileService.saveUserProfile(userProfile))
        );

    }
}
