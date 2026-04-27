package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.model.Employee;
import com.bankingapp.model.Shift;
import com.bankingapp.service.EmployeeService;
import com.bankingapp.service.PackageShiftService;
import com.bankingapp.util.Constants;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;

/**
 * Employee Servlet
 * 
 * Handles all employee management operations.
 * Supports CRUD operations on employees.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class EmployeeServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private EmployeeService employeeService;
    private PackageShiftService shiftService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.employeeService = new EmployeeService();
        this.shiftService = new PackageShiftService();
    }
    
    /**
     * Handle GET requests
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            if (!isUserLoggedIn(request, response)) {
                return;
            }
            
            String action = request.getParameter(Constants.ACTION);
            if (action == null) {
                action = Constants.ACTION_LIST;
            }
            
            switch (action) {
                case Constants.ACTION_LIST:
                    listEmployees(request, response);
                    break;
                case Constants.ACTION_ADD:
                    showAddForm(request, response);
                    break;
                case Constants.ACTION_EDIT:
                    showEditForm(request, response);
                    break;
                default:
                    listEmployees(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in EmployeeServlet: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Handle POST requests
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            if (!isUserLoggedIn(request, response)) {
                return;
            }
            
            String action = request.getParameter(Constants.ACTION);
            
            if (Constants.ACTION_SAVE.equals(action)) {
                saveEmployee(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deleteEmployee(request, response);
            } else {
                listEmployees(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in EmployeeServlet: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Display list of all employees
     */
    private void listEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            request.setAttribute("employees", employees);
            
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_EMPLOYEE_LIST);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error listing employees: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_EMPLOYEE_LIST);
            dispatcher.forward(request, response);
        }
    }
    
    /**
     * Show add employee form
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            List<Shift> shifts = shiftService.getAllShifts();
            request.setAttribute("shifts", shifts);
            
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_EMPLOYEE_FORM);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Show edit employee form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            int employeeId = Integer.parseInt(request.getParameter(Constants.EMPLOYEE_ID));
            
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee == null) {
                request.setAttribute("error", "Employee not found");
                listEmployees(request, response);
                return;
            }
            
            List<Shift> shifts = shiftService.getAllShifts();
            
            request.setAttribute("employee", employee);
            request.setAttribute("shifts", shifts);
            request.setAttribute("isEdit", true);
            
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_EMPLOYEE_FORM);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Save employee (create or update)
     */
    private void saveEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            String employeeIdStr = request.getParameter("employeeId");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String designation = request.getParameter("designation");
            String department = request.getParameter("department");
            String shiftIdStr = request.getParameter("shiftId");
            String basicSalaryStr = request.getParameter("basicSalary");
            String hireDate = request.getParameter("hireDate");
            
            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setDesignation(designation);
            employee.setDepartment(department);
            
            if (shiftIdStr != null && !shiftIdStr.isEmpty()) {
                employee.setShiftId(Integer.parseInt(shiftIdStr));
            }
            
            if (basicSalaryStr != null && !basicSalaryStr.isEmpty()) {
                employee.setBasicSalary(Double.parseDouble(basicSalaryStr));
            }
            
            employee.setHireDate(hireDate);
            
            boolean success = false;
            String message = "";
            
            if (employeeIdStr == null || employeeIdStr.isEmpty()) {
                success = employeeService.addEmployee(employee);
                message = success ? "Employee added successfully" : "Failed to add employee";
            } else {
                employee.setEmployeeId(Integer.parseInt(employeeIdStr));
                success = employeeService.updateEmployee(employee);
                message = success ? "Employee updated successfully" : "Failed to update employee";
            }
            
            if (success) {
                request.setAttribute("success", message);
            } else {
                request.setAttribute("error", message);
            }
            
            response.sendRedirect(request.getContextPath() + "/employee?action=list");
            
        } catch (Exception e) {
            System.err.println("Error saving employee: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error saving employee");
            listEmployees(request, response);
        }
    }
    
    /**
     * Delete employee
     */
    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            int employeeId = Integer.parseInt(request.getParameter(Constants.EMPLOYEE_ID));
            
            boolean success = employeeService.deleteEmployee(employeeId);
            
            if (success) {
                request.setAttribute("success", "Employee deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete employee");
            }
            
            response.sendRedirect(request.getContextPath() + "/employee?action=list");
            
        } catch (Exception e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Check if user is logged in
     */
    private boolean isUserLoggedIn(HttpServletRequest request, HttpServletResponse response)
            throws java.io.IOException {
        
        HttpSession session = request.getSession(false);
        Admin admin = null;
        
        if (session != null) {
            admin = (Admin) session.getAttribute(Constants.ADMIN_SESSION);
        }
        
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return false;
        }
        
        return true;
    }
}
