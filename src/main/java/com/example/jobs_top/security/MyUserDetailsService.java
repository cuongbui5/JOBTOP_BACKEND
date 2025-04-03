package com.example.jobs_top.security;

import com.example.jobs_top.model.User;
import com.example.jobs_top.model.enums.UserStatus;
import com.example.jobs_top.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if(user.get().getStatus()== UserStatus.BANNED){
            throw new RuntimeException("Tài khoản đã bị khóa vĩnh viễn.");
        }

        return new UserPrincipal(user.get());
    }
}
