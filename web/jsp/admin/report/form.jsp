<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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
    <title>Generate Report - Basotho Ownership Bank</title>
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
                <h1>Generate Report</h1>
                <p class="text-muted">Select report type and date range to generate comprehensive banking reports.</p>
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

            <!-- Report Generation Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                    </svg>
                    Report Parameters
                </div>

                <form method="POST" action="${pageContext.request.contextPath}/report?action=generate">
                    <!-- Report Type Selection -->
                    <div class="form-group mb-4">
                        <label for="reportType">Report Type <span style="color: var(--error-red);">*</span></label>
                        <select id="reportType" name="reportType" required>
                            <option value="">Select Report Type</option>
                            <option value="transactions">Transaction Report</option>
                            <option value="payments">Payment Report</option>
                            <option value="customers">Customer Report</option>
                            <option value="employees">Employee Report</option>
                            <option value="packages">Package Report</option>
                            <option value="shifts">Shift Report</option>
                        </select>
                        <small class="form-help">Choose the type of report you want to generate</small>
                    </div>

                    <!-- Date Range Selection -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="startDate">Start Date</label>
                            <input type="date" id="startDate" name="startDate">
                            <small class="form-help">Leave empty for all time</small>
                        </div>
                        <div class="form-group">
                            <label for="endDate">End Date</label>
                            <input type="date" id="endDate" name="endDate">
                            <small class="form-help">Leave empty for all time</small>
                        </div>
                    </div>

                    <!-- Report Options -->
                    <div class="form-group mb-4">
                        <label>Report Options</label>
                        <div class="checkbox-group">
                            <label class="checkbox-label">
                                <input type="checkbox" name="includeInactive" value="true">
                                <span class="checkmark"></span>
                                Include inactive records
                            </label>
                            <label class="checkbox-label">
                                <input type="checkbox" name="detailedView" value="true" checked>
                                <span class="checkmark"></span>
                                Detailed view
                            </label>
                            <label class="checkbox-label">
                                <input type="checkbox" name="exportCsv" value="true">
                                <span class="checkmark"></span>
                                Export as CSV
                            </label>
                        </div>
                    </div>

                    <!-- Form Actions -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                            </svg>
                            Generate Report
                        </button>
                        <a href="${pageContext.request.contextPath}/report?action=list" class="btn btn-secondary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>
                </form>
            </div>

            <!-- Generated Report Display -->
            <%
                Boolean isGenerated = (Boolean) request.getAttribute("isGenerated");
                if (isGenerated != null && isGenerated) {
                    Map<String, Object> reportData = (Map<String, Object>) request.getAttribute("reportData");
                    if (reportData != null) {
            %>
            <div class="card mt-4">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                    </svg>
                    <%= reportData.get("title") %>
                </div>

                <%
                    String reportType = (String) reportData.get("reportType");
                    List<?> data = (List<?>) reportData.get("data");
                    String startDate = (String) reportData.get("startDate");
                    String endDate = (String) reportData.get("endDate");
                %>

                <!-- Report Summary -->
                <div class="report-summary mb-4">
                    <div class="grid grid-3">
                        <div class="stat-card">
                            <div class="stat-value"><%= data != null ? data.size() : 0 %></div>
                            <div class="stat-label">Total Records</div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-value"><%= startDate != null ? startDate : "All Time" %></div>
                            <div class="stat-label">Start Date</div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-value"><%= endDate != null ? endDate : "All Time" %></div>
                            <div class="stat-label">End Date</div>
                        </div>
                    </div>
                </div>

                <!-- Report Actions -->
                <div class="report-actions mb-4">
                    <a href="${pageContext.request.contextPath}/report?action=export&reportType=<%= reportType %>&startDate=<%= startDate != null ? startDate : "" %>&endDate=<%= endDate != null ? endDate : "" %>" class="btn btn-sm btn-primary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z"/>
                        </svg>
                        Export Report
                    </a>
                    <button onclick="window.print()" class="btn btn-sm btn-secondary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M18,3H6V7H18M19,12A1,1 0 0,1 18,11A1,1 0 0,1 19,10A1,1 0 0,1 20,11A1,1 0 0,1 19,12M16,19H8V15H16M19,8H5A3,3 0 0,0 2,11V17H6V21H18V17H22V11A3,3 0 0,0 19,8Z"/>
                        </svg>
                        Print Report
                    </button>
                </div>

                <!-- Report Data Table -->
                <% if (data != null && !data.isEmpty()) { %>
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <%
                                if ("transactions".equals(reportType)) {
                            %>
                            <tr>
                                <th>ID</th>
                                <th>Type</th>
                                <th>Amount</th>
                                <th>Customer</th>
                                <th>Date</th>
                                <th>Status</th>
                            </tr>
                            <%
                                } else if ("payments".equals(reportType)) {
                            %>
                            <tr>
                                <th>ID</th>
                                <th>Type</th>
                                <th>Amount</th>
                                <th>Employee</th>
                                <th>Date</th>
                                <th>Status</th>
                            </tr>
                            <%
                                } else if ("customers".equals(reportType)) {
                            %>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Account Type</th>
                                <th>Status</th>
                            </tr>
                            <%
                                } else if ("employees".equals(reportType)) {
                            %>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Position</th>
                                <th>Department</th>
                                <th>Salary</th>
                                <th>Status</th>
                            </tr>
                            <%
                                } else if ("packages".equals(reportType)) {
                            %>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Type</th>
                                <th>Price</th>
                                <th>Description</th>
                                <th>Status</th>
                            </tr>
                            <%
                                } else if ("shifts".equals(reportType)) {
                            %>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Type</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Status</th>
                            </tr>
                            <%
                                }
                            %>
                        </thead>
                        <tbody>
                            <!-- Data rows would be populated here based on the report type -->
                            <tr>
                                <td colspan="6" class="text-center text-muted">Report data would be displayed here</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <% } else { %>
                <div class="empty-state">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z"/>
                    </svg>
                    <h3>No Data Found</h3>
                    <p>No records found for the selected criteria.</p>
                </div>
                <% } %>
            </div>
            <%
                    }
                }
            %>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Set default dates (last 30 days)
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date();
            const thirtyDaysAgo = new Date();
            thirtyDaysAgo.setDate(today.getDate() - 30);

            const startDateInput = document.getElementById('startDate');
            const endDateInput = document.getElementById('endDate');

            if (!startDateInput.value) {
                startDateInput.value = thirtyDaysAgo.toISOString().split('T')[0];
            }
            if (!endDateInput.value) {
                endDateInput.value = today.toISOString().split('T')[0];
            }
        });
    </script>
</body>
</html>