package com.example.demo.service.impl;

import com.example.demo.dto.MerchantCreateRequest;
import com.example.demo.dto.MerchantDTO;
import com.example.demo.entity.Merchant;
import com.example.demo.entity.MerchantStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.MerchantRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MerchantServiceImpl implements MerchantService {
    
    private final MerchantRepository merchantRepository;
    private final UserRepository userRepository;
    
    @Override
    public MerchantDTO createMerchant(MerchantCreateRequest request) {
        User bankAgent = null;
        if (request.getBankAgentId() != null) {
            bankAgent = userRepository.findById(request.getBankAgentId())
                    .orElseThrow(() -> new RuntimeException("Bank agent not found"));
        }
        
        Merchant merchant = Merchant.builder()
                .merchantCode(generateMerchantCode())
                .businessName(request.getBusinessName())
                .contactPerson(request.getContactPerson())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .businessAddress(request.getBusinessAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .businessType(request.getBusinessType())
                .commissionRate(request.getCommissionRate())
                .bankAgent(bankAgent)
                .status(MerchantStatus.ACTIVE)
                .accountBalance(0.0)
                .build();
        
        Merchant savedMerchant = merchantRepository.save(merchant);
        return mapToDTO(savedMerchant);
    }
    
    @Override
    @Transactional(readOnly = true)
    public MerchantDTO getMerchantById(Long id) {
        return merchantRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public MerchantDTO getMerchantByCode(String merchantCode) {
        return merchantRepository.findByMerchantCode(merchantCode)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Merchant not found with code: " + merchantCode));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getAllMerchants() {
        return merchantRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getMerchantsByStatus(MerchantStatus status) {
        return merchantRepository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getMerchantsByBankAgent(Long bankAgentId) {
        return merchantRepository.findByBankAgentId(bankAgentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MerchantDTO> getMerchantsByBusinessType(String businessType) {
        return merchantRepository.findByBusinessType(businessType).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public MerchantDTO updateMerchant(Long id, MerchantCreateRequest request) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));
        
        merchant.setBusinessName(request.getBusinessName());
        merchant.setContactPerson(request.getContactPerson());
        merchant.setEmail(request.getEmail());
        merchant.setPhoneNumber(request.getPhoneNumber());
        merchant.setBusinessAddress(request.getBusinessAddress());
        merchant.setCity(request.getCity());
        merchant.setCountry(request.getCountry());
        merchant.setBusinessType(request.getBusinessType());
        merchant.setCommissionRate(request.getCommissionRate());
        
        Merchant updatedMerchant = merchantRepository.save(merchant);
        return mapToDTO(updatedMerchant);
    }
    
    @Override
    public void deleteMerchant(Long id) {
        merchantRepository.deleteById(id);
    }
    
    @Override
    public void updateMerchantStatus(Long id, MerchantStatus status) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));
        merchant.setStatus(status);
        merchantRepository.save(merchant);
    }
    
    @Override
    public void updateAccountBalance(Long id, Double amount) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));
        merchant.setAccountBalance(merchant.getAccountBalance() + amount);
        merchantRepository.save(merchant);
    }
    
    private String generateMerchantCode() {
        return "MER" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private MerchantDTO mapToDTO(Merchant merchant) {
        return MerchantDTO.builder()
                .id(merchant.getId())
                .merchantCode(merchant.getMerchantCode())
                .businessName(merchant.getBusinessName())
                .contactPerson(merchant.getContactPerson())
                .email(merchant.getEmail())
                .phoneNumber(merchant.getPhoneNumber())
                .businessAddress(merchant.getBusinessAddress())
                .city(merchant.getCity())
                .country(merchant.getCountry())
                .businessType(merchant.getBusinessType())
                .commissionRate(merchant.getCommissionRate())
                .accountBalance(merchant.getAccountBalance())
                .status(merchant.getStatus())
                .bankAgentId(merchant.getBankAgent() != null ? merchant.getBankAgent().getId() : null)
                .createdAt(merchant.getCreatedAt())
                .updatedAt(merchant.getUpdatedAt())
                .build();
    }
}
