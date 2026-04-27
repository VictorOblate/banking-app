<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bankingapp.model.Admin" %>
<%@ page import="com.bankingapp.util.Constants" %>
<%
    // Check if user is logged in
    com.bankingapp.model.Admin currentAdmin = (com.bankingapp.model.Admin) session.getAttribute(Constants.ADMIN_SESSION);
    if (currentAdmin == null) {
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
        if (isEdit != null && isEdit) {
    %>Edit Admin<% } else { %>Add Admin<% } %> - Basotho Ownership Bank</title>
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
                    if (isEditMode != null && isEditMode) {
                %>Edit Admin User<% } else { %>Add New Admin User<% } %></h1>
                <p class="text-muted"><%
                    if (isEditMode != null && isEditMode) {
                %>Update admin user information and credentials.<% } else { %>Create a new admin user account.<% } %></p>
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

            <!-- Admin Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                    Admin Information
                </div>

                <%
                    Admin admin = (Admin) request.getAttribute("admin");
                    Boolean isEditMode2 = (Boolean) request.getAttribute("isEdit");
                    isEditMode2 = isEditMode2 != null ? isEditMode2 : false;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/admin?action=save">
                    <% if (isEditMode2) { %>
                    <input type="hidden" name="adminId" value="<%= admin.getAdminId() %>">
                    <% } %>

                    <!-- Login Credentials -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="username">Username <span style="color: var(--error-red);">*</span></label>
                            <% if (isEditMode2) { %>
                            <input type="text" id="username" name="username" required disabled
                                   value="<%= admin.getUsername() %>">
                            <input type="hidden" name="username" value="<%= admin.getUsername() %>">
                            <% } else { %>
                            <input type="text" id="username" name="username" required
                                   placeholder="Enter unique username" value="">
                            <% } %>
                            <small>Username cannot be changed after creation</small>
                        </div>
                        <div class="form-group">
                            <label for="password">Password <span style="color: var(--error-red);">*</span></label>
                            <input type="password" id="password" name="password" required
                                   placeholder="Enter secure password" value="">
                            <small><% if (isEditMode2) { %>Leave blank to keep current password<% } else { %>Minimum 6 characters<% } %></small>
                        </div>
                    </div>

                    <!-- Personal Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="fullName">Full Name <span style="color: var(--error-red);">*</span></label>
                            <input type="text" id="fullName" name="fullName" required
                                   value="<%= isEditMode2 && admin != null ? admin.getFullName() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email"
                                   value="<%= isEditMode2 && admin != null && admin.getEmail() != null ? admin.getEmail() : "" %>">
                        </div>
                    </div>

                    <!-- Contact Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="tel" id="phone" name="phone"
                                   value="<%= isEditMode2 && admin != null && admin.getPhone() != null ? admin.getPhone() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="isActive">
                                <input type="checkbox" id="isActive" name="isActive"
                                    <% if (!isEditMode2 || (admin != null && admin.isActive())) { %>checked<% } %>>
                                <span>Active Status</span>
                            </label>
                            <small>Uncheck to deactivate this admin account</small>
                        </div>
                    </div>

                    <!-- Form Actions -->
                    <div class="form-actions mb-4">
                        <button type="submit" class="btn btn-primary">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                                <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z"/>
                            </svg>
                            <% if (isEditMode2) { %>Update Admin<% } else { %>Create Admin<% } %>
                        </button>
                        <a href="${pageContext.request.contextPath}/admin?action=list" class="btn btn-secondary">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                                <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>

                    <!-- Information -->
                    <% if (isEditMode2 && admin != null) { %>
                    <div class="alert alert-info">
                        <p><strong>Created:</strong> <%= admin.getCreatedDate() != null ? admin.getCreatedDate() : "N/A" %></p>
                        <p><strong>Last Login:</strong> <%= admin.getLastLogin() != null ? admin.getLastLogin() : "Never logged in" %></p>
                    </div>
                    <% } %>
                </form>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>

    <script>
        // Validate form before submission
        document.querySelector('form').addEventListener('submit', function(e) {
            var username = document.getElementById('username').value.trim();
            var password = document.getElementById('password').value.trim();
            var fullName = document.getElementById('fullName').value.trim();
            
            if (!username || !fullName) {
                e.preventDefault();
                alert('Username and Full Name are required fields.');
                return false;
            }
            
            if (!<%= isEditMode2 %> && !password) {
                e.preventDefault();
                alert('Password is required for new admin accounts.');
                return false;
            }
            
            if (password && password.length < 6) {
                e.preventDefault();
                alert('Password must be at least 6 characters long.');
                return false;
            }
            
            return true;
        });
    </script>
</body>
</html>
