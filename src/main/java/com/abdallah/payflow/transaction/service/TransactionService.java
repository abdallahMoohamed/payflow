package com.abdallah.payflow.transaction.service;

import com.abdallah.payflow.auth.security.AuthenticatedUserService;
import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.transaction.constant.TransactionStatus;
import com.abdallah.payflow.transaction.dto.DepositRequest;
import com.abdallah.payflow.transaction.dto.TransactionResponse;
import com.abdallah.payflow.transaction.dto.TransferRequest;
import com.abdallah.payflow.transaction.dto.WithdrawRequest;
import com.abdallah.payflow.transaction.entity.Transaction;
import com.abdallah.payflow.transaction.exception.DuplicateTransactionException;
import com.abdallah.payflow.transaction.exception.InsufficientBalanceException;
import com.abdallah.payflow.transaction.exception.InvalidTransferException;
import com.abdallah.payflow.transaction.factory.TransactionFactory;
import com.abdallah.payflow.transaction.respository.TransactionRepository;
import com.abdallah.payflow.user.entity.User;
import com.abdallah.payflow.wallet.entity.Wallet;
import com.abdallah.payflow.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.abdallah.payflow.transaction.helper.TransactionHelper.*;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final TransactionFactory transactionFactory;
    private final AuthenticatedUserService authenticatedUserService;

    public TransactionService(
            TransactionRepository transactionRepository,
            WalletRepository walletRepository,
            TransactionFactory transactionFactory,
            AuthenticatedUserService authenticatedUserService
    ) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.transactionFactory = transactionFactory;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional
    public TransactionResponse deposit(DepositRequest request) {
        // Check for duplicate transaction using idempotency key
        if (transactionRepository.findByIdempotencyKey(request.idempotencyKey()).isPresent()) {
            throw new DuplicateTransactionException("Transaction with this idempotency key already exists");
        }

        // Get the authenticated user
        User user = authenticatedUserService.getCurrentUser();

        // Fetch the wallet and lock it for update to prevent race conditions
        Wallet wallet = walletRepository
                .findByUserIdForUpdate(user.getId())
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        // Validate the currency
        validateCurrency(wallet, request.currency());

        // Increase balance inside wallet
        BigDecimal newBalance = wallet.getBalance().add(request.amount());
        wallet.setBalance(newBalance);

        // Create transaction
        Transaction transaction = transactionFactory.createDeposit(
                wallet,
                request.amount(),
                request.currency().toUpperCase(),
                request.idempotencyKey()
        );
        transaction.setStatus(TransactionStatus.COMPLETED);

        // Save wallet in DB with new balance
        walletRepository.save(wallet);
        // Save transaction in DB
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return response
        return toResponse(savedTransaction);
    }


    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {
        // Check for duplicate transaction using idempotency key
        if (transactionRepository.findByIdempotencyKey(request.idempotencyKey()).isPresent()) {
            throw new DuplicateTransactionException("Transaction with this idempotency key already exists");
        }
        // Get the authenticated user
        User user = authenticatedUserService.getCurrentUser();

        // Fetch the wallet and lock it for update to prevent race conditions
        Wallet wallet = walletRepository
                .findByUserIdForUpdate(user.getId())
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        // Validate the currency
        validateCurrency(wallet, request.currency());

        // Check if the wallet has sufficient balance for the withdrawal
        if (wallet.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient wallet balance");
        }

        // Decrease balance inside wallet
        wallet.setBalance(wallet.getBalance().subtract(request.amount()));

        // Create transaction
        Transaction transaction = transactionFactory.createWithdrawal(
                wallet,
                request.amount(),
                request.currency().toUpperCase(),
                request.idempotencyKey()
        );
        transaction.setStatus(TransactionStatus.COMPLETED);
        // Save wallet in DB with new balance
        walletRepository.save(wallet);
        // Save transaction in DB
        Transaction savedTransaction = transactionRepository.save(transaction);
        // Return response
        return toResponse(savedTransaction);
    }


    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        // Check for duplicate transaction using idempotency key
        if (transactionRepository.findByIdempotencyKey(request.idempotencyKey()).isPresent()) {
            throw new DuplicateTransactionException("Transaction with this idempotency key already exists");
        }
        // Get the authenticated user's wallet
        User user = authenticatedUserService.getCurrentUser();

        Wallet userWallet = walletRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        UUID sourceWalletId = userWallet.getId();
        UUID destinationWalletId = request.destinationWalletId();

        // Validate that source and destination wallets are different
        if (sourceWalletId.equals(destinationWalletId)) {
            throw new InvalidTransferException("Source and destination wallets must be different");
        }

        /// Lock both wallets in a deterministic order to prevent deadlocks
        // 1. Get deterministic lock order
        UUID[] lockOrder = getLockOrder(sourceWalletId, destinationWalletId);

        // 2. Lock both wallet by consistent order
        Wallet firstWallet = walletRepository
                .findByIdForUpdate(lockOrder[0])
                .orElseThrow(() -> new NotFoundException("Wallet not found"));
        Wallet secondWallet = walletRepository
                .findByIdForUpdate(lockOrder[1])
                .orElseThrow(() -> new NotFoundException("Wallet not found"));

        // 3. Map locked wallets back to their business roles
        boolean isFirstSource = firstWallet.getId().equals(sourceWalletId);
        Wallet sourceWallet = isFirstSource ? firstWallet : secondWallet;
        Wallet destinationWallet = isFirstSource ? secondWallet : firstWallet;

        // Validate that both wallets have the same currency as the transfer request
        validateCurrency(sourceWallet, request.currency());
        validateCurrency(destinationWallet, request.currency());

        // Check if the source wallet has sufficient balance for the transfer
        if (sourceWallet.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient wallet balance");
        }

        // Decrease balance in source wallet and increase balance in destination wallet
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(request.amount()));
        destinationWallet.setBalance(destinationWallet.getBalance().add(request.amount()));

        // Create transaction for the transfer
        Transaction transaction = transactionFactory.createTransfer(
                sourceWallet,
                destinationWallet,
                request.amount(),
                request.currency().toUpperCase(),
                request.idempotencyKey()
        );
        transaction.setStatus(TransactionStatus.COMPLETED);
        // Save both wallets in DB with new balances
        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);
        // Save transaction in DB
        Transaction savedTransaction = transactionRepository.save(transaction);
        // Return response
        return toResponse(savedTransaction);
    }
}
