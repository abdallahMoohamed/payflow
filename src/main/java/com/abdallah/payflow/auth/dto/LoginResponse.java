package com.abdallah.payflow.auth.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}
