package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.UpdateAccountRequest;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account updateAccount(UpdateAccountRequest updateAccountRequest) {
        Account account = Utils.getAccount();
        account.setAvatar(updateAccountRequest.getAvatar());
        account.setFullName(updateAccountRequest.getFullName());
        if (StringUtils.hasText(updateAccountRequest.getNewPassword())) {
            if (!passwordEncoder.matches(updateAccountRequest.getCurrentPassword(), account.getPassword())) {
                throw new RuntimeException("Sai mật khẩu");
            }


            account.setPassword(passwordEncoder.encode(updateAccountRequest.getNewPassword()));
        }

        return accountRepository.save(account);

    }
}
