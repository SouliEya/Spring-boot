package com.example.demo.config;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final SubscriberRepository subscriberRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();
    
    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeSubscribers();
        initializeMerchants();
        initializeTransactions();
    }
    
    private void initializeUsers() {
        // Initialize default admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@banking.com")
                    .firstName("Admin")
                    .lastName("User")
                    .role(UserRole.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            System.out.println("✓ Default admin user created: admin / admin123");
        }
        
        // Initialize default bank agents if not exists
        if (userRepository.findByUsername("agent1").isEmpty()) {
            User agent1 = User.builder()
                    .username("agent1")
                    .password(passwordEncoder.encode("agent123"))
                    .email("agent1@banking.com")
                    .firstName("John")
                    .lastName("Agent")
                    .role(UserRole.BANK_AGENT)
                    .active(true)
                    .build();
            userRepository.save(agent1);
            System.out.println("✓ Default bank agent created: agent1 / agent123");
        }
        
        if (userRepository.findByUsername("agent2").isEmpty()) {
            User agent2 = User.builder()
                    .username("agent2")
                    .password(passwordEncoder.encode("agent123"))
                    .email("agent2@banking.com")
                    .firstName("Jane")
                    .lastName("Agent")
                    .role(UserRole.BANK_AGENT)
                    .active(true)
                    .build();
            userRepository.save(agent2);
            System.out.println("✓ Default bank agent created: agent2 / agent123");
        }
    }
    
    private void initializeSubscribers() {
        if (subscriberRepository.count() > 0) {
            return; // Already initialized
        }
        
        User agent1 = userRepository.findByUsername("agent1").orElse(null);
        User agent2 = userRepository.findByUsername("agent2").orElse(null);
        
        List<Subscriber> subscribers = new ArrayList<>();
        String[] firstNames = {"Ahmed", "Fatima", "Mohamed", "Aisha", "Hassan", "Leila", "Omar", "Noor"};
        String[] lastNames = {"Ali", "Hassan", "Ibrahim", "Ahmed", "Khan", "Malik", "Rashid", "Saeed"};
        String[] cities = {"Casablanca", "Rabat", "Fez", "Marrakech", "Tangier", "Agadir", "Meknes", "Oujda"};
        
        for (int i = 0; i < 8; i++) {
            Subscriber subscriber = Subscriber.builder()
                    .accountNumber("ACC" + String.format("%06d", 1000 + i))
                    .firstName(firstNames[i])
                    .lastName(lastNames[i])
                    .email(firstNames[i].toLowerCase() + "." + lastNames[i].toLowerCase() + "@email.com")
                    .phoneNumber("+212" + String.format("%09d", 600000000 + random.nextInt(100000000)))
                    .address("Street " + (i + 1) + ", Building " + (i + 10))
                    .city(cities[i])
                    .country("Morocco")
                    .accountBalance(1000.0 + random.nextDouble() * 9000.0)
                    .status(SubscriberStatus.ACTIVE)
                    .bankAgent(i % 2 == 0 ? agent1 : agent2)
                    .build();
            subscribers.add(subscriber);
        }
        
        subscriberRepository.saveAll(subscribers);
        System.out.println("✓ " + subscribers.size() + " dummy subscribers created");
    }
    
    private void initializeMerchants() {
        if (merchantRepository.count() > 0) {
            return; // Already initialized
        }
        
        User agent1 = userRepository.findByUsername("agent1").orElse(null);
        User agent2 = userRepository.findByUsername("agent2").orElse(null);
        
        List<Merchant> merchants = new ArrayList<>();
        String[] businessNames = {"Tech Store", "Fashion Hub", "Food Court", "Electronics Plus", 
                                  "Beauty Shop", "Sports Zone", "Book Cafe", "Auto Parts"};
        String[] businessTypes = {"Retail", "Fashion", "Food", "Electronics", "Beauty", "Sports", "Books", "Automotive"};
        String[] cities = {"Casablanca", "Rabat", "Fez", "Marrakech", "Tangier", "Agadir", "Meknes", "Oujda"};
        
        for (int i = 0; i < 8; i++) {
            Merchant merchant = Merchant.builder()
                    .merchantCode("MERCH" + String.format("%04d", 1000 + i))
                    .businessName(businessNames[i])
                    .contactPerson("Manager " + (i + 1))
                    .email("merchant" + (i + 1) + "@business.com")
                    .phoneNumber("+212" + String.format("%09d", 700000000 + random.nextInt(100000000)))
                    .businessAddress("Avenue " + (i + 1) + ", Shop " + (i + 100))
                    .city(cities[i])
                    .country("Morocco")
                    .businessType(businessTypes[i])
                    .commissionRate(0.5 + random.nextDouble() * 2.5)
                    .accountBalance(5000.0 + random.nextDouble() * 15000.0)
                    .status(MerchantStatus.ACTIVE)
                    .bankAgent(i % 2 == 0 ? agent1 : agent2)
                    .build();
            merchants.add(merchant);
        }
        
        merchantRepository.saveAll(merchants);
        System.out.println("✓ " + merchants.size() + " dummy merchants created");
    }
    
    private void initializeTransactions() {
        if (transactionRepository.count() > 0) {
            return; // Already initialized
        }
        
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<Merchant> merchants = merchantRepository.findAll();
        
        if (subscribers.isEmpty() || merchants.isEmpty()) {
            return;
        }
        
        List<Transaction> transactions = new ArrayList<>();
        TransactionType[] types = {TransactionType.PAYMENT, TransactionType.TRANSFER, 
                                   TransactionType.DEPOSIT, TransactionType.WITHDRAWAL};
        TransactionStatus[] statuses = {TransactionStatus.COMPLETED, TransactionStatus.PENDING, TransactionStatus.FAILED};
        
        for (int i = 0; i < 20; i++) {
            Subscriber subscriber = subscribers.get(random.nextInt(subscribers.size()));
            Merchant merchant = merchants.get(random.nextInt(merchants.size()));
            TransactionType type = types[random.nextInt(types.length)];
            TransactionStatus status = statuses[random.nextInt(statuses.length)];
            
            Transaction transaction = Transaction.builder()
                    .transactionReference("TXN" + String.format("%08d", 10000000 + i))
                    .subscriber(subscriber)
                    .merchant(type == TransactionType.PAYMENT ? merchant : null)
                    .amount(10.0 + random.nextDouble() * 990.0)
                    .transactionType(type)
                    .status(status)
                    .description(type.name() + " transaction for " + subscriber.getFirstName())
                    .build();
            transactions.add(transaction);
        }
        
        transactionRepository.saveAll(transactions);
        System.out.println("✓ " + transactions.size() + " dummy transactions created");
    }
}
