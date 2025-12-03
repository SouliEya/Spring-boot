-- Sample Data for Banking Agent Application
USE banking_db;

-- Insert Users (Passwords are hashed with BCrypt in real application)
-- Admin user: admin / admin123
INSERT INTO users (username, password, email, first_name, last_name, role, active) VALUES
('admin', '$2a$10$slYQmyNdGzin7olVN3p5Be7DkH0rI24tk9QgwRQlS5KfZiJL0/4m6', 'admin@banking.com', 'Admin', 'User', 'ADMIN', TRUE),
('agent1', '$2a$10$slYQmyNdGzin7olVN3p5Be7DkH0rI24tk9QgwRQlS5KfZiJL0/4m6', 'agent1@banking.com', 'John', 'Agent', 'BANK_AGENT', TRUE),
('agent2', '$2a$10$slYQmyNdGzin7olVN3p5Be7DkH0rI24tk9QgwRQlS5KfZiJL0/4m6', 'agent2@banking.com', 'Jane', 'Agent', 'BANK_AGENT', TRUE);

-- Insert Subscribers
INSERT INTO subscribers (account_number, first_name, last_name, email, phone_number, address, city, country, account_balance, status, bank_agent_id) VALUES
('ACC1704110400001', 'Michael', 'Johnson', 'michael.johnson@example.com', '+1234567890', '123 Main Street', 'New York', 'USA', 5000.00, 'ACTIVE', 2),
('ACC1704110400002', 'Sarah', 'Williams', 'sarah.williams@example.com', '+1234567891', '456 Oak Avenue', 'Boston', 'USA', 3500.50, 'ACTIVE', 2),
('ACC1704110400003', 'David', 'Brown', 'david.brown@example.com', '+1234567892', '789 Pine Road', 'Chicago', 'USA', 7200.75, 'ACTIVE', 3),
('ACC1704110400004', 'Emily', 'Davis', 'emily.davis@example.com', '+1234567893', '321 Elm Street', 'Houston', 'USA', 2100.00, 'ACTIVE', 3),
('ACC1704110400005', 'Robert', 'Miller', 'robert.miller@example.com', '+1234567894', '654 Maple Drive', 'Phoenix', 'USA', 8900.25, 'ACTIVE', 2),
('ACC1704110400006', 'Jennifer', 'Wilson', 'jennifer.wilson@example.com', '+1234567895', '987 Cedar Lane', 'Philadelphia', 'USA', 1500.00, 'SUSPENDED', 3),
('ACC1704110400007', 'James', 'Moore', 'james.moore@example.com', '+1234567896', '147 Birch Court', 'San Antonio', 'USA', 4300.50, 'ACTIVE', 2),
('ACC1704110400008', 'Patricia', 'Taylor', 'patricia.taylor@example.com', '+1234567897', '258 Spruce Way', 'San Diego', 'USA', 6100.75, 'ACTIVE', 3);

-- Insert Merchants
INSERT INTO merchants (merchant_code, business_name, contact_person, email, phone_number, business_address, city, country, business_type, commission_rate, account_balance, status, bank_agent_id) VALUES
('MER1704110400001', 'ABC Electronics Store', 'Tom Anderson', 'contact@abcelectronics.com', '+1987654321', '100 Commerce Street', 'New York', 'USA', 'Retail', 2.50, 1500.00, 'ACTIVE', 2),
('MER1704110400002', 'XYZ Restaurant Group', 'Maria Garcia', 'info@xyzrestaurant.com', '+1987654322', '200 Food Court', 'Boston', 'USA', 'Restaurant', 3.00, 2300.50, 'ACTIVE', 2),
('MER1704110400003', 'Tech Solutions Inc', 'Robert Chen', 'sales@techsolutions.com', '+1987654323', '300 Tech Park', 'Chicago', 'USA', 'Technology', 2.00, 5600.75, 'ACTIVE', 3),
('MER1704110400004', 'Fashion Hub Boutique', 'Lisa Martinez', 'hello@fashionhub.com', '+1987654324', '400 Fashion Lane', 'Houston', 'USA', 'Fashion', 3.50, 800.00, 'ACTIVE', 3),
('MER1704110400005', 'Health & Wellness Center', 'Dr. James Wilson', 'contact@healthwellness.com', '+1987654325', '500 Health Avenue', 'Phoenix', 'USA', 'Healthcare', 1.50, 3200.25, 'ACTIVE', 2),
('MER1704110400006', 'Auto Parts Warehouse', 'Kevin Johnson', 'sales@autoparts.com', '+1987654326', '600 Industrial Road', 'Philadelphia', 'USA', 'Automotive', 2.75, 4100.50, 'SUSPENDED', 3),
('MER1704110400007', 'Book Store Paradise', 'Amanda Lee', 'info@bookstore.com', '+1987654327', '700 Library Lane', 'San Antonio', 'USA', 'Retail', 2.25, 1200.00, 'ACTIVE', 2),
('MER1704110400008', 'Coffee House Deluxe', 'Marco Rossi', 'contact@coffeehouse.com', '+1987654328', '800 Cafe Street', 'San Diego', 'USA', 'Food & Beverage', 3.25, 2900.75, 'ACTIVE', 3);

-- Insert Transactions
INSERT INTO transactions (transaction_reference, subscriber_id, merchant_id, amount, transaction_type, status, description) VALUES
('TXN1704110400001', 1, 1, 150.00, 'PAYMENT', 'COMPLETED', 'Electronics purchase'),
('TXN1704110400002', 2, 2, 45.50, 'PAYMENT', 'COMPLETED', 'Restaurant meal'),
('TXN1704110400003', 3, 3, 500.00, 'PAYMENT', 'COMPLETED', 'Software license'),
('TXN1704110400004', 4, 4, 200.00, 'PAYMENT', 'COMPLETED', 'Fashion items'),
('TXN1704110400005', 5, 5, 100.00, 'PAYMENT', 'COMPLETED', 'Health consultation'),
('TXN1704110400006', 1, NULL, 1000.00, 'DEPOSIT', 'COMPLETED', 'Monthly salary deposit'),
('TXN1704110400007', 2, NULL, 500.00, 'DEPOSIT', 'COMPLETED', 'Bonus deposit'),
('TXN1704110400008', 3, NULL, 2000.00, 'DEPOSIT', 'COMPLETED', 'Quarterly payment'),
('TXN1704110400009', 4, NULL, 300.00, 'WITHDRAWAL', 'COMPLETED', 'Cash withdrawal'),
('TXN1704110400010', 5, NULL, 500.00, 'WITHDRAWAL', 'COMPLETED', 'ATM withdrawal'),
('TXN1704110400011', 6, 6, 75.00, 'PAYMENT', 'COMPLETED', 'Auto parts purchase'),
('TXN1704110400012', 7, 7, 35.00, 'PAYMENT', 'COMPLETED', 'Book purchase'),
('TXN1704110400013', 8, 8, 25.50, 'PAYMENT', 'COMPLETED', 'Coffee purchase'),
('TXN1704110400014', 1, 2, 60.00, 'PAYMENT', 'PENDING', 'Restaurant reservation'),
('TXN1704110400015', 2, 1, 200.00, 'TRANSFER', 'COMPLETED', 'Transfer to savings'),
('TXN1704110400016', 3, 4, 150.00, 'REFUND', 'COMPLETED', 'Refund for returned items'),
('TXN1704110400017', 4, 5, 50.00, 'PAYMENT', 'COMPLETED', 'Wellness package'),
('TXN1704110400018', 5, 3, 300.00, 'PAYMENT', 'COMPLETED', 'Software subscription'),
('TXN1704110400019', 6, NULL, 1500.00, 'DEPOSIT', 'COMPLETED', 'Large deposit'),
('TXN1704110400020', 7, 7, 45.00, 'PAYMENT', 'COMPLETED', 'Book collection purchase');

-- Verify Data
SELECT 'Users' as Table_Name, COUNT(*) as Record_Count FROM users
UNION ALL
SELECT 'Subscribers', COUNT(*) FROM subscribers
UNION ALL
SELECT 'Merchants', COUNT(*) FROM merchants
UNION ALL
SELECT 'Transactions', COUNT(*) FROM transactions;
