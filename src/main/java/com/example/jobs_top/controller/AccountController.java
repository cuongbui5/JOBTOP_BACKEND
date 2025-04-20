package com.example.jobs_top.controller;

import com.example.jobs_top.dto.req.UpdateAccountRequest;
import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.enums.AccountStatus;
import com.example.jobs_top.model.enums.RoleType;
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

    @GetMapping
    public ResponseEntity<?> getAllAccounts(@RequestParam(value = "page",defaultValue = "1") int page,
                                            @RequestParam(value = "size",defaultValue = "5") int size,
                                            @RequestParam(value = "roleType",required = false) RoleType roleType,
                                            @RequestParam(value = "companyName",required = false) String companyName) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                accountService.getAllAccounts(roleType,companyName,page,size)));
    }
    @PostMapping("/update-status/{id}")
    public ResponseEntity<?> updateAccountStatus(@PathVariable Long id,@RequestParam("status") AccountStatus status) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                accountService.updateAccountStatus(id,status)));
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountRequest account) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                Constants.SUCCESS_MESSAGE,
                accountService.updateAccount(account)));
    }


}
