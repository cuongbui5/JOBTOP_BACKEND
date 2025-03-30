package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.LoginRequest;
import com.example.jobs_top.dto.req.RegisterRequest;
import com.example.jobs_top.dto.res.LoginResponse;
import com.example.jobs_top.dto.res.OauthTokenResponse;
import com.example.jobs_top.model.Role;
import com.example.jobs_top.model.User;
import com.example.jobs_top.repository.RoleRepository;
import com.example.jobs_top.repository.UserRepository;
import com.example.jobs_top.security.jwt.JwtService;
import com.example.jobs_top.security.UserPrincipal;
import com.example.jobs_top.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final WebClient webClient= WebClient.builder().build();

    @Value("${github.client-id}")
    private String clientId;
    @Value("${github.client-secret}")
    private String clientSecret;

    public AuthService(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;

    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.getRoles().add(registerRequest.getRole());
        return userRepository.save(user);
    }

    public User updateUserRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role ID không tồn tại: " + roleId));
        User user = Utils.getUserFromContext();
        if (user == null) {
            throw new IllegalStateException("Không tìm thấy user trong context");
        }
        Set<Role> roles = new HashSet<>(user.getRoles());
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }







    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userPrincipal.getUser();
            String token = jwtService.createToken(loginRequest.getEmail());
            return new LoginResponse(user,token);

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
        System.out.println(oauthTokenResponse.getAccess_token());
        HashMap<String,Object> userData = webClient.get().uri("https://api.github.com/user")
                .header("Authorization","Bearer "+oauthTokenResponse.getAccess_token())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Object>>() {})
                .block();

        String email = (String) userData.get("email");
        System.out.println(email);
        if(userData.get("email")==null){
            throw new RuntimeException("Làm ơn cài đặt chế độ công khai cho địa chỉ email trên tài khoản github của bạn!");
        }

        Optional<User> userOptional=userRepository.findByEmail(email);
        String token=jwtService.createToken(email);
        if(userOptional.isPresent()){
            return new LoginResponse(userOptional.get(),token );

        }
        Optional<Role> role=roleRepository.findByName("NONE");
        User user=new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.getRoles().add(role.get());
        userRepository.save(user);
        return new LoginResponse(user, token);

    }

}
