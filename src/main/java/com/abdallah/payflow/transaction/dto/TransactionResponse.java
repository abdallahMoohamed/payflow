package com.abdallah.payflow.transaction.dto;

import com.abdallah.payflow.transaction.constant.TransactionStatus;
import com.abdallah.payflow.transaction.constant.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String reference,
        TransactionType type,
        TransactionStatus status,
        BigDecimal amount,
        String currency
) {
}