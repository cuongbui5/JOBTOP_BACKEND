package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.AccountPlan;
import com.example.jobs_top.service.AccountPlanService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-plans")
public class AccountPlanController {
    private final AccountPlanService accountPlanService;

    public AccountPlanController(AccountPlanService accountPlanService) {
        this.accountPlanService = accountPlanService;
    }

    @GetMapping
    public ResponseEntity<?> getAccountPlans() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        accountPlanService.getAllAccountPlan())
        );
    }

    @PostMapping("/active/{id}")
    public ResponseEntity<?> activeAccountPlan(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        accountPlanService.activeAccountPlan(id))
        );
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAccountPlans(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        accountPlanService.cancelAccountPlan(id))
        );
    }

    /*@PostMapping
    public ResponseEntity<?> createAccountPlan(@RequestBody AccountPlan accountPlan) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        accountPlanService.createAccountPlan(accountPlan))
        );
    }*/
}
