package com.abdallah.payflow.admin.service;

import com.abdallah.payflow.admin.dto.AdminWalletResponse;
import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.wallet.entity.Wallet;
import com.abdallah.payflow.wallet.repository.WalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.abdallah.payflow.admin.helper.AdminHelper.toResponse;

@Service
public class AdminWalletService {

    private final WalletRepository walletRepository;

    public AdminWalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Page<AdminWalletResponse> getWallets(Pageable pageable) {
        return walletRepository.findAll(pageable).map(wallet -> toResponse(wallet));
    }

    public AdminWalletResponse getWallet(UUID id) {
        Wallet wallet = walletRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        return toResponse(wallet);
    }

}