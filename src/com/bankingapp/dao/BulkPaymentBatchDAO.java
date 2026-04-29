package com.bankingapp.dao;

import com.bankingapp.model.BulkPaymentBatch;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bulk Payment Batch Data Access Object (DAO)
 * 
 * Handles all database operations related to bulk payment batches.
 * Includes CRUD operations for batch management.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class BulkPaymentBatchDAO {
    
    /**
     * Create a new payment batch
     * 
     * @param batch The BulkPaymentBatch object to create
     * @return The batch ID if successful, -1 otherwise
     */
    public int createBatch(BulkPaymentBatch batch) {
        String sql = "INSERT INTO bulk_payment_batch (batch_type, batch_name, description, " +
                     "total_amount, total_records, payment_date, batch_status, created_by, remarks) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, batch.getBatchType());
            preparedStatement.setString(2, batch.getBatchName());
            preparedStatement.setString(3, batch.getDescription());
            preparedStatement.setDouble(4, batch.getTotalAmount());
            preparedStatement.setInt(5, batch.getTotalRecords());
            preparedStatement.setString(6, batch.getPaymentDate());
            preparedStatement.setString(7, batch.getBatchStatus() != null ? batch.getBatchStatus() : "DRAFT");
            preparedStatement.setInt(8, batch.getCreatedBy());
            preparedStatement.setString(9, batch.getRemarks());
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int batchId = resultSet.getInt(1);
                    System.out.println("Batch created successfully with ID: " + batchId);
                    return batchId;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error creating batch: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return -1;
    }
    
    /**
     * Get batch by ID
     * 
     * @param batchId The ID of the batch
     * @return BulkPaymentBatch object if found, null otherwise
     */
    public BulkPaymentBatch getBatchById(int batchId) {
        String sql = "SELECT batch_id, batch_type, batch_name, description, total_amount, " +
                     "total_records, payment_date, batch_status, created_by, created_date, " +
                     "processed_date, remarks FROM bulk_payment_batch WHERE batch_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, batchId);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToBatch(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving batch: " + e.getMessage());
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return null;
    }
    
    /**
     * Get all batches
     * 
     * @return List of all batches ordered by creation date descending
     */
    public List<BulkPaymentBatch> getAllBatches() {
        String sql = "SELECT batch_id, batch_type, batch_name, description, total_amount, " +
                     "total_records, payment_date, batch_status, created_by, created_date, " +
                     "processed_date, remarks FROM bulk_payment_batch " +
                     "ORDER BY created_date DESC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<BulkPaymentBatch> batches = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                batches.add(mapResultSetToBatch(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving batches: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return batches;
    }
    
    /**
     * Get batches by type
     * 
     * @param batchType The type of batch (SALARY, OVERTIME, etc.)
     * @return List of batches of the specified type
     */
    public List<BulkPaymentBatch> getBatchesByType(String batchType) {
        String sql = "SELECT batch_id, batch_type, batch_name, description, total_amount, " +
                     "total_records, payment_date, batch_status, created_by, created_date, " +
                     "processed_date, remarks FROM bulk_payment_batch " +
                     "WHERE batch_type = ? ORDER BY created_date DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<BulkPaymentBatch> batches = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, batchType);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                batches.add(mapResultSetToBatch(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving batches by type: " + e.getMessage());
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return batches;
    }
    
    /**
     * Get batches by status
     * 
     * @param status The status of batches
     * @return List of batches with the specified status
     */
    public List<BulkPaymentBatch> getBatchesByStatus(String status) {
        String sql = "SELECT batch_id, batch_type, batch_name, description, total_amount, " +
                     "total_records, payment_date, batch_status, created_by, created_date, " +
                     "processed_date, remarks FROM bulk_payment_batch " +
                     "WHERE batch_status = ? ORDER BY created_date DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<BulkPaymentBatch> batches = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                batches.add(mapResultSetToBatch(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving batches by status: " + e.getMessage());
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return batches;
    }
    
    /**
     * Update batch
     * 
     * @param batch The BulkPaymentBatch object to update
     * @return true if update was successful, false otherwise
     */
    public boolean updateBatch(BulkPaymentBatch batch) {
        String sql = "UPDATE bulk_payment_batch SET batch_type = ?, batch_name = ?, " +
                     "description = ?, total_amount = ?, total_records = ?, " +
                     "payment_date = ?, batch_status = ?, remarks = ? WHERE batch_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, batch.getBatchType());
            preparedStatement.setString(2, batch.getBatchName());
            preparedStatement.setString(3, batch.getDescription());
            preparedStatement.setDouble(4, batch.getTotalAmount());
            preparedStatement.setInt(5, batch.getTotalRecords());
            preparedStatement.setString(6, batch.getPaymentDate());
            preparedStatement.setString(7, batch.getBatchStatus());
            preparedStatement.setString(8, batch.getRemarks());
            preparedStatement.setInt(9, batch.getBatchId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating batch: " + e.getMessage());
            return false;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }
    
    /**
     * Update batch status
     * 
     * @param batchId The ID of the batch
     * @param status The new status
     * @return true if update was successful, false otherwise
     */
    public boolean updateBatchStatus(int batchId, String status) {
        String sql = "UPDATE bulk_payment_batch SET batch_status = ? ";
        
        if ("PROCESSED".equals(status)) {
            sql += ", processed_date = NOW() ";
        }
        
        sql += "WHERE batch_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, batchId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating batch status: " + e.getMessage());
            return false;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }
    
    /**
     * Delete batch
     * 
     * @param batchId The ID of the batch to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteBatch(int batchId) {
        String sql = "DELETE FROM bulk_payment_batch WHERE batch_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, batchId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting batch: " + e.getMessage());
            return false;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }
    
    /**
     * Map ResultSet to BulkPaymentBatch object
     */
    private BulkPaymentBatch mapResultSetToBatch(ResultSet resultSet) throws SQLException {
        BulkPaymentBatch batch = new BulkPaymentBatch();
        batch.setBatchId(resultSet.getInt("batch_id"));
        batch.setBatchType(resultSet.getString("batch_type"));
        batch.setBatchName(resultSet.getString("batch_name"));
        batch.setDescription(resultSet.getString("description"));
        batch.setTotalAmount(resultSet.getDouble("total_amount"));
        batch.setTotalRecords(resultSet.getInt("total_records"));
        batch.setPaymentDate(resultSet.getString("payment_date"));
        batch.setBatchStatus(resultSet.getString("batch_status"));
        batch.setCreatedBy(resultSet.getInt("created_by"));
        batch.setCreatedDate(resultSet.getTimestamp("created_date"));
        batch.setProcessedDate(resultSet.getTimestamp("processed_date"));
        batch.setRemarks(resultSet.getString("remarks"));
        return batch;
    }
    
    /**
     * Close database resources
     */
    private void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) DBConnection.closeConnection(connection);
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
