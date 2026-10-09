package com.abdallah.payflow.transaction.factory;

import com.abdallah.payflow.transaction.constant.TransactionStatus;
import com.abdallah.payflow.transaction.constant.TransactionType;
import com.abdallah.payflow.transaction.entity.Transaction;
import com.abdallah.payflow.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransactionFactory {
    public Transaction createDeposit(
            Wallet wallet,
            BigDecimal amount,
            String currency,
            String idempotencyKey
    ) {
        Transaction transaction = new Transaction();

        transaction.setReference(generateReference());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setDestinationWallet(wallet);
        transaction.setIdempotencyKey(idempotencyKey);

        return transaction;
    }

    public Transaction createWithdrawal(
            Wallet wallet,
            BigDecimal amount,
            String currency,
            String idempotencyKey
    ) {
        Transaction transaction = new Transaction();

        transaction.setReference(generateReference());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setSourceWallet(wallet);
        transaction.setIdempotencyKey(idempotencyKey);

        return transaction;
    }

    public Transaction createTransfer(
            Wallet sourceWallet,
            Wallet destinationWallet,
            BigDecimal amount,
            String currency,
            String idempotencyKey
    ) {
        Transaction transaction = new Transaction();

        transaction.setReference(generateReference());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setSourceWallet(sourceWallet);
        transaction.setDestinationWallet(destinationWallet);
        transaction.setIdempotencyKey(idempotencyKey);

        return transaction;
    }

    private String generateReference() {
        return "TXN-" + UUID.randomUUID();
    }
}
