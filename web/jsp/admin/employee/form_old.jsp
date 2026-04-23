<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Employee" %>
<%@ page import="com.bankingapp.model.Shift" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Form - Online Banking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
        }
        .header h1 {
            margin: 0;
        }
        .container {
            padding: 30px;
            max-width: 800px;
            margin: 0 auto;
        }
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        .form-group input[type="text"],
        .form-group input[type="email"],
        .form-group input[type="date"],
        .form-group input[type="number"],
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group input:focus,
        .form-group select:focus {
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
