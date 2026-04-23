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
import com.bankingapp.model.Transaction;
import com.bankingapp.service.TransactionPaymentService;
import com.bankingapp.util.Constants;

public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionPaymentService transactionService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.transactionService = new TransactionPaymentService();
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
                    listTransactions(request, response);
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
                    listTransactions(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in TransactionServlet: " + e.getMessage());
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
                saveTransaction(request, response);
            } else if (Constants.ACTION_DELETE.equals(action)) {
                deleteTransaction(request, response);
            } else {
                listTransactions(request, response);
            }

        } catch (Exception e) {
            System.err.println("Error in TransactionServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void listTransactions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get all transactions
            List<Transaction> transactions = transactionService.getAllTransactions();
            request.setAttribute("transactions", transactions);

            // Get statistics
            int totalTransactions = transactionService.getTransactionCount();
            int todayTransactions = 0; // TODO: Implement today count
            double totalAmount = 0.0; // TODO: Implement total amount
            double todayAmount = 0.0; // TODO: Implement today amount

            request.setAttribute("totalTransactions", totalTransactions);
            request.setAttribute("todayTransactions", todayTransactions);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("todayAmount", todayAmount);

            // Forward to transaction list page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/transaction/list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error listing transactions: " + e.getMessage());
            request.setAttribute("error", Constants.ERROR_DATABASE);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/transaction/list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Forward to transaction form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/transaction/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing add form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int transactionId = Integer.parseInt(request.getParameter("id"));

            // Get transaction by ID
            Transaction transaction = transactionService.getTransactionById(transactionId);
            if (transaction == null) {
                request.setAttribute("error", "Transaction not found");
                listTransactions(request, response);
                return;
            }

            request.setAttribute("transaction", transaction);
            request.setAttribute("isEdit", true);

            // Forward to transaction form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/transaction/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing edit form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void showViewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int transactionId = Integer.parseInt(request.getParameter("id"));

            // Get transaction by ID
            Transaction transaction = transactionService.getTransactionById(transactionId);
            if (transaction == null) {
                request.setAttribute("error", "Transaction not found");
                listTransactions(request, response);
                return;
            }

            request.setAttribute("transaction", transaction);
            request.setAttribute("isView", true);

            // Forward to transaction form page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin/transaction/form.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.err.println("Error showing view form: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void saveTransaction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            // Get transaction data from request
            String transactionIdStr = request.getParameter("transactionId");
            String customerIdStr = request.getParameter("customerId");
            String transactionType = request.getParameter("transactionType");
            String description = request.getParameter("description");
            String amountStr = request.getParameter("amount");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");

            // Create transaction object
            Transaction transaction = new Transaction();
            transaction.setTransactionType(transactionType);
            transaction.setDescription(description);
            transaction.setAmount(Double.parseDouble(amountStr));
            transaction.setStatus(status != null ? status : "PENDING");
            transaction.setNotes(notes);

            if (customerIdStr != null && !customerIdStr.isEmpty()) {
                transaction.setCustomerId(Integer.parseInt(customerIdStr));
            }

            boolean success = false;
            String message = "";

            // Check if create or update
            if (transactionIdStr == null || transactionIdStr.isEmpty()) {
                // Create new transaction
                success = transactionService.recordTransaction(transaction);
                message = success ? "Transaction recorded successfully" : "Failed to record transaction";
            } else {
                // Update existing transaction
                transaction.setTransactionId(Integer.parseInt(transactionIdStr));
                success = transactionService.updateTransaction(transaction);
                message = success ? "Transaction updated successfully" : "Failed to update transaction";
            }

            if (success) {
                request.setAttribute("success", message);
            } else {
                request.setAttribute("error", message);
            }

            // Redirect to transaction list
            response.sendRedirect(request.getContextPath() + "/transaction?action=list");

        } catch (Exception e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error saving transaction");
            listTransactions(request, response);
        }
    }

    private void deleteTransaction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        try {
            int transactionId = Integer.parseInt(request.getParameter("id"));

            boolean success = transactionService.deleteTransaction(transactionId);

            if (success) {
                request.setAttribute("success", "Transaction deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete transaction");
            }

            // Redirect to transaction list
            response.sendRedirect(request.getContextPath() + "/transaction?action=list");

        } catch (Exception e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
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