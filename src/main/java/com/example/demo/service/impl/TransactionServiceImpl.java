package com.example.demo.service.impl;

import com.example.demo.dto.TransactionCreateRequest;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.MerchantRepository;
import com.example.demo.repository.SubscriberRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final SubscriberRepository subscriberRepository;
    private final MerchantRepository merchantRepository;
    
    @Override
    public TransactionDTO createTransaction(TransactionCreateRequest request) {
        Subscriber subscriber = subscriberRepository.findById(request.getSubscriberId())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));
        
        Merchant merchant = null;
        if (request.getMerchantId() != null) {
            merchant = merchantRepository.findById(request.getMerchantId())
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));
        }
        
        // Validate sufficient balance for withdrawal/transfer
        if ((request.getTransactionType() == TransactionType.WITHDRAWAL || 
             request.getTransactionType() == TransactionType.TRANSFER) &&
            subscriber.getAccountBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        
        Transaction transaction = Transaction.builder()
                .transactionReference(generateTransactionReference())
                .subscriber(subscriber)
                .merchant(merchant)
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .status(TransactionStatus.PENDING)
                .description(request.getDescription())
                .build();
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        processTransaction(savedTransaction);
        
        return mapToDTO(savedTransaction);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public TransactionDTO getTransactionByReference(String reference) {
        return transactionRepository.findByTransactionReference(reference)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Transaction not found with reference: " + reference));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsBySubscriber(Long subscriberId) {
        return transactionRepository.findBySubscriberId(subscriberId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByMerchant(Long merchantId) {
        return transactionRepository.findByMerchantId(merchantId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void updateTransactionStatus(Long id, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
    
    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    
    private void processTransaction(Transaction transaction) {
        Subscriber subscriber = transaction.getSubscriber();
        
        switch (transaction.getTransactionType()) {
            case DEPOSIT:
                subscriber.setAccountBalance(subscriber.getAccountBalance() + transaction.getAmount());
                transaction.setStatus(TransactionStatus.COMPLETED);
                break;
            case WITHDRAWAL:
                subscriber.setAccountBalance(subscriber.getAccountBalance() - transaction.getAmount());
                transaction.setStatus(TransactionStatus.COMPLETED);
                break;
            case TRANSFER:
                subscriber.setAccountBalance(subscriber.getAccountBalance() - transaction.getAmount());
                transaction.setStatus(TransactionStatus.COMPLETED);
                break;
            case PAYMENT:
                subscriber.setAccountBalance(subscriber.getAccountBalance() - transaction.getAmount());
                if (transaction.getMerchant() != null) {
                    Merchant merchant = transaction.getMerchant();
                    Double commission = transaction.getAmount() * (merchant.getCommissionRate() / 100);
                    merchant.setAccountBalance(merchant.getAccountBalance() + commission);
                    merchantRepository.save(merchant);
                }
                transaction.setStatus(TransactionStatus.COMPLETED);
                break;
            case REFUND:
                subscriber.setAccountBalance(subscriber.getAccountBalance() + transaction.getAmount());
                transaction.setStatus(TransactionStatus.COMPLETED);
                break;
        }
        
        subscriberRepository.save(subscriber);
        transactionRepository.save(transaction);
    }
    
    private String generateTransactionReference() {
        return "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private TransactionDTO mapToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .transactionReference(transaction.getTransactionReference())
                .subscriberId(transaction.getSubscriber().getId())
                .merchantId(transaction.getMerchant() != null ? transaction.getMerchant().getId() : null)
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
