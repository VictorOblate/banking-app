package com.bankingapp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bankingapp.model.Admin;
import com.bankingapp.model.Package;
import com.bankingapp.service.PackageShiftService;
import com.bankingapp.util.Constants;

/**
 * Package Servlet
 * Package management operations.
 *
 * @author Banking App Development Team
 * @version 1.0
 */
public class PackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PackageShiftService packageService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.packageService = new PackageShiftService();
    }

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
                    listPackages(request, response);
                    break;
                case Constants.ACTION_ADD:
                    showAddForm(request, response);
                    break;
                case Constants.ACTION_EDIT:
                    showEditForm(request, response);
                    break;
                case Constants.ACTION_VIEW:
                    showViewForm(request, response);
                    break;
                default:
                    listPackages(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in PackageServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Check if user is logged in
            if (!isUserLoggedIn(request, response)) {
                return;
            }

            String action = request.getParameter(Constants.ACTION);

            if (Constants.ACTION_SAVE.equals(action)) {
                savePackage(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deletePackage(request, response);
            } else {
                listPackages(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in PackageServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listPackages(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Pick up flash messages from query parameters
            String msg = request.getParameter("success");
            if (msg != null) request.setAttribute("success", msg);
            msg = request.getParameter("error");
            if (msg != null) request.setAttribute("error", msg);
            
            // Get all packages (active and inactive) for admin management
            List<Package> packages = packageService.getAllPackagesForManagement();
            request.setAttribute("packages", packages);

            // Get statistics
            int totalPackages = packages.size();
            int activePackages = (int) packages.stream().filter(p -> p.isActive()).count();
            int inactivePackages = totalPackages - activePackages;

            request.setAttribute("totalPackages", totalPackages);
            request.setAttribute("activePackages", activePackages);
            request.setAttribute("inactivePackages", inactivePackages);

            // Forward to package list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/package/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error listing packages: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/package/list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Forward to package form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/package/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int packageId = Integer.parseInt(request.getParameter("id"));

            // Get package by ID
            Package pkg = packageService.getPackageById(packageId);
            if (pkg == null) {
                request.setAttribute("error", "Package not found");
                listPackages(request, response);
                return;
            }

            request.setAttribute("package", pkg);
            request.setAttribute("isEdit", true);

            // Forward to package form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/package/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showViewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int packageId = Integer.parseInt(request.getParameter("id"));

            // Get package by ID
            Package pkg = packageService.getPackageById(packageId);
            if (pkg == null) {
                request.setAttribute("error", "Package not found");
                listPackages(request, response);
                return;
            }

            request.setAttribute("package", pkg);
            request.setAttribute("isView", true);

            // Forward to package form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/package/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing view form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void savePackage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get package data from request
            String packageIdStr = request.getParameter("packageId");
            String packageName = request.getParameter("packageName");
            String packageType = request.getParameter("packageType");
            String description = request.getParameter("description");
            String benefits = request.getParameter("benefits");
            String monthlyFeeStr = request.getParameter("monthlyFee");
            String annualFeeStr = request.getParameter("annualFee");
            String isActiveStr = request.getParameter("isActive");

            // Create package object
            Package pkg = new Package();
            pkg.setPackageName(packageName);
            pkg.setPackageType(packageType);
            pkg.setDescription(description);
            pkg.setBenefits(benefits);
            pkg.setMonthlyFee(monthlyFeeStr != null && !monthlyFeeStr.isEmpty() ? Double.parseDouble(monthlyFeeStr) : 0.0);
            pkg.setAnnualFee(annualFeeStr != null && !annualFeeStr.isEmpty() ? Double.parseDouble(annualFeeStr) : 0.0);
            // Consistent isActive parsing
            boolean isActive = "true".equalsIgnoreCase(isActiveStr)
                    || "on".equalsIgnoreCase(isActiveStr)
                    || "yes".equalsIgnoreCase(isActiveStr);
            pkg.setActive(isActive);

            boolean success = false;
            String message = "";

            // Check if create or update
            if (packageIdStr == null || packageIdStr.isEmpty()) {
                // Create new package
                success = packageService.createPackage(pkg);
                message = success ? "Package created successfully" : "Failed to create package";
            } else {
                // Update existing package
                pkg.setPackageId(Integer.parseInt(packageIdStr));
                success = packageService.updatePackage(pkg);
                message = success ? "Package updated successfully" : "Failed to update package";
            }

            // Redirect with flash message
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/package?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error saving package: " + e.getMessage());
            e.printStackTrace();
            String message = "Error saving package";
            response.sendRedirect(request.getContextPath() + "/package?action=list&error=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));
        }
    }

    private void deletePackage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int packageId = Integer.parseInt(request.getParameter("id"));

            boolean success = packageService.deletePackage(packageId);

            String message = success ? "Package deleted successfully" : "Failed to delete package";
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/package?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error deleting package: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

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
