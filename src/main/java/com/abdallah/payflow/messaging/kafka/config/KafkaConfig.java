package com.abdallah.payflow.messaging.kafka.config;

import com.abdallah.payflow.messaging.kafka.event.VerificationEmailEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, VerificationEmailEvent> producerFactory() {

        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", "localhost:9092");
        return new DefaultKafkaProducerFactory<>(
                properties,
                new StringSerializer(),
                new JacksonJsonSerializer<>()
        );
    }

    @Bean
    public KafkaTemplate<String, VerificationEmailEvent> kafkaTemplate(
            ProducerFactory<String, VerificationEmailEvent> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, VerificationEmailEvent> consumerFactory() {

        Map<String, Object> properties = new HashMap<>();

        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "verification-email-worker");
        properties.put("auto.offset.reset", "earliest");

        JacksonJsonDeserializer<VerificationEmailEvent> deserializer =
                new JacksonJsonDeserializer<>(VerificationEmailEvent.class);

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VerificationEmailEvent>
    kafkaListenerContainerFactory(ConsumerFactory<String, VerificationEmailEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, VerificationEmailEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}