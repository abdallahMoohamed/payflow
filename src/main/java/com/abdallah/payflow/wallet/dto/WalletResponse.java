package com.abdallah.payflow.wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        UUID userId,
        BigDecimal balance,
        String currency
) {
}