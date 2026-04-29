package com.bankingapp.model;

import java.util.Date;

/**
 * Bulk Payment Batch Model Class
 * 
 * Represents a batch of payments (salary or overtime) in the banking application.
 * Tracks batch creation, submission, approval, and processing status.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class BulkPaymentBatch {
    
    private int batchId;
    private String batchType;      // SALARY, OVERTIME, or OTHER
    private String batchName;
    private String description;
    private double totalAmount;
    private int totalRecords;
    private String paymentDate;
    private String batchStatus;   // DRAFT, SUBMITTED, APPROVED, PROCESSED, REJECTED
    private int createdBy;
    private Date createdDate;
    private Date processedDate;
    private String remarks;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public BulkPaymentBatch() {
    }
    
    /**
     * Constructor with basic information
     */
    public BulkPaymentBatch(String batchType, String batchName, String paymentDate, int createdBy) {
        this.batchType = batchType;
        this.batchName = batchName;
        this.paymentDate = paymentDate;
        this.createdBy = createdBy;
        this.batchStatus = "DRAFT";
    }
    
    /**
     * Full constructor
     */
    public BulkPaymentBatch(int batchId, String batchType, String batchName, String description,
                            double totalAmount, int totalRecords, String paymentDate, 
                            String batchStatus, int createdBy, Date createdDate,
                            Date processedDate, String remarks) {
        this.batchId = batchId;
        this.batchType = batchType;
        this.batchName = batchName;
        this.description = description;
        this.totalAmount = totalAmount;
        this.totalRecords = totalRecords;
        this.paymentDate = paymentDate;
        this.batchStatus = batchStatus;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.processedDate = processedDate;
        this.remarks = remarks;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getBatchId() {
        return batchId;
    }
    
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }
    
    public String getBatchType() {
        return batchType;
    }
    
    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }
    
    public String getBatchName() {
        return batchName;
    }
    
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public int getTotalRecords() {
        return totalRecords;
    }
    
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
    
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getBatchStatus() {
        return batchStatus;
    }
    
    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * Get batch details summary
     */
    @Override
    public String toString() {
        return "BulkPaymentBatch{" +
                "batchId=" + batchId +
                ", batchType='" + batchType + '\'' +
                ", batchName='" + batchName + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalRecords=" + totalRecords +
                ", batchStatus='" + batchStatus + '\'' +
                '}';
    }
}
