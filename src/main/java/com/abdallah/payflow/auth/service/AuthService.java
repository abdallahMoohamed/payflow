package com.abdallah.payflow.auth.service;


import com.abdallah.payflow.auth.dto.VerifyEmailRequest;
import com.abdallah.payflow.auth.dto.VerifyEmailResponse;
import com.abdallah.payflow.auth.security.JwtService;
import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.redis.service.OtpService;
import com.abdallah.payflow.redis.service.RateLimitService;
import com.abdallah.payflow.auth.dto.LoginRequest;
import com.abdallah.payflow.auth.dto.LoginResponse;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.user.repository.UserRepository;
import com.abdallah.payflow.wallet.entity.Wallet;
import com.abdallah.payflow.wallet.factory.WalletFactory;
import com.abdallah.payflow.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RateLimitService rateLimitService;
    private final WalletRepository walletRepository;
    private final WalletFactory walletFactory;
    private final OtpService otpService;
    private final UserRepository userRepository;


    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RateLimitService rateLimitService,
            WalletRepository walletRepository,
            WalletFactory walletFactory,
            OtpService otpService,
            UserRepository userRepository


    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.rateLimitService = rateLimitService;
        this.walletRepository = walletRepository;
        this.walletFactory = walletFactory;
        this.otpService = otpService;
        this.userRepository = userRepository;

    }

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails);

            return new LoginResponse(
                    token,
                    "Bearer"
            );

        } catch (BadCredentialsException e) {
            rateLimitService.checkLoginRateLimit(request.email());
            throw e;
        }

    }

    @Transactional
    public VerifyEmailResponse verifyEmail(VerifyEmailRequest request) {
        // Check OTP verification
        otpService.verifyOtp(request.email(), request.otp());

        // Get user details
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("User not found"));
        // If email already verified ignore it
        if (user.isEmailVerified()) {
            return new VerifyEmailResponse("Email already verified");
        }
        // Verified the account and update DB
        user.setEmailVerified(true);
        userRepository.save(user);

        // Create wallet for this user
        Wallet wallet = walletFactory.create(user);
        walletRepository.save(wallet);


        // Return response
        return new VerifyEmailResponse("Email Verified Successfully!");
    }
}
