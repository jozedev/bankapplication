package com.jozedev.bankapplication.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jozedev.bankapplication.backend.account.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("FROM Transaction WHERE accountId = :accountId AND date BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountId(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
    
}
