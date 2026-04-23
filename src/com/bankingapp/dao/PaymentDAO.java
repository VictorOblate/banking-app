package com.bankingapp.dao;

import com.bankingapp.model.Payment;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Payment Data Access Object (DAO)
 * 
 * Handles all database operations related to Payments.
 * Includes CRUD operations for payment management and salary processing.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class PaymentDAO {
    
    /**
     * Add a new payment to the database
     * 
     * @param payment The Payment object to add
     * @return true if payment was added successfully, false otherwise
     */
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payments (employee_id, customer_id, payment_type, " +
                     "payment_description, amount, payment_method, payment_date, " +
                     "payment_status, reference_number, remarks) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            if (payment.getEmployeeId() > 0) {
                preparedStatement.setInt(1, payment.getEmployeeId());
            } else {
                preparedStatement.setNull(1, java.sql.Types.INTEGER);
            }
            
            if (payment.getCustomerId() > 0) {
                preparedStatement.setInt(2, payment.getCustomerId());
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }
            
            preparedStatement.setString(3, payment.getPaymentType());
            preparedStatement.setString(4, payment.getPaymentDescription());
            preparedStatement.setDouble(5, payment.getAmount());
            preparedStatement.setString(6, payment.getPaymentMethod());
            preparedStatement.setString(7, payment.getPaymentDate());
            preparedStatement.setString(8, payment.getPaymentStatus() != null ? payment.getPaymentStatus() : "PENDING");
            preparedStatement.setString(9, payment.getReferenceNumber());
            preparedStatement.setString(10, payment.getRemarks());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Payment added successfully: " + payment.getReferenceNumber());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding payment: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get payment by payment ID
     * 
     * @param paymentId The ID of the payment to retrieve
     * @return Payment object if found, null otherwise
     */
    public Payment getPaymentById(int paymentId) {
        String sql = "SELECT payment_id, employee_id, customer_id, payment_type, " +
                     "payment_description, amount, payment_method, payment_date, " +
                     "payment_status, reference_number, remarks, created_date, processed_date " +
                     "FROM payments WHERE payment_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Payment payment = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, paymentId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                payment = mapResultSetToPayment(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving payment: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return payment;
    }
    
    /**
     * Get all payments for a specific employee
     * 
     * @param employeeId The ID of the employee
     * @return List of all payments for the employee
     */
    public List<Payment> getPaymentsByEmployeeId(int employeeId) {
        String sql = "SELECT payment_id, employee_id, customer_id, payment_type, " +
                     "payment_description, amount, payment_method, payment_date, " +
                     "payment_status, reference_number, remarks, created_date, processed_date " +
                     "FROM payments WHERE employee_id = ? ORDER BY payment_date DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Payment payment = mapResultSetToPayment(resultSet);
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return payments;
    }
    
    /**
     * Get all pending payments
     * 
     * @return List of all pending payments
     */
    public List<Payment> getPendingPayments() {
        String sql = "SELECT payment_id, employee_id, customer_id, payment_type, " +
                     "payment_description, amount, payment_method, payment_date, " +
                     "payment_status, reference_number, remarks, created_date, processed_date " +
                     "FROM payments WHERE payment_status = 'PENDING' ORDER BY payment_date ASC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Payment payment = mapResultSetToPayment(resultSet);
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving pending payments: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return payments;
    }
    
    /**
     * Update payment status
     * 
     * @param paymentId The ID of the payment
     * @param newStatus The new status value
     * @return true if update was successful, false otherwise
     */
    public boolean updatePaymentStatus(int paymentId, String newStatus) {
        String sql = "UPDATE payments SET payment_status = ?, processed_date = NOW() WHERE payment_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, paymentId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Payment status updated: " + paymentId + " -> " + newStatus);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get all payments (limit 500 for performance)
     * 
     * @return List of all payments
     */
    public List<Payment> getAllPayments() {
        String sql = "SELECT payment_id, employee_id, customer_id, payment_type, " +
                     "payment_description, amount, payment_method, payment_date, " +
                     "payment_status, reference_number, remarks, created_date, processed_date " +
                     "FROM payments ORDER BY payment_date DESC LIMIT 500";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Payment payment = mapResultSetToPayment(resultSet);
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return payments;
    }
    
    /**
     * Helper method to map ResultSet to Payment object
     * 
     * @param resultSet The ResultSet to map
     * @return Payment object populated from ResultSet
     * @throws SQLException if ResultSet access fails
     */
    private Payment mapResultSetToPayment(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(resultSet.getInt("payment_id"));
        payment.setEmployeeId(resultSet.getInt("employee_id"));
        payment.setCustomerId(resultSet.getInt("customer_id"));
        payment.setPaymentType(resultSet.getString("payment_type"));
        payment.setPaymentDescription(resultSet.getString("payment_description"));
        payment.setAmount(resultSet.getDouble("amount"));
        payment.setPaymentMethod(resultSet.getString("payment_method"));
        payment.setPaymentDate(resultSet.getString("payment_date"));
        payment.setPaymentStatus(resultSet.getString("payment_status"));
        payment.setReferenceNumber(resultSet.getString("reference_number"));
        payment.setRemarks(resultSet.getString("remarks"));
        payment.setCreatedDate(resultSet.getTimestamp("created_date"));
        payment.setProcessedDate(resultSet.getTimestamp("processed_date"));
        return payment;
    }
    
    /**
     * Get total payments amount for current month
     * 
     * @return Total payment amount for the current month
     */
    public double getTotalPaymentsThisMonth() {
        String sql = "SELECT SUM(amount) as total FROM payments WHERE MONTH(payment_date) = MONTH(CURDATE()) AND YEAR(payment_date) = YEAR(CURDATE())";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error getting total payments: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return 0.0;
    }
    
    /**
     * Update an existing payment
     * 
     * @param payment The Payment object with updated data
     * @return true if payment was updated successfully, false otherwise
     */
    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET employee_id = ?, customer_id = ?, payment_type = ?, " +
                     "payment_description = ?, amount = ?, payment_method = ?, payment_date = ?, " +
                     "payment_status = ?, reference_number = ?, remarks = ?, processed_date = ? " +
                     "WHERE payment_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, payment.getEmployeeId());
            preparedStatement.setInt(2, payment.getCustomerId());
            preparedStatement.setString(3, payment.getPaymentType());
            preparedStatement.setString(4, payment.getPaymentDescription());
            preparedStatement.setDouble(5, payment.getAmount());
            preparedStatement.setString(6, payment.getPaymentMethod());
            preparedStatement.setDate(7, payment.getPaymentDate() != null ? 
                new java.sql.Date(payment.getPaymentDate().getTime()) : null);
            preparedStatement.setString(8, payment.getStatus());
            preparedStatement.setString(9, payment.getReferenceNumber());
            preparedStatement.setString(10, payment.getRemarks());
            preparedStatement.setTimestamp(11, payment.getProcessedDate() != null ? 
                new java.sql.Timestamp(payment.getProcessedDate().getTime()) : null);
            preparedStatement.setInt(12, payment.getPaymentId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Payment updated successfully: " + payment.getPaymentId());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Delete a payment by ID
     * 
     * @param paymentId The ID of the payment to delete
     * @return true if payment was deleted successfully, false otherwise
     */
    public boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, paymentId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Payment deleted successfully: " + paymentId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
