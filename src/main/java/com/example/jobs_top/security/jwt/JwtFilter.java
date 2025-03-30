package com.example.jobs_top.security.jwt;

import com.example.jobs_top.dto.res.BaseResponse;
import com.example.jobs_top.security.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtFilter extends OncePerRequestFilter {


    private final MyUserDetailsService userDetailsService;

    public JwtFilter( MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext securityContext=SecurityContextHolder.getContext();

        if (securityContext.getAuthentication() == null || securityContext.getAuthentication() instanceof AnonymousAuthenticationToken) {
            try {
                String authHeader = request.getHeader("Authorization");
                String token = null;
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);

                }

                if(token!=null){
                    var decodedJWT = JwtService.validate(token);
                    String email  = JwtService.getEmailFromToken(decodedJWT);
                    UserDetails userDetails=userDetailsService.loadUserByUsername(email);

                    if(userDetails!=null){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                        securityContext.setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);

            } catch (Exception e) {
                handleUnauthorizedResponse(e,response);

            }

        }


    }



    private void handleUnauthorizedResponse(Exception e,HttpServletResponse response) throws IOException {
        BaseResponse res=new BaseResponse(HttpStatus.UNAUTHORIZED.value(),e.getClass().getSimpleName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try (OutputStream responseStream = response.getOutputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(responseStream, res);
            responseStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to write unauthorized response", ex);
        }
    }
}
