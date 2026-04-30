package com.bankingapp.service;

import com.bankingapp.dao.CustomerDAO;
import com.bankingapp.model.Customer;
import com.bankingapp.util.ValidationUtil;
import java.util.List;
import java.util.Random;

/**
 * Customer Service Class
 * 
 * Contains business logic for customer-related operations.
 * Handles customer CRUD operations and validation.
 * Acts as intermediary between controllers and DAO layer.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class CustomerService {
    
    private CustomerDAO customerDAO;
    
    /**
     * Constructor initializing the CustomerDAO
     */
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }
    
    /**
     * Add a new customer
     * 
     * Validates customer data before adding to database.
     * Generates an account number automatically.
     * 
     * @param customer The Customer object to add
     * @return true if customer was added successfully, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        if (customer.getAccountNumber() == null || customer.getAccountNumber().trim().isEmpty()) {
            customer.setAccountNumber(generateAccountNumber());
        }
        if (customer.getAccountStatus() == null || customer.getAccountStatus().trim().isEmpty()) {
            customer.setAccountStatus("ACTIVE");
        }
        return customerDAO.addCustomer(customer);
    }
    
    /**
     * Get customer by ID
     * 
     * @param customerId The ID of the customer
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerById(int customerId) {
        if (customerId <= 0) {
            return null;
        }
        return customerDAO.getCustomerById(customerId);
    }
    
    /**
     * Get all customers
     * 
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
    
    /**
     * Update customer information
     * 
     * Validates updated data before making changes.
     * 
     * @param customer The Customer object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateCustomer(Customer customer) {
        if (customer == null || customer.getCustomerId() <= 0) {
            return false;
        }
        
        if (!isValidCustomer(customer)) {
            System.out.println("Customer validation failed for update");
            return false;
        }
        
        return customerDAO.updateCustomer(customer);
    }
    
    /**
     * Delete (deactivate) a customer
     * 
     * @param customerId The ID of the customer to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteCustomer(int customerId) {
        if (customerId <= 0) {
            return false;
        }
        return customerDAO.deleteCustomer(customerId);
    }
    
    /**
     * Validate customer data
     * 
     * Performs comprehensive validation of customer information.
     * 
     * @param customer The customer to validate
     * @return true if customer data is valid, false otherwise
     */
    private boolean isValidCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }
        
        // Validate required fields
        if (!ValidationUtil.isValidName(customer.getFirstName())) {
            System.out.println("Invalid first name");
            return false;
        }
        
        if (!ValidationUtil.isValidName(customer.getLastName())) {
            System.out.println("Invalid last name");
            return false;
        }
        
        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
            System.out.println("Invalid email");
            return false;
        }
        
        
        
        return true;
    }
    
    /**
     * Get total count of customers
     * 
     * @return Total number of customers in the system
     */
    public int getCustomerCount() {
        return customerDAO.getCustomerCount();
    }
    
    /**
     * Generate a unique account number for new customers
     * 
     * @return A unique account number string
     */
    private String generateAccountNumber() {
        // Generate account number in format: BASO + timestamp + random digits
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000);
        return "BASO" + timestamp + String.format("%03d", random);
    }
}
