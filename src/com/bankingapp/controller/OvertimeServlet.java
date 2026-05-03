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
import com.bankingapp.model.Overtime;
import com.bankingapp.service.OvertimeService;
import com.bankingapp.util.Constants;

/**
 * Overtime Servlet
 * Overtime record management and approval operations.
 *
 * @author Banking App Development Team
 * @version 1.0
 */
public class OvertimeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OvertimeService overtimeService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.overtimeService = new OvertimeService();
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
                    listOvertimes(request, response);
                    break;
                case Constants.ACTION_ADD:
                    showAddForm(request, response);
                    break;
                case Constants.ACTION_VIEW:
                    viewOvertime(request, response);
                    break;
                case "pending":
                    showPendingOvertimes(request, response);
                    break;
                default:
                    listOvertimes(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in OvertimeServlet: " + e.getMessage());
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
                saveOvertime(request, response);
            } else if ("approve".equals(action)) {
                approveOvertime(request, response);
            } else if ("reject".equals(action)) {
                rejectOvertime(request, response);
            } else {
                listOvertimes(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in OvertimeServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listOvertimes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Pick up flash messages from query parameters
            String msg = request.getParameter("success");
            if (msg != null) request.setAttribute("success", msg);
            msg = request.getParameter("error");
            if (msg != null) request.setAttribute("error", msg);
            
            String status = request.getParameter("status");
            List<Overtime> overtimes;

            if (status != null && !status.isEmpty()) {
                if ("PENDING".equals(status)) {
                    overtimes = overtimeService.getPendingOvertimeRecords();
                } else if ("APPROVED".equals(status)) {
                    overtimes = overtimeService.getApprovedOvertimeRecords();
                } else {
                    overtimes = overtimeService.getAllOvertimeRecords();
                }
            } else {
                overtimes = overtimeService.getAllOvertimeRecords();
            }

            request.setAttribute("overtimes", overtimes);

            // Forward to overtime list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/overtime/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error listing overtimes: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/overtime/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void viewOvertime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int overtimeId = Integer.parseInt(request.getParameter("id"));

            Overtime overtime = overtimeService.getOvertimeById(overtimeId);
            if (overtime == null) {
                request.setAttribute("error", "Overtime record not found");
                listOvertimes(request, response);
                return;
            }

            request.setAttribute("overtime", overtime);
            request.setAttribute("isView", true);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/overtime/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error viewing overtime: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showPendingOvertimes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            List<Overtime> overtimes = overtimeService.getPendingOvertimeRecords();
            request.setAttribute("overtimes", overtimes);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/overtime/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing pending overtimes: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void saveOvertime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            String overtimeDate = request.getParameter("overtimeDate");
            double hoursWorked = Double.parseDouble(request.getParameter("hoursWorked"));
            double hourlyRate = Double.parseDouble(request.getParameter("hourlyRate"));
            String overtimeType = request.getParameter("overtimeType");
            String remarks = request.getParameter("remarks");

            Overtime overtime = new Overtime();
            overtime.setEmployeeId(employeeId);
            overtime.setOvertimeDate(overtimeDate);
            overtime.setHoursWorked(hoursWorked);
            overtime.setHourlyRate(hourlyRate);
            overtime.setOvertimeType(overtimeType);
            overtime.setRemarks(remarks);

            boolean success = overtimeService.addOvertimeRecord(overtime);

            String message = success ? "Overtime record saved successfully" : "Failed to save overtime record";
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/overtime?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error saving overtime: " + e.getMessage());
            e.printStackTrace();
            String message = "Error saving overtime record";
            response.sendRedirect(request.getContextPath() + "/overtime?action=list&error=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));
        }
    }

    private void approveOvertime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int overtimeId = Integer.parseInt(request.getParameter("id"));

            boolean success = overtimeService.approveOvertime(overtimeId);

            String message = success ? "Overtime approved successfully" : "Failed to approve overtime";
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/overtime?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error approving overtime: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void rejectOvertime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int overtimeId = Integer.parseInt(request.getParameter("id"));

            boolean success = overtimeService.rejectOvertime(overtimeId);

            String message = success ? "Overtime rejected successfully" : "Failed to reject overtime";
            String redirectParam = success ? "success" : "error";
            response.sendRedirect(request.getContextPath() + "/overtime?action=list&" + redirectParam + "=" + 
                java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            System.err.println("Error rejecting overtime: " + e.getMessage());
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
