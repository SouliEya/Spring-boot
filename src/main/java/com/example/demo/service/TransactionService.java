package com.example.demo.service;

import com.example.demo.dto.TransactionCreateRequest;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.TransactionStatus;
import com.example.demo.entity.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionCreateRequest request);
    TransactionDTO getTransactionById(Long id);
    TransactionDTO getTransactionByReference(String reference);
    List<TransactionDTO> getAllTransactions();
    List<TransactionDTO> getTransactionsBySubscriber(Long subscriberId);
    List<TransactionDTO> getTransactionsByMerchant(Long merchantId);
    List<TransactionDTO> getTransactionsByStatus(TransactionStatus status);
    List<TransactionDTO> getTransactionsByType(TransactionType type);
    List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void updateTransactionStatus(Long id, TransactionStatus status);
    void deleteTransaction(Long id);
}
