package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.dto.res.MonthlyRevenueDto;
import com.example.jobs_top.service.TransactionService;
import com.example.jobs_top.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/total")
    public ResponseEntity<?> getTotalRevenue() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,
                        Constants.SUCCESS_MESSAGE,
                        transactionService.getTotalRevenue())
        );
    }

    @GetMapping("/date")
    public ResponseEntity<?> getRevenueByDate(@RequestParam String date) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        transactionService.getRevenueByDate(LocalDate.parse(date)))
        );
    }

    @GetMapping("/month")
    public ResponseEntity<?> getRevenueByMonth(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        transactionService.getRevenueByMonth(month, year))
        );

    }

    @GetMapping("/year")
    public ResponseEntity<?> getRevenueByYear(@RequestParam int year) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        transactionService.getRevenueByYear(year))
        );

    }

    @GetMapping("/statistics/year-detail")
    public ResponseEntity<?> getMonthlyRevenueOfYear(@RequestParam int year) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        transactionService.getMonthlyRevenueOfYear(year))
        );

    }
}
