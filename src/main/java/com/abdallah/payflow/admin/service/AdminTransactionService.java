package com.abdallah.payflow.admin.service;

import com.abdallah.payflow.admin.dto.AdminTransactionResponse;
import com.abdallah.payflow.common.exception.NotFoundException;
import com.abdallah.payflow.transaction.entity.Transaction;
import com.abdallah.payflow.transaction.respository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.abdallah.payflow.admin.helper.AdminHelper.toResponse;

@Service
public class AdminTransactionService {

    private final TransactionRepository transactionRepository;

    public AdminTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<AdminTransactionResponse> getTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(transaction -> toResponse(transaction));
    }

    public AdminTransactionResponse getTransaction(UUID id) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        return toResponse(transaction);
    }
}