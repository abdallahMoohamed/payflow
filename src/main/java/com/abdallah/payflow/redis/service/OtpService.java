package com.abdallah.payflow.redis.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpService {

    private static final Duration OTP_TTL = Duration.ofMinutes(5);

    private final RedisService redisService;

    public OtpService(RedisService redisService) {
        this.redisService = redisService;
    }

    public String generateOtp(String email) {
        String otp = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
        String key = "otp:" + email.toLowerCase();
        redisService.set(key, otp, OTP_TTL);
        return otp;
    }

    public void verifyOtp(String email, String otp) {
        String key = "otp:" + email.toLowerCase();
        String storedOtp = redisService.get(key);

        if (storedOtp == null) throw new BadCredentialsException("OTP is invalid or expired");
        if (!storedOtp.equals(otp)) throw new BadCredentialsException("OTP is invalid or expired");

        redisService.delete(key);

    }
}