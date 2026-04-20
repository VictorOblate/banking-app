package com.bankingapp.model;

import java.util.Date;

/**
 * Admin Model Class
 * 
 * Represents an administrator user in the banking application.
 * Stores admin credentials and profile information.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Admin {
    
    private int adminId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Date createdDate;
    private Date lastLogin;
    private boolean isActive;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Admin() {
    }
    
    /**
     * Constructor with credentials
     */
    public Admin(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
    
    /**
     * Full constructor
     */
    public Admin(int adminId, String username, String password, String fullName, 
                 String email, String phone, Date createdDate, Date lastLogin, boolean isActive) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getAdminId() {
        return adminId;
    }
    
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    // ===== UTILITY METHODS =====
    
    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
