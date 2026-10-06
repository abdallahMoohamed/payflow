package com.abdallah.payflow.wallet.service;

import com.abdallah.payflow.wallet.dto.WalletResponse;
import com.abdallah.payflow.wallet.entity.Wallet;
import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    // Dependency injection
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // Get wallet by user ID
    public WalletResponse getWallet(UUID userId) {

        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() ->
                new NotFoundException("Wallet not found")
        );

        return new WalletResponse(
                wallet.getId(),
                wallet.getUser().getId(),
                wallet.getBalance(),
                wallet.getCurrency()
        );
    }
}
