package com.abdallah.payflow.user.service;

import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.messaging.kafka.event.VerificationEmailEvent;
import com.abdallah.payflow.messaging.kafka.producer.KafkaProducer;
import com.abdallah.payflow.redis.service.OtpService;
import com.abdallah.payflow.user.dto.CreateUserRequest;
import com.abdallah.payflow.user.dto.UserResponse;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.user.exception.EmailAlreadyExistsException;
import com.abdallah.payflow.user.factory.UserFactory;
import com.abdallah.payflow.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    // Dependency injection
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final OtpService otpService;
    private final KafkaProducer kafkaProducer;

    public UserService(
            UserRepository userRepository,
            UserFactory userFactory,
            OtpService otpService,
            KafkaProducer kafkaProducer

    ) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.otpService = otpService;
        this.kafkaProducer = kafkaProducer;
    }


    // Create user and wallet in a single transaction
    public UserResponse createUser(CreateUserRequest userDto) {
        // Check email existence
        if (userRepository.existsByEmail(userDto.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Create user
        User user = userFactory.create(userDto);
        User savedUser = userRepository.save(user);
        // Generate otp
        String otp = otpService.generateOtp(savedUser.getEmail());
        // Publish Kafka event
        VerificationEmailEvent event = new VerificationEmailEvent(savedUser.getEmail(), otp);
        kafkaProducer.sendVerificationEmail(event);

        //Return user response
        return toResponse(savedUser);
    }

    // Get user by ID
    public UserResponse getUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException("User not found")
                );

        return toResponse(user);
    }

    // Convert User entity to UserResponse DTO
    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
