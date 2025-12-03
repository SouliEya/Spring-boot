package com.example.demo.dto;

import com.example.demo.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCreateRequest {
    private Long subscriberId;
    private Long merchantId;
    private Double amount;
    private TransactionType transactionType;
    private String description;
}
