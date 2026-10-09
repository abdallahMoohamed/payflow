package com.abdallah.payflow.messaging.kafka.consumer;

import com.abdallah.payflow.email.service.EmailService;
import com.abdallah.payflow.messaging.kafka.topcis.KafkaTopics;
import com.abdallah.payflow.messaging.kafka.event.VerificationEmailEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VerificationEmailConsumer {

    private final EmailService emailService;

    public VerificationEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }


    @KafkaListener(topics = KafkaTopics.VERIFICATION_EMAIL, groupId = "verification-email-worker")
    public void consume(VerificationEmailEvent event) {

        emailService.sendVerificationEmail(event.email(), event.otp());

    }
}