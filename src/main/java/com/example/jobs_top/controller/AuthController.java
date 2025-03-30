package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.LoginRequest;
import com.example.jobs_top.dto.req.RegisterRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Log in successfully",
                authService.login(loginRequest)
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Register successfully",
                authService.register(registerRequest)
        ));
    }



    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(new ApiResponse<>(200,"All roles successfully",authService.getAllRoles()));
    }


    @GetMapping("/oauth/login")
    public ResponseEntity<?> oauthLogin(@RequestParam("code") String code) {
        return ResponseEntity.ok(new ApiResponse<>(200,"Success",authService.loginByOauth(code)));
    }


}
