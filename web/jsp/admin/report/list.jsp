<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reports - Basotho Ownership Bank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="../components/header.jsp" %>

    <main class="main-content">
        <div class="container">
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

            <!-- Page Header -->
            <div class="page-header">
                <div class="page-title">
                    <svg width="32" height="32" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                    </svg>
                    <h1>Reports & Analytics</h1>
                </div>
                <div class="page-actions">
                    <a href="${pageContext.request.contextPath}/report?action=form" class="btn btn-primary">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                        </svg>
                        Generate Custom Report
                    </a>
                </div>
            </div>

            <!-- Report Categories -->
            <div class="report-grid">
                <!-- Customer Reports -->
                <div class="report-card">
                    <div class="report-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M16 4c0-1.11.89-2 2-2s2 .89 2 2-.89 2-2 2-2-.89-2-2zm4 18v-6h2.5l-2.54-7.63C19.68 7.55 18.92 7 18.06 7h-.12c-.86 0-1.63.55-1.9 1.37l-2.54 7.63H16v6h-1.5v-1h-5v1H8v-6H6.5l2.54-7.63C9.32 7.55 10.08 7 10.94 7h.12c.86 0 1.63.55 1.9 1.37L15.5 16H16v6zM12 6c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z"/>
                        </svg>
                    </div>
                    <div class="report-content">
                        <h3>Customer Reports</h3>
                        <p>Comprehensive customer analytics and account summaries</p>
                        <div class="report-actions">
                            <a href="${pageContext.request.contextPath}/report?action=customer-summary" class="btn btn-sm btn-primary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                                </svg>
                                Generate Report
                            </a>
                            <a href="${pageContext.request.contextPath}/report?action=customer-list" class="btn btn-sm btn-secondary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                </svg>
                                View Details
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Transaction Reports -->
                <div class="report-card">
                    <div class="report-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M7 2v11h3v9l7-12h-4l4-8z"/>
                        </svg>
                    </div>
                    <div class="report-content">
                        <h3>Transaction Reports</h3>
                        <p>Detailed transaction history and financial activity analysis</p>
                        <div class="report-actions">
                            <a href="${pageContext.request.contextPath}/report?action=transaction-summary" class="btn btn-sm btn-primary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                                </svg>
                                Generate Report
                            </a>
                            <a href="${pageContext.request.contextPath}/report?action=transaction-history" class="btn btn-sm btn-secondary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                </svg>
                                View History
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Employee Reports -->
                <div class="report-card">
                    <div class="report-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zm8 15H6V9h2v2c0 .55.45 1 1 1s1-.45 1-1v-2h4v2c0 .55.45 1 1 1s1-.45 1-1v-2h2v10z"/>
                        </svg>
                    </div>
                    <div class="report-content">
                        <h3>Employee Reports</h3>
                        <p>Staff performance, attendance, and payroll analytics</p>
                        <div class="report-actions">
                            <a href="${pageContext.request.contextPath}/report?action=employee-summary" class="btn btn-sm btn-primary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                                </svg>
                                Generate Report
                            </a>
                            <a href="${pageContext.request.contextPath}/report?action=employee-list" class="btn btn-sm btn-secondary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                </svg>
                                View Details
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Financial Reports -->
                <div class="report-card">
                    <div class="report-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M11.8 10.9c-2.27-.59-3-1.2-3-2.15 0-1.09 1.01-1.85 2.7-1.85 1.78 0 2.44.85 2.5 2.1h2.21c-.07-1.72-1.12-3.3-3.21-3.81V3h-3v2.16c-1.94.42-3.5 1.68-3.5 3.61 0 2.31 1.91 3.46 4.7 4.13 2.5.6 3 1.48 3 2.41 0 .69-.49 1.79-2.7 1.79-2.06 0-2.87-.92-2.98-2.1h-2.2c.12 2.19 1.76 3.42 3.68 3.83V21h3v-2.15c1.95-.37 3.5-1.5 3.5-3.55 0-2.84-2.43-3.81-4.7-4.4z"/>
                        </svg>
                    </div>
                    <div class="report-content">
                        <h3>Financial Reports</h3>
                        <p>Profit & loss, balance sheets, and financial statements</p>
                        <div class="report-actions">
                            <a href="${pageContext.request.contextPath}/report?action=financial-summary" class="btn btn-sm btn-primary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                                </svg>
                                Generate Report
                            </a>
                            <a href="${pageContext.request.contextPath}/report?action=financial-statements" class="btn btn-sm btn-secondary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                </svg>
                                View Statements
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Payment Reports -->
                <div class="report-card">
                    <div class="report-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M20 4H4c-1.11 0-1.99.89-1.99 2L2 18c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V6c0-1.11-.89-2-2-2zm0 14H4v-6h16v6zm0-10H4V6h16v2z"/>
                        </svg>
                    </div>
                    <div class="report-content">
                        <h3>Payment Reports</h3>
                        <p>Payment processing, overdue accounts, and collection analytics</p>
                        <div class="report-actions">
                            <a href="${pageContext.request.contextPath}/report?action=payment-summary" class="btn btn-sm btn-primary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                                </svg>
                                Generate Report
                            </a>
                            <a href="${pageContext.request.contextPath}/report?action=payment-status" class="btn btn-sm btn-secondary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                </svg>
                                Payment Status
                            </a>
                        </div>
                    </div>
                </div>

                <!-- System Reports -->
                <div class="report-card">
                    <div class="report-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M22 2H2v20h20V2zm-2 18H4V4h16v16zM8 9h8v2H8V9zm0 4h8v2H8v11zm0-8h8v2H8V5z"/>
                        </svg>
                    </div>
                    <div class="report-content">
                        <h3>System Reports</h3>
                        <p>System usage, performance metrics, and audit logs</p>
                        <div class="report-actions">
                            <a href="${pageContext.request.contextPath}/report?action=system-summary" class="btn btn-sm btn-primary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                                </svg>
                                Generate Report
                            </a>
                            <a href="${pageContext.request.contextPath}/report?action=audit-logs" class="btn btn-sm btn-secondary">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 6px;">
                                    <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                </svg>
                                Audit Logs
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Recent Reports -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                    </svg>
                    Recent Reports
                </div>

                <%
                    // Mock recent reports data - in real implementation, this would come from database
                    String[][] recentReports = {
                        {"Customer Summary Report", "2024-01-15", "admin", "customer-summary-jan2024.pdf"},
                        {"Transaction Analysis", "2024-01-14", "admin", "transaction-analysis-jan2024.pdf"},
                        {"Monthly Financial Statement", "2024-01-10", "admin", "financial-statement-jan2024.pdf"},
                        {"Employee Performance Report", "2024-01-08", "admin", "employee-performance-jan2024.pdf"}
                    };
                %>

                <div class="table-responsive">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Report Name</th>
                                <th>Generated Date</th>
                                <th>Generated By</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (String[] report : recentReports) {
                            %>
                            <tr>
                                <td><%= report[0] %></td>
                                <td><%= report[1] %></td>
                                <td><%= report[2] %></td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="${pageContext.request.contextPath}/reports/<%= report[3] %>" class="btn btn-sm btn-info">
                                            <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                                <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                                            </svg>
                                            Download
                                        </a>
                                        <a href="${pageContext.request.contextPath}/report?action=view&id=<%= report[3] %>" class="btn btn-sm btn-warning">
                                            <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                                <path d="M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"/>
                                            </svg>
                                            View
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="../components/footer.jsp" %>
</body>
</html>
