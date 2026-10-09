package com.abdallah.payflow.messaging.kafka.event;

public record VerificationEmailEvent(
        String email,
        String otp
) {
}