package com.abdallah.payflow.user.factory;

import com.abdallah.payflow.user.dto.CreateUserRequest;
import com.abdallah.payflow.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User create(CreateUserRequest userDto) {
        User newUser = new User();
        newUser.setName(userDto.name());
        newUser.setEmail(userDto.email());
        newUser.setPassword(passwordEncoder.encode(userDto.password()));
        return newUser;
    }
}
