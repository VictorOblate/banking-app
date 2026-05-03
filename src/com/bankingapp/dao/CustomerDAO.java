package com.bankingapp.dao;

import com.bankingapp.model.Customer;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer Data Access Object (DAO)
 * 
 * Handles all database operations related to Customers.
 * Includes CRUD operations (Create, Read, Update, Delete) for customer management.
 * Uses PreparedStatements to prevent SQL injection.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class CustomerDAO {
    
    /**
     * Add a new customer to the database
     * 
     * @param customer The Customer object to add
     * @return true if customer was added successfully, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, email, phone, date_of_birth, " +
                     "gender, address, city, state, postal_code, country, package_id, account_number, " +
                     "account_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getDateOfBirth());
            preparedStatement.setString(6, customer.getGender());
            preparedStatement.setString(7, customer.getAddress());
            preparedStatement.setString(8, customer.getCity());
            preparedStatement.setString(9, customer.getState());
            preparedStatement.setString(10, customer.getPostalCode());
            preparedStatement.setString(11, customer.getCountry());
            if (customer.getPackageId() > 0) {
                preparedStatement.setInt(12, customer.getPackageId());
            } else {
                preparedStatement.setNull(12, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(13, customer.getAccountNumber());
            preparedStatement.setString(14, customer.getAccountStatus() != null ? customer.getAccountStatus() : "ACTIVE");
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Customer added successfully: " + customer.getFullName());
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get customer by customer ID
     * 
     * @param customerId The ID of the customer to retrieve
     * @return Customer object if found, null otherwise
     */
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT customer_id, first_name, last_name, email, phone, date_of_birth, " +
                     "gender, address, city, state, postal_code, country, package_id, " +
                     "account_number, account_status, registration_date, modified_date " +
                     "FROM customers WHERE customer_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                customer = mapResultSetToCustomer(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return customer;
    }
    
    /**
     * Get all customers from database
     * 
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        String sql = "SELECT customer_id, first_name, last_name, email, phone, date_of_birth, " +
             "gender, address, city, state, postal_code, country, package_id, " +
             "account_number, account_status, registration_date, modified_date " +
             "FROM customers " +
             "WHERE account_status <> 'INACTIVE' " +
             "ORDER BY customer_id DESC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Customer customer = mapResultSetToCustomer(resultSet);
                customers.add(customer);
            }
            System.out.println("Retrieved " + customers.size() + " customers from database");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return customers;
    }
    
    /**
     * Update customer information
     * 
     * @param customer The Customer object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
                     "date_of_birth = ?, gender = ?, address = ?, city = ?, state = ?, " +
                     "postal_code = ?, country = ?, package_id = ?, account_status = ?, " +
                     "modified_date = NOW() WHERE customer_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getDateOfBirth());
            preparedStatement.setString(6, customer.getGender());
            preparedStatement.setString(7, customer.getAddress());
            preparedStatement.setString(8, customer.getCity());
            preparedStatement.setString(9, customer.getState());
            preparedStatement.setString(10, customer.getPostalCode());
            preparedStatement.setString(11, customer.getCountry());
            if (customer.getPackageId() > 0) {
    preparedStatement.setInt(12, customer.getPackageId());
} else {
    preparedStatement.setNull(12, java.sql.Types.INTEGER);
}
            preparedStatement.setString(13, customer.getAccountStatus());
            preparedStatement.setInt(14, customer.getCustomerId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Customer updated successfully: " + customer.getFullName());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Delete a customer from database
     * 
     * Note: This performs a soft delete by marking account as inactive.
     * For true hard delete, enable foreign key constraints and remove related records.
     * 
     * @param customerId The ID of the customer to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Customer deleted successfully: ID " + customerId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Check if email already exists
     * 
     * @param email The email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) as count FROM customers WHERE email = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error checking email: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * Get total count of customers
     * 
     * @return Total number of customers in the system
     */
    public int getCustomerCount() {
        String sql = "SELECT COUNT(*) as count FROM customers";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error getting customer count: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return 0;
    }
    
    /**
     * Helper method to map ResultSet to Customer object
     * 
     * @param resultSet The ResultSet to map
     * @return Customer object populated from ResultSet
     * @throws SQLException if ResultSet access fails
     */
    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhone(resultSet.getString("phone"));
        customer.setDateOfBirth(resultSet.getString("date_of_birth"));
        customer.setGender(resultSet.getString("gender"));
        customer.setAddress(resultSet.getString("address"));
        customer.setCity(resultSet.getString("city"));
        customer.setState(resultSet.getString("state"));
        customer.setPostalCode(resultSet.getString("postal_code"));
        customer.setCountry(resultSet.getString("country"));
        customer.setPackageId(resultSet.getInt("package_id"));
        customer.setAccountNumber(resultSet.getString("account_number"));
        customer.setAccountStatus(resultSet.getString("account_status"));
        customer.setRegistrationDate(resultSet.getTimestamp("registration_date"));
        customer.setModifiedDate(resultSet.getTimestamp("modified_date"));
        return customer;
    }
}
