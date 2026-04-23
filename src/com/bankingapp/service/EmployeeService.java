package com.bankingapp.service;

import com.bankingapp.dao.EmployeeDAO;
import com.bankingapp.model.Employee;
import com.bankingapp.util.ValidationUtil;
import java.util.List;
import java.util.Random;

/**
 * Employee Service Class
 * 
 * Contains business logic for employee-related operations.
 * Handles employee CRUD operations and validation.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class EmployeeService {
    
    private EmployeeDAO employeeDAO;
    
    /**
     * Constructor initializing the EmployeeDAO
     */
    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }
    
    /**
     * Add a new employee
     * 
     * Validates employee data before adding to database.
     * Generates an employee code if not provided.
     * 
     * @param employee The Employee object to add
     * @return true if employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        // Validate employee data
        if (!isValidEmployee(employee)) {
            System.out.println("Employee validation failed");
            return false;
        }
        
        // Generate employee code if not provided
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().trim().isEmpty()) {
            employee.setEmployeeCode(generateEmployeeCode());
        }
        
        // Set default employment status if not provided
        if (employee.getEmploymentStatus() == null || employee.getEmploymentStatus().trim().isEmpty()) {
            employee.setEmploymentStatus("ACTIVE");
        }
        
        // Add employee to database
        return employeeDAO.addEmployee(employee);
    }
    
    /**
     * Get employee by ID
     * 
     * @param employeeId The ID of the employee
     * @return Employee object if found, null otherwise
     */
    public Employee getEmployeeById(int employeeId) {
        if (employeeId <= 0) {
            return null;
        }
        return employeeDAO.getEmployeeById(employeeId);
    }
    
    /**
     * Get all employees
     * 
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
    
    /**
     * Update employee information
     * 
     * @param employee The Employee object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateEmployee(Employee employee) {
        if (employee == null || employee.getEmployeeId() <= 0) {
            return false;
        }
        
        if (!isValidEmployee(employee)) {
            System.out.println("Employee validation failed for update");
            return false;
        }
        
        return employeeDAO.updateEmployee(employee);
    }
    
    /**
     * Delete (deactivate) an employee
     * 
     * @param employeeId The ID of the employee to delete
     * @return true if delete was successful, false otherwise
     */
    public boolean deleteEmployee(int employeeId) {
        if (employeeId <= 0) {
            return false;
        }
        return employeeDAO.deleteEmployee(employeeId);
    }
    
    /**
     * Calculate total salary for an employee
     * 
     * Includes basic salary + overtime if applicable.
     * 
     * @param employeeId The ID of the employee
     * @param overtimeHours The number of overtime hours worked
     * @return Total salary amount
     */
    public double calculateSalary(int employeeId, int overtimeHours) {
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            return 0.0;
        }
        
        // Calculate overtime pay (assume 1.5x hourly rate for overtime)
        double basicSalary = employee.getBasicSalary();
        double hourlyRate = basicSalary / 160; // Assuming 160 working hours per month
        double overtimePay = overtimeHours * hourlyRate * 1.5;
        
        return basicSalary + overtimePay;
    }
    
    /**
     * Validate employee data
     * 
     * Performs comprehensive validation of employee information.
     * 
     * @param employee The employee to validate
     * @return true if employee data is valid, false otherwise
     */
    private boolean isValidEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        // Validate required fields
        if (!ValidationUtil.isValidName(employee.getFirstName())) {
            System.out.println("Invalid first name");
            return false;
        }
        
        if (!ValidationUtil.isValidName(employee.getLastName())) {
            System.out.println("Invalid last name");
            return false;
        }
        
        if (employee.getEmail() != null && !employee.getEmail().trim().isEmpty() &&
            !ValidationUtil.isValidEmail(employee.getEmail())) {
            System.out.println("Invalid email");
            return false;
        }
        
        if (employee.getBasicSalary() < 0) {
            System.out.println("Invalid salary");
            return false;
        }
        
        return true;
    }
    
    /**
     * Get total count of employees
     * 
     * @return Total number of employees in the system
     */
    public int getEmployeeCount() {
        return employeeDAO.getEmployeeCount();
    }
    
    /**
     * Generate a unique employee code for new employees
     * 
     * @return A unique employee code string
     */
    private String generateEmployeeCode() {
        // Generate employee code in format: EMP + timestamp + random digits
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 100);
        return "EMP" + timestamp + String.format("%02d", random);
    }
}
