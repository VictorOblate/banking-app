package com.bankingapp.service;

import com.bankingapp.dao.AdminDAO;
import com.bankingapp.model.Admin;

/**
 * Admin Service Class
 * 
 * Contains business logic for admin-related operations.
 * Handles authentication and admin management.
 * Acts as intermediary between controllers and DAO layer.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class AdminService {
    
    private AdminDAO adminDAO;
    
    /**
     * Constructor initializing the AdminDAO
     */
    public AdminService() {
        this.adminDAO = new AdminDAO();
    }
    
    /**
     * Authenticate an admin user
     * 
     * This method validates admin credentials and returns the admin object 
     * if authentication is successful. Also updates the last login time.
     * 
     * @param username The admin username
     * @param password The admin password
     * @return Admin object if authentication successful, null otherwise
     */
    public Admin authenticateAdmin(String username, String password) {
        // Validate inputs
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            System.out.println("Invalid credentials provided for authentication");
            return null;
        }
        
        // Authenticate using DAO
        Admin admin = adminDAO.authenticate(username, password);
        
        // Update last login if authentication successful
        if (admin != null) {
            adminDAO.updateLastLogin(admin.getAdminId());
            System.out.println("Admin authenticated successfully: " + username);
        } else {
            System.out.println("Authentication failed for user: " + username);
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
        if (adminId <= 0) {
            return null;
        }
        return adminDAO.getAdminById(adminId);
    }
    
    /**
     * Check if username already exists
     * 
     * This is used for validation when creating new admin accounts.
     * 
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    public boolean doesUsernameExist(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return adminDAO.usernameExists(username);
    }
}
