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
    
    /**
     * Add a new admin user to the database
     * 
     * @param admin The Admin object containing admin data
     * @return true if admin was added successfully, false otherwise
     */
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admin (username, password, full_name, email, phone, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getFullName());
            preparedStatement.setString(4, admin.getEmail());
            preparedStatement.setString(5, admin.getPhone());
            preparedStatement.setBoolean(6, admin.isActive());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Admin added successfully: " + admin.getUsername());
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
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
     * Update admin information
     * 
     * @param admin The Admin object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admin SET full_name = ?, email = ?, phone = ?, password = ?, is_active = ? " +
                     "WHERE admin_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, admin.getFullName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPhone());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setBoolean(5, admin.isActive());
            preparedStatement.setInt(6, admin.getAdminId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Admin updated successfully: " + admin.getUsername());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating admin: " + e.getMessage());
            e.printStackTrace();
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
     * Delete an admin user (hard delete)
     * 
     * This method permanently deletes an admin record from the database.
     * 
     * @param adminId The ID of the admin to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM admin WHERE admin_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, adminId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Admin deleted successfully: " + adminId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
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
     * Get all admin users (active and inactive)
     * 
     * @return List of all Admin objects
     */
    public java.util.List<Admin> getAllAdmins() {
        String sql = "SELECT admin_id, username, password, full_name, email, phone, " +
                     "created_date, last_login, is_active FROM admin ORDER BY username ASC";
        
        java.util.List<Admin> admins = new java.util.ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFullName(resultSet.getString("full_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPhone(resultSet.getString("phone"));
                admin.setCreatedDate(resultSet.getTimestamp("created_date"));
                admin.setLastLogin(resultSet.getTimestamp("last_login"));
                admin.setActive(resultSet.getBoolean("is_active"));
                
                admins.add(admin);
            }
            
            System.out.println("Retrieved " + admins.size() + " admins from database");
            return admins;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving all admins: " + e.getMessage());
            return admins;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get all active admin users only
     * 
     * @return List of active Admin objects
     */
    public java.util.List<Admin> getActiveAdmins() {
        String sql = "SELECT admin_id, username, password, full_name, email, phone, " +
                     "created_date, last_login, is_active FROM admin WHERE is_active = TRUE ORDER BY username ASC";
        
        java.util.List<Admin> admins = new java.util.ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFullName(resultSet.getString("full_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPhone(resultSet.getString("phone"));
                admin.setCreatedDate(resultSet.getTimestamp("created_date"));
                admin.setLastLogin(resultSet.getTimestamp("last_login"));
                admin.setActive(resultSet.getBoolean("is_active"));
                
                admins.add(admin);
            }
            
            return admins;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving active admins: " + e.getMessage());
            return admins;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
