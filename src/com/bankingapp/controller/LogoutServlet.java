package com.bankingapp.controller;

import com.bankingapp.util.Constants;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Logout Servlet
 * 
 * Handles admin user logout functionality.
 * Invalidates the session and redirects to login page.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Handle GET requests - process logout
     * 
     * @param request The HTTP request
     * @param response The HTTP response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        
        try {
            // Get and invalidate session
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = "";
                Object adminObj = session.getAttribute(Constants.ADMIN_SESSION);
                if (adminObj != null) {
                    username = adminObj.toString();
                }
                
                session.invalidate();
                System.out.println("User logged out: " + username);
            }
            
            // Redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            
        } catch (Exception e) {
            System.err.println("Error in LogoutServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
