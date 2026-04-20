package com.bankingapp.model;

import java.util.Date;

/**
 * Payment Model Class
 * 
 * Represents a payment transaction in the banking application.
 * Stores payment details, amounts, status, and tracking information.
 * Supports both salary payments for employees and other payment types.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Payment {
    
    private int paymentId;
    private int employeeId;
    private int customerId;
    private String paymentType;
    private String paymentDescription;
    private double amount;
    private String paymentMethod;
    private String paymentDate;
    private String paymentStatus;
    private String referenceNumber;
    private String remarks;
    private Date createdDate;
    private Date processedDate;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Payment() {
    }
    
    /**
     * Constructor with basic payment information
     */
    public Payment(String paymentType, double amount, String paymentMethod, 
                   String paymentDate, String paymentStatus) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }
    
    /**
     * Full constructor
     */
    public Payment(int paymentId, int employeeId, int customerId, String paymentType, 
                   String paymentDescription, double amount, String paymentMethod, 
                   String paymentDate, String paymentStatus, String referenceNumber, 
                   String remarks, Date createdDate, Date processedDate) {
        this.paymentId = paymentId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.paymentType = paymentType;
        this.paymentDescription = paymentDescription;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.referenceNumber = referenceNumber;
        this.remarks = remarks;
        this.createdDate = createdDate;
        this.processedDate = processedDate;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getPaymentDescription() {
        return paymentDescription;
    }
    
    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date getProcessedDate() {
        return processedDate;
    }
    
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
    
    // ===== UTILITY METHODS =====
    
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentType='" + paymentType + '\'' +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
