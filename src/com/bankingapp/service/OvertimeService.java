package com.bankingapp.service;

import com.bankingapp.dao.OvertimeDAO;
import com.bankingapp.dao.EmployeeDAO;
import com.bankingapp.model.Overtime;
import com.bankingapp.model.Employee;
import java.util.List;

/**
 * Overtime Service Class
 * 
 * Contains business logic for overtime-related operations.
 * Handles overtime CRUD operations, approval, and payment processing.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class OvertimeService {
    
    private OvertimeDAO overtimeDAO;
    private EmployeeDAO employeeDAO;
    
    /**
     * Constructor initializing DAOs
     */
    public OvertimeService() {
        this.overtimeDAO = new OvertimeDAO();
        this.employeeDAO = new EmployeeDAO();
    }
    
    /**
     * Add a new overtime record
     * 
     * @param overtime The Overtime object to add
     * @return true if overtime was added successfully, false otherwise
     */
    public boolean addOvertimeRecord(Overtime overtime) {
        if (!isValidOvertime(overtime)) {
            System.out.println("Overtime validation failed");
            return false;
        }
        
        // Set default hourly rate if not provided
        if (overtime.getHourlyRate() <= 0) {
            Employee employee = employeeDAO.getEmployeeById(overtime.getEmployeeId());
            if (employee != null) {
                // Calculate hourly rate from basic salary (assuming 40 hours per week, 4 weeks per month)
                double monthlyRate = employee.getBasicSalary();
                overtime.setHourlyRate(monthlyRate / 160);  // 160 hours per month (40 * 4)
            }
        }
        
        // Calculate overtime amount
        double overtimeAmount = overtime.getHourlyRate() * overtime.getHoursWorked();
        overtime.setOvertimeAmount(overtimeAmount);
        
        // Set default status
        if (overtime.getStatus() == null || overtime.getStatus().isEmpty()) {
            overtime.setStatus("PENDING");
        }
        
        return overtimeDAO.addOvertime(overtime);
    }
    
    /**
     * Get overtime record by ID
     * 
     * @param overtimeId The ID of the overtime
     * @return Overtime object if found, null otherwise
     */
    public Overtime getOvertimeById(int overtimeId) {
        if (overtimeId <= 0) {
            return null;
        }
        return overtimeDAO.getOvertimeById(overtimeId);
    }
    
    /**
     * Get all overtime records for an employee
     * 
     * @param employeeId The ID of the employee
     * @return List of overtime records
     */
    public List<Overtime> getEmployeeOvertimeRecords(int employeeId) {
        if (employeeId <= 0) {
            return new java.util.ArrayList<>();
        }
        return overtimeDAO.getOvertimeByEmployeeId(employeeId);
    }
    
    /**
     * Get all overtime records
     * 
     * @return List of all overtime records
     */
    public List<Overtime> getAllOvertimeRecords() {
        return overtimeDAO.getAllOvertimeRecords();
    }
    
    /**
     * Get all pending overtime records
     * 
     * @return List of pending overtime records
     */
    public List<Overtime> getPendingOvertimeRecords() {
        return overtimeDAO.getPendingOvertimeRecords();
    }
    
    /**
     * Get all approved overtime records (ready for payment)
     * 
     * @return List of approved overtime records
     */
    public List<Overtime> getApprovedOvertimeRecords() {
        return overtimeDAO.getApprovedOvertimeRecords();
    }
    
    /**
     * Approve an overtime record
     * 
     * @param overtimeId The ID of the overtime to approve
     * @return true if operation was successful, false otherwise
     */
    public boolean approveOvertime(int overtimeId) {
        return overtimeDAO.updateOvertimeStatus(overtimeId, "APPROVED");
    }
    
    /**
     * Reject an overtime record
     * 
     * @param overtimeId The ID of the overtime to reject
     * @return true if operation was successful, false otherwise
     */
    public boolean rejectOvertime(int overtimeId) {
        return overtimeDAO.updateOvertimeStatus(overtimeId, "REJECTED");
    }
    
    /**
     * Process/Pay an overtime record
     * 
     * @param overtimeId The ID of the overtime to process
     * @return true if operation was successful, false otherwise
     */
    public boolean processOvertime(int overtimeId) {
        return overtimeDAO.updateOvertimeStatus(overtimeId, "PROCESSED");
    }
    
    /**
     * Get total overtime amount for a date range
     * 
     * @param startDate Start date (YYYY-MM-DD)
     * @param endDate End date (YYYY-MM-DD)
     * @return Total overtime amount
     */
    public double getTotalOvertimeAmount(String startDate, String endDate) {
        return overtimeDAO.getTotalOvertimeAmount(startDate, endDate, "APPROVED");
    }
    
    /**
     * Get total overtime amount for a date range with specific status
     * 
     * @param startDate Start date (YYYY-MM-DD)
     * @param endDate End date (YYYY-MM-DD)
     * @param status Status filter
     * @return Total overtime amount
     */
    public double getTotalOvertimeAmount(String startDate, String endDate, String status) {
        return overtimeDAO.getTotalOvertimeAmount(startDate, endDate, status);
    }
    
    /**
     * Delete overtime record
     * 
     * @param overtimeId The ID of the overtime to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteOvertime(int overtimeId) {
        return overtimeDAO.deleteOvertime(overtimeId);
    }
    
    /**
     * Validate overtime record
     * 
     * @param overtime The overtime to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidOvertime(Overtime overtime) {
        if (overtime == null) return false;
        if (overtime.getEmployeeId() <= 0) return false;
        if (overtime.getOvertimeDate() == null || overtime.getOvertimeDate().isEmpty()) return false;
        if (overtime.getHoursWorked() <= 0) return false;
        
        return true;
    }
}
