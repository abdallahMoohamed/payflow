package com.abdallah.payflow.wallet.repository;

import com.abdallah.payflow.wallet.entity.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findByUserId(UUID userId);

    // This for join with user table and get user data
    @EntityGraph(attributePaths = "user")
    Page<Wallet> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Optional<Wallet> findById(UUID id);


    /// We lock its database row so another transaction cannot modify it until my transaction finishes.
    // Find this wallet by userId and lockIt
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w  WHERE w.user.id = :userId")
    Optional<Wallet> findByUserIdForUpdate(@Param("userId") UUID userId);

    // Find this wallet by id and lockIt
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.id = :id")
    Optional<Wallet> findByIdForUpdate(@Param("id") UUID id);
}