package com.bankingapp.dao;

import com.bankingapp.model.Employee;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee Data Access Object (DAO)
 * 
 * Handles all database operations related to Employees.
 * Includes CRUD operations (Create, Read, Update, Delete) for employee management.
 * Uses PreparedStatements to prevent SQL injection.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class EmployeeDAO {
    
    /**
     * Add a new employee to the database
     * 
     * @param employee The Employee object to add
     * @return true if employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email, phone, date_of_birth, " +
                     "gender, address, city, state, postal_code, country, employee_code, " +
                     "designation, department, shift_id, basic_salary, hire_date, " +
                     "employment_status, bank_account_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setString(5, employee.getDateOfBirth());
            preparedStatement.setString(6, employee.getGender());
            preparedStatement.setString(7, employee.getAddress());
            preparedStatement.setString(8, employee.getCity());
            preparedStatement.setString(9, employee.getState());
            preparedStatement.setString(10, employee.getPostalCode());
            preparedStatement.setString(11, employee.getCountry());
            preparedStatement.setString(12, employee.getEmployeeCode());
            preparedStatement.setString(13, employee.getDesignation());
            preparedStatement.setString(14, employee.getDepartment());
            if (employee.getShiftId() > 0) {
    preparedStatement.setInt(15, employee.getShiftId());
} else {
    preparedStatement.setNull(15, java.sql.Types.INTEGER);
}
            preparedStatement.setDouble(16, employee.getBasicSalary());
            preparedStatement.setString(17, employee.getHireDate());
            preparedStatement.setString(18, employee.getEmploymentStatus() != null ? employee.getEmploymentStatus() : "ACTIVE");
            preparedStatement.setString(19, employee.getBankAccountNumber());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Employee added successfully: " + employee.getFullName());
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
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
     * Get employee by employee ID
     * 
     * @param employeeId The ID of the employee to retrieve
     * @return Employee object if found, null otherwise
     */
    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, date_of_birth, " +
                     "gender, address, city, state, postal_code, country, employee_code, " +
                     "designation, department, shift_id, basic_salary, hire_date, " +
                     "employment_status, bank_account_number, registration_date, modified_date " +
                     "FROM employees WHERE employee_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Employee employee = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                employee = mapResultSetToEmployee(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return employee;
    }
    
    /**
     * Get all employees from database
     * 
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, date_of_birth, " +
                     "gender, address, city, state, postal_code, country, employee_code, " +
                     "designation, department, shift_id, basic_salary, hire_date, " +
                     "employment_status, bank_account_number, registration_date, modified_date " +
                     "FROM employees ORDER BY employee_id DESC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Employee> employees = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Employee employee = mapResultSetToEmployee(resultSet);
                employees.add(employee);
            }
            System.out.println("Retrieved " + employees.size() + " employees from database");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return employees;
    }
    
    /**
     * Update employee information
     * 
     * @param employee The Employee object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
                     "date_of_birth = ?, gender = ?, address = ?, city = ?, state = ?, " +
                     "postal_code = ?, country = ?, designation = ?, department = ?, " +
                     "shift_id = ?, basic_salary = ?, employment_status = ?, " +
                     "bank_account_number = ?, modified_date = NOW() WHERE employee_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setString(5, employee.getDateOfBirth());
            preparedStatement.setString(6, employee.getGender());
            preparedStatement.setString(7, employee.getAddress());
            preparedStatement.setString(8, employee.getCity());
            preparedStatement.setString(9, employee.getState());
            preparedStatement.setString(10, employee.getPostalCode());
            preparedStatement.setString(11, employee.getCountry());
            preparedStatement.setString(12, employee.getDesignation());
            preparedStatement.setString(13, employee.getDepartment());
            if (employee.getShiftId() > 0) {
                preparedStatement.setInt(14, employee.getShiftId());
            } else {
                preparedStatement.setNull(14, java.sql.Types.INTEGER);
            };
            preparedStatement.setDouble(15, employee.getBasicSalary());
            preparedStatement.setString(16, employee.getEmploymentStatus());
            preparedStatement.setString(17, employee.getBankAccountNumber());
            preparedStatement.setInt(18, employee.getEmployeeId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Employee updated successfully: " + employee.getFullName());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating employee: " + e.getMessage());
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
     * Delete an employee from database (soft delete)
     * 
     * @param employeeId The ID of the employee to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteEmployee(int employeeId) {
        String sql = "UPDATE employees SET employment_status = 'INACTIVE', modified_date = NOW() " +
                     "WHERE employee_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Employee deleted successfully: ID " + employeeId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
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
     * Get total count of employees
     * 
     * @return Total number of employees in the system
     */
    public int getEmployeeCount() {
        String sql = "SELECT COUNT(*) as count FROM employees";
        
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
            System.err.println("Error getting employee count: " + e.getMessage());
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
     * Helper method to map ResultSet to Employee object
     * 
     * @param resultSet The ResultSet to map
     * @return Employee object populated from ResultSet
     * @throws SQLException if ResultSet access fails
     */
    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setEmail(resultSet.getString("email"));
        employee.setPhone(resultSet.getString("phone"));
        employee.setDateOfBirth(resultSet.getString("date_of_birth"));
        employee.setGender(resultSet.getString("gender"));
        employee.setAddress(resultSet.getString("address"));
        employee.setCity(resultSet.getString("city"));
        employee.setState(resultSet.getString("state"));
        employee.setPostalCode(resultSet.getString("postal_code"));
        employee.setCountry(resultSet.getString("country"));
        employee.setEmployeeCode(resultSet.getString("employee_code"));
        employee.setDesignation(resultSet.getString("designation"));
        employee.setDepartment(resultSet.getString("department"));
        employee.setShiftId(resultSet.getInt("shift_id"));
        employee.setBasicSalary(resultSet.getDouble("basic_salary"));
        employee.setHireDate(resultSet.getString("hire_date"));
        employee.setEmploymentStatus(resultSet.getString("employment_status"));
        employee.setBankAccountNumber(resultSet.getString("bank_account_number"));
        employee.setRegistrationDate(resultSet.getTimestamp("registration_date"));
        employee.setModifiedDate(resultSet.getTimestamp("modified_date"));
        return employee;
    }
}
