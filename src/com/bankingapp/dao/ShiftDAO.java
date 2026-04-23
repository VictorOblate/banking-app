package com.bankingapp.dao;

import com.bankingapp.model.Shift;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Shift Data Access Object (DAO)
 * 
 * Handles all database operations related to work shifts.
 * Includes CRUD operations for shift management.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class ShiftDAO {
    
    /**
     * Add a new shift to the database
     * 
     * @param shift The Shift object to add
     * @return true if shift was added successfully, false otherwise
     */
    public boolean addShift(Shift shift) {
        String sql = "INSERT INTO shifts (shift_name, start_time, end_time, shift_type, " +
                     "description, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, shift.getShiftName());
            preparedStatement.setString(2, shift.getStartTime());
            preparedStatement.setString(3, shift.getEndTime());
            preparedStatement.setString(4, shift.getShiftType());
            preparedStatement.setString(5, shift.getDescription());
            preparedStatement.setBoolean(6, shift.isActive());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Shift added successfully: " + shift.getShiftName());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding shift: " + e.getMessage());
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
     * Get all active shifts
     * 
     * @return List of all active shifts
     */
    public List<Shift> getAllActiveShifts() {
        String sql = "SELECT shift_id, shift_name, start_time, end_time, shift_type, " +
                     "description, created_date, modified_date, is_active " +
                     "FROM shifts WHERE is_active = TRUE ORDER BY shift_name";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Shift> shifts = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Shift shift = mapResultSetToShift(resultSet);
                shifts.add(shift);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving shifts: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return shifts;
    }
    
    /**
     * Get shift by ID
     * 
     * @param shiftId The ID of the shift to retrieve
     * @return Shift object if found, null otherwise
     */
    public Shift getShiftById(int shiftId) {
        String sql = "SELECT shift_id, shift_name, start_time, end_time, shift_type, " +
                     "description, created_date, modified_date, is_active " +
                     "FROM shifts WHERE shift_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Shift shift = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, shiftId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                shift = mapResultSetToShift(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving shift: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return shift;
    }
    
    /**
     * Update an existing shift
     * 
     * @param shift The Shift object with updated data
     * @return true if shift was updated successfully, false otherwise
     */
    public boolean updateShift(Shift shift) {
        String sql = "UPDATE shifts SET shift_name = ?, start_time = ?, end_time = ?, " +
                     "shift_type = ?, description = ?, modified_date = ?, is_active = ? " +
                     "WHERE shift_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, shift.getShiftName());
            preparedStatement.setString(2, shift.getStartTime());
            preparedStatement.setString(3, shift.getEndTime());
            preparedStatement.setString(4, shift.getShiftType());
            preparedStatement.setString(5, shift.getDescription());
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(7, shift.isActive());
            preparedStatement.setInt(8, shift.getShiftId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Shift updated successfully: " + shift.getShiftId());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating shift: " + e.getMessage());
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
     * Delete a shift by ID (soft delete - set inactive)
     * 
     * @param shiftId The ID of the shift to delete
     * @return true if shift was deleted successfully, false otherwise
     */
    public boolean deleteShift(int shiftId) {
        String sql = "UPDATE shifts SET is_active = FALSE, modified_date = ? WHERE shift_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, shiftId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Shift deleted (soft delete) successfully: " + shiftId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting shift: " + e.getMessage());
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
     * Helper method to map ResultSet to Shift object
     * 
     * @param resultSet The ResultSet to map
     * @return Shift object populated from ResultSet
     * @throws SQLException if ResultSet access fails
     */
    private Shift mapResultSetToShift(ResultSet resultSet) throws SQLException {
        Shift shift = new Shift();
        shift.setShiftId(resultSet.getInt("shift_id"));
        shift.setShiftName(resultSet.getString("shift_name"));
        shift.setStartTime(resultSet.getString("start_time"));
        shift.setEndTime(resultSet.getString("end_time"));
        shift.setShiftType(resultSet.getString("shift_type"));
        shift.setDescription(resultSet.getString("description"));
        shift.setCreatedDate(resultSet.getTimestamp("created_date"));
        shift.setModifiedDate(resultSet.getTimestamp("modified_date"));
        shift.setActive(resultSet.getBoolean("is_active"));
        return shift;
    }
    
    /**
     * Get total count of shifts
     * 
     * @return Total number of shifts in the database
     */
    public int getShiftCount() {
        String sql = "SELECT COUNT(*) as count FROM shifts";
        
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
            System.err.println("Error getting shift count: " + e.getMessage());
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
}
