package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.LoginRequest;
import com.example.jobs_top.dto.req.RegisterRequest;
import com.example.jobs_top.dto.res.LoginResponse;
import com.example.jobs_top.dto.res.OauthTokenResponse;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.enums.AccountStatus;
import com.example.jobs_top.model.enums.RoleType;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.security.jwt.JwtService;
import com.example.jobs_top.security.UserPrincipal;
import com.example.jobs_top.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.*;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient= WebClient.builder().build();

    @Value("${github.client-id}")
    private String clientId;
    @Value("${github.client-secret}")
    private String clientSecret;

    public AuthService(JwtService jwtService, AccountRepository accountRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;


    }



    public Account register(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setEmail(registerRequest.getEmail());
        account.setStatus(AccountStatus.ACTIVE);
        account.setFullName(registerRequest.getFullName());
        account.setRole(registerRequest.getRole());
        if (registerRequest.getRole()==RoleType.EMPLOYER) {
            account.setFreePost(3);
        }

        return accountRepository.save(account);
    }









    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Account account = userPrincipal.getUser();
            String token = jwtService.createToken(loginRequest.getEmail());
            return new LoginResponse(account,token);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }



    }

    public OauthTokenResponse getAccessToken(String code) {

        String params = String.format("?client_id=%s&client_secret=%s&code=%s", clientId, clientSecret, code);

        return webClient.post()
                .uri("https://github.com/login/oauth/access_token"+params)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();

    }
    public LoginResponse loginByOauth(String code) {
        OauthTokenResponse oauthTokenResponse=getAccessToken(code);
        HashMap<String,Object> userData = webClient.get().uri("https://api.github.com/user")
                .header("Authorization","Bearer "+oauthTokenResponse.getAccess_token())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {})
                .block();
        if(userData==null){
            throw new RuntimeException("Login failed");
        }

        String email = (String) userData.get("email");

        if(userData.get("email")==null){
            throw new RuntimeException("Làm ơn cài đặt chế độ công khai cho địa chỉ email trên tài khoản github của bạn!");
        }

        Optional<Account> userOptional= accountRepository.findByEmail(email);
        String token=jwtService.createToken(email);
        if(userOptional.isPresent()){
            return new LoginResponse(userOptional.get(),token );

        }
        Account user=new Account();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRole(RoleType.CANDIDATE);
        accountRepository.save(user);
        return new LoginResponse(user, token);

    }

    public PaginatedResponse<?> getAllUsers(int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        Page<Account> users= accountRepository.findAll(pageable);
        return new PaginatedResponse<>(
                users.getContent(),
                users.getTotalPages(),
                page,
                users.getTotalElements()
        );
    }

    public Account updateStatusForUser(Long userId, AccountStatus accountStatus) {
        Account user= accountRepository.findById(userId).orElseThrow(()->new RuntimeException("Not found user"));
        user.setStatus(accountStatus);
        return accountRepository.save(user);
    }
}
