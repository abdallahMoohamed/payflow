package com.abdallah.payflow.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AdminWalletResponse(
        UUID id,
        BigDecimal balance,
        String currency,
        UUID userId,
        String userName,
        String userEmail,
        LocalDateTime createdAt
) {
}