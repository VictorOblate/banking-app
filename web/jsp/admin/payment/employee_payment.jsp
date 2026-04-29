<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Employee" %>
<%@ page import="com.bankingapp.service.EmployeeService" %>
<%@ page import="com.bankingapp.util.Constants" %>
<%
    // Check if user is logged in
    com.bankingapp.model.Admin admin = (com.bankingapp.model.Admin) session.getAttribute(Constants.ADMIN_SESSION);
    if (admin == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    // Get all employees for dropdown
    EmployeeService employeeService = new EmployeeService();
    List<Employee> employees = employeeService.getAllEmployees();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Payment - Basotho Ownership Bank</title>
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
                <h1>Employee Payment</h1>
                <p class="text-muted">Process individual employee payments for salary, allowances, or other compensation.</p>
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
                        <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm3.5-9c.83 0 1.5-.67 1.5-1.5S16.33 8 15.5 8 14 8.67 14 9.5s.67 1.5 1.5 1.5zm-7 0c.83 0 1.5-.67 1.5-1.5S9.33 8 8.5 8 7 8.67 7 9.5 7.67 11 8.5 11zm3.5 6.5c2.33 0 4.31-1.46 5.11-3.5H6.89c.8 2.04 2.78 3.5 5.11 3.5z"/>
                    </svg>
                    Employee Payment Information
                </div>

                <form method="POST" action="${pageContext.request.contextPath}/payment?action=employee_payment">
                    <!-- Employee Selection -->
                    <div class="form-group mb-4">
                        <label for="employeeId">Employee <span style="color: var(--error-red);">*</span></label>
                        <select id="employeeId" name="employeeId" required>
                            <option value="">Select Employee...</option>
                            <% if (employees != null && !employees.isEmpty()) {
                                for (Employee emp : employees) { %>
                            <option value="<%= emp.getEmployeeId() %>"><%= emp.getFirstName() %> <%= emp.getLastName() %> (<%= emp.getEmployeeCode() %>)</option>
                            <% }
                            } %>
                        </select>
                    </div>

                    <!-- Payment Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="paymentType">Payment Type <span style="color: var(--error-red);">*</span></label>
                            <select id="paymentType" name="paymentType" required>
                                <option value="">Select Payment Type...</option>
                                <option value="SALARY">Salary</option>
                                <option value="ALLOWANCE">Allowance</option>
                                <option value="BONUS">Bonus</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="amount">Amount <span style="color: var(--error-red);">*</span></label>
                            <input type="number" id="amount" name="amount" required step="0.01" placeholder="0.00">
                        </div>
                    </div>

                    <!-- Description -->
                    <div class="form-group mb-4">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" rows="2" placeholder="Payment description"></textarea>
                    </div>

                    <!-- Payment Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="paymentDate">Payment Date <span style="color: var(--error-red);">*</span></label>
                            <input type="date" id="paymentDate" name="paymentDate" required>
                        </div>
                        <div class="form-group">
                            <label for="paymentMethod">Payment Method</label>
                            <select id="paymentMethod" name="paymentMethod">
                                <option value="">Select Method...</option>
                                <option value="BANK_TRANSFER">Bank Transfer</option>
                                <option value="CASH">Cash</option>
                                <option value="CHECK">Check</option>
                                <option value="ONLINE">Online</option>
                            </select>
                        </div>
                    </div>

                    <!-- Remarks -->
                    <div class="form-group mb-4">
                        <label for="remarks">Remarks</label>
                        <textarea id="remarks" name="remarks" rows="2" placeholder="Additional remarks"></textarea>
                    </div>

                    <!-- Form Actions -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V7h10v2z"/>
                            </svg>
                            Process Payment
                        </button>
                        <a href="${pageContext.request.contextPath}/payment?action=list" class="btn btn-secondary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Set today's date by default
        document.getElementById('paymentDate').valueAsDate = new Date();
    </script>
</body>
</html>
