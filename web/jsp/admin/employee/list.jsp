<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Employee" %>
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
    <title>Employee Management - Basotho Ownership Bank</title>
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
                <h1>Employee Management</h1>
                <p class="text-muted">Manage employee records, salaries, and organizational information.</p>
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

            <!-- Action Buttons -->
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/employee?action=add" class="btn btn-primary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                        <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                    </svg>
                    Add New Employee
                </a>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                        <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                    </svg>
                    Back to Dashboard
                </a>
            </div>

            <!-- Employee Table -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zm8 15H6V9h2v2c0 .55.45 1 1 1s1-.45 1-1v-2h4v2c0 .55.45 1 1 1s1-.45 1-1v-2h2v10z"/>
                    </svg>
                    Employee List
                </div>

                <%
                    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                    if (employees != null && employees.size() > 0) {
                %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Full Name</th>
                            <th>Employee Code</th>
                            <th>Designation</th>
                            <th>Department</th>
                            <th>Basic Salary</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Employee employee : employees) {
                        %>
                        <tr>
                            <td><%= employee.getEmployeeId() %></td>
                            <td><%= employee.getFullName() %></td>
                            <td><%= employee.getEmployeeCode() %></td>
                            <td><%= employee.getDesignation() %></td>
                            <td><%= employee.getDepartment() %></td>
                            <td>L<%= String.format("%.2f", employee.getBasicSalary()) %></td>
                            <td>
                                <span class="badge <%= "ACTIVE".equals(employee.getEmploymentStatus()) ? "badge-success" : "badge-inactive" %>">
                                    <%= employee.getEmploymentStatus() %>
                                </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/employee?action=edit&employeeId=<%= employee.getEmployeeId() %>" class="btn btn-secondary btn-small">
                                    <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                        <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/>
                                    </svg>
                                    Edit
                                </a>
                                <form method="POST" action="${pageContext.request.contextPath}/employee" style="display:inline; margin-left: 8px;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="employeeId" value="<%= employee.getEmployeeId() %>">
                                    <button type="submit" class="btn btn-secondary btn-small" style="background-color: var(--error-red); color: white;" onclick="return confirm('Are you sure you want to delete this employee?')">
                                        <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                            <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/>
                                        </svg>
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <%
                    } else {
                %>
                <div class="text-center p-4">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor" style="color: var(--medium-gray); margin-bottom: 1rem;">
                        <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zm8 15H6V9h2v2c0 .55.45 1 1 1s1-.45 1-1v-2h4v2c0 .55.45 1 1 1s1-.45 1-1v-2h2v10z"/>
                    </svg>
                    <h3 class="text-muted">No Employees Found</h3>
                    <p class="text-muted mb-3">Get started by adding your first employee to the system.</p>
                    <a href="${pageContext.request.contextPath}/employee?action=add" class="btn btn-primary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                            <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                        </svg>
                        Add First Employee
                    </a>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>
</body>
</html>
