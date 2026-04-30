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
DROP TABLE IF EXISTS overtime;
DROP TABLE IF EXISTS bulk_payment_batch;

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

CREATE TABLE IF NOT EXISTS overtime (
    overtime_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    overtime_date DATE NOT NULL,
    hours_worked DECIMAL(5, 2) NOT NULL,
    hourly_rate DECIMAL(10, 2),
    overtime_amount DECIMAL(15, 2),
    overtime_type VARCHAR(50),
    remarks TEXT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'PROCESSED') DEFAULT 'PENDING',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_date DATETIME,
    process_date DATETIME,
    
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE,
    INDEX idx_employee_id (employee_id),
    INDEX idx_overtime_date (overtime_date),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS bulk_payment_batch (
    batch_id INT PRIMARY KEY AUTO_INCREMENT,
    batch_type VARCHAR(50) NOT NULL,
    batch_name VARCHAR(100),
    description TEXT,
    total_amount DECIMAL(15, 2),
    total_records INT,
    payment_date DATE,
    batch_status ENUM('DRAFT', 'SUBMITTED', 'APPROVED', 'PROCESSED', 'REJECTED') DEFAULT 'DRAFT',
    created_by INT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_date DATETIME,
    remarks TEXT,
    
    FOREIGN KEY (created_by) REFERENCES admin(admin_id),
    INDEX idx_batch_type (batch_type),
    INDEX idx_batch_status (batch_status),
    INDEX idx_payment_date (payment_date)
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

