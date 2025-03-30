package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/setRole/{roleId}")
    public ResponseEntity<?> updateUserRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Update role for user successfully",
                authService.updateUserRole(roleId)
        ));
    }
}
