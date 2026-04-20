package com.bankingapp.model;

import java.util.Date;

/**
 * Shift Model Class
 * 
 * Represents a work shift in the banking application.
 * Stores shift timing and related information for employees.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Shift {
    
    private int shiftId;
    private String shiftName;
    private String startTime;
    private String endTime;
    private String shiftType;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private boolean isActive;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Shift() {
    }
    
    /**
     * Constructor with basic information
     */
    public Shift(String shiftName, String startTime, String endTime, String shiftType) {
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
    }
    
    /**
     * Full constructor
     */
    public Shift(int shiftId, String shiftName, String startTime, String endTime,
                 String shiftType, String description, Date createdDate, 
                 Date modifiedDate, boolean isActive) {
        this.shiftId = shiftId;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getShiftId() {
        return shiftId;
    }
    
    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }
    
    public String getShiftName() {
        return shiftName;
    }
    
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getShiftType() {
        return shiftType;
    }
    
    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
        return "Shift{" +
                "shiftId=" + shiftId +
                ", shiftName='" + shiftName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", shiftType='" + shiftType + '\'' +
                '}';
    }
}
