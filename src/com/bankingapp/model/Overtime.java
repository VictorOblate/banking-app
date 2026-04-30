package com.bankingapp.model;

import java.util.Date;

/**
 * Overtime Model Class
 * 
 * Represents an employee's overtime record in the banking application.
 * Stores overtime date, hours, rates, and approval/processing status.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Overtime {
    
    private int overtimeId;
    private int employeeId;
    private String employeeName;
    private String overtimeDate;
    private double hoursWorked;
    private double hourlyRate;
    private double overtimeAmount;
    private String overtimeType;
    private String remarks;
    private String status;
    private Date createdDate;
    private Date approvedDate;
    private Date processDate;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Overtime() {
    }
    
    /**
     * Constructor with basic information
     */
    public Overtime(int employeeId, String overtimeDate, double hoursWorked, 
                    double hourlyRate, String overtimeType) {
        this.employeeId = employeeId;
        this.overtimeDate = overtimeDate;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.overtimeType = overtimeType;
        this.status = "PENDING";
    }
    
    /**
     * Full constructor
     */
    public Overtime(int overtimeId, int employeeId, String overtimeDate, double hoursWorked,
                    double hourlyRate, double overtimeAmount, String overtimeType, String remarks,
                    String status, Date createdDate, Date approvedDate, Date processDate) {
        this.overtimeId = overtimeId;
        this.employeeId = employeeId;
        this.overtimeDate = overtimeDate;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.overtimeAmount = overtimeAmount;
        this.overtimeType = overtimeType;
        this.remarks = remarks;
        this.status = status;
        this.createdDate = createdDate;
        this.approvedDate = approvedDate;
        this.processDate = processDate;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getOvertimeId() {
        return overtimeId;
    }
    
    public void setOvertimeId(int overtimeId) {
        this.overtimeId = overtimeId;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getOvertimeDate() {
        return overtimeDate;
    }
    
    public void setOvertimeDate(String overtimeDate) {
        this.overtimeDate = overtimeDate;
    }
    
    public double getHoursWorked() {
        return hoursWorked;
    }
    
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
        calculateOvertimeAmount();
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
    
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
        calculateOvertimeAmount();
    }
    
    public double getOvertimeAmount() {
        return overtimeAmount;
    }
    
    public void setOvertimeAmount(double overtimeAmount) {
        this.overtimeAmount = overtimeAmount;
    }
    
    private void calculateOvertimeAmount() {
        if (this.hourlyRate > 0 && this.hoursWorked > 0) {
            this.overtimeAmount = this.hourlyRate * this.hoursWorked;
        }
    }
    
    public String getOvertimeType() {
        return overtimeType;
    }
    
    public void setOvertimeType(String overtimeType) {
        this.overtimeType = overtimeType;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date getApprovedDate() {
        return approvedDate;
    }
    
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
    
    public Date getProcessDate() {
        return processDate;
    }
    
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * Get employee details summary
     */
    @Override
    public String toString() {
        return "Overtime{" +
                "overtimeId=" + overtimeId +
                ", employeeId=" + employeeId +
                ", overtimeDate='" + overtimeDate + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", hourlyRate=" + hourlyRate +
                ", overtimeAmount=" + overtimeAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
