package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.util.Constants;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Dashboard Servlet
 * 
 * Handles admin dashboard page requests.
 * Requires authenticated session to access.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class DashboardServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Handle GET requests - display dashboard
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
