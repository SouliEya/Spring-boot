package com.example.demo.controller.rest;

import com.example.demo.dto.SubscriberCreateRequest;
import com.example.demo.dto.SubscriberDTO;
import com.example.demo.entity.SubscriberStatus;
import com.example.demo.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribers")
@RequiredArgsConstructor
public class SubscriberRestController {
    
    private final SubscriberService subscriberService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<SubscriberDTO> createSubscriber(@RequestBody SubscriberCreateRequest request) {
        SubscriberDTO subscriber = subscriberService.createSubscriber(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriber);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'SUBSCRIBER')")
    public ResponseEntity<SubscriberDTO> getSubscriberById(@PathVariable Long id) {
        SubscriberDTO subscriber = subscriberService.getSubscriberById(id);
        return ResponseEntity.ok(subscriber);
    }
    
    @GetMapping("/account/{accountNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'SUBSCRIBER')")
    public ResponseEntity<SubscriberDTO> getSubscriberByAccountNumber(@PathVariable String accountNumber) {
        SubscriberDTO subscriber = subscriberService.getSubscriberByAccountNumber(accountNumber);
        return ResponseEntity.ok(subscriber);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<SubscriberDTO>> getAllSubscribers() {
        List<SubscriberDTO> subscribers = subscriberService.getAllSubscribers();
        return ResponseEntity.ok(subscribers);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<SubscriberDTO>> getSubscribersByStatus(@PathVariable SubscriberStatus status) {
        List<SubscriberDTO> subscribers = subscriberService.getSubscribersByStatus(status);
        return ResponseEntity.ok(subscribers);
    }
    
    @GetMapping("/agent/{bankAgentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<SubscriberDTO>> getSubscribersByBankAgent(@PathVariable Long bankAgentId) {
        List<SubscriberDTO> subscribers = subscriberService.getSubscribersByBankAgent(bankAgentId);
        return ResponseEntity.ok(subscribers);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<SubscriberDTO> updateSubscriber(@PathVariable Long id, @RequestBody SubscriberCreateRequest request) {
        SubscriberDTO subscriber = subscriberService.updateSubscriber(id, request);
        return ResponseEntity.ok(subscriber);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<Void> updateSubscriberStatus(@PathVariable Long id, @PathVariable SubscriberStatus status) {
        subscriberService.updateSubscriberStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/balance/{amount}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<Void> updateAccountBalance(@PathVariable Long id, @PathVariable Double amount) {
        subscriberService.updateAccountBalance(id, amount);
        return ResponseEntity.ok().build();
    }
}
