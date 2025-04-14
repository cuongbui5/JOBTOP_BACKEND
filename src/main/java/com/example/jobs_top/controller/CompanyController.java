package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.dto.view.RecruiterProfileView;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Company;
import com.example.jobs_top.service.CompanyService;
import com.example.jobs_top.utils.Constants;
import com.example.jobs_top.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    public ResponseEntity<?> getCompanyProfile() {
        Account account= Utils.getAccount();
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                companyService.getCompanyByAccount(account)
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveCompany(@RequestBody Company company) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        companyService.createCompany(company))
        );

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id,@RequestBody Company company) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        Constants.SUCCESS_MESSAGE,
                        companyService.updateCompany(id,company))
        );

    }

}
