package com.example.demo.dto;

import com.example.demo.entity.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDTO {
    private Long id;
    private String merchantCode;
    private String businessName;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String businessAddress;
    private String city;
    private String country;
    private String businessType;
    private Double commissionRate;
    private Double accountBalance;
    private MerchantStatus status;
    private Long bankAgentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
