package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.service.AdminService;
import com.bankingapp.util.Constants;
import com.bankingapp.util.ValidationUtil;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Login Servlet
 * 
 * Handles admin user authentication and login functionality.
 * Validates credentials and creates session for authenticated users.
 * 
 * Request Parameters:
 * - username: Admin username
 * - password: Admin password
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private AdminService adminService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize service
        this.adminService = new AdminService();
    }
    
    /**
     * Handle GET requests - display login page
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        // Check if already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Constants.ADMIN_SESSION) != null) {
            // Redirect to dashboard if already logged in
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        // Forward to login page
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
        dispatcher.forward(request, response);
    }
    
    /**
     * Handle POST requests - process login
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            // Get parameters from request
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // Validate inputs
            if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)) {
                request.setAttribute("error", "Username and password are required");
                RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
                dispatcher.forward(request, response);
                return;
            }
            
            // Authenticate user
            Admin admin = adminService.authenticateAdmin(username, password);
            
            if (admin != null) {
                // Create session
                HttpSession session = request.getSession(true);
                session.setAttribute(Constants.ADMIN_SESSION, admin);
                session.setMaxInactiveInterval(Constants.SESSION_TIMEOUT * 60); // Set timeout in seconds
                
                System.out.println("User logged in: " + username);
                
                // Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                // Authentication failed
                request.setAttribute("error", Constants.ERROR_INVALID_LOGIN);
                RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
                dispatcher.forward(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in LoginServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", Constants.ERROR_DATABASE);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
            dispatcher.forward(request, response);
        }
    }
}
