package com.example.demo.controller.rest;

import com.example.demo.dto.TransactionCreateRequest;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.TransactionStatus;
import com.example.demo.entity.TransactionType;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionRestController {
    
    private final TransactionService transactionService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'SUBSCRIBER', 'MERCHANT')")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionCreateRequest request) {
        TransactionDTO transaction = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'SUBSCRIBER', 'MERCHANT')")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    
    @GetMapping("/reference/{reference}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'SUBSCRIBER', 'MERCHANT')")
    public ResponseEntity<TransactionDTO> getTransactionByReference(@PathVariable String reference) {
        TransactionDTO transaction = transactionService.getTransactionByReference(reference);
        return ResponseEntity.ok(transaction);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/subscriber/{subscriberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'SUBSCRIBER')")
    public ResponseEntity<List<TransactionDTO>> getTransactionsBySubscriber(@PathVariable Long subscriberId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsBySubscriber(subscriberId);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/merchant/{merchantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'MERCHANT')")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByMerchant(@PathVariable Long merchantId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByMerchant(merchantId);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(@PathVariable TransactionType type) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
    
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<Void> updateTransactionStatus(@PathVariable Long id, @PathVariable TransactionStatus status) {
        transactionService.updateTransactionStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
