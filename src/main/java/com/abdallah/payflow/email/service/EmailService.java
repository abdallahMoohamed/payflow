package com.abdallah.payflow.email.service;

import com.abdallah.payflow.email.exception.FailedSendException;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Resend resend;

    public EmailService(@Value("${resend.api-key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void sendVerificationEmail(String email, String otp) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(email)
                .subject("Verify your PayFlow account")
                .html(
                        """
                                <h2>Verify your PayFlow account</h2>
                                <p>Your verification code is:</p>
                                <h1>%s</h1>
                                <p>This code expires in 5 minutes.</p>
                                """.formatted(otp)
                )
                .build();
        try {
            resend.emails().send(params);
        } catch (ResendException e) {
            throw new FailedSendException("Failed to send verification email");
        }
    }
}