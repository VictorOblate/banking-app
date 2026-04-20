<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employees - Online Banking</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .container {
            padding: 30px;
        }
        .action-buttons {
            margin-bottom: 20px;
        }
        .btn {
            padding: 10px 20px;
            margin-right: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
        .btn-primary {
            background-color: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background-color: #764ba2;
        }
        .btn-danger {
            background-color: #e74c3c;
            color: white;
        }
        .btn-danger:hover {
            background-color: #c0392b;
        }
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
        .table-container {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            overflow: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        thead {
            background-color: #f8f9fa;
            border-bottom: 2px solid #ddd;
        }
        th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #333;
        }
        tbody tr {
            border-bottom: 1px solid #ddd;
            transition: background-color 0.2s;
        }
        tbody tr:hover {
            background-color: #f9f9f9;
        }
        td {
            padding: 15px;
            color: #555;
        }
    </style>
</head>
<body>

<!-- Header -->
<div class="header">
    <h1>👔 Employee Management</h1>
    <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a>
</div>

<!-- Container -->
<div class="container">
    <!-- Action Buttons -->
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/employee?action=add" class="btn btn-primary">+ Add New Employee</a>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Back to Dashboard</a>
    </div>
    
    <!-- Table -->
    <div class="table-container">
        <%
            List<Employee> employees = (List<Employee>) request.getAttribute("employees");
            if (employees != null && employees.size() > 0) {
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
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
                    <td>₹<%= employee.getBasicSalary() %></td>
                    <td><%= employee.getEmploymentStatus() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/employee?action=edit&employeeId=<%= employee.getEmployeeId() %>" class="btn btn-primary">Edit</a>
                        <form method="POST" action="${pageContext.request.contextPath}/employee" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="employeeId" value="<%= employee.getEmployeeId() %>">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
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
        <div style="padding: 40px; text-align: center;">
            <p>No employees found</p>
            <a href="${pageContext.request.contextPath}/employee?action=add" class="btn btn-primary">Add First Employee</a>
        </div>
        <%
            }
        %>
    </div>
</div>

</body>
</html>
