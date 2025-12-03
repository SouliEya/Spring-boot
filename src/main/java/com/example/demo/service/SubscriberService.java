package com.example.demo.service;

import com.example.demo.dto.SubscriberCreateRequest;
import com.example.demo.dto.SubscriberDTO;
import com.example.demo.entity.SubscriberStatus;

import java.util.List;

public interface SubscriberService {
    SubscriberDTO createSubscriber(SubscriberCreateRequest request);
    SubscriberDTO getSubscriberById(Long id);
    SubscriberDTO getSubscriberByAccountNumber(String accountNumber);
    List<SubscriberDTO> getAllSubscribers();
    List<SubscriberDTO> getSubscribersByStatus(SubscriberStatus status);
    List<SubscriberDTO> getSubscribersByBankAgent(Long bankAgentId);
    SubscriberDTO updateSubscriber(Long id, SubscriberCreateRequest request);
    void deleteSubscriber(Long id);
    void updateSubscriberStatus(Long id, SubscriberStatus status);
    void updateAccountBalance(Long id, Double amount);
}
