package com.example.jobs_top.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${rate.limit.maxRequests:100}")
    private int maxRequests;

    @Value("${rate.limit.timeWindowSeconds:60}")
    private int timeWindowSeconds;
    public RateLimiterService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public boolean isAllowed(String key) {
        String redisKey = "rate_limit:" + key;
        Long currentCount = redisTemplate.opsForValue().increment(redisKey);

        if (currentCount == 1) {
            // First time this key is set, set expiration
            redisTemplate.expire(redisKey, Duration.ofSeconds(timeWindowSeconds));
        }

        return currentCount <= maxRequests;
    }
}
