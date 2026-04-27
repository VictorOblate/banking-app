package com.bankingapp.service;

import com.bankingapp.dao.AdminDAO;
import com.bankingapp.model.Admin;
import java.util.List;

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
    
    /**
     * Add a new admin user
     * 
     * @param admin The Admin object to be added
     * @return true if admin was added successfully, false otherwise
     */
    public boolean addAdmin(Admin admin) {
        // Validate input
        if (admin == null || admin.getUsername() == null || admin.getUsername().trim().isEmpty() ||
            admin.getPassword() == null || admin.getPassword().trim().isEmpty() ||
            admin.getFullName() == null || admin.getFullName().trim().isEmpty()) {
            System.out.println("Invalid admin data provided");
            return false;
        }
        
        // Check if username already exists
        if (doesUsernameExist(admin.getUsername())) {
            System.out.println("Username already exists: " + admin.getUsername());
            return false;
        }
        
        // Add the admin
        return adminDAO.addAdmin(admin);
    }
    
    /**
     * Update admin information
     * 
     * @param admin The Admin object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateAdmin(Admin admin) {
        // Validate input
        if (admin == null || admin.getAdminId() <= 0 || 
            admin.getPassword() == null || admin.getPassword().trim().isEmpty() ||
            admin.getFullName() == null || admin.getFullName().trim().isEmpty()) {
            System.out.println("Invalid admin data provided for update");
            return false;
        }
        
        return adminDAO.updateAdmin(admin);
    }
    
    /**
     * Delete/Deactivate admin user
     * 
     * @param adminId The ID of the admin to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteAdmin(int adminId) {
        if (adminId <= 0) {
            return false;
        }
        return adminDAO.deleteAdmin(adminId);
    }
    
    /**
     * Get all admin users
     * 
     * @return List of all Admin objects
     */
    public List<Admin> getAllAdmins() {
        return adminDAO.getAllAdmins();
    }
    
    /**
     * Get all active admin users
     * 
     * @return List of active Admin objects
     */
    public List<Admin> getActiveAdmins() {
        return adminDAO.getActiveAdmins();
    }
}
