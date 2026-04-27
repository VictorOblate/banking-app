<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Payment" %>
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
    %>View Payment<% } else if (isEdit != null && isEdit) { %>Edit Payment<% } else { %>Add Payment<% } %> - Basotho Ownership Bank</title>
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
                %>View Payment<% } else if (isEditMode != null && isEditMode) { %>Edit Payment<% } else { %>Add New Payment<% } %></h1>
                <p class="text-muted"><%
                    if (isViewMode != null && isViewMode) {
                %>View payment details and processing information.<% } else if (isEditMode != null && isEditMode) { %>Update payment information and status.<% } else { %>Process a new payment with complete details.<% } %></p>
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

            <!-- Payment Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M20 4H4c-1.11 0-1.99.89-1.99 2L2 18c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V6c0-1.11-.89-2-2-2zm0 14H4v-6h16v6zm0-10H4V6h16v2z"/>
                    </svg>
                    Payment Information
                </div>

                <%
                    Payment payment = (Payment) request.getAttribute("payment");
                    Boolean isEditMode2 = (Boolean) request.getAttribute("isEdit");
                    Boolean isViewMode2 = (Boolean) request.getAttribute("isView");
                    isEditMode2 = isEditMode2 != null ? isEditMode2 : false;
                    isViewMode2 = isViewMode2 != null ? isViewMode2 : false;
                    boolean isReadOnly = isViewMode2;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/payment?action=save">
                    <% if (isEditMode2 && payment != null) { %>
                    <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">
                    <% } %>

                    <!-- Payment Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="paymentType">Payment Type <span style="color: var(--error-red);">*</span></label>
                            <select id="paymentType" name="paymentType" required <%= isReadOnly ? "disabled" : "" %>>
                                <option value="">Select Type...</option>
                                <option value="SALARY" <%= isEditMode2 && payment != null && "SALARY".equals(payment.getPaymentType()) ? "selected" : "" %>>Salary</option>
                                <option value="BONUS" <%= isEditMode2 && payment != null && "BONUS".equals(payment.getPaymentType()) ? "selected" : "" %>>Bonus</option>
                                <option value="ALLOWANCE" <%= isEditMode2 && payment != null && "ALLOWANCE".equals(payment.getPaymentType()) ? "selected" : "" %>>Allowance</option>
                                <option value="ADVANCE" <%= isEditMode2 && payment != null && "ADVANCE".equals(payment.getPaymentType()) ? "selected" : "" %>>Advance</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="amount">Amount (LSL) <span style="color: var(--error-red);">*</span></label>
                            <input type="number" id="amount" name="amount" step="0.01" min="0" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && payment != null ? payment.getAmount() : "" %>">
                        </div>
                    </div>

                    <!-- Customer/Employee Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="customerId">Customer ID</label>
                            <input type="number" id="customerId" name="customerId" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && payment != null ? payment.getCustomerId() : "" %>">
                            <small class="text-muted">For customer payments</small>
                        </div>
                        <div class="form-group">
                            <label for="employeeId">Employee ID</label>
                            <input type="number" id="employeeId" name="employeeId" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && payment != null ? payment.getEmployeeId() : "" %>">
                            <small class="text-muted">For employee salary payments</small>
                        </div>
                    </div>

                    <!-- Payment Description -->
                    <div class="form-group mb-4">
                        <label for="paymentDescription">Description <span style="color: var(--error-red);">*</span></label>
                        <textarea id="paymentDescription" name="paymentDescription" rows="3" required <%= isReadOnly ? "readonly" : "" %>><%= isEditMode2 && payment != null && payment.getPaymentDescription() != null ? payment.getPaymentDescription() : "" %></textarea>
                    </div>

                    <!-- Payment Method and Date -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="paymentMethod">Payment Method</label>
                            <input type="text" id="paymentMethod" name="paymentMethod" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && payment != null && payment.getPaymentMethod() != null ? payment.getPaymentMethod() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="paymentDate">Payment Date <span style="color: var(--error-red);">*</span></label>
                            <input type="date" id="paymentDate" name="paymentDate" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && payment != null ? payment.getPaymentDate() : "" %>">
                        </div>
                    </div>

                    <!-- Status and Remarks -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="paymentStatus">Status</label>
                            <select id="paymentStatus" name="paymentStatus" <%= isReadOnly ? "disabled" : "" %>>
                                <option value="PENDING" <%= isEditMode2 && payment != null && "PENDING".equals(payment.getPaymentStatus()) ? "selected" : "" %>>Pending</option>
                                <option value="PROCESSED" <%= isEditMode2 && payment != null && "PROCESSED".equals(payment.getPaymentStatus()) ? "selected" : "" %>>Processed</option>
                                <option value="FAILED" <%= isEditMode2 && payment != null && "FAILED".equals(payment.getPaymentStatus()) ? "selected" : "" %>>Failed</option>
                                <option value="REVERSED" <%= isEditMode2 && payment != null && "REVERSED".equals(payment.getPaymentStatus()) ? "selected" : "" %>>Reversed</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="remarks">Remarks</label>
                            <input type="text" id="remarks" name="remarks" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && payment != null && payment.getRemarks() != null ? payment.getRemarks() : "" %>">
                        </div>
                    </div>

                    <!-- Reference Number -->
                    <div class="form-group mb-4">
                        <label for="referenceNumber">Reference Number</label>
                        <input type="text" id="referenceNumber" name="referenceNumber" <%= isReadOnly ? "readonly" : "" %>
                               value="<%= isEditMode2 && payment != null && payment.getReferenceNumber() != null ? payment.getReferenceNumber() : "" %>">
                    </div>

                    <!-- Form Actions -->
                    <% if (!isViewMode2) { %>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V7h10v2z"/>
                            </svg>
                            <%= isEditMode2 ? "Update Payment" : "Process Payment" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/payment?action=list" class="btn btn-secondary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>
                    <% } else { %>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/payment?action=list" class="btn btn-primary">
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