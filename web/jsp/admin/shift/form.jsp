<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Shift" %>
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
    %>View Shift<% } else if (isEdit != null && isEdit) { %>Edit Shift<% } else { %>Add Shift<% } %> - Basotho Ownership Bank</title>
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
                %>View Shift<% } else if (isEditMode != null && isEditMode) { %>Edit Shift<% } else { %>Add New Shift<% } %></h1>
                <p class="text-muted"><%
                    if (isViewMode != null && isViewMode) {
                %>View shift details and scheduling information.<% } else if (isEditMode != null && isEditMode) { %>Update shift timing and information.<% } else { %>Create a new work shift schedule.<% } %></p>
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

            <!-- Shift Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8z"/>
                        <path d="M12.5 7H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                    </svg>
                    Shift Information
                </div>

                <%
                    Shift shift = (Shift) request.getAttribute("shift");
                    Boolean isEditMode2 = (Boolean) request.getAttribute("isEdit");
                    Boolean isViewMode2 = (Boolean) request.getAttribute("isView");
                    isEditMode2 = isEditMode2 != null ? isEditMode2 : false;
                    isViewMode2 = isViewMode2 != null ? isViewMode2 : false;
                    boolean isReadOnly = isViewMode2;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/shift?action=save">
                    <% if (isEditMode2 && shift != null) { %>
                    <input type="hidden" name="shiftId" value="<%= shift.getShiftId() %>">
                    <% } %>

                    <!-- Shift Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="shiftName">Shift Name <span style="color: var(--error-red);">*</span></label>
                            <input type="text" id="shiftName" name="shiftName" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && shift != null ? shift.getShiftName() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="shiftType">Shift Type</label>
                            <select id="shiftType" name="shiftType" <%= isReadOnly ? "disabled" : "" %>>
                                <option value="MORNING" <%= isEditMode2 && shift != null && "MORNING".equals(shift.getShiftType()) ? "selected" : "" %>>Morning</option>
                                <option value="AFTERNOON" <%= isEditMode2 && shift != null && "AFTERNOON".equals(shift.getShiftType()) ? "selected" : "" %>>Afternoon</option>
                                <option value="EVENING" <%= isEditMode2 && shift != null && "EVENING".equals(shift.getShiftType()) ? "selected" : "" %>>Evening</option>
                                <option value="NIGHT" <%= isEditMode2 && shift != null && "NIGHT".equals(shift.getShiftType()) ? "selected" : "" %>>Night</option>
                                <option value="WEEKEND" <%= isEditMode2 && shift != null && "WEEKEND".equals(shift.getShiftType()) ? "selected" : "" %>>Weekend</option>
                            </select>
                        </div>
                    </div>

                    <!-- Timing Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="startTime">Start Time <span style="color: var(--error-red);">*</span></label>
                            <input type="time" id="startTime" name="startTime" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && shift != null ? shift.getStartTime() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="endTime">End Time <span style="color: var(--error-red);">*</span></label>
                            <input type="time" id="endTime" name="endTime" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && shift != null ? shift.getEndTime() : "" %>">
                        </div>
                    </div>

                    <!-- Shift Description -->
                    <div class="form-group mb-4">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" rows="3" <%= isReadOnly ? "readonly" : "" %>><%= isEditMode2 && shift != null && shift.getDescription() != null ? shift.getDescription() : "" %></textarea>
                    </div>

                    <!-- Active Status -->
                    <div class="form-group mb-4">
                        <label for="isActive">Status</label>
                        <select id="isActive" name="isActive" <%= isReadOnly ? "disabled" : "" %>>
                            <option value="true"  <%= (isEditMode2 && shift != null && shift.isActive())  ? "selected" : (!isEditMode2 ? "selected" : "") %>>Active</option>
                            <option value="false" <%= (isEditMode2 && shift != null && !shift.isActive()) ? "selected" : "" %>>Inactive</option>
                        </select>
                    </div>

                    <!-- Form Actions -->
                    <% if (!isViewMode2) { %>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V7h10v2z"/>
                            </svg>
                            <%= isEditMode2 ? "Update Shift" : "Create Shift" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/shift?action=list" class="btn btn-secondary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>
                    <% } else { %>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/shift?action=list" class="btn btn-primary">
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