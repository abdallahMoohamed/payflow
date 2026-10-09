package com.abdallah.payflow.admin.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminUserResponse(
        UUID id,
        String name,
        String email,
        String role,
        boolean emailVerified,
        LocalDateTime createdAt
) {
}