package com.abdallah.payflow.auth.security;


import com.abdallah.payflow.common.exception.UnauthorizedException;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    private final UserRepository userRepository;

    public AuthenticatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        return userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("User not found"));
    }
}