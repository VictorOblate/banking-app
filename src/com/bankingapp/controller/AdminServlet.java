package com.bankingapp.controller;

import com.bankingapp.model.Admin;
import com.bankingapp.service.AdminService;
import com.bankingapp.util.Constants;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;

/**
 * Admin Servlet
 * 
 * Handles all admin management operations.
 * Supports CRUD operations on admin users (only for super-admin).
 * 
 * Request Parameters:
 * - action: Operation to perform (list, add, edit, save, delete)
 * - adminId: Admin ID for edit/delete
 * - username, password, fullName, email, phone: Admin data
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class AdminServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private AdminService adminService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.adminService = new AdminService();
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
                    listAdmins(request, response);
                    break;
                case Constants.ACTION_ADD:
                    showAddForm(request, response);
                    break;
                case Constants.ACTION_EDIT:
                    showEditForm(request, response);
                    break;
                default:
                    listAdmins(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in AdminServlet: " + e.getMessage());
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
                saveAdmin(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deleteAdmin(request, response);
            } else {
                listAdmins(request, response);
            }
            
        } catch (Exception e) {
            System.err.println("Error in AdminServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Display list of all admins
     */
    private void listAdmins(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            // Pick up flash messages from query parameters
            String msg = request.getParameter("success");
            if (msg != null) request.setAttribute("success", msg);
            msg = request.getParameter("error");
            if (msg != null) request.setAttribute("error", msg);
            
            // Get all admins
            List<Admin> admins = adminService.getAllAdmins();
            request.setAttribute("admins", admins);
            
            // Forward to admin list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/admin/list.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error listing admins: " + e.getMessage());
            request.setAttribute("error", "Failed to retrieve admin list");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/admin/list.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    /**
     * Show add admin form
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            // Forward to admin form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/admin/form.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Show edit admin form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            int adminId = Integer.parseInt(request.getParameter("adminId"));
            
            // Get admin by ID
            Admin admin = adminService.getAdminById(adminId);
            if (admin == null) {
                request.setAttribute("error", "Admin not found");
                listAdmins(request, response);
                return;
            }
            
            request.setAttribute("editAdmin", admin);
            request.setAttribute("isEdit", true);
            
            // Forward to admin form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/admin/form.jsp");
            dispatcher.forward(request, response);
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid admin ID: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Save admin (create or update)
     */
    private void saveAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            // Get admin data from request
            String adminIdStr = request.getParameter("adminId");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String isActiveStr = request.getParameter("isActive");
            
            // Validate required fields for add
            if (adminIdStr == null || adminIdStr.isEmpty()) {
                if (username == null || username.trim().isEmpty() ||
                    password == null || password.trim().isEmpty() ||
                    fullName == null || fullName.trim().isEmpty()) {
                    String message = "Username, password, and full name are required";
                    response.sendRedirect(request.getContextPath() + "/admin?action=add&error=" + 
                        java.net.URLEncoder.encode(message, "UTF-8"));
                    return;
                }
            }
            
            // Create admin object
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.setFullName(fullName);
            admin.setEmail(email);
            admin.setPhone(phone);
            // Consistent isActive parsing
            boolean isActive = "true".equalsIgnoreCase(isActiveStr)
                    || "on".equalsIgnoreCase(isActiveStr)
                    || "yes".equalsIgnoreCase(isActiveStr);
            admin.setActive(isActive);
            
            boolean success = false;
            String message = "";
            
            // Check if create or update
            if (adminIdStr == null || adminIdStr.isEmpty()) {
                // Create new admin
                success = adminService.addAdmin(admin);
                message = success ? "Admin created successfully" : "Failed to create admin";
            } else {
                // Update existing admin
                admin.setAdminId(Integer.parseInt(adminIdStr));
                // If password is blank, fetch existing password
                if (password == null || password.trim().isEmpty()) {
                    Admin existing = adminService.getAdminById(Integer.parseInt(adminIdStr));
                    if (existing != null) {
                        admin.setPassword(existing.getPassword());
                    }
                }
                success = adminService.updateAdmin(admin);
                message = success ? "Admin updated successfully" : "Failed to update admin";
            }
            
            // Redirect with flash message
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/admin?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));
            
        } catch (Exception e) {
            System.err.println("Error saving admin: " + e.getMessage());
            e.printStackTrace();
            String message = "Error saving admin";
            response.sendRedirect(request.getContextPath() + "/admin?action=list&error=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));
        }
    }
    
    /**
     * Delete admin
     */
    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        try {
            int adminId = Integer.parseInt(request.getParameter("adminId"));
            
            // Prevent deleting current admin
            HttpSession session = request.getSession(false);
            Admin currentAdmin = (Admin) session.getAttribute(Constants.ADMIN_SESSION);
            if (currentAdmin != null && currentAdmin.getAdminId() == adminId) {
                String message = "Cannot delete the currently logged-in admin";
                response.sendRedirect(request.getContextPath() + "/admin?action=list&error=" + 
                    java.net.URLEncoder.encode(message, "UTF-8"));
                return;
            }
            
            boolean success = adminService.deleteAdmin(adminId);
            
            String message = success ? "Admin deactivated successfully" : "Failed to delete admin";
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/admin?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid admin ID: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error deleting admin: " + e.getMessage());
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
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        
        return true;
    }
}
