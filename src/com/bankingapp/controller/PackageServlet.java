package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.util.Constants;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Package Servlet
 * Package management operations placeholder.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class PackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        handleRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        handleRequest(request, response);
    }
    
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.ADMIN_SESSION) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }
        // Placeholder for package management
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/package/list.jsp");
        dispatcher.forward(request, response);
    }
}

/**
 * Shift Servlet
 * Shift management operations placeholder.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
class ShiftServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        handleRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        handleRequest(request, response);
    }
    
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.ADMIN_SESSION) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }
        // Placeholder for shift management
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/shift/list.jsp");
        dispatcher.forward(request, response);
    }
}
