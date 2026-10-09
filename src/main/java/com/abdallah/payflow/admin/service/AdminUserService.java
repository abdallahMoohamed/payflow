package com.abdallah.payflow.admin.service;

import com.abdallah.payflow.admin.dto.AdminUserResponse;
import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.abdallah.payflow.admin.helper.AdminHelper.*;

import java.util.UUID;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<AdminUserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> toResponse(user));
    }

    public AdminUserResponse getUser(UUID id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toResponse(user);
    }

}