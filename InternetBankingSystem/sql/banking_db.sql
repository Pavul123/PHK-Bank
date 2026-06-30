-- ============================================
-- Internet Banking System - Database Schema
-- ============================================

CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

-- =====================
-- Admin Table
-- =====================
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- =====================
-- Customer Table
-- =====================
CREATE TABLE customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    address TEXT,
    password VARCHAR(255) NOT NULL,
    security_question VARCHAR(200),
    answer VARCHAR(100),
    status ENUM('ACTIVE', 'FROZEN', 'CLOSED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- Account Table
-- =====================
CREATE TABLE account (
    account_no VARCHAR(20) PRIMARY KEY,
    customer_id INT NOT NULL,
    account_type ENUM('SAVINGS', 'CURRENT') DEFAULT 'SAVINGS',
    balance DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

-- =====================
-- Transaction Table
-- =====================
CREATE TABLE transaction (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_no VARCHAR(20) NOT NULL,
    type ENUM('CREDIT', 'DEBIT', 'TRANSFER') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(255),
    FOREIGN KEY (account_no) REFERENCES account(account_no)
);

-- =====================
-- Beneficiary Table
-- =====================
CREATE TABLE beneficiary (
    beneficiary_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    beneficiary_account VARCHAR(20) NOT NULL,
    beneficiary_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

-- =====================
-- Sample Data
-- =====================
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');

INSERT INTO customer (name, phone, email, address, password, security_question, answer)
VALUES ('John Doe', '9876543210', 'john@example.com', '123 Main St, Chennai', 'pass123',
        'What is your pet name?', 'Tommy');

INSERT INTO account (account_no, customer_id, account_type, balance)
VALUES ('ACC001', 1, 'SAVINGS', 50000.00);
