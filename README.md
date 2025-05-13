# Payment System

A Java-based banking payment system with user authentication, account management, and transaction processing capabilities.

## Features

### User Features
- Registration and authentication (JWT)
- Role-based access (User/Admin)
- Multiple bank accounts per user
- Account blocking/closure
- Payments between accounts
- Order payment integration
- Credit card blocking

### Admin Features
- Credit card blocking for limit violations
- Transaction monitoring
- System oversight

## Tech Stack

**Backend:**  
![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)  
![Servlets](https://img.shields.io/badge/Java_Servlets-4.0-blue?logo=apache-tomcat)

**Database:**  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue?logo=postgresql)

**Tools:**  
![Maven](https://img.shields.io/badge/Maven-3.8+-red?logo=apachemaven)  
![JDBC](https://img.shields.io/badge/JDBC-4.2-green)

## Database Schema

```sql
-- Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Accounts Table
CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance NUMERIC(12,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'active'
);

-- Credit Cards Table
CREATE TABLE credit_cards (
    id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id) ON DELETE CASCADE,
    card_number VARCHAR(20) UNIQUE NOT NULL,
    credit_limit NUMERIC(12,2) NOT NULL,
    current_usage NUMERIC(12,2) DEFAULT 0.00
);

-- Orders Table
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    amount NUMERIC(12,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending'
);

-- Payments Table
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    from_account_id INTEGER REFERENCES accounts(id),
    to_account_id INTEGER REFERENCES accounts(id),
    order_id INTEGER REFERENCES orders(id),
    amount NUMERIC(12,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'completed'
);
