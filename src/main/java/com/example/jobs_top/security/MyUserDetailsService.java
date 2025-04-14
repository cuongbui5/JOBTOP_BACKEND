package com.example.jobs_top.security;

import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.enums.AccountStatus;
import com.example.jobs_top.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public MyUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if(account.get().getStatus()== AccountStatus.BANNED){
            throw new RuntimeException("Tài khoản đã bị khóa vĩnh viễn.");
        }

        return new UserPrincipal(account.get());
    }
}
