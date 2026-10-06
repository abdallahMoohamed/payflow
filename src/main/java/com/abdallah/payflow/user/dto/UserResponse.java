package com.abdallah.payflow.user.dto;

import com.abdallah.payflow.user.role.UserRole;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        UserRole role
) {
}
