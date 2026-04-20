package com.bankingapp.model;

import java.util.Date;

/**
 * Transaction Model Class
 * 
 * Represents a banking transaction in the application.
 * Stores transaction details, amounts, status, and tracking information.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Transaction {
    
    private int transactionId;
    private int customerId;
    private String transactionType;
    private String description;
    private double amount;
    private double balanceBefore;
    private double balanceAfter;
    private String referenceNumber;
    private String status;
    private Date transactionDate;
    private Date processedDate;
    private String notes;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Transaction() {
    }
    
    /**
     * Constructor with basic transaction information
     */
    public Transaction(int customerId, String transactionType, double amount, String status) {
        this.customerId = customerId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
    }
    
    /**
     * Full constructor
     */
    public Transaction(int transactionId, int customerId, String transactionType, 
                       String description, double amount, double balanceBefore, 
                       double balanceAfter, String referenceNumber, String status, 
                       Date transactionDate, Date processedDate, String notes) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionType = transactionType;
        this.description = description;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.referenceNumber = referenceNumber;
        this.status = status;
        this.transactionDate = transactionDate;
        this.processedDate = processedDate;
        this.notes = notes;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getBalanceBefore() {
        return balanceBefore;
    }
    
    public void setBalanceBefore(double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public Date getProcessedDate() {
        return processedDate;
    }
    
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    // ===== UTILITY METHODS =====
    
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
