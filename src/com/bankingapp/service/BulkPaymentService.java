package com.bankingapp.service;

import com.bankingapp.dao.BulkPaymentBatchDAO;
import com.bankingapp.dao.PaymentDAO;
import com.bankingapp.dao.EmployeeDAO;
import com.bankingapp.dao.OvertimeDAO;
import com.bankingapp.model.BulkPaymentBatch;
import com.bankingapp.model.Payment;
import com.bankingapp.model.Employee;
import com.bankingapp.model.Overtime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Bulk Payment Service Class
 * 
 * Contains business logic for bulk payment operations.
 * Handles bulk salary payments, bulk overtime payments, and batch management.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class BulkPaymentService {
    
    private BulkPaymentBatchDAO batchDAO;
    private PaymentDAO paymentDAO;
    private EmployeeDAO employeeDAO;
    private OvertimeDAO overtimeDAO;
    
    /**
     * Constructor initializing DAOs
     */
    public BulkPaymentService() {
        this.batchDAO = new BulkPaymentBatchDAO();
        this.paymentDAO = new PaymentDAO();
        this.employeeDAO = new EmployeeDAO();
        this.overtimeDAO = new OvertimeDAO();
    }
    
    /**
     * Create a bulk salary payment batch
     * 
     * @param batchName The name of the batch
     * @param paymentDate The payment date (YYYY-MM-DD)
     * @param createdBy Admin ID who created the batch
     * @return The batch ID if successful, -1 otherwise
     */
    public int createBulkSalaryBatch(String batchName, String paymentDate, int createdBy) {
        BulkPaymentBatch batch = new BulkPaymentBatch();
        batch.setBatchType("SALARY");
        batch.setBatchName(batchName);
        batch.setPaymentDate(paymentDate);
        batch.setCreatedBy(createdBy);
        batch.setBatchStatus("DRAFT");
        batch.setDescription("Bulk salary payment batch created on " + new Date());
        
        return batchDAO.createBatch(batch);
    }
    
    /**
     * Create a bulk overtime payment batch
     * 
     * @param batchName The name of the batch
     * @param paymentDate The payment date (YYYY-MM-DD)
     * @param createdBy Admin ID who created the batch
     * @return The batch ID if successful, -1 otherwise
     */
    public int createBulkOvertimeBatch(String batchName, String paymentDate, int createdBy) {
        BulkPaymentBatch batch = new BulkPaymentBatch();
        batch.setBatchType("OVERTIME");
        batch.setBatchName(batchName);
        batch.setPaymentDate(paymentDate);
        batch.setCreatedBy(createdBy);
        batch.setBatchStatus("DRAFT");
        batch.setDescription("Bulk overtime payment batch created on " + new Date());
        
        return batchDAO.createBatch(batch);
    }
    
    /**
     * Process bulk salary payments for all active employees
     * 
     * @param batchId The batch ID
     * @param paymentDate The payment date
     * @return Number of payments processed
     */
    public int processBulkSalaryPayments(int batchId, String paymentDate) {
        BulkPaymentBatch batch = batchDAO.getBatchById(batchId);
        if (batch == null) {
            System.out.println("Batch not found");
            return 0;
        }
        
        // Get all active employees
        List<Employee> employees = employeeDAO.getAllEmployees();
        int processedCount = 0;
        double totalAmount = 0;
        
        for (Employee employee : employees) {
            if ("ACTIVE".equals(employee.getEmploymentStatus()) && employee.getBasicSalary() > 0) {
                Payment payment = new Payment();
                payment.setEmployeeId(employee.getEmployeeId());
                payment.setPaymentType("SALARY");
                payment.setPaymentDescription("Monthly Salary Payment");
                payment.setAmount(employee.getBasicSalary());
                payment.setPaymentMethod("BANK_TRANSFER");
                payment.setPaymentDate(paymentDate);
                payment.setPaymentStatus("PENDING");
                payment.setReferenceNumber(generateReferenceNumber());
                payment.setRemarks("Salary payment via batch ID: " + batchId);
                
                if (paymentDAO.addPayment(payment)) {
                    processedCount++;
                    totalAmount += employee.getBasicSalary();
                }
            }
        }
        
        // Update batch details
        batch.setTotalRecords(processedCount);
        batch.setTotalAmount(totalAmount);
        batch.setBatchStatus("SUBMITTED");
        batchDAO.updateBatch(batch);
        
        System.out.println("Processed " + processedCount + " salary payments totaling: " + totalAmount);
        return processedCount;
    }
    
    /**
     * Process bulk overtime payments from approved overtime records
     * 
     * @param batchId The batch ID
     * @param paymentDate The payment date
     * @return Number of payments processed
     */
    public int processBulkOvertimePayments(int batchId, String paymentDate) {
        BulkPaymentBatch batch = batchDAO.getBatchById(batchId);
        if (batch == null) {
            System.out.println("Batch not found");
            return 0;
        }
        
        // Get all approved overtime records
        List<Overtime> overtimes = overtimeDAO.getApprovedOvertimeRecords();
        int processedCount = 0;
        double totalAmount = 0;
        
        for (Overtime overtime : overtimes) {
            Payment payment = new Payment();
            payment.setEmployeeId(overtime.getEmployeeId());
            payment.setPaymentType("OVERTIME");
            payment.setPaymentDescription("Overtime Payment - " + overtime.getOvertimeDate());
            payment.setAmount(overtime.getOvertimeAmount());
            payment.setPaymentMethod("BANK_TRANSFER");
            payment.setPaymentDate(paymentDate);
            payment.setPaymentStatus("PENDING");
            payment.setReferenceNumber(generateReferenceNumber());
            payment.setRemarks("Overtime " + overtime.getHoursWorked() + " hours @ " + 
                             overtime.getHourlyRate() + " per hour. Batch ID: " + batchId);
            
            if (paymentDAO.addPayment(payment)) {
                // Mark overtime as processed
                overtimeDAO.updateOvertimeStatus(overtime.getOvertimeId(), "PROCESSED");
                processedCount++;
                totalAmount += overtime.getOvertimeAmount();
            }
        }
        
        // Update batch details
        batch.setTotalRecords(processedCount);
        batch.setTotalAmount(totalAmount);
        batch.setBatchStatus("SUBMITTED");
        batchDAO.updateBatch(batch);
        
        System.out.println("Processed " + processedCount + " overtime payments totaling: " + totalAmount);
        return processedCount;
    }
    
    /**
     * Approve a payment batch for processing
     * 
     * @param batchId The ID of the batch to approve
     * @return true if approval was successful, false otherwise
     */
    public boolean approveBatch(int batchId) {
        return batchDAO.updateBatchStatus(batchId, "APPROVED");
    }
    
    /**
     * Reject a payment batch
     * 
     * @param batchId The ID of the batch to reject
     * @return true if rejection was successful, false otherwise
     */
    public boolean rejectBatch(int batchId) {
        return batchDAO.updateBatchStatus(batchId, "REJECTED");
    }
    
    /**
     * Mark batch as processed
     * 
     * @param batchId The ID of the batch to process
     * @return true if processing was successful, false otherwise
     */
    public boolean processBatch(int batchId) {
        return batchDAO.updateBatchStatus(batchId, "PROCESSED");
    }
    
    /**
     * Get batch by ID
     * 
     * @param batchId The ID of the batch
     * @return BulkPaymentBatch object if found, null otherwise
     */
    public BulkPaymentBatch getBatchById(int batchId) {
        return batchDAO.getBatchById(batchId);
    }
    
    /**
     * Get all batches
     * 
     * @return List of all batches
     */
    public List<BulkPaymentBatch> getAllBatches() {
        return batchDAO.getAllBatches();
    }
    
    /**
     * Get batches by type
     * 
     * @param batchType The type of batch
     * @return List of batches of the specified type
     */
    public List<BulkPaymentBatch> getBatchesByType(String batchType) {
        return batchDAO.getBatchesByType(batchType);
    }
    
    /**
     * Get batches by status
     * 
     * @param status The status of batches
     * @return List of batches with the specified status
     */
    public List<BulkPaymentBatch> getBatchesByStatus(String status) {
        return batchDAO.getBatchesByStatus(status);
    }
    
    /**
     * Cancel a batch (only if in DRAFT status)
     * 
     * @param batchId The ID of the batch to cancel
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelBatch(int batchId) {
        BulkPaymentBatch batch = batchDAO.getBatchById(batchId);
        if (batch != null && "DRAFT".equals(batch.getBatchStatus())) {
            return batchDAO.deleteBatch(batchId);
        }
        return false;
    }
    
    /**
     * Generate a unique reference number for payments
     * 
     * @return A unique reference number
     */
    private String generateReferenceNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
