package com.example.demo.service.impl;

import com.example.demo.dto.SubscriberCreateRequest;
import com.example.demo.dto.SubscriberDTO;
import com.example.demo.entity.Subscriber;
import com.example.demo.entity.SubscriberStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.SubscriberRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriberServiceImpl implements SubscriberService {
    
    private final SubscriberRepository subscriberRepository;
    private final UserRepository userRepository;
    
    @Override
    public SubscriberDTO createSubscriber(SubscriberCreateRequest request) {
        User bankAgent = null;
        if (request.getBankAgentId() != null) {
            bankAgent = userRepository.findById(request.getBankAgentId())
                    .orElseThrow(() -> new RuntimeException("Bank agent not found"));
        }
        
        Subscriber subscriber = Subscriber.builder()
                .accountNumber(generateAccountNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .bankAgent(bankAgent)
                .status(SubscriberStatus.ACTIVE)
                .accountBalance(0.0)
                .build();
        
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        return mapToDTO(savedSubscriber);
    }
    
    @Override
    @Transactional(readOnly = true)
    public SubscriberDTO getSubscriberById(Long id) {
        return subscriberRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public SubscriberDTO getSubscriberByAccountNumber(String accountNumber) {
        return subscriberRepository.findByAccountNumber(accountNumber)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with account number: " + accountNumber));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SubscriberDTO> getAllSubscribers() {
        return subscriberRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SubscriberDTO> getSubscribersByStatus(SubscriberStatus status) {
        return subscriberRepository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SubscriberDTO> getSubscribersByBankAgent(Long bankAgentId) {
        return subscriberRepository.findByBankAgentId(bankAgentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public SubscriberDTO updateSubscriber(Long id, SubscriberCreateRequest request) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with id: " + id));
        
        subscriber.setFirstName(request.getFirstName());
        subscriber.setLastName(request.getLastName());
        subscriber.setEmail(request.getEmail());
        subscriber.setPhoneNumber(request.getPhoneNumber());
        subscriber.setAddress(request.getAddress());
        subscriber.setCity(request.getCity());
        subscriber.setCountry(request.getCountry());
        
        Subscriber updatedSubscriber = subscriberRepository.save(subscriber);
        return mapToDTO(updatedSubscriber);
    }
    
    @Override
    public void deleteSubscriber(Long id) {
        subscriberRepository.deleteById(id);
    }
    
    @Override
    public void updateSubscriberStatus(Long id, SubscriberStatus status) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with id: " + id));
        subscriber.setStatus(status);
        subscriberRepository.save(subscriber);
    }
    
    @Override
    public void updateAccountBalance(Long id, Double amount) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with id: " + id));
        subscriber.setAccountBalance(subscriber.getAccountBalance() + amount);
        subscriberRepository.save(subscriber);
    }
    
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private SubscriberDTO mapToDTO(Subscriber subscriber) {
        return SubscriberDTO.builder()
                .id(subscriber.getId())
                .accountNumber(subscriber.getAccountNumber())
                .firstName(subscriber.getFirstName())
                .lastName(subscriber.getLastName())
                .email(subscriber.getEmail())
                .phoneNumber(subscriber.getPhoneNumber())
                .address(subscriber.getAddress())
                .city(subscriber.getCity())
                .country(subscriber.getCountry())
                .accountBalance(subscriber.getAccountBalance())
                .status(subscriber.getStatus())
                .bankAgentId(subscriber.getBankAgent() != null ? subscriber.getBankAgent().getId() : null)
                .createdAt(subscriber.getCreatedAt())
                .updatedAt(subscriber.getUpdatedAt())
                .build();
    }
}
