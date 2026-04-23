package com.bankingapp.dao;

import com.bankingapp.model.Transaction;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction Data Access Object (DAO)
 * 
 * Handles all database operations related to Transactions.
 * Includes CRUD operations and advanced queries for transaction management and reporting.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class TransactionDAO {
    
    /**
     * Add a new transaction to the database
     * 
     * @param transaction The Transaction object to add
     * @return true if transaction was added successfully, false otherwise
     */
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (customer_id, transaction_type, description, " +
                     "amount, balance_before, balance_after, reference_number, status, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, transaction.getCustomerId());
            preparedStatement.setString(2, transaction.getTransactionType());
            preparedStatement.setString(3, transaction.getDescription());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setDouble(5, transaction.getBalanceBefore());
            preparedStatement.setDouble(6, transaction.getBalanceAfter());
            preparedStatement.setString(7, transaction.getReferenceNumber());
            preparedStatement.setString(8, transaction.getStatus() != null ? transaction.getStatus() : "PENDING");
            preparedStatement.setString(9, transaction.getNotes());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Transaction added successfully: " + transaction.getReferenceNumber());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
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
     * Get transaction by transaction ID
     * 
     * @param transactionId The ID of the transaction to retrieve
     * @return Transaction object if found, null otherwise
     */
    public Transaction getTransactionById(int transactionId) {
        String sql = "SELECT transaction_id, customer_id, transaction_type, description, " +
                     "amount, balance_before, balance_after, reference_number, status, " +
                     "transaction_date, processed_date, notes FROM transactions WHERE transaction_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Transaction transaction = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transactionId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                transaction = mapResultSetToTransaction(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving transaction: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return transaction;
    }
    
    /**
     * Get all transactions for a specific customer
     * 
     * @param customerId The ID of the customer
     * @return List of all transactions for the customer
     */
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        String sql = "SELECT transaction_id, customer_id, transaction_type, description, " +
                     "amount, balance_before, balance_after, reference_number, status, " +
                     "transaction_date, processed_date, notes FROM transactions " +
                     "WHERE customer_id = ? ORDER BY transaction_date DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Transaction transaction = mapResultSetToTransaction(resultSet);
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return transactions;
    }
    
    /**
     * Get all transactions with a specific status
     * 
     * @param status The transaction status to filter by
     * @return List of all transactions with the specified status
     */
    public List<Transaction> getTransactionsByStatus(String status) {
        String sql = "SELECT transaction_id, customer_id, transaction_type, description, " +
                     "amount, balance_before, balance_after, reference_number, status, " +
                     "transaction_date, processed_date, notes FROM transactions " +
                     "WHERE status = ? ORDER BY transaction_date DESC LIMIT 100";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Transaction transaction = mapResultSetToTransaction(resultSet);
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return transactions;
    }
    
    /**
     * Update transaction status
     * 
     * @param transactionId The ID of the transaction
     * @param newStatus The new status value
     * @return true if update was successful, false otherwise
     */
    public boolean updateTransactionStatus(int transactionId, String newStatus) {
        String sql = "UPDATE transactions SET status = ?, processed_date = NOW() WHERE transaction_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, transactionId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Transaction status updated: " + transactionId + " -> " + newStatus);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating transaction: " + e.getMessage());
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
     * Get all transactions (limit 500 for performance)
     * 
     * @return List of all transactions
     */
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT transaction_id, customer_id, transaction_type, description, " +
                     "amount, balance_before, balance_after, reference_number, status, " +
                     "transaction_date, processed_date, notes FROM transactions " +
                     "ORDER BY transaction_date DESC LIMIT 500";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Transaction transaction = mapResultSetToTransaction(resultSet);
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return transactions;
    }
    
    /**
     * Get total count of transactions
     * 
     * @return Total number of transactions in the system
     */
    public int getTransactionCount() {
        String sql = "SELECT COUNT(*) as count FROM transactions";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error getting transaction count: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return 0;
    }
    
    /**
     * Update an existing transaction
     * 
     * @param transaction The Transaction object with updated data
     * @return true if transaction was updated successfully, false otherwise
     */
    public boolean updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET customer_id = ?, transaction_type = ?, description = ?, " +
                     "amount = ?, balance_before = ?, balance_after = ?, reference_number = ?, " +
                     "status = ?, processed_date = ?, notes = ? WHERE transaction_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, transaction.getCustomerId());
            preparedStatement.setString(2, transaction.getTransactionType());
            preparedStatement.setString(3, transaction.getDescription());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setDouble(5, transaction.getBalanceBefore());
            preparedStatement.setDouble(6, transaction.getBalanceAfter());
            preparedStatement.setString(7, transaction.getReferenceNumber());
            preparedStatement.setString(8, transaction.getStatus());
            preparedStatement.setTimestamp(9, transaction.getProcessedDate() != null ? 
                new java.sql.Timestamp(transaction.getProcessedDate().getTime()) : null);
            preparedStatement.setString(10, transaction.getNotes());
            preparedStatement.setInt(11, transaction.getTransactionId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Transaction updated successfully: " + transaction.getTransactionId());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating transaction: " + e.getMessage());
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
     * Delete a transaction by ID
     * 
     * @param transactionId The ID of the transaction to delete
     * @return true if transaction was deleted successfully, false otherwise
     */
    public boolean deleteTransaction(int transactionId) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transactionId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Transaction deleted successfully: " + transactionId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
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
     * Helper method to map ResultSet to Transaction object
     * 
     * @param resultSet The ResultSet to map
     * @return Transaction object populated from ResultSet
     * @throws SQLException if ResultSet access fails
     */
    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(resultSet.getInt("transaction_id"));
        transaction.setCustomerId(resultSet.getInt("customer_id"));
        transaction.setTransactionType(resultSet.getString("transaction_type"));
        transaction.setDescription(resultSet.getString("description"));
        transaction.setAmount(resultSet.getDouble("amount"));
        transaction.setBalanceBefore(resultSet.getDouble("balance_before"));
        transaction.setBalanceAfter(resultSet.getDouble("balance_after"));
        transaction.setReferenceNumber(resultSet.getString("reference_number"));
        transaction.setStatus(resultSet.getString("status"));
        transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
        transaction.setProcessedDate(resultSet.getTimestamp("processed_date"));
        transaction.setNotes(resultSet.getString("notes"));
        return transaction;
    }
}
