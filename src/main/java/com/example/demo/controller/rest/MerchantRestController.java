package com.example.demo.controller.rest;

import com.example.demo.dto.MerchantCreateRequest;
import com.example.demo.dto.MerchantDTO;
import com.example.demo.entity.MerchantStatus;
import com.example.demo.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantRestController {
    
    private final MerchantService merchantService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantCreateRequest request) {
        MerchantDTO merchant = merchantService.createMerchant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(merchant);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'MERCHANT')")
    public ResponseEntity<MerchantDTO> getMerchantById(@PathVariable Long id) {
        MerchantDTO merchant = merchantService.getMerchantById(id);
        return ResponseEntity.ok(merchant);
    }
    
    @GetMapping("/code/{merchantCode}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT', 'MERCHANT')")
    public ResponseEntity<MerchantDTO> getMerchantByCode(@PathVariable String merchantCode) {
        MerchantDTO merchant = merchantService.getMerchantByCode(merchantCode);
        return ResponseEntity.ok(merchant);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<MerchantDTO>> getAllMerchants() {
        List<MerchantDTO> merchants = merchantService.getAllMerchants();
        return ResponseEntity.ok(merchants);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<MerchantDTO>> getMerchantsByStatus(@PathVariable MerchantStatus status) {
        List<MerchantDTO> merchants = merchantService.getMerchantsByStatus(status);
        return ResponseEntity.ok(merchants);
    }
    
    @GetMapping("/agent/{bankAgentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<MerchantDTO>> getMerchantsByBankAgent(@PathVariable Long bankAgentId) {
        List<MerchantDTO> merchants = merchantService.getMerchantsByBankAgent(bankAgentId);
        return ResponseEntity.ok(merchants);
    }
    
    @GetMapping("/type/{businessType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<List<MerchantDTO>> getMerchantsByBusinessType(@PathVariable String businessType) {
        List<MerchantDTO> merchants = merchantService.getMerchantsByBusinessType(businessType);
        return ResponseEntity.ok(merchants);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<MerchantDTO> updateMerchant(@PathVariable Long id, @RequestBody MerchantCreateRequest request) {
        MerchantDTO merchant = merchantService.updateMerchant(id, request);
        return ResponseEntity.ok(merchant);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<Void> updateMerchantStatus(@PathVariable Long id, @PathVariable MerchantStatus status) {
        merchantService.updateMerchantStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/balance/{amount}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BANK_AGENT')")
    public ResponseEntity<Void> updateAccountBalance(@PathVariable Long id, @PathVariable Double amount) {
        merchantService.updateAccountBalance(id, amount);
        return ResponseEntity.ok().build();
    }
}
