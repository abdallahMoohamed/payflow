package com.abdallah.payflow.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL) // This line to disappear null values
public record AdminTransactionResponse(
        UUID id,
        String reference,
        String type,
        String status,
        BigDecimal amount,
        String currency,

        UUID sourceWalletId,
        UUID sourceUserId,
        String sourceUserName,
        String sourceUserEmail,

        UUID destinationWalletId,
        UUID destinationUserId,
        String destinationUserName,
        String destinationUserEmail,

        String idempotencyKey,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}