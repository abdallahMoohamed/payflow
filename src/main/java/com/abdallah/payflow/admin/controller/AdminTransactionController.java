package com.abdallah.payflow.admin.controller;

import com.abdallah.payflow.admin.dto.AdminTransactionResponse;
import com.abdallah.payflow.admin.service.AdminTransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/transactions")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    public AdminTransactionController(AdminTransactionService adminTransactionService) {
        this.adminTransactionService = adminTransactionService;
    }

    @GetMapping
    public Page<AdminTransactionResponse> getTransactions(@PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        return adminTransactionService.getTransactions(pageable);
    }

    @GetMapping("/{id}")
    public AdminTransactionResponse getTransaction(@PathVariable UUID id) {
        return adminTransactionService.getTransaction(id);
    }
}