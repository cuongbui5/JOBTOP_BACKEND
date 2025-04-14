package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.UpdateAccountRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.AccountService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountRequest account) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                accountService.updateAccount(account)));
    }


}
