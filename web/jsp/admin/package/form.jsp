<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Package" %>
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
    %>View Package<% } else if (isEdit != null && isEdit) { %>Edit Package<% } else { %>Add Package<% } %> - Basotho Ownership Bank</title>
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
                %>View Package<% } else if (isEditMode != null && isEditMode) { %>Edit Package<% } else { %>Add New Package<% } %></h1>
                <p class="text-muted"><%
                    if (isViewMode != null && isViewMode) {
                %>View package details and features.<% } else if (isEditMode != null && isEditMode) { %>Update package information and pricing.<% } else { %>Create a new banking package with features and pricing.<% } %></p>
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

            <!-- Package Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zM4 9h16v11H4V9z"/>
                    </svg>
                    Package Information
                </div>

                <%
                    Package pkg = (Package) request.getAttribute("package");
                    Boolean isEditMode2 = (Boolean) request.getAttribute("isEdit");
                    Boolean isViewMode2 = (Boolean) request.getAttribute("isView");
                    isEditMode2 = isEditMode2 != null ? isEditMode2 : false;
                    isViewMode2 = isViewMode2 != null ? isViewMode2 : false;
                    boolean isReadOnly = isViewMode2;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/package?action=save">
                    <% if (isEditMode2 && pkg != null) { %>
                    <input type="hidden" name="packageId" value="<%= pkg.getPackageId() %>">
                    <% } %>

                    <!-- Package Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="packageName">Package Name <span style="color: var(--error-red);">*</span></label>
                            <input type="text" id="packageName" name="packageName" required <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && pkg != null ? pkg.getPackageName() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="packageType">Package Type <span style="color: var(--error-red);">*</span></label>
                            <select id="packageType" name="packageType" required <%= isReadOnly ? "disabled" : "" %>>
                                <option value="">Select Type...</option>
                                <option value="SAVINGS" <%= isEditMode2 && pkg != null && "SAVINGS".equals(pkg.getPackageType()) ? "selected" : "" %>>Savings Account</option>
                                <option value="CURRENT" <%= isEditMode2 && pkg != null && "CURRENT".equals(pkg.getPackageType()) ? "selected" : "" %>>Current Account</option>
                                <option value="BUSINESS" <%= isEditMode2 && pkg != null && "BUSINESS".equals(pkg.getPackageType()) ? "selected" : "" %>>Business Account</option>
                                <option value="STUDENT" <%= isEditMode2 && pkg != null && "STUDENT".equals(pkg.getPackageType()) ? "selected" : "" %>>Student Account</option>
                                <option value="SENIOR" <%= isEditMode2 && pkg != null && "SENIOR".equals(pkg.getPackageType()) ? "selected" : "" %>>Senior Citizen Account</option>
                            </select>
                        </div>
                    </div>

                    <!-- Pricing Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="monthlyFee">Monthly Fee (LSL)</label>
                            <input type="number" id="monthlyFee" name="monthlyFee" step="0.01" min="0" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && pkg != null ? pkg.getMonthlyFee() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="annualFee">Annual Fee (LSL)</label>
                            <input type="number" id="annualFee" name="annualFee" step="0.01" min="0" <%= isReadOnly ? "readonly" : "" %>
                                   value="<%= isEditMode2 && pkg != null ? pkg.getAnnualFee() : "" %>">
                        </div>
                    </div>

                    <!-- Package Description -->
                    <div class="form-group mb-4">
                        <label for="description">Description <span style="color: var(--error-red);">*</span></label>
                        <textarea id="description" name="description" rows="3" required <%= isReadOnly ? "readonly" : "" %>><%= isEditMode2 && pkg != null && pkg.getDescription() != null ? pkg.getDescription() : "" %></textarea>
                    </div>

                    <!-- Package Benefits -->
                    <div class="form-group mb-4">
                        <label for="benefits">Benefits & Features</label>
                        <textarea id="benefits" name="benefits" rows="4" <%= isReadOnly ? "readonly" : "" %>><%= isEditMode2 && pkg != null && pkg.getBenefits() != null ? pkg.getBenefits() : "" %></textarea>
                        <small class="text-muted">List the key benefits and features of this package</small>
                    </div>

                    <!-- Status -->
                    <div class="form-group mb-4">
                        <label for="isActive">Status</label>
                        <select id="isActive" name="isActive" <%= isReadOnly ? "disabled" : "" %>>
                            <option value="true" <%= isEditMode2 && pkg != null && pkg.isActive() ? "selected" : "" %>>Active</option>
                            <option value="false" <%= isEditMode2 && pkg != null && !pkg.isActive() ? "selected" : "" %>>Inactive</option>
                        </select>
                    </div>

                    <!-- Form Actions -->
                    <% if (!isViewMode2) { %>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V7h10v2z"/>
                            </svg>
                            <%= isEditMode2 ? "Update Package" : "Create Package" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/package?action=list" class="btn btn-secondary">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"/>
                            </svg>
                            Cancel
                        </a>
                    </div>
                    <% } else { %>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/package?action=list" class="btn btn-primary">
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