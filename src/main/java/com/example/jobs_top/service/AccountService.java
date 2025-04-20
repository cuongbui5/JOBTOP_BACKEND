package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.UpdateAccountRequest;
import com.example.jobs_top.dto.res.AccountDto;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Company;
import com.example.jobs_top.model.enums.AccountStatus;
import com.example.jobs_top.model.enums.RoleType;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.repository.CompanyRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
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

    public Account updateAccountStatus(Long id,AccountStatus accountStatus) {
        Account account=accountRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Not found account"));
        account.setStatus(accountStatus);
        return accountRepository.save(account);
    }


    public PaginatedResponse<?> getAllAccounts(RoleType role,String companyName, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size, Sort.by("createdAt").descending());
        Page<Object[]> results = accountRepository.findAccountsWithCompanyAndFilter(role,companyName, pageable);
        Page<AccountDto> dtoPage = results.map(result -> {
            Account account = (Account) result[0];
            Company company = (Company) result[1];
            return new AccountDto(account, company);
        });

        return new PaginatedResponse<>(
                dtoPage.getContent(),
                dtoPage.getTotalPages(),
                page,
                dtoPage.getTotalElements()
        );
    }
}
