package com.abdallah.payflow.wallet.controller;


import com.abdallah.payflow.wallet.dto.WalletResponse;
import com.abdallah.payflow.wallet.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    // Dependency injection
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/user/{userId}")
    public WalletResponse getWallet(@PathVariable UUID userId
    ) {
        return walletService.getWallet(userId);
    }
}
