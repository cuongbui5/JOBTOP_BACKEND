package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.LoginRequest;
import com.example.jobs_top.dto.req.RegisterRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.AuthService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        Constants.SUCCESS_MESSAGE,
                        authService.login(loginRequest)
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        Constants.SUCCESS_MESSAGE,
                        authService.register(registerRequest)
                )
        );
    }






    @GetMapping("/oauth/login")
    public ResponseEntity<?> oauthLogin(@RequestParam("code") String code) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        "success",
                        authService.loginByOauth(code))
        );
    }


}
