package com.abdallah.payflow.transaction.helper;

import com.abdallah.payflow.transaction.dto.TransactionResponse;
import com.abdallah.payflow.transaction.entity.Transaction;
import com.abdallah.payflow.wallet.entity.Wallet;

import java.util.UUID;

public class TransactionHelper {
    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getReference(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getAmount(),
                transaction.getCurrency()
        );
    }

    public static void validateCurrency(Wallet wallet, String currency) {
        if (!wallet.getCurrency().equalsIgnoreCase(currency)) {
            throw new IllegalArgumentException("Currency does not match wallet currency");
        }
    }


    // This method returns the lock order for two wallet IDs to prevent deadlocks during concurrent transfers.
    public static UUID[] getLockOrder(UUID firstWalletId, UUID secondWalletId) {
        if (firstWalletId.compareTo(secondWalletId) < 0) {
            return new UUID[]{firstWalletId, secondWalletId};
        }
        return new UUID[]{secondWalletId, firstWalletId};
    }

}
