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
import com.bankingapp.model.Payment;
import com.bankingapp.model.BulkPaymentBatch;
import com.bankingapp.service.TransactionPaymentService;
import com.bankingapp.service.BulkPaymentService;
import com.bankingapp.util.Constants;

/**
 * Payment Servlet
 * Payment management operations including bulk payments.
 *
 * @author Banking App Development Team
 * @version 1.0
 */
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionPaymentService paymentService;
    private BulkPaymentService bulkPaymentService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.paymentService = new TransactionPaymentService();
        this.bulkPaymentService = new BulkPaymentService();
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
                    listPayments(request, response);
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
                case "bulk_salary":
                    showBulkSalaryForm(request, response);
                    break;
                case "bulk_overtime":
                    showBulkOvertimeForm(request, response);
                    break;
                case "batch_list":
                    listBatches(request, response);
                    break;
                case "batch_view":
                    viewBatch(request, response);
                    break;
                default:
                    listPayments(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in PaymentServlet: " + e.getMessage());
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
                savePayment(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deletePayment(request, response);
            } else if ("process_bulk_salary".equals(action)) {
                processBulkSalary(request, response);
            } else if ("process_bulk_overtime".equals(action)) {
                processBulkOvertime(request, response);
            } else if ("approve_batch".equals(action)) {
                approveBatch(request, response);
            } else if ("process_batch".equals(action)) {
                processPaymentBatch(request, response);
            } else if ("reject_batch".equals(action)) {
                rejectBatch(request, response);
            } else {
                listPayments(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in PaymentServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get all payments
            List<Payment> payments = paymentService.getAllPayments();
            request.setAttribute("payments", payments);

            // Get statistics
            int totalPayments = paymentService.getPaymentCount();
            int todayPayments = paymentService.getPaymentCountToday();
            double totalAmount = paymentService.getTotalPaymentsThisMonth();
            double todayAmount = paymentService.getTotalPaymentsAmountToday();

            request.setAttribute("totalPayments", totalPayments);
            request.setAttribute("todayPayments", todayPayments);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("todayAmount", todayAmount);

            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }

            // Forward to payment list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error listing payments: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            // Forward to payment form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int paymentId = Integer.parseInt(request.getParameter("id"));

            // Get payment by ID
            Payment payment = paymentService.getPaymentById(paymentId);
            if (payment == null) {
                request.setAttribute("error", "Payment not found");
                listPayments(request, response);
                return;
            }

            request.setAttribute("payment", payment);
            request.setAttribute("isEdit", true);

            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }

            // Forward to payment form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showViewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int paymentId = Integer.parseInt(request.getParameter("id"));

            // Get payment by ID
            Payment payment = paymentService.getPaymentById(paymentId);
            if (payment == null) {
                request.setAttribute("error", "Payment not found");
                listPayments(request, response);
                return;
            }

            request.setAttribute("payment", payment);
            request.setAttribute("isView", true);

            // Set admin in request for the included header
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }

            // Forward to payment form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing view form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void savePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get payment data from request
            String paymentIdStr = request.getParameter("paymentId");
            String customerIdStr = request.getParameter("customerId");
            String paymentType = request.getParameter("paymentType");
            String description = request.getParameter("description");
            String amountStr = request.getParameter("amount");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");

            // Create payment object
            Payment payment = new Payment();
            payment.setPaymentType(paymentType);
            payment.setPaymentDescription(description);
            payment.setAmount(Double.parseDouble(amountStr));
            payment.setPaymentStatus(status != null ? status : "PENDING");
            payment.setRemarks(notes);

            if (customerIdStr != null && !customerIdStr.isEmpty()) {
                payment.setCustomerId(Integer.parseInt(customerIdStr));
            }

            boolean success = false;
            String message = "";

            // Check if create or update
            if (paymentIdStr == null || paymentIdStr.isEmpty()) {
                // Create new payment
                success = paymentService.processPayment(payment);
                message = success ? "Payment processed successfully" : "Failed to process payment";
            } else {
                // Update existing payment
                payment.setPaymentId(Integer.parseInt(paymentIdStr));
                success = paymentService.updatePayment(payment);
                message = success ? "Payment updated successfully" : "Failed to update payment";
            }

            if (success) {
                request.setAttribute("success", message);
            } else {
                request.setAttribute("error", message);
            }

            // Redirect to payment list
            response.sendRedirect(request.getContextPath() + "/payment?action=list");

        } catch (Exception e) {
            System.err.println("Error saving payment: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error saving payment");
            listPayments(request, response);
        }
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int paymentId = Integer.parseInt(request.getParameter("id"));

            boolean success = paymentService.deletePayment(paymentId);

            if (success) {
                request.setAttribute("success", "Payment deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete payment");
            }

            // Redirect to payment list
            response.sendRedirect(request.getContextPath() + "/payment?action=list");

        } catch (Exception e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showBulkSalaryForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/bulk_salary.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error showing bulk salary form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showBulkOvertimeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/bulk_overtime.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error showing bulk overtime form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void processBulkSalary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            String batchName = request.getParameter("batchName");
            String paymentDate = request.getParameter("paymentDate");
            
            // Create batch
            int batchId = bulkPaymentService.createBulkSalaryBatch(batchName, paymentDate, admin.getAdminId());
            
            if (batchId > 0) {
                // Process payments
                int processed = bulkPaymentService.processBulkSalaryPayments(batchId, paymentDate);
                request.setAttribute("success", "Bulk salary batch created with " + processed + " payments");
                request.setAttribute("batchId", batchId);
            } else {
                request.setAttribute("error", "Failed to create salary batch");
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/bulk_salary_result.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error processing bulk salary: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error processing bulk salary: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/bulk_salary.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void processBulkOvertime(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            String batchName = request.getParameter("batchName");
            String paymentDate = request.getParameter("paymentDate");
            
            // Create batch
            int batchId = bulkPaymentService.createBulkOvertimeBatch(batchName, paymentDate, admin.getAdminId());
            
            if (batchId > 0) {
                // Process payments
                int processed = bulkPaymentService.processBulkOvertimePayments(batchId, paymentDate);
                request.setAttribute("success", "Bulk overtime batch created with " + processed + " payments");
                request.setAttribute("batchId", batchId);
            } else {
                request.setAttribute("error", "Failed to create overtime batch");
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/bulk_overtime_result.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error processing bulk overtime: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error processing bulk overtime: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/bulk_overtime.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listBatches(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            String batchType = request.getParameter("type");
            String status = request.getParameter("status");
            
            List<BulkPaymentBatch> batches;
            if (batchType != null && !batchType.isEmpty()) {
                batches = bulkPaymentService.getBatchesByType(batchType);
            } else if (status != null && !status.isEmpty()) {
                batches = bulkPaymentService.getBatchesByStatus(status);
            } else {
                batches = bulkPaymentService.getAllBatches();
            }
            
            request.setAttribute("batches", batches);
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/batch_list.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error listing batches: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void viewBatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            int batchId = Integer.parseInt(request.getParameter("id"));
            BulkPaymentBatch batch = bulkPaymentService.getBatchById(batchId);
            
            if (batch == null) {
                request.setAttribute("error", "Batch not found");
                listBatches(request, response);
                return;
            }
            
            request.setAttribute("batch", batch);
            Admin admin = (Admin) request.getSession().getAttribute(Constants.ADMIN_SESSION);
            if (admin != null) {
                request.setAttribute("admin", admin);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/payment/batch_view.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error viewing batch: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void approveBatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            int batchId = Integer.parseInt(request.getParameter("batchId"));
            boolean success = bulkPaymentService.approveBatch(batchId);
            
            if (success) {
                request.setAttribute("success", "Batch approved successfully");
            } else {
                request.setAttribute("error", "Failed to approve batch");
            }
            
            viewBatch(request, response);
        } catch (Exception e) {
            System.err.println("Error approving batch: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void processPaymentBatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            int batchId = Integer.parseInt(request.getParameter("batchId"));
            boolean success = bulkPaymentService.processBatch(batchId);
            
            if (success) {
                request.setAttribute("success", "Batch processed successfully");
            } else {
                request.setAttribute("error", "Failed to process batch");
            }
            
            viewBatch(request, response);
        } catch (Exception e) {
            System.err.println("Error processing batch: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void rejectBatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        try {
            int batchId = Integer.parseInt(request.getParameter("batchId"));
            boolean success = bulkPaymentService.rejectBatch(batchId);
            
            if (success) {
                request.setAttribute("success", "Batch rejected successfully");
            } else {
                request.setAttribute("error", "Failed to reject batch");
            }
            
            viewBatch(request, response);
        } catch (Exception e) {
            System.err.println("Error rejecting batch: " + e.getMessage());
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
