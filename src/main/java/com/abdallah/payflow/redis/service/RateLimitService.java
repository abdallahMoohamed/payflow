package com.abdallah.payflow.redis.service;

import com.abdallah.payflow.redis.exception.RateLimitExceededException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final RedisService redisService;

    public RateLimitService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void checkLoginRateLimit(String email) {
        String key = "rate_limit:login:" + email.toLowerCase();

        Long attempts = redisService.increment(key);

        if (attempts == 1) {
            redisService.expire(key, WINDOW);
        }

        if (attempts > MAX_ATTEMPTS) {
            throw new RateLimitExceededException("Too many login attempts. Please try again later.");
        }
    }
}