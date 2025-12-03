package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantCreateRequest {
    private String businessName;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String businessAddress;
    private String city;
    private String country;
    private String businessType;
    private Double commissionRate;
    private Long bankAgentId;
}
