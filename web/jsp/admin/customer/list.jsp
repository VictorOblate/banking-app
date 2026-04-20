<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customers - Online Banking</title>
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
        .message {
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
    </style>
</head>
<body>

<!-- Header -->
<div class="header">
    <h1>👥 Customer Management</h1>
    <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a>
</div>

<!-- Container -->
<div class="container">
    <!-- Messages -->
    <%
        String error = (String) request.getAttribute("error");
        String success = (String) request.getAttribute("success");
        if (error != null) {
    %>
    <div class="message error"><%= error %></div>
    <%
        }
        if (success != null) {
    %>
    <div class="message success"><%= success %></div>
    <%
        }
    %>
    
    <!-- Action Buttons -->
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/customer?action=add" class="btn btn-primary">+ Add New Customer</a>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Back to Dashboard</a>
    </div>
    
    <!-- Table -->
    <div class="table-container">
        <%
            List<Customer> customers = (List<Customer>) request.getAttribute("customers");
            if (customers != null && customers.size() > 0) {
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Account Number</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Customer customer : customers) {
                %>
                <tr>
                    <td><%= customer.getCustomerId() %></td>
                    <td><%= customer.getFullName() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getPhone() %></td>
                    <td><%= customer.getAccountNumber() %></td>
                    <td><%= customer.getAccountStatus() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/customer?action=edit&customerId=<%= customer.getCustomerId() %>" class="btn btn-primary">Edit</a>
                        <form method="POST" action="${pageContext.request.contextPath}/customer" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
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
            <p>No customers found</p>
            <a href="${pageContext.request.contextPath}/customer?action=add" class="btn btn-primary">Add First Customer</a>
        </div>
        <%
            }
        %>
    </div>
</div>

</body>
</html>
