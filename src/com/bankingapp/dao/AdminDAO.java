package com.bankingapp.dao;

import com.bankingapp.model.Admin;
import com.bankingapp.util.DBConnection;
import java.sql.*;

/**
 * Admin Data Access Object (DAO)
 * 
 * Handles all database operations related to Admin users.
 * Includes authentication, profile management, and admin-specific queries.
 * Uses PreparedStatements to prevent SQL injection.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class AdminDAO {
    
    /**
     * Authenticate admin user with username and password
     * 
     * This method retrieves an admin from the database and verifies credentials.
     * Password comparison is done as plain text for this basic implementation.
     * In production, passwords should be hashed (bcrypt, SHA-256, etc.)
     * 
     * @param username The admin username
     * @param password The admin password
     * @return Admin object if authentication successful, null otherwise
     */
    public Admin authenticate(String username, String password) {
        String sql = "SELECT admin_id, username, password, full_name, email, phone, " +
                     "created_date, last_login, is_active FROM admin WHERE username = ? AND password = ? AND is_active = TRUE";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Admin admin = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFullName(resultSet.getString("full_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPhone(resultSet.getString("phone"));
                admin.setCreatedDate(resultSet.getTimestamp("created_date"));
                admin.setLastLogin(resultSet.getTimestamp("last_login"));
                admin.setActive(resultSet.getBoolean("is_active"));
                
                System.out.println("Admin authentication successful for: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error during admin authentication: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return admin;
    }
    
    /**
     * Get admin by admin ID
     * 
     * @param adminId The ID of the admin to retrieve
     * @return Admin object if found, null otherwise
     */
    public Admin getAdminById(int adminId) {
        String sql = "SELECT admin_id, username, password, full_name, email, phone, " +
                     "created_date, last_login, is_active FROM admin WHERE admin_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Admin admin = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, adminId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFullName(resultSet.getString("full_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPhone(resultSet.getString("phone"));
                admin.setCreatedDate(resultSet.getTimestamp("created_date"));
                admin.setLastLogin(resultSet.getTimestamp("last_login"));
                admin.setActive(resultSet.getBoolean("is_active"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving admin: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return admin;
    }
    
    /**
     * Update admin's last login timestamp
     * 
     * This method updates the last_login field when an admin logs in successfully.
     * 
     * @param adminId The ID of the admin
     * @return true if update was successful, false otherwise
     */
    public boolean updateLastLogin(int adminId) {
        String sql = "UPDATE admin SET last_login = NOW() WHERE admin_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, adminId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating last login: " + e.getMessage());
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
     * Check if username already exists
     * 
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) as count FROM admin WHERE username = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error checking username: " + e.getMessage());
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
}
