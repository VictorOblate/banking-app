-- Create the database
CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS activity_log;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS shifts;
DROP TABLE IF EXISTS packages;
DROP TABLE IF EXISTS admin;

SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login DATETIME,
    is_active BOOLEAN DEFAULT TRUE,
    
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS packages (
    package_id INT PRIMARY KEY AUTO_INCREMENT,
    package_name VARCHAR(100) NOT NULL UNIQUE,
    package_type VARCHAR(50) NOT NULL,
    description TEXT,
    benefits TEXT,
    monthly_fee DECIMAL(10, 2) DEFAULT 0.00,
    annual_fee DECIMAL(10, 2) DEFAULT 0.00,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME,
    is_active BOOLEAN DEFAULT TRUE,
    
    INDEX idx_package_type (package_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS shifts (
    shift_id INT PRIMARY KEY AUTO_INCREMENT,
    shift_name VARCHAR(100) NOT NULL UNIQUE,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    shift_type VARCHAR(50),
    description TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME,
    is_active BOOLEAN DEFAULT TRUE,
    
    INDEX idx_shift_name (shift_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE,
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(10),
    country VARCHAR(50),
    package_id INT,
    account_number VARCHAR(20) UNIQUE,
    account_status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME,
    
    FOREIGN KEY (package_id) REFERENCES packages(package_id),
    INDEX idx_email (email),
    INDEX idx_phone (phone),
    INDEX idx_account_number (account_number),
    INDEX idx_account_status (account_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(10),
    country VARCHAR(50),
    employee_code VARCHAR(20) UNIQUE NOT NULL,
    designation VARCHAR(100),
    department VARCHAR(100),
    shift_id INT,
    basic_salary DECIMAL(12, 2),
    hire_date DATE,
    employment_status ENUM('ACTIVE', 'INACTIVE', 'ON_LEAVE', 'TERMINATED') DEFAULT 'ACTIVE',
    bank_account_number VARCHAR(20),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME,
    
    FOREIGN KEY (shift_id) REFERENCES shifts(shift_id),
    INDEX idx_email (email),
    INDEX idx_employee_code (employee_code),
    INDEX idx_employment_status (employment_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    description TEXT,
    amount DECIMAL(15, 2) NOT NULL,
    balance_before DECIMAL(15, 2),
    balance_after DECIMAL(15, 2),
    reference_number VARCHAR(50) UNIQUE,
    status ENUM('PENDING', 'COMPLETED', 'FAILED', 'CANCELLED') DEFAULT 'PENDING',
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_date DATETIME,
    notes TEXT,
    
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE RESTRICT,
    INDEX idx_customer_id (customer_id),
    INDEX idx_transaction_type (transaction_type),
    INDEX idx_status (status),
    INDEX idx_transaction_date (transaction_date),
    INDEX idx_reference_number (reference_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT,
    customer_id INT,
    payment_type VARCHAR(50) NOT NULL,
    payment_description TEXT,
    amount DECIMAL(15, 2) NOT NULL,
    payment_method VARCHAR(50),
    payment_date DATE NOT NULL,
    payment_status ENUM('PENDING', 'PROCESSED', 'FAILED', 'REVERSED') DEFAULT 'PENDING',
    reference_number VARCHAR(50) UNIQUE,
    remarks TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_date DATETIME,
    
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE SET NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE SET NULL,
    INDEX idx_payment_type (payment_type),
    INDEX idx_payment_status (payment_status),
    INDEX idx_payment_date (payment_date),
    INDEX idx_employee_id (employee_id),
    INDEX idx_customer_id (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS activity_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50),
    entity_id INT,
    description TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id) ON DELETE SET NULL,
    INDEX idx_admin_id (admin_id),
    INDEX idx_log_date (log_date),
    INDEX idx_action (action)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO admin (username, email, password, full_name, phone, is_active) VALUES
('admin', 'admin@bankingapp.com', 'admin123', 'Administrator', '1234567890', TRUE);

-- Insert sample packages
INSERT INTO packages (package_name, package_type, description, benefits, monthly_fee, is_active) VALUES
('Basic Savings', 'SAVINGS', 'Basic savings account for individuals', 'Interest on deposits, Free transfers', 0.00, TRUE),
('Premium Checking', 'CHECKING', 'Premium checking account with advanced features', 'Unlimited transactions, Debit card, Check book', 5.00, TRUE),
('Student Account', 'STUDENT', 'Special account for students', 'No monthly fee, Student benefits, Discounts', 0.00, TRUE),
('Business Pro', 'BUSINESS', 'Account for business owners', 'Multiple users, Batch processing, Business tools', 25.00, TRUE);

-- Insert sample shifts
INSERT INTO shifts (shift_name, start_time, end_time, shift_type) VALUES
('Morning Shift', '09:00:00', '17:00:00', 'FULL_TIME'),
('Evening Shift', '14:00:00', '22:00:00', 'FULL_TIME'),
('Night Shift', '22:00:00', '06:00:00', 'FULL_TIME'),
('Part-time', '10:00:00', '14:00:00', 'PART_TIME');

-- Insert sample customers
INSERT INTO customers (first_name, last_name, email, phone, date_of_birth, gender, address, city, state, postal_code, country, package_id, account_number, account_status) VALUES
('Rajesh', 'Kumar', 'rajesh.kumar@email.com', '9876543210', '1990-05-15', 'MALE', '123 Main St', 'Mumbai', 'MH', '400001', 'India', 1, 'ACC001001', 'ACTIVE'),
('Priya', 'Singh', 'priya.singh@email.com', '9765432109', '1992-08-20', 'FEMALE', '456 Oak Ave', 'Delhi', 'DL', '110001', 'India', 2, 'ACC001002', 'ACTIVE'),
('Amit', 'Patel', 'amit.patel@email.com', '9654321098', '1995-12-10', 'MALE', '789 Pine Rd', 'Bangalore', 'KA', '560001', 'India', 1, 'ACC001003', 'ACTIVE'),
('Neha', 'Gupta', 'neha.gupta@email.com', '9543210987', '1998-03-25', 'FEMALE', '321 Elm St', 'Pune', 'MH', '411001', 'India', 3, 'ACC001004', 'ACTIVE');

-- Insert sample employees
INSERT INTO employees (first_name, last_name, email, phone, date_of_birth, gender, address, city, state, postal_code, country, employee_code, designation, department, shift_id, basic_salary, hire_date, employment_status, bank_account_number) VALUES
('Vikram', 'Sharma', 'vikram.sharma@bankingapp.com', '8888888888', '1988-01-10', 'MALE', '100 Bank St', 'Mumbai', 'MH', '400001', 'India', 'EMP001', 'Manager', 'Operations', 1, 50000.00, '2015-06-01', 'ACTIVE', 'BANK001'),
('Anjali', 'Verma', 'anjali.verma@bankingapp.com', '9999999999', '1994-06-20', 'FEMALE', '200 Finance Ave', 'Delhi', 'DL', '110001', 'India', 'EMP002', 'Senior Executive', 'Customer Service', 1, 40000.00, '2017-03-15', 'ACTIVE', 'BANK002'),
('Rohan', 'Mishra', 'rohan.mishra@bankingapp.com', '8765432107', '2000-11-05', 'MALE', '300 Tech Road', 'Bangalore', 'KA', '560001', 'India', 'EMP003', 'Junior Executive', 'IT Department', 2, 25000.00, '2020-01-20', 'ACTIVE', 'BANK003');

