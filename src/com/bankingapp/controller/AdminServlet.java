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
            // Get all admins
            List<Admin> admins = adminService.getAllAdmins();
            request.setAttribute("admins", admins);
            
            // Set admin in request for the included header
            Admin currentAdmin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (currentAdmin != null) {
                request.setAttribute("admin", currentAdmin);
            }
            
            // Forward to admin list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/admin/list.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error listing admins: " + e.getMessage());
            request.setAttribute("error", "Failed to retrieve admin list");
            
            // Set admin in request for the included header
            Admin currentAdmin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (currentAdmin != null) {
                request.setAttribute("admin", currentAdmin);
            }
            
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
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
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
            
            request.setAttribute("admin", admin);
            request.setAttribute("isEdit", true);
            
            // Note: admin is already being used for the current session admin, so this attribute
            // will be the admin being edited and will be available in the JSP
            
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
            
            // Validate required fields
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("error", "Username, password, and full name are required");
                showAddForm(request, response);
                return;
            }
            
            // Create admin object
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.setFullName(fullName);
            admin.setEmail(email);
            admin.setPhone(phone);
            admin.setActive(isActiveStr != null && isActiveStr.equals("on"));
            
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
                success = adminService.updateAdmin(admin);
                message = success ? "Admin updated successfully" : "Failed to update admin";
            }
            
            if (success) {
                request.setAttribute("success", message);
            } else {
                request.setAttribute("error", message);
            }
            
            // Redirect to admin list
            response.sendRedirect(request.getContextPath() + "/admin?action=list");
            
        } catch (Exception e) {
            System.err.println("Error saving admin: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error saving admin");
            showAddForm(request, response);
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
                request.setAttribute("error", "Cannot delete the currently logged-in admin");
                listAdmins(request, response);
                return;
            }
            
            boolean success = adminService.deleteAdmin(adminId);
            
            if (success) {
                request.setAttribute("success", "Admin deactivated successfully");
            } else {
                request.setAttribute("error", "Failed to delete admin");
            }
            
            // Redirect to admin list
            response.sendRedirect(request.getContextPath() + "/admin?action=list");
            
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
