package com.abdallah.payflow.auth.controller;

import com.abdallah.payflow.auth.dto.VerifyEmailRequest;
import com.abdallah.payflow.auth.dto.VerifyEmailResponse;
import com.abdallah.payflow.auth.service.AuthService;
import com.abdallah.payflow.auth.dto.LoginRequest;
import com.abdallah.payflow.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/verify-email")
    public VerifyEmailResponse verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        return authService.verifyEmail(request);
    }
}
