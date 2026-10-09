package com.abdallah.payflow.admin.helper;

import com.abdallah.payflow.admin.dto.AdminTransactionResponse;
import com.abdallah.payflow.admin.dto.AdminUserResponse;
import com.abdallah.payflow.admin.dto.AdminWalletResponse;
import com.abdallah.payflow.transaction.entity.Transaction;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.wallet.entity.Wallet;

public class AdminHelper {
    public static AdminUserResponse toResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.isEmailVerified(),
                user.getCreatedAt()
        );
    }

    public static AdminWalletResponse toResponse(Wallet wallet) {
        return new AdminWalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getCurrency(),
                wallet.getUser().getId(),
                wallet.getUser().getName(),
                wallet.getUser().getEmail(),
                wallet.getCreatedAt()
        );
    }

    public static AdminTransactionResponse toResponse(Transaction transaction) {

        Wallet sourceWallet = transaction.getSourceWallet();
        Wallet destinationWallet = transaction.getDestinationWallet();

        return new AdminTransactionResponse(
                transaction.getId(),
                transaction.getReference(),
                transaction.getType().name(),
                transaction.getStatus().name(),
                transaction.getAmount(),
                transaction.getCurrency(),

                sourceWallet != null
                        ? sourceWallet.getId()
                        : null,
                sourceWallet != null
                        ? sourceWallet.getUser().getId()
                        : null,
                sourceWallet != null
                        ? sourceWallet.getUser().getName()
                        : null,
                sourceWallet != null
                        ? sourceWallet.getUser().getEmail()
                        : null,


                destinationWallet != null
                        ? destinationWallet.getId()
                        : null,
                destinationWallet != null
                        ? destinationWallet.getUser().getId()
                        : null,
                destinationWallet != null
                        ? destinationWallet.getUser().getName()
                        : null,
                destinationWallet != null
                        ? destinationWallet.getUser().getEmail()
                        : null,


                transaction.getIdempotencyKey(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

}
