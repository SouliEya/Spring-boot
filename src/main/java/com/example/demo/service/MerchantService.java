package com.example.demo.service;

import com.example.demo.dto.MerchantCreateRequest;
import com.example.demo.dto.MerchantDTO;
import com.example.demo.entity.MerchantStatus;

import java.util.List;

public interface MerchantService {
    MerchantDTO createMerchant(MerchantCreateRequest request);
    MerchantDTO getMerchantById(Long id);
    MerchantDTO getMerchantByCode(String merchantCode);
    List<MerchantDTO> getAllMerchants();
    List<MerchantDTO> getMerchantsByStatus(MerchantStatus status);
    List<MerchantDTO> getMerchantsByBankAgent(Long bankAgentId);
    List<MerchantDTO> getMerchantsByBusinessType(String businessType);
    MerchantDTO updateMerchant(Long id, MerchantCreateRequest request);
    void deleteMerchant(Long id);
    void updateMerchantStatus(Long id, MerchantStatus status);
    void updateAccountBalance(Long id, Double amount);
}
