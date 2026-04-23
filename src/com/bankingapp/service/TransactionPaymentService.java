package com.bankingapp.service;

import com.bankingapp.dao.TransactionDAO;
import com.bankingapp.dao.PaymentDAO;
import com.bankingapp.model.Transaction;
import com.bankingapp.model.Payment;
import java.util.List;
import java.util.UUID;

/**
 * Transaction and Payment Service Class
 * 
 * Contains business logic for transaction-related and payment-related operations.
 * Handles transaction creation, status updates, reporting, and payment processing.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class TransactionPaymentService {
    
    private TransactionDAO transactionDAO;
    private PaymentDAO paymentDAO;
    
    /**
     * Constructor initializing both DAOs
     */
    public TransactionPaymentService() {
        this.transactionDAO = new TransactionDAO();
        this.paymentDAO = new PaymentDAO();
    }
    
    // ==================== Transaction Methods ====================
    
    /**
     * Record a new transaction
     * 
     * Generates a unique reference number and records the transaction.
     * 
     * @param transaction The Transaction object to record
     * @return true if transaction was recorded successfully, false otherwise
     */
    public boolean recordTransaction(Transaction transaction) {
        if (transaction == null || transaction.getCustomerId() <= 0) {
            System.out.println("Invalid transaction data");
            return false;
        }
        
        if (transaction.getReferenceNumber() == null || transaction.getReferenceNumber().trim().isEmpty()) {
            transaction.setReferenceNumber(generateTransactionReference());
        }
        
        if (transaction.getStatus() == null || transaction.getStatus().trim().isEmpty()) {
            transaction.setStatus("COMPLETED");
        }
        
        return transactionDAO.addTransaction(transaction);
    }
    
    public Transaction getTransactionById(int transactionId) {
        if (transactionId <= 0) {
            return null;
        }
        return transactionDAO.getTransactionById(transactionId);
    }
    
    public List<Transaction> getCustomerTransactions(int customerId) {
        if (customerId <= 0) {
            return null;
        }
        return transactionDAO.getTransactionsByCustomerId(customerId);
    }
    
    public List<Transaction> getTransactionsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return null;
        }
        return transactionDAO.getTransactionsByStatus(status);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
    
    public boolean updateTransactionStatus(int transactionId, String newStatus) {
        if (transactionId <= 0 || newStatus == null || newStatus.trim().isEmpty()) {
            return false;
        }
        return transactionDAO.updateTransactionStatus(transactionId, newStatus);
    }
    
    private String generateTransactionReference() {
        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "TRX" + timestamp + uuid;
    }
    
    // ==================== Payment Methods ====================
    
    public boolean processPayment(Payment payment) {
        if (payment == null || payment.getAmount() <= 0) {
            System.out.println("Invalid payment data");
            return false;
        }
        
        if (payment.getReferenceNumber() == null || payment.getReferenceNumber().trim().isEmpty()) {
            payment.setReferenceNumber(generatePaymentReference());
        }
        
        return paymentDAO.addPayment(payment);
    }
    
    public Payment getPaymentById(int paymentId) {
        if (paymentId <= 0) {
            return null;
        }
        return paymentDAO.getPaymentById(paymentId);
    }
    
    public List<Payment> getPendingPayments() {
        return paymentDAO.getPendingPayments();
    }
    
    public List<Payment> getEmployeePayments(int employeeId) {
        if (employeeId <= 0) {
            return null;
        }
        return paymentDAO.getPaymentsByEmployeeId(employeeId);
    }
    
    public boolean updatePaymentStatus(int paymentId, String newStatus) {
        if (paymentId <= 0 || newStatus == null || newStatus.trim().isEmpty()) {
            return false;
        }
        return paymentDAO.updatePaymentStatus(paymentId, newStatus);
    }
    
    private String generatePaymentReference() {
        long timestamp = System.currentTimeMillis();
        return "PAY" + timestamp;
    }
    
    /**
     * Get total count of transactions
     * 
     * @return Total number of transactions in the system
     */
    public int getTransactionCount() {
        return transactionDAO.getTransactionCount();
    }
    
    /**
     * Update an existing transaction
     * 
     * @param transaction The Transaction object with updated data
     * @return true if transaction was updated successfully, false otherwise
     */
    public boolean updateTransaction(Transaction transaction) {
        if (transaction == null || transaction.getTransactionId() <= 0) {
            System.out.println("Invalid transaction data for update");
            return false;
        }
        return transactionDAO.updateTransaction(transaction);
    }
    
    /**
     * Delete a transaction by ID
     * 
     * @param transactionId The ID of the transaction to delete
     * @return true if transaction was deleted successfully, false otherwise
     */
    public boolean deleteTransaction(int transactionId) {
        if (transactionId <= 0) {
            System.out.println("Invalid transaction ID for deletion");
            return false;
        }
        return transactionDAO.deleteTransaction(transactionId);
    }
    
    /**
     * Get total payments amount for current month
     * 
     * @return Total payment amount for the current month
     */
    public double getTotalPaymentsThisMonth() {
        return paymentDAO.getTotalPaymentsThisMonth();
    }
}
