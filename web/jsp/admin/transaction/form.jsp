<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Transaction" %>
<%@ page import="com.bankingapp.util.Constants" %>
<%
    // Check if user is logged in
    com.bankingapp.model.Admin admin = (com.bankingapp.model.Admin) session.getAttribute(Constants.ADMIN_SESSION);
    if (admin == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%
        Boolean isEdit = (Boolean) request.getAttribute("isEdit");
        Boolean isView = (Boolean) request.getAttribute("isView");
        if (isView != null && isView) {
    %>View Transaction<% } else if (isEdit != null && isEdit) { %>Edit Transaction<% } else { %>Add Transaction<% } %> - Basotho Ownership Bank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="/jsp/components/header.jsp" %>

    <!-- Main Content -->
    <main class="container">
        <div class="main-content">
            <div class="mb-4">
                <h1><%
                    Boolean isEditMode = (Boolean) request.getAttribute("isEdit");
                    Boolean isViewMode = (Boolean) request.getAttribute("isView");
                    if (isViewMode != null && isViewMode) {
                %>View Transaction<% } else if (isEditMode != null && isEditMode) { %>Edit Transaction<% } else { %>Add New Transaction<% } %></h1>
                <p class="text-muted"><%
                    if (isViewMode != null && isViewMode) {
                %>View transaction details and history.<% } else if (isEditMode != null && isEditMode) { %>Update transaction information and status.<% } else { %>Record a new transaction with complete details.<% } %></p>
            </div>

            <!-- Messages -->
            <%
                String error = (String) request.getAttribute("error");
                String success = (String) request.getAttribute("success");
                if (error != null) {
            %>
            <div class="alert alert-error">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
                </svg>
                <%= error %>
            </div>
            <%
                }
                if (success != null) {
            %>
            <div class="alert alert-success">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
                </svg>
                <%= success %>
            </div>
            <%
                }
            %>

            <!-- Transaction Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M7 2v11h3v9l7-12h-4l4-8z"/>
                    </svg>
                    Transaction Information
                </div>

                <%
                    Transaction transaction = (Transaction) request.getAttribute("transaction");
                    Boolean isEditMode2 = (Boolean) request.getAttribute("isEdit");
                    Boolean isViewMode2 = (Boolean) request.getAttribute("isView");
                    isEditMode2 = isEditMode2 != null ? isEditMode2 : false;
                    isViewMode2 = isViewMode2 != null ? isViewMode2 : false;
                    boolean isReadOnly = isViewMode2;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/transaction?action=save">
                    <% if (isEditMode2 && transaction != null) { %>
                    <input type="hidden" name="transactionId" value="<%= transaction.getTransactionId() %>">
                    <% } %>

                    <!-- Transaction Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="transactionType">Transaction Type <span style="color: var(--error-red);">*</span></label>
                            <select id="transactionType" name="transactionType" required <%= isReadOnly ? "disabled" : "" %>>
                                <option value="">Select Type...</option>
                                <option value="DEPOSIT" <%= isEditMode2 && transaction != null && "DEPOSIT".equals(transaction.getTransactionType()) ? "selected" : "" %>>Deposit</option>
                                <option value="WITHDRAWAL" <%= isEditMode2 && transaction != null && "WITHDRAWAL".equals(transaction.getTransactionType()) ? "selected" : "" %>>Withdrawal</option>
                                <option value="TRANSFER" <%= isEditMode2 && transaction != null && "TRANSFER".equals(transaction.getTransactionType()) ? "selected" : "" %>>Transfer</option>
                                <option value="PAYMENT" <%= isEditMode2 && transaction != null && "PAYMENT".equals(transaction.getTransactionType()) ? "selected" : "" %>>Payment</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="amount">Amount (LSL) <span style="color: var(--error-red);">*</span></label>
                            <input type="number" id="amount" name="amount" step="0.01" min="0" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && transaction != null ? transaction.getAmount() : "" %>">
                        </div>
                    </div>

                    <!-- Customer Information -->
                    <div class="form-group mb-4">
                        <label for="customerId">Customer ID</label>
                        <input type="number" id="customerId" name="customerId" <%= isReadOnly ? "readonly" : "" %>
                               value="<%= isEditMode2 && transaction != null ? transaction.getCustomerId() : "" %>">
                        <small class="text-muted">Leave empty for system transactions</small>
                    </div>

                    <!-- Transaction Description -->
                    <div class="form-group mb-4">
                        <label for="description">Description <span style="color: var(--error-red);">*</span></label>
                        <textarea id="description" name="description" rows="3" required <%= isReadOnly ? "readonly" : "" %>><%= isEditMode2 && transaction != null && transaction.getDescription() != null ? transaction.getDescription() : "" %></textarea>
                    </div>

                    <!-- Status and Notes -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="status">Status</label>
                            <select id="status" name="status" <%= isReadOnly ? "disabled" : "" %>>
                                <option value="PENDING" <%= isEditMode2 && transaction != null && "PENDING".equals(transaction.getStatus()) ? "selected" : "" %>>Pending</option>
                                <option value="COMPLETED" <%= isEditMode2 && transaction != null && "COMPLETED".equals(transaction.getStatus()) ? "selected" : "" %>>Completed</option>
                                <option value="FAILED" <%= isEditMode2 && transaction != null && "FAILED".equals(transaction.getStatus()) ? "selected" : "" %>>Failed</option>
                                <option value="CANCELLED" <%= isEditMode2 && transaction != null && "CANCELLED".equals(transaction.getStatus()) ? "selected" : "" %>>Cancelled</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="notes">Notes</label>
                            <input type="text" id="notes" name="notes" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && transaction != null && transaction.getNotes() != null ? transaction.getNotes() : "" %>">
                        </div>
                    </div>

                    <!-- Form Actions -->
                    <% if (!isViewMode2) { %>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V7h10v2z"/>
                            </svg>
                            <%= isEditMode2 ? "Update Transaction" : "Record Transaction" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/transaction?action=list" class="btn btn-secondary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>
                    <% } else { %>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/transaction?action=list" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Back to List
                        </a>
                    </div>
                    <% } %>
                </form>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>