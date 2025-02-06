CREATE DATABASE IF NOT EXISTS banking;

USE banking;

CREATE TABLE IF NOT EXISTS accounts (
    customer_id VARCHAR(255) PRIMARY KEY,
    customer_name VARCHAR(255),
    balance INT DEFAULT 0  -- Initial balance is 0
);


CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255),
    transaction_type VARCHAR(50), -- 'deposit' or 'withdraw'
    amount INT,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Automatically records the transaction time
    FOREIGN KEY (customer_id) REFERENCES accounts(customer_id) -- Links transactions to accounts
);
