package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AuthService authService;

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "page",defaultValue = "1") int page,
                                                           @RequestParam(value = "size",defaultValue = "5") int size) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "success",
                authService.getAllUsers(page,size)));
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<?> updateStatusForUser(@PathVariable Long id, Account user) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "success",
                authService.updateStatusForUser(id,user.getStatus())));
    }

}
