package com.example.demo.repository;

import com.example.demo.entity.Subscriber;
import com.example.demo.entity.SubscriberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByAccountNumber(String accountNumber);
    Optional<Subscriber> findByEmail(String email);
    Optional<Subscriber> findByPhoneNumber(String phoneNumber);
    List<Subscriber> findByStatus(SubscriberStatus status);
    List<Subscriber> findByBankAgentId(Long bankAgentId);
}
