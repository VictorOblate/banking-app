<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bankingapp.model.Admin" %>
<%@ page import="com.bankingapp.util.Constants" %>
<%
    // Check if user is logged in
    Admin admin = (Admin) session.getAttribute(Constants.ADMIN_SESSION);
    if (admin == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    // Get dashboard statistics from request attributes (set by servlet)
    Integer customerCount = (Integer) request.getAttribute("customerCount");
    Integer employeeCount = (Integer) request.getAttribute("employeeCount");
    Integer transactionCount = (Integer) request.getAttribute("transactionCount");
    Double totalPayments = (Double) request.getAttribute("totalPayments");

    // Default values if not set
    if (customerCount == null) customerCount = 0;
    if (employeeCount == null) employeeCount = 0;
    if (transactionCount == null) transactionCount = 0;
    if (totalPayments == null) totalPayments = 0.0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Basotho Ownership Bank</title>
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
                <h1>Dashboard Overview</h1>
                <p class="text-muted">Welcome back, <%= admin.getUsername() %>! Here's what's happening with your banking operations.</p>
            </div>

            <!-- Statistics Widgets -->
            <div class="grid grid-4 mb-4">
                <div class="widget">
                    <div class="widget-title">Total Customers</div>
                    <div class="widget-value"><%= customerCount %></div>
                    <div class="widget-subtitle">Active accounts</div>
                </div>

                <div class="widget">
                    <div class="widget-title">Total Employees</div>
                    <div class="widget-value"><%= employeeCount %></div>
                    <div class="widget-subtitle">Staff members</div>
                </div>

                <div class="widget">
                    <div class="widget-title">Transactions</div>
                    <div class="widget-value"><%= transactionCount %></div>
                    <div class="widget-subtitle">This month</div>
                </div>

                <div class="widget">
                    <div class="widget-title">Total Payments</div>
                    <div class="widget-value">L<%= String.format("%.2f", totalPayments) %></div>
                    <div class="widget-subtitle">Monthly volume</div>
                </div>
            </div>

            <!-- Quick Actions Grid -->
            <div class="mb-4">
                <h2>Quick Actions</h2>
                <div class="grid grid-3">
                    <!-- Customer Management -->
                    <div class="card">
                        <div class="card-title">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                            </svg>
                            Customer Management
                        </div>
                        <p>Manage customer accounts, view profiles, and handle account operations.</p>
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/customer?action=list" class="btn btn-primary btn-small">View Customers</a>
                            <a href="${pageContext.request.contextPath}/customer?action=add" class="btn btn-secondary btn-small">Add Customer</a>
                        </div>
                    </div>

                    <!-- Employee Management -->
                    <div class="card">
                        <div class="card-title">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zm8 15H6V9h2v2c0 .55.45 1 1 1s1-.45 1-1v-2h4v2c0 .55.45 1 1 1s1-.45 1-1v-2h2v10z"/>
                            </svg>
                            Employee Management
                        </div>
                        <p>Manage employee records, salaries, and work assignments.</p>
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/employee?action=list" class="btn btn-primary btn-small">View Employees</a>
                            <a href="${pageContext.request.contextPath}/employee?action=add" class="btn btn-secondary btn-small">Add Employee</a>
                        </div>
                    </div>

                    <!-- Transaction Monitoring -->
                    <div class="card">
                        <div class="card-title">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"/>
                            </svg>
                            Transaction Monitoring
                        </div>
                        <p>Monitor and manage all banking transactions and payment processing.</p>
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/transaction?action=list" class="btn btn-primary btn-small">View Transactions</a>
                            <a href="${pageContext.request.contextPath}/payment?action=list" class="btn btn-secondary btn-small">View Payments</a>
                        </div>
                    </div>

                    <!-- Package Management -->
                    <div class="card">
                        <div class="card-title">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zm8 15H6V9h2v2c0 .55.45 1 1 1s1-.45 1-1v-2h4v2c0 .55.45 1 1 1s1-.45 1-1v-2h2v10z"/>
                            </svg>
                            Package Management
                        </div>
                        <p>Configure and manage banking service packages and offerings.</p>
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/package?action=list" class="btn btn-primary btn-small">View Packages</a>
                            <a href="${pageContext.request.contextPath}/package?action=add" class="btn btn-secondary btn-small">Add Package</a>
                        </div>
                    </div>

                    <!-- Shift Management -->
                    <div class="card">
                        <div class="card-title">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                            </svg>
                            Shift Management
                        </div>
                        <p>Schedule and manage employee work shifts and time tracking.</p>
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/shift?action=list" class="btn btn-primary btn-small">View Shifts</a>
                            <a href="${pageContext.request.contextPath}/shift?action=schedule" class="btn btn-secondary btn-small">Schedule Shift</a>
                        </div>
                    </div>

                    <!-- Reports & Analytics -->
                    <div class="card">
                        <div class="card-title">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                            </svg>
                            Reports & Analytics
                        </div>
                        <p>Generate comprehensive reports and analyze banking performance metrics.</p>
                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/report?action=generate" class="btn btn-primary btn-small">Generate Report</a>
                            <a href="${pageContext.request.contextPath}/report?action=view" class="btn btn-secondary btn-small">View Reports</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Recent Activity Section -->
            <div class="card">
                <div class="card-title">Recent Activity</div>
                <div class="alert alert-info">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
                    </svg>
                    <div>
                        <strong>System Status:</strong> All systems operational. Last backup completed successfully.
                        <br><small class="text-muted">Updated <%= new java.util.Date() %></small>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>
</body>
</html>
