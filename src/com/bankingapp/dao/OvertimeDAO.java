package com.bankingapp.dao;

import com.bankingapp.model.Overtime;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Overtime Data Access Object (DAO)
 * 
 * Handles all database operations related to overtime records.
 * Includes CRUD operations for overtime management and bulk operations.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class OvertimeDAO {
    
    /**
     * Add a new overtime record to the database
     * 
     * @param overtime The Overtime object to add
     * @return true if overtime was added successfully, false otherwise
     */
    public boolean addOvertime(Overtime overtime) {
        String sql = "INSERT INTO overtime (employee_id, overtime_date, hours_worked, " +
                     "hourly_rate, overtime_amount, overtime_type, remarks, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, overtime.getEmployeeId());
            preparedStatement.setString(2, overtime.getOvertimeDate());
            preparedStatement.setDouble(3, overtime.getHoursWorked());
            preparedStatement.setDouble(4, overtime.getHourlyRate());
            preparedStatement.setDouble(5, overtime.getOvertimeAmount());
            preparedStatement.setString(6, overtime.getOvertimeType());
            preparedStatement.setString(7, overtime.getRemarks());
            preparedStatement.setString(8, overtime.getStatus() != null ? overtime.getStatus() : "PENDING");
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Overtime record added successfully for employee: " + overtime.getEmployeeId());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding overtime: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }
    
    /**
     * Get overtime record by ID
     * 
     * @param overtimeId The ID of the overtime to retrieve
     * @return Overtime object if found, null otherwise
     */
    public Overtime getOvertimeById(int overtimeId) {
        String sql = "SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
                     "o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
                     "o.created_date, o.approved_date, o.process_date, " +
                     "CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
                     "FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id " +
                     "WHERE o.overtime_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, overtimeId);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToOvertime(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving overtime: " + e.getMessage());
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return null;
    }
    
    /**
     * Get all overtime records for a specific employee
     * 
     * @param employeeId The ID of the employee
     * @return List of overtime records for the employee
     */
    public List<Overtime> getOvertimeByEmployeeId(int employeeId) {
        String sql = "SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
                     "o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
                     "o.created_date, o.approved_date, o.process_date, " +
                     "CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
                     "FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id " +
                     "WHERE o.employee_id = ? ORDER BY o.created_date DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Overtime> overtimes = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                overtimes.add(mapResultSetToOvertime(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving overtime records: " + e.getMessage());
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return overtimes;
    }
    
    /**
     * Get all pending overtime records
     * 
     * @return List of pending overtime records
     */
    public List<Overtime> getPendingOvertimeRecords() {
        String sql = "SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
                     "o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
                     "o.created_date, o.approved_date, o.process_date, " +
                     "CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
                     "FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id " +
                     "WHERE o.status = 'PENDING' ORDER BY o.created_date DESC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Overtime> overtimes = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                overtimes.add(mapResultSetToOvertime(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving pending overtime: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return overtimes;
    }
    
    /**
     * Get all approved overtime records
     * 
     * @return List of approved overtime records
     */
    public List<Overtime> getApprovedOvertimeRecords() {
        String sql = "SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
                     "o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
                     "o.created_date, o.approved_date, o.process_date, " +
                     "CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
                     "FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id " +
                     "WHERE o.status = 'APPROVED' ORDER BY o.created_date DESC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Overtime> overtimes = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                overtimes.add(mapResultSetToOvertime(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving approved overtime: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return overtimes;
    }
    
    /**
     * Update overtime record status
     * 
     * @param overtimeId The ID of the overtime to update
     * @param status The new status
     * @return true if update was successful, false otherwise
     */
    public boolean updateOvertimeStatus(int overtimeId, String status) {
        String sql = "UPDATE overtime SET status = ?, ";
        
        if ("APPROVED".equals(status)) {
            sql += "approved_date = NOW() WHERE overtime_id = ?";
        } else if ("PROCESSED".equals(status)) {
            sql += "process_date = NOW() WHERE overtime_id = ?";
        } else {
            sql += "process_date = NULL WHERE overtime_id = ?";
        }
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, overtimeId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating overtime status: " + e.getMessage());
            return false;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }
    
    /**
     * Get total overtime amount for a date range and status
     * 
     * @param startDate Start date (YYYY-MM-DD)
     * @param endDate End date (YYYY-MM-DD)
     * @param status Status filter
     * @return Total overtime amount
     */
    public double getTotalOvertimeAmount(String startDate, String endDate, String status) {
        String sql = "SELECT SUM(overtime_amount) as total FROM overtime " +
                     "WHERE overtime_date BETWEEN ? AND ? ";
        if (status != null && !status.isEmpty()) {
            sql += "AND status = ?";
        }
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double total = 0;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            if (status != null && !status.isEmpty()) {
                preparedStatement.setString(3, status);
            }
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getDouble("total");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error calculating total overtime: " + e.getMessage());
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        
        return total;
    }
    
    /**
     * Delete overtime record
     * 
     * @param overtimeId The ID of the overtime to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteOvertime(int overtimeId) {
        String sql = "DELETE FROM overtime WHERE overtime_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, overtimeId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting overtime: " + e.getMessage());
            return false;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }
    
    /**
     * Map ResultSet to Overtime object
     */
    private Overtime mapResultSetToOvertime(ResultSet resultSet) throws SQLException {
        Overtime overtime = new Overtime();
        overtime.setOvertimeId(resultSet.getInt("overtime_id"));
        overtime.setEmployeeId(resultSet.getInt("employee_id"));
        overtime.setEmployeeName(resultSet.getString("employee_name"));
        overtime.setOvertimeDate(resultSet.getString("overtime_date"));
        overtime.setHoursWorked(resultSet.getDouble("hours_worked"));
        overtime.setHourlyRate(resultSet.getDouble("hourly_rate"));
        overtime.setOvertimeAmount(resultSet.getDouble("overtime_amount"));
        overtime.setOvertimeType(resultSet.getString("overtime_type"));
        overtime.setRemarks(resultSet.getString("remarks"));
        overtime.setStatus(resultSet.getString("status"));
        overtime.setCreatedDate(resultSet.getTimestamp("created_date"));
        overtime.setApprovedDate(resultSet.getTimestamp("approved_date"));
        overtime.setProcessDate(resultSet.getTimestamp("process_date"));
        return overtime;
    }
    
    /**
     * Get all overtime records
     * 
     * @return List of all overtime records
     */
    public List<Overtime> getAllOvertimeRecords() {
        String sql = "SELECT o.overtime_id, o.employee_id, o.overtime_date, o.hours_worked, " +
                     "o.hourly_rate, o.overtime_amount, o.overtime_type, o.remarks, o.status, " +
                     "o.created_date, o.approved_date, o.process_date, " +
                     "CONCAT(e.first_name, ' ', e.last_name) as employee_name " +
                     "FROM overtime o LEFT JOIN employees e ON o.employee_id = e.employee_id " +
                     "ORDER BY o.created_date DESC";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Overtime> overtimes = new ArrayList<>();

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                overtimes.add(mapResultSetToOvertime(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving all overtime records: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        return overtimes;
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
