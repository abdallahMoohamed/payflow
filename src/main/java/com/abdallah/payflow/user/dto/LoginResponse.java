package com.abdallah.payflow.user.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}
