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
import com.bankingapp.model.Shift;
import com.bankingapp.service.PackageShiftService;
import com.bankingapp.util.Constants;

public class ShiftServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PackageShiftService shiftService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.shiftService = new PackageShiftService();
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
                    listShifts(request, response);
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
                    listShifts(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in ShiftServlet: " + e.getMessage());
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
                saveShift(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deleteShift(request, response);
            } else {
                listShifts(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in ShiftServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listShifts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Pick up flash messages from query parameters
            String msg = request.getParameter("success");
            if (msg != null) request.setAttribute("success", msg);
            msg = request.getParameter("error");
            if (msg != null) request.setAttribute("error", msg);
            
            // Get all shifts
            List<Shift> shifts = shiftService.getAllShifts();
            request.setAttribute("shifts", shifts);

            // Get statistics
            int totalShifts = shiftService.getShiftCount();
            int activeShifts = shiftService.getActiveShiftsCount();
            int todayShifts = shiftService.getTodayShiftsCount();

            request.setAttribute("totalShifts", totalShifts);
            request.setAttribute("activeShifts", activeShifts);
            request.setAttribute("todayShifts", todayShifts);

            // Forward to shift list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/shift/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error listing shifts: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/shift/list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Forward to shift form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/shift/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int shiftId = Integer.parseInt(request.getParameter("id"));

            // Get shift by ID
            Shift shift = shiftService.getShiftById(shiftId);
            if (shift == null) {
                request.setAttribute("error", "Shift not found");
                listShifts(request, response);
                return;
            }

            request.setAttribute("shift", shift);
            request.setAttribute("isEdit", true);

            // Forward to shift form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/shift/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showViewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int shiftId = Integer.parseInt(request.getParameter("id"));

            // Get shift by ID
            Shift shift = shiftService.getShiftById(shiftId);
            if (shift == null) {
                request.setAttribute("error", "Shift not found");
                listShifts(request, response);
                return;
            }

            request.setAttribute("shift", shift);
            request.setAttribute("isView", true);

            // Forward to shift form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/shift/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing view form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void saveShift(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get shift data from request
            String shiftIdStr = request.getParameter("shiftId");
            String shiftName = request.getParameter("shiftName");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String shiftType = request.getParameter("shiftType");
            String description = request.getParameter("description");
            String isActiveStr = request.getParameter("isActive");

            // Create shift object
            Shift shift = new Shift();
            shift.setShiftName(shiftName);
            shift.setStartTime(startTime);
            shift.setEndTime(endTime);
            shift.setShiftType(shiftType);
            shift.setDescription(description);
            shift.setActive(Boolean.parseBoolean(isActiveStr));

            boolean success = false;
            String message = "";

            // Check if create or update
            if (shiftIdStr == null || shiftIdStr.isEmpty()) {
                // Create new shift
                success = shiftService.createShift(shift);
                message = success ? "Shift created successfully" : "Failed to create shift";
            } else {
                // Update existing shift
                shift.setShiftId(Integer.parseInt(shiftIdStr));
                success = shiftService.updateShift(shift);
                message = success ? "Shift updated successfully" : "Failed to update shift";
            }

            // Redirect with flash message
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/shift?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error saving shift: " + e.getMessage());
            e.printStackTrace();
            String message = "Error saving shift";
            response.sendRedirect(request.getContextPath() + "/shift?action=list&error=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));
        }
    }

    private void deleteShift(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int shiftId = Integer.parseInt(request.getParameter("id"));

            boolean success = shiftService.deleteShift(shiftId);

            String message = success ? "Shift deleted successfully" : "Failed to delete shift";
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/shift?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error deleting shift: " + e.getMessage());
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