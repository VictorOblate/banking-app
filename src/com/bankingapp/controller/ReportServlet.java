package com.bankingapp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bankingapp.model.Admin;
import com.bankingapp.service.TransactionPaymentService;
import com.bankingapp.service.PackageShiftService;
import com.bankingapp.service.CustomerService;
import com.bankingapp.service.EmployeeService;
import com.bankingapp.util.Constants;

public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionPaymentService transactionService;
    private PackageShiftService packageShiftService;
    private CustomerService customerService;
    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.transactionService = new TransactionPaymentService();
        this.packageShiftService = new PackageShiftService();
        this.customerService = new CustomerService();
        this.employeeService = new EmployeeService();
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
                    listReports(request, response);
                    break;
                case "form":
                    showReportForm(request, response);
                    break;
                case "generate":
                    generateReport(request, response);
                    break;
                case "export":
                    exportReport(request, response);
                    break;
                default:
                    listReports(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in ReportServlet: " + e.getMessage());
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

            if ("generate".equals(action)) {
                generateReport(request, response);
            } else if ("export".equals(action)) {
                exportReport(request, response);
            } else {
                listReports(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in ReportServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get basic statistics for dashboard
            Map<String, Object> stats = new HashMap<>();

            // Transaction statistics
            int totalTransactions = transactionService.getTransactionCount();
            stats.put("totalTransactions", totalTransactions);

            // Payment statistics
            int totalPayments = transactionService.getPaymentCount();
            stats.put("totalPayments", totalPayments);

            // Customer statistics
            int totalCustomers = customerService.getCustomerCount();
            stats.put("totalCustomers", totalCustomers);

            // Employee statistics
            int totalEmployees = employeeService.getEmployeeCount();
            stats.put("totalEmployees", totalEmployees);

            // Package statistics
            int totalPackages = packageShiftService.getPackageCount();
            stats.put("totalPackages", totalPackages);

            // Shift statistics
            int totalShifts = packageShiftService.getShiftCount();
            stats.put("totalShifts", totalShifts);

            request.setAttribute("stats", stats);

            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }

            // Forward to report list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/report/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error listing reports: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/report/list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showReportForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Forward to report form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/report/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing report form: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/report/list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            String reportType = request.getParameter("reportType");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            Map<String, Object> reportData = new HashMap<>();
            reportData.put("reportType", reportType);
            reportData.put("startDate", startDate);
            reportData.put("endDate", endDate);

            // Generate report based on type
            switch (reportType) {
                case "transactions":
                    // Get transaction report data
                    List<?> transactionData = transactionService.getAllTransactions();
                    reportData.put("data", transactionData);
                    reportData.put("title", "Transaction Report");
                    break;
                case "payments":
                    // Get payment report data
                    List<?> paymentData = transactionService.getAllPayments();
                    reportData.put("data", paymentData);
                    reportData.put("title", "Payment Report");
                    break;
                case "customers":
                    // Get customer report data
                    List<?> customerData = customerService.getAllCustomers();
                    reportData.put("data", customerData);
                    reportData.put("title", "Customer Report");
                    break;
                case "employees":
                    // Get employee report data
                    List<?> employeeData = employeeService.getAllEmployees();
                    reportData.put("data", employeeData);
                    reportData.put("title", "Employee Report");
                    break;
                case "packages":
                    // Get package report data
                    List<?> packageData = packageShiftService.getAllPackages();
                    reportData.put("data", packageData);
                    reportData.put("title", "Package Report");
                    break;
                case "shifts":
                    // Get shift report data
                    List<?> shiftData = packageShiftService.getAllShifts();
                    reportData.put("data", shiftData);
                    reportData.put("title", "Shift Report");
                    break;
                default:
                    reportData.put("error", "Invalid report type");
            }

            request.setAttribute("reportData", reportData);
            request.setAttribute("isGenerated", true);

            // Forward to report list page with generated data
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/report/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error generating report");
            listReports(request, response);
        }
    }

    private void exportReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            String reportType = request.getParameter("reportType");
            String format = request.getParameter("format"); // csv, pdf, excel

            // Set response headers for file download
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + reportType + "_report.csv\"");

            // TODO: Implement actual export functionality
            // For now, just return a simple CSV
            response.getWriter().write("Report Type: " + reportType + "\n");
            response.getWriter().write("Format: " + format + "\n");
            response.getWriter().write("Status: Export functionality to be implemented\n");

        } catch (Exception e) {
            System.err.println("Error exporting report: " + e.getMessage());
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