package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.util.Constants;
import com.bankingapp.service.CustomerService;
import com.bankingapp.service.EmployeeService;
import com.bankingapp.service.TransactionPaymentService;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Dashboard Servlet
 * 
 * Handles admin dashboard page requests.
 * Requires authenticated session to access.
 * Provides dashboard statistics and metrics.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class DashboardServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private CustomerService customerService;
    private EmployeeService employeeService;
    private TransactionPaymentService transactionPaymentService;
    
    /**
     * Initialize services
     */
    @Override
    public void init() throws ServletException {
        super.init();
        this.customerService = new CustomerService();
        this.employeeService = new EmployeeService();
        this.transactionPaymentService = new TransactionPaymentService();
    }
    
    /**
     * Handle GET requests - display dashboard with statistics
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            Admin admin = null;
            
            if (session != null) {
                admin = (Admin) session.getAttribute(Constants.ADMIN_SESSION);
            }
            
            if (admin == null) {
                // User not logged in, redirect to login
                response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
                return;
            }
            
            // Get dashboard statistics
            int customerCount = customerService.getCustomerCount();
            int employeeCount = employeeService.getEmployeeCount();
            int transactionCount = transactionPaymentService.getTransactionCount();
            double totalPayments = transactionPaymentService.getTotalPaymentsThisMonth();
            
            // Set statistics as request attributes
            request.setAttribute("customerCount", customerCount);
            request.setAttribute("employeeCount", employeeCount);
            request.setAttribute("transactionCount", transactionCount);
            request.setAttribute("totalPayments", totalPayments);
            
            // Admin is logged in, forward to dashboard page
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_DASHBOARD);
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in DashboardServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
