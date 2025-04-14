package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.CreateReport;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> addReport(@RequestBody CreateReport createReport) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",reportService.createReport(createReport))
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllReports(@RequestParam(value = "page",defaultValue = "1") int page,
                                           @RequestParam(value = "size",defaultValue = "5") int size) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",reportService.getAllReports(page,size))
        );
    }
}
