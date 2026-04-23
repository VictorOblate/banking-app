<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.banking.model.Shift" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shift Management - Basotho Ownership Bank</title>
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
                        <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13h-1v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                    </svg>
                    <h1>Shift Management</h1>
                </div>
                <div class="page-actions">
                    <a href="${pageContext.request.contextPath}/shift?action=add" class="btn btn-primary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                            <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                        </svg>
                        New Shift
                    </a>
                </div>
            </div>

            <!-- Shift Statistics -->
            <div class="stats-grid">
                <%
                    Integer totalShifts = (Integer) request.getAttribute("totalShifts");
                    Integer activeShifts = (Integer) request.getAttribute("activeShifts");
                    Integer employeesAssigned = (Integer) request.getAttribute("employeesAssigned");

                    totalShifts = totalShifts != null ? totalShifts : 0;
                    activeShifts = activeShifts != null ? activeShifts : 0;
                    employeesAssigned = employeesAssigned != null ? employeesAssigned : 0;
                %>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13h-1v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value"><%= totalShifts %></div>
                        <div class="stat-label">Total Shifts</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value"><%= activeShifts %></div>
                        <div class="stat-label">Active Shifts</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M16 4c0-1.11.89-2 2-2s2 .89 2 2-.89 2-2 2-2-.89-2-2zm4 18v-6h2.5l-2.54-7.63C19.68 7.55 18.92 7 18.06 7h-.12c-.86 0-1.63.55-1.9 1.37l-2.54 7.63H16v6h-1.5v-1h-5v1H8v-6H6.5l2.54-7.63C9.32 7.55 10.08 7 10.94 7h.12c.86 0 1.63.55 1.9 1.37L15.5 16H16v6zM12 6c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value"><%= employeesAssigned %></div>
                        <div class="stat-label">Employees Assigned</div>
                    </div>
                </div>
            </div>

            <!-- Shift List -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13h-1v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                    </svg>
                    All Shifts
                </div>

                <%
                    List<Shift> shifts = (List<Shift>) request.getAttribute("shifts");
                    if (shifts != null && !shifts.isEmpty()) {
                %>
                <div class="table-responsive">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Shift Name</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Shift shift : shifts) {
                            %>
                            <tr>
                                <td><%= shift.getShiftId() %></td>
                                <td><%= shift.getShiftName() != null ? shift.getShiftName() : "N/A" %></td>
                                <td><%= shift.getStartTime() != null ? shift.getStartTime() : "N/A" %></td>
                                <td><%= shift.getEndTime() != null ? shift.getEndTime() : "N/A" %></td>
                                <td><%= shift.getDescription() != null ? shift.getDescription() : "N/A" %></td>
                                <td>
                                    <span class="status status-<%= shift.getStatus() != null ? shift.getStatus().toLowerCase() : "inactive" %>">
                                        <%= shift.getStatus() != null ? shift.getStatus() : "Inactive" %>
                                    </span>
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="${pageContext.request.contextPath}/shift?action=view&id=<%= shift.getShiftId() %>" class="btn btn-sm btn-info">
                                            <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                                <path d="M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"/>
                                            </svg>
                                            View
                                        </a>
                                        <a href="${pageContext.request.contextPath}/shift?action=edit&id=<%= shift.getShiftId() %>" class="btn btn-sm btn-warning">
                                            <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                                <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/>
                                            </svg>
                                            Edit
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
                <%
                    } else {
                %>
                <div class="empty-state">
                    <svg width="64" height="64" viewBox="0 0 24 24" fill="currentColor" opacity="0.3">
                        <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13h-1v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                    </svg>
                    <h3>No Shifts Found</h3>
                    <p>There are no shifts to display at the moment.</p>
                    <a href="${pageContext.request.contextPath}/shift?action=add" class="btn btn-primary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                            <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                        </svg>
                        Add First Shift
                    </a>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="../components/footer.jsp" %>
</body>
</html>
