package com.bankingapp.util;

/**
 * Application Constants
 * 
 * This class contains all global constants used throughout the Online Banking Application.
 * It serves as a centralized location for configuration values and constants that should
 * not be hardcoded in various places.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Constants {
    
    // ===== SESSION CONFIGURATION =====
    /** Session timeout in minutes */
    public static final int SESSION_TIMEOUT = 30;
    
    /** Session attribute name for logged-in admin */
    public static final String ADMIN_SESSION = "adminUser";
    
    // ===== REQUEST PARAMETER NAMES =====
    /** Action parameter for servlet routing */
    public static final String ACTION = "action";
    
    /** Employee ID parameter */
    public static final String EMPLOYEE_ID = "employeeId";
    
    /** Customer ID parameter */
    public static final String CUSTOMER_ID = "customerId";
    
    /** Package ID parameter */
    public static final String PACKAGE_ID = "packageId";
    
    // ===== ACTION NAMES =====
    /** Action to list customers */
    public static final String ACTION_LIST = "list";
    
    /** Action to add new customer */
    public static final String ACTION_ADD = "add";
    
    /** Action to edit customer */
    public static final String ACTION_EDIT = "edit";
    
    /** Action to delete customer */
    public static final String ACTION_DELETE = "delete";
    
    /** Action to view customer details */
    public static final String ACTION_VIEW = "view";
    
    /** Action to save customer */
    public static final String ACTION_SAVE = "save";
    
    // ===== JSP PAGE PATHS =====
    /** Login page path */
    public static final String PAGE_LOGIN = "/jsp/login.jsp";
    
    /** Dashboard page path */
    public static final String PAGE_DASHBOARD = "/jsp/admin/dashboard.jsp";
    
    /** Customer list page */
    public static final String PAGE_CUSTOMER_LIST = "/jsp/admin/customer/list.jsp";
    
    /** Customer add/edit form page */
    public static final String PAGE_CUSTOMER_FORM = "/jsp/admin/customer/form.jsp";
    
    /** Employee list page */
    public static final String PAGE_EMPLOYEE_LIST = "/jsp/admin/employee/list.jsp";
    
    /** Employee add/edit form page */
    public static final String PAGE_EMPLOYEE_FORM = "/jsp/admin/employee/form.jsp";
    
    // ===== ERROR MESSAGES =====
    /** Invalid login credentials */
    public static final String ERROR_INVALID_LOGIN = "Invalid username or password";
    
    /** Session expired */
    public static final String ERROR_SESSION_EXPIRED = "Your session has expired. Please login again.";
    
    /** Access denied */
    public static final String ERROR_ACCESS_DENIED = "You do not have permission to access this resource.";
    
    /** Database error */
    public static final String ERROR_DATABASE = "A database error occurred. Please try again later.";
    
    // ===== SUCCESS MESSAGES =====
    /** Customer added successfully */
    public static final String SUCCESS_CUSTOMER_ADDED = "Customer added successfully";
    
    /** Customer updated successfully */
    public static final String SUCCESS_CUSTOMER_UPDATED = "Customer updated successfully";
    
    /** Customer deleted successfully */
    public static final String SUCCESS_CUSTOMER_DELETED = "Customer deleted successfully";
    
    // ===== INPUT VALIDATION CONSTANTS =====
    /** Minimum password length */
    public static final int MIN_PASSWORD_LENGTH = 6;
    
    /** Maximum password length */
    public static final int MAX_PASSWORD_LENGTH = 20;
    
    /** Minimum customer name length */
    public static final int MIN_NAME_LENGTH = 2;
    
    /** Maximum customer name length */
    public static final int MAX_NAME_LENGTH = 100;
    
    /** Empty string constant */
    public static final String EMPTY_STRING = "";
}
