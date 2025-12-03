package com.example.demo.dto;

import com.example.demo.entity.SubscriberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberDTO {
    private Long id;
    private String accountNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private Double accountBalance;
    private SubscriberStatus status;
    private Long bankAgentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
