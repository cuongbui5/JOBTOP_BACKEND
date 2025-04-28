package com.example.jobs_top.config;

import com.example.jobs_top.dto.res.BaseResponse;
import com.example.jobs_top.service.RateLimiterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private final RateLimiterService rateLimiterService;
    private final ObjectMapper objectMapper;

    public RateLimitInterceptor(RateLimiterService rateLimiterService, ObjectMapper objectMapper) {
        this.rateLimiterService = rateLimiterService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String clientIp = request.getRemoteAddr(); // hoặc lấy theo Authorization header/token nếu cần

        if (!rateLimiterService.isAllowed(clientIp)) {
            BaseResponse baseResponse = new BaseResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests. Please try again later.");

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json;charset=UTF-8");

            // Convert BaseResponse to JSON

            String json = objectMapper.writeValueAsString(baseResponse);

            response.getWriter().write(json);
            return false;
        }

        return true;
    }
}
