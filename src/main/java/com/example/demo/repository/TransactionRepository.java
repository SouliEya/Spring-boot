package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionStatus;
import com.example.demo.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionReference(String transactionReference);
    List<Transaction> findBySubscriberId(Long subscriberId);
    List<Transaction> findByMerchantId(Long merchantId);
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByTransactionType(TransactionType transactionType);
    List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findBySubscriberIdAndStatus(Long subscriberId, TransactionStatus status);
}
