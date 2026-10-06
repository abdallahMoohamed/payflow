package com.abdallah.payflow.user.service;

import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.user.dto.CreateUserRequest;
import com.abdallah.payflow.user.dto.UserResponse;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.user.exception.EmailAlreadyExistsException;
import com.abdallah.payflow.user.factory.UserFactory;
import com.abdallah.payflow.user.repository.UserRepository;
import com.abdallah.payflow.wallet.entity.Wallet;
import com.abdallah.payflow.wallet.factory.WalletFactory;
import com.abdallah.payflow.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    // Dependency injection
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final UserFactory userFactory;
    private final WalletFactory walletFactory;

    public UserService(
            UserRepository userRepository,
            WalletRepository walletRepository,
            UserFactory userFactory,
            WalletFactory walletFactory
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.userFactory = userFactory;
        this.walletFactory = walletFactory;
    }


    // Create user and wallet in a single transaction
    @Transactional
    public UserResponse createUser(CreateUserRequest userDto) {
        // 1. Check email existence
        if (userRepository.existsByEmail(userDto.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // 2. Create user
        User user = userFactory.create(userDto);
        User savedUser = userRepository.save(user);

        // 3. Create wallet for the user
        Wallet wallet = walletFactory.create(savedUser);
        walletRepository.save(wallet);

        // 4. Return user response
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
