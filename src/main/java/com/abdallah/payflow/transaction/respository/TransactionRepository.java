package com.abdallah.payflow.transaction.respository;

import com.abdallah.payflow.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Optional<Transaction> findByReference(String reference);

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);

    // Get wallet with user details
    @EntityGraph(attributePaths = {
            "sourceWallet",
            "sourceWallet.user",
            "destinationWallet",
            "destinationWallet.user"
    })
    Page<Transaction> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {
            "sourceWallet",
            "sourceWallet.user",
            "destinationWallet",
            "destinationWallet.user"
    })
    Optional<Transaction> findById(UUID id);
}
