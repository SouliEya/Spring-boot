package com.example.demo.repository;

import com.example.demo.entity.Merchant;
import com.example.demo.entity.MerchantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMerchantCode(String merchantCode);
    Optional<Merchant> findByEmail(String email);
    Optional<Merchant> findByPhoneNumber(String phoneNumber);
    List<Merchant> findByStatus(MerchantStatus status);
    List<Merchant> findByBankAgentId(Long bankAgentId);
    List<Merchant> findByBusinessType(String businessType);
}
