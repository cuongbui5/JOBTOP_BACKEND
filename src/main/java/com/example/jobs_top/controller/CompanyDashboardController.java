package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.CompanyStatisticsService;
import com.example.jobs_top.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/employer/dashboard")
public class CompanyDashboardController {
    private final CompanyStatisticsService companyStatisticsService;

    public CompanyDashboardController(CompanyStatisticsService companyStatisticsService) {
        this.companyStatisticsService = companyStatisticsService;
    }



    @GetMapping
    public ResponseEntity<?> getCompanyDashboard() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        companyStatisticsService.getEmployerDashboardView()
                )
        );
    }





    @GetMapping("/job/{id}")
    public ResponseEntity<?> getJobStatistics(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        companyStatisticsService.getJobStatisticsView(id)
                )
        );
    }

}
