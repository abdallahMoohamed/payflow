package com.abdallah.payflow.redis.service;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;


import java.time.Duration;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public void expire(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
