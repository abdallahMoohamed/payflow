package com.abdallah.payflow.admin.controller;

import com.abdallah.payflow.admin.dto.AdminUserResponse;
import com.abdallah.payflow.admin.service.AdminUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public Page<AdminUserResponse> getUsers(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        return adminUserService.getUsers(pageable);
    }

    @GetMapping("/{id}")
    public AdminUserResponse getUser(@PathVariable UUID id) {
        return adminUserService.getUser(id);
    }
}