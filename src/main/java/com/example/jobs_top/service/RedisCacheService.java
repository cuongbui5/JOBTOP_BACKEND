package com.example.jobs_top.service;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value, int expire) {
        redisTemplate.opsForValue().set(key, value, Duration.ofHours(expire));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
