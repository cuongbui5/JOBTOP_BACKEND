package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.AdminDashboardService;
import com.example.jobs_top.service.StripeService;
import com.example.jobs_top.utils.Constants;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }


    @GetMapping("/accounts")
    public ResponseEntity<?> getAccountCountByRole(){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        adminDashboardService.getAccountCountsByRole()
                )
        );
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> getJobCountByStatus(){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        adminDashboardService.getJobCountByStatus()
                )
        );
    }

    @GetMapping("/interviews")
    public ResponseEntity<?> getInterviewCountByStatus(){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        adminDashboardService.getInterviewCountsByStatus()
                )
        );
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getApplicationCountByStatus(){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        adminDashboardService.countApplicationByStatus()
                )
        );
    }

    @GetMapping("/jobs/top-view")
    public ResponseEntity<?> findTopViewedJobs(@RequestParam("top") int top){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        adminDashboardService.getTopNViewedJobs(top)
                )
        );
    }

    @GetMapping("/plans/top-used")
    public ResponseEntity<?> getTopUsedPlans(){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        adminDashboardService.getTopUsedPlans()
                )
        );
    }

}
