package com.abdallah.payflow.messaging.kafka.producer;

import com.abdallah.payflow.messaging.kafka.event.VerificationEmailEvent;
import com.abdallah.payflow.messaging.kafka.topcis.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, VerificationEmailEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, VerificationEmailEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendVerificationEmail(VerificationEmailEvent event) {
        kafkaTemplate.send(KafkaTopics.VERIFICATION_EMAIL, event.email(), event);
    }
}