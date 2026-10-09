package com.abdallah.payflow.admin.controller;

import com.abdallah.payflow.admin.dto.AdminWalletResponse;
import com.abdallah.payflow.admin.service.AdminWalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/wallets")
@PreAuthorize("hasRole('ADMIN')")
public class AdminWalletController {

    private final AdminWalletService adminWalletService;

    public AdminWalletController(AdminWalletService adminWalletService) {
        this.adminWalletService = adminWalletService;
    }

    @GetMapping
    public Page<AdminWalletResponse> getWallets(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        return adminWalletService.getWallets(pageable);
    }

    @GetMapping("/{id}")
    public AdminWalletResponse getWallet(@PathVariable UUID id) {
        return adminWalletService.getWallet(id);
    }
}