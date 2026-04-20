package com.bankingapp.model;

import java.util.Date;

/**
 * Package Model Class
 * 
 * Represents a banking service package offered to customers.
 * Stores package details, type, and associated benefits.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Package {
    
    private int packageId;
    private String packageName;
    private String packageType;
    private String description;
    private String benefits;
    private double monthlyFee;
    private double annualFee;
    private Date createdDate;
    private Date modifiedDate;
    private boolean isActive;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Package() {
    }
    
    /**
     * Constructor with basic information
     */
    public Package(String packageName, String packageType, String description) {
        this.packageName = packageName;
        this.packageType = packageType;
        this.description = description;
    }
    
    /**
     * Full constructor
     */
    public Package(int packageId, String packageName, String packageType, String description,
                   String benefits, double monthlyFee, double annualFee, 
                   Date createdDate, Date modifiedDate, boolean isActive) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packageType = packageType;
        this.description = description;
        this.benefits = benefits;
        this.monthlyFee = monthlyFee;
        this.annualFee = annualFee;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getPackageId() {
        return packageId;
    }
    
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getPackageType() {
        return packageType;
    }
    
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getBenefits() {
        return benefits;
    }
    
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
    
    public double getMonthlyFee() {
        return monthlyFee;
    }
    
    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
    
    public double getAnnualFee() {
        return annualFee;
    }
    
    public void setAnnualFee(double annualFee) {
        this.annualFee = annualFee;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date getModifiedDate() {
        return modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
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
        return "Package{" +
                "packageId=" + packageId +
                ", packageName='" + packageName + '\'' +
                ", packageType='" + packageType + '\'' +
                ", monthlyFee=" + monthlyFee +
                ", isActive=" + isActive +
                '}';
    }
}
