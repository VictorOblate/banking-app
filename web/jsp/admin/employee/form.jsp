<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Employee" %>
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
        if (isEdit != null && isEdit) {
    %>Edit Employee<% } else { %>Add Employee<% } %> - Basotho Ownership Bank</title>
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
                %>Edit Employee<% } else { %>Add New Employee<% } %></h1>
                <p class="text-muted"><%
                    if (isEditMode != null && isEditMode) {
                %>Update employee information and employment details.<% } else { %>Create a new employee record with complete profile information.<% } %></p>
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

            <!-- Employee Form -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M20 7h-4V4c0-1.1-.9-2-2-2h-4c-1.1 0-2 .9-2 2v3H4c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V9c0-1.1-.9-2-2-2zM10 4h4v3h-4V4zm8 15H6V9h2v2c0 .55.45 1 1 1s1-.45 1-1v-2h4v2c0 .55.45 1 1 1s1-.45 1-1v-2h2v10z"/>
                    </svg>
                    <%= isEditMode ? "Edit Employee" : "Add New Employee" %>
                </div>

                <%
                    Employee employee = (Employee) request.getAttribute("employee");
                    Boolean isEditMode = (Boolean) request.getAttribute("isEdit");
                    isEditMode = isEditMode != null ? isEditMode : false;
                %>

                <form method="POST" action="${pageContext.request.contextPath}/employee?action=save">
                    <% if (isEditMode) { %>
                    <input type="hidden" name="employeeId" value="<%= employee.getEmployeeId() %>">
                    <% } %>

                    <!-- Personal Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="firstName">First Name <span class="required">*</span></label>
                            <input type="text" id="firstName" name="firstName" required
                                   value="<%= isEditMode && employee != null ? employee.getFirstName() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name <span class="required">*</span></label>
                            <input type="text" id="lastName" name="lastName" required
                                   value="<%= isEditMode && employee != null ? employee.getLastName() : "" %>">
                        </div>
                    </div>

                    <!-- Contact Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" id="email" name="email"
                                   value="<%= isEditMode && employee != null ? employee.getEmail() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="text" id="phone" name="phone"
                                   value="<%= isEditMode && employee != null ? employee.getPhone() : "" %>">
                        </div>
                    </div>

                    <!-- Personal Details -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="dateOfBirth">Date of Birth</label>
                            <input type="date" id="dateOfBirth" name="dateOfBirth"
                                   value="<%= isEditMode && employee != null ? employee.getDateOfBirth() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender</label>
                            <select id="gender" name="gender">
                                <option value="">Select Gender...</option>
                                <option value="MALE" <%= isEditMode && employee != null && "MALE".equals(employee.getGender()) ? "selected" : "" %>>Male</option>
                                <option value="FEMALE" <%= isEditMode && employee != null && "FEMALE".equals(employee.getGender()) ? "selected" : "" %>>Female</option>
                                <option value="OTHER" <%= isEditMode && employee != null && "OTHER".equals(employee.getGender()) ? "selected" : "" %>>Other</option>
                            </select>
                        </div>
                    </div>

                    <!-- Address Information -->
                    <div class="form-group mb-4">
                        <label for="address">Address</label>
                        <textarea id="address" name="address" rows="3"><%= isEditMode && employee != null && employee.getAddress() != null ? employee.getAddress() : "" %></textarea>
                    </div>

                    <div class="grid grid-3 mb-4">
                        <div class="form-group">
                            <label for="city">City</label>
                            <input type="text" id="city" name="city"
                                   value="<%= isEditMode && employee != null ? employee.getCity() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="state">State/Province</label>
                            <input type="text" id="state" name="state"
                                   value="<%= isEditMode && employee != null ? employee.getState() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="postalCode">Postal Code</label>
                            <input type="text" id="postalCode" name="postalCode"
                                   value="<%= isEditMode && employee != null ? employee.getPostalCode() : "" %>">
                        </div>
                    </div>

                    <div class="form-group mb-4">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country"
                               value="<%= isEditMode && employee != null ? employee.getCountry() : "" %>">
                    </div>

                    <!-- Employment Information -->
                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="designation">Job Designation</label>
                            <input type="text" id="designation" name="designation"
                                   value="<%= isEditMode && employee != null ? employee.getDesignation() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="department">Department</label>
                            <input type="text" id="department" name="department"
                                   value="<%= isEditMode && employee != null ? employee.getDepartment() : "" %>">
                        </div>
                    </div>

                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="shiftId">Work Shift</label>
                            <select id="shiftId" name="shiftId">
                                <option value="">Select Shift...</option>
                                <%
                                    List<Shift> shifts = (List<Shift>) request.getAttribute("shifts");
                                    if (shifts != null) {
                                        for (Shift shift : shifts) {
                                %>
                                <option value="<%= shift.getShiftId() %>"
                                        <%= isEditMode && employee != null && employee.getShiftId() == shift.getShiftId() ? "selected" : "" %>>
                                    <%= shift.getShiftName() %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="basicSalary">Basic Salary (LSL)</label>
                            <input type="number" id="basicSalary" name="basicSalary" step="0.01" min="0"
                                   value="<%= isEditMode && employee != null ? employee.getBasicSalary() : "" %>">
                        </div>
                    </div>

                    <div class="grid grid-2 mb-4">
                        <div class="form-group">
                            <label for="hireDate">Hire Date</label>
                            <input type="date" id="hireDate" name="hireDate"
                                   value="<%= isEditMode && employee != null ? employee.getHireDate() : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="employmentStatus">Employment Status</label>
                            <select id="employmentStatus" name="employmentStatus">
                                <option value="ACTIVE" <%= isEditMode && employee != null && "ACTIVE".equals(employee.getEmploymentStatus()) ? "selected" : "" %>>Active</option>
                                <option value="INACTIVE" <%= isEditMode && employee != null && "INACTIVE".equals(employee.getEmploymentStatus()) ? "selected" : "" %>>Inactive</option>
                                <option value="TERMINATED" <%= isEditMode && employee != null && "TERMINATED".equals(employee.getEmploymentStatus()) ? "selected" : "" %>>Terminated</option>
                            </select>
                        </div>
                    </div>

                    <!-- Banking Information -->
                    <div class="form-group mb-4">
                        <label for="bankAccountNumber">Bank Account Number</label>
                        <input type="text" id="bankAccountNumber" name="bankAccountNumber"
                               value="<%= isEditMode && employee != null ? employee.getBankAccountNumber() : "" %>">
                    </div>

                    <!-- Form Actions -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                                <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/>
                            </svg>
                            <%= isEditMode ? "Update Employee" : "Create Employee" %>
                        </button>
                        <a href="${pageContext.request.contextPath}/employee?action=list" class="btn btn-secondary">
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
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.3);
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .btn-container {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary {
            background-color: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background-color: #764ba2;
        }
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
    </style>
</head>
<body>

<!-- Header -->
<div class="header">
    <h1><%
        Boolean isEdit = (Boolean) request.getAttribute("isEdit");
        if (isEdit != null && isEdit) {
    %>Edit Employee<% } else { %>Add New Employee<% } %></h1>
</div>

<!-- Container -->
<div class="container">
    <div class="form-container">
        <%
            Employee employee = (Employee) request.getAttribute("employee");
            Boolean isEditMode = (Boolean) request.getAttribute("isEdit");
            isEditMode = isEditMode != null ? isEditMode : false;
        %>
        
        <form method="POST" action="${pageContext.request.contextPath}/employee?action=save">
            <% if (isEditMode) { %>
            <input type="hidden" name="employeeId" value="<%= employee.getEmployeeId() %>">
            <% } %>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name *</label>
                    <input type="text" id="firstName" name="firstName" required 
                           value="<%= isEditMode && employee != null ? employee.getFirstName() : "" %>">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name *</label>
                    <input type="text" id="lastName" name="lastName" required
                           value="<%= isEditMode && employee != null ? employee.getLastName() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email"
                           value="<%= isEditMode && employee != null ? employee.getEmail() : "" %>">
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone"
                           value="<%= isEditMode && employee != null ? employee.getPhone() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="designation">Designation</label>
                    <input type="text" id="designation" name="designation"
                           value="<%= isEditMode && employee != null ? employee.getDesignation() : "" %>">
                </div>
                <div class="form-group">
                    <label for="department">Department</label>
                    <input type="text" id="department" name="department"
                           value="<%= isEditMode && employee != null ? employee.getDepartment() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="shiftId">Shift</label>
                    <select id="shiftId" name="shiftId">
                        <option value="">Select Shift...</option>
                        <%
                            List<Shift> shifts = (List<Shift>) request.getAttribute("shifts");
                            if (shifts != null) {
                                for (Shift shift : shifts) {
                        %>
                        <option value="<%= shift.getShiftId() %>" 
                                <%= isEditMode && employee != null && employee.getShiftId() == shift.getShiftId() ? "selected" : "" %>>
                            <%= shift.getShiftName() %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="basicSalary">Basic Salary</label>
                    <input type="number" id="basicSalary" name="basicSalary" step="0.01"
                           value="<%= isEditMode && employee != null ? employee.getBasicSalary() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="hireDate">Hire Date</label>
                    <input type="date" id="hireDate" name="hireDate"
                           value="<%= isEditMode && employee != null ? employee.getHireDate() : "" %>">
                </div>
            </div>
            
            <div class="btn-container">
                <button type="submit" class="btn btn-primary"><%= isEditMode ? "Update Employee" : "Add Employee" %></button>
                <a href="${pageContext.request.contextPath}/employee?action=list" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
