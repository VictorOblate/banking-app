package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.model.Customer;
import com.bankingapp.model.Package;
import com.bankingapp.service.CustomerService;
import com.bankingapp.service.PackageShiftService;
import com.bankingapp.util.Constants;
import com.bankingapp.util.ValidationUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;

/**
 * Customer Servlet
 * 
 * Handles all customer management operations.
 * Supports CRUD operations on customers.
 * 
 * Request Parameters:
 * - action: Operation to perform (list, add, edit, save, delete)
 * - customerId: Customer ID for edit/delete
 * - firstName, lastN ame, email, phone, etc.: Customer data
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class CustomerServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;
    private PackageShiftService packageService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.customerService = new CustomerService();
        this.packageService = new PackageShiftService();
    }
    
    /**
     * Handle GET requests
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            // Check if user is logged in
            if (!isUserLoggedIn(request, response)) {
                return;
            }
            
            String action = request.getParameter(Constants.ACTION);
            
            if (action == null) {
                action = Constants.ACTION_LIST;
            }
            
            switch (action) {
                case Constants.ACTION_LIST:
                    listCustomers(request, response);
                    break;
                case Constants.ACTION_ADD:
                    showAddForm(request, response);
                    break;
                case Constants.ACTION_EDIT:
                    showEditForm(request, response);
                    break;
                default:
                    listCustomers(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in CustomerServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Handle POST requests
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            // Check if user is logged in
            if (!isUserLoggedIn(request, response)) {
                return;
            }
            
            String action = request.getParameter(Constants.ACTION);
            
            if (Constants.ACTION_SAVE.equals(action)) {
                saveCustomer(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deleteCustomer(request, response);
            } else {
                listCustomers(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in CustomerServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Display list of all customers
     */
    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            // Get all customers
            List<Customer> customers = customerService.getAllCustomers();
            request.setAttribute("customers", customers);
            
            // Forward to customer list page
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_CUSTOMER_LIST);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error listing customers: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_CUSTOMER_LIST);
            dispatcher.forward(request, response);
        }
    }
    
    /**
     * Show add customer form
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            // Get all packages for dropdown
            List<Package> packages = packageService.getAllPackages();
            request.setAttribute("packages", packages);
            
            // Forward to customer form page
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_CUSTOMER_FORM);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Show edit customer form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            int customerId = Integer.parseInt(request.getParameter(Constants.CUSTOMER_ID));
            
            // Get customer by ID
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                request.setAttribute("error", "Customer not found");
                listCustomers(request, response);
                return;
            }
            
            // Get all packages for dropdown
            List<Package> packages = packageService.getAllPackages();
            
            request.setAttribute("customer", customer);
            request.setAttribute("packages", packages);
            request.setAttribute("isEdit", true);
            
            // Forward to customer form page
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_CUSTOMER_FORM);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Save customer (create or update)
     */
    private void saveCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            // Get customer data from request
            String customerIdStr = request.getParameter("customerId");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String postalCode = request.getParameter("postalCode");
            String country = request.getParameter("country");
            String packageIdStr = request.getParameter("packageId");
            String accountStatus = request.getParameter("accountStatus");
            
            // Create customer object
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setDateOfBirth(dateOfBirth);
            customer.setGender(gender);
            customer.setAddress(address);
            customer.setCity(city);
            customer.setState(state);
            customer.setPostalCode(postalCode);
            customer.setCountry(country);
            customer.setAccountStatus(accountStatus);
            
            if (packageIdStr != null && !packageIdStr.isEmpty()) {
                customer.setPackageId(Integer.parseInt(packageIdStr));
            }
            
            boolean success = false;
            String message = "";
            
            // Check if create or update
            if (customerIdStr == null || customerIdStr.isEmpty()) {
                // Create new customer
                success = customerService.addCustomer(customer);
                message = success ? Constants.SUCCESS_CUSTOMER_ADDED : "Failed to add customer";
            } else {
                // Update existing customer
                customer.setCustomerId(Integer.parseInt(customerIdStr));
                success = customerService.updateCustomer(customer);
                message = success ? Constants.SUCCESS_CUSTOMER_UPDATED : "Failed to update customer";
            }
            
            if (success) {
                request.setAttribute("success", message);
            } else {
                request.setAttribute("error", message);
            }
            
            // Redirect to customer list
            response.sendRedirect(request.getContextPath() + "/customer?action=list");
            
        } catch (Exception e) {
            System.err.println("Error saving customer: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error saving customer");
            listCustomers(request, response);
        }
    }
    
    /**
     * Delete customer
     */
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            int customerId = Integer.parseInt(request.getParameter(Constants.CUSTOMER_ID));
            
            boolean success = customerService.deleteCustomer(customerId);
            
            if (success) {
                request.setAttribute("success", Constants.SUCCESS_CUSTOMER_DELETED);
            } else {
                request.setAttribute("error", "Failed to delete customer");
            }
            
            // Redirect to customer list
            response.sendRedirect(request.getContextPath() + "/customer?action=list");
            
        } catch (Exception e) {
            System.err.println("Error deleting customer: " + e.getMessage());
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
