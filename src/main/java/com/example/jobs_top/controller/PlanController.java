package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Plan;
import com.example.jobs_top.service.PlanService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPlans() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        planService.getAllPlans()
                        )
        );
    }
    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody Plan plan) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        planService.createPlan(plan)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id,@RequestBody Plan plan) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        planService.updatePlan(id,plan)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        null
                )
        );
    }
}
