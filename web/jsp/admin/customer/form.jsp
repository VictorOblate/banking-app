<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Customer" %>
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
        if (isEdit != null && isEdit) {
    %>Edit Customer<% } else { %>Add Customer<% } %> - Basotho Ownership Bank</title>
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
                %>Edit Customer<% } else { %>Add New Customer<% } %></h1>
                <p class="text-muted"><%
                    if (isEditMode != null && isEditMode) {
                %>Update customer information and account details.<% } else { %>Create a new customer account with complete profile information.<% } %></p>
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

            <!-- Customer Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                    Customer Information
                </div>

                <%
                    Customer customer = (Customer) request.getAttribute("customer");
                    Boolean isEditMode2 = (Boolean) request.getAttribute("isEdit");
                    isEditMode2 = isEditMode2 != null ? isEditMode2 : false;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/customer?action=save">
                    <% if (isEditMode2) { %>
                    <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
                    <% } %>

                    <!-- Personal Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="firstName">First Name <span style="color: var(--error-red);">*</span></label>
                            <input type="text" id="firstName" name="firstName" required
                                   value="<%= isEditMode2 && customer != null ? customer.getFirstName() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name <span style="color: var(--error-red);">*</span></label>
                            <input type="text" id="lastName" name="lastName" required
                                   value="<%= isEditMode2 && customer != null ? customer.getLastName() : "" %>">
                        </div>
                    </div>

                    <!-- Contact Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="email">Email Address <span style="color: var(--error-red);">*</span></label>
                            <input type="email" id="email" name="email" required
                                   value="<%= isEditMode2 && customer != null ? customer.getEmail() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number <span style="color: var(--error-red);">*</span></label>
                            <input type="text" id="phone" name="phone" required
                                   value="<%= isEditMode2 && customer != null ? customer.getPhone() : "" %>">
                        </div>
                    </div>

                    <!-- Personal Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="dateOfBirth">Date of Birth</label>
                            <input type="date" id="dateOfBirth" name="dateOfBirth"
                                   value="<%= isEditMode2 && customer != null ? customer.getDateOfBirth() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender</label>
                            <select id="gender" name="gender">
                                <option value="">Select Gender...</option>
                                <option value="MALE" <%= isEditMode2 && customer != null && "MALE".equals(customer.getGender()) ? "selected" : "" %>>Male</option>
                                <option value="FEMALE" <%= isEditMode2 && customer != null && "FEMALE".equals(customer.getGender()) ? "selected" : "" %>>Female</option>
                                <option value="OTHER" <%= isEditMode2 && customer != null && "OTHER".equals(customer.getGender()) ? "selected" : "" %>>Other</option>
                            </select>
                        </div>
                    </div>

                    <!-- Address Information -->
                    <div class="form-group mb-4">
                        <label for="address">Address</label>
                        <textarea id="address" name="address" rows="3"><%= isEditMode2 && customer != null && customer.getAddress() != null ? customer.getAddress() : "" %></textarea>
                    </div>

                    <div class="grid grid-3 mb-4">
                        <div class="form-group">
                            <label for="city">City</label>
                            <input type="text" id="city" name="city"
                                   value="<%= isEditMode2 && customer != null ? customer.getCity() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="state">State/Province</label>
                            <input type="text" id="state" name="state"
                                   value="<%= isEditMode2 && customer != null ? customer.getState() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="postalCode">Postal Code</label>
                            <input type="text" id="postalCode" name="postalCode"
                                   value="<%= isEditMode2 && customer != null ? customer.getPostalCode() : "" %>">
                        </div>
                    </div>

                    <div class="form-group mb-4">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country"
                               value="<%= isEditMode2 && customer != null ? customer.getCountry() : "" %>">
                    </div>

                    <!-- Account Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="packageId">Banking Package</label>
                            <select id="packageId" name="packageId">
                                <option value="">Select Package...</option>
                                <%
                                    List<Package> packages = (List<Package>) request.getAttribute("packages");
                                    if (packages != null) {
                                        for (Package pkg : packages) {
                                %>
                                <option value="<%= pkg.getPackageId() %>"
                                        <%= isEditMode2 && customer != null && customer.getPackageId() == pkg.getPackageId() ? "selected" : "" %>>
                                    <%= pkg.getPackageName() %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="accountStatus">Account Status</label>
                            <select id="accountStatus" name="accountStatus">
                                <option value="ACTIVE" <%= isEditMode2 && customer != null && "ACTIVE".equals(customer.getAccountStatus()) ? "selected" : "" %>>Active</option>
                                <option value="INACTIVE" <%= isEditMode2 && customer != null && "INACTIVE".equals(customer.getAccountStatus()) ? "selected" : "" %>>Inactive</option>
                                <option value="SUSPENDED" <%= isEditMode2 && customer != null && "SUSPENDED".equals(customer.getAccountStatus()) ? "selected" : "" %>>Suspended</option>
                            </select>
                        </div>
                    </div>

                    <!-- Form Actions -->
                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                                <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/>
                            </svg>
                            <%= isEditMode2 ? "Update Customer" : "Create Customer" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/customer?action=list" class="btn btn-secondary">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
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
</body>
</html>
