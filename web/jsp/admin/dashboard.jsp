<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bankingapp.model.Admin" %>
<%
    // Check if user is logged in
    Admin admin = (Admin) session.getAttribute("adminUser");
    if (admin == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Online Banking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
        }
        .top-nav {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .top-nav h1 {
            margin: 0;
            font-size: 24px;
        }
        .user-info {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        .logout-link {
            background-color: rgba(255, 255, 255, 0.2);
            padding: 8px 15px;
            border-radius: 5px;
            text-decoration: none;
            color: white;
            cursor: pointer;
        }
        .logout-link:hover {
            background-color: rgba(255, 255, 255, 0.3);
        }
        .container {
            display: flex;
            min-height: calc(100vh - 60px);
        }
        .sidebar {
            width: 280px;
            background-color: #2c3e50;
            color: white;
            padding: 30px 0;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
        }
        .sidebar h3 {
            color: #667eea;
            margin: 0 0 20px 20px;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .sidebar ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .sidebar li {
            margin: 0;
        }
        .sidebar a {
            display: block;
            padding: 15px 20px;
            color: #ecf0f1;
            text-decoration: none;
            transition: all 0.3s;
            border-left: 3px solid transparent;
        }
        .sidebar a:hover,
        .sidebar a.active {
            background-color: #314456;
            border-left-color: #667eea;
        }
        .main-content {
            flex: 1;
            padding: 30px;
            overflow-y: auto;
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .dashboard-card {
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center;
            cursor: pointer;
            transition: all 0.3s;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        .dashboard-card icon {
            font-size: 40px;
            display: block;
            margin-bottom: 15px;
        }
        .dashboard-card h3 {
            margin: 10px 0;
            color: #333;
            font-size: 18px;
        }
        .dashboard-card p {
            margin: 10px 0 0 0;
            color: #999;
            font-size: 14px;
        }
        .card-link {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 20px;
            background-color: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .card-link:hover {
            background-color: #764ba2;
        }
    </style>
</head>
<body>

<!-- Top Navigation -->
<div class="top-nav">
    <h1>🏦 Online Banking Admin Portal</h1>
    <div class="user-info">
        <span>Welcome, <%= admin.getFullName() %></span>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
    </div>
</div>

<!-- Main Container -->
<div class="container">
    <!-- Sidebar Navigation -->
    <div class="sidebar">
        <h3>📊 Dashboard</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard" class="active">Overview</a></li>
        </ul>
        
        <h3 style="margin-top: 30px;">👥 Management</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/customer?action=list">Customers</a></li>
            <li><a href="${pageContext.request.contextPath}/employee?action=list">Employees</a></li>
            <li><a href="${pageContext.request.contextPath}/package">Packages</a></li>
            <li><a href="${pageContext.request.contextPath}/shift">Shifts</a></li>
        </ul>
        
        <h3 style="margin-top: 30px;">💰 Operations</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/payment">Payments</a></li>
            <li><a href="${pageContext.request.contextPath}/transaction">Transactions</a></li>
        </ul>
        
        <h3 style="margin-top: 30px;">📄 Reports</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/report">Generate Report</a></li>
        </ul>
    </div>
    
    <!-- Main Content -->
    <div class="main-content">
        <h2>Dashboard Overview</h2>
        
        <div class="dashboard-grid">
            <!-- Customers Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">👥</div>
                <h3>Customers</h3>
                <p>Manage customer accounts and information</p>
                <a href="${pageContext.request.contextPath}/customer?action=list" class="card-link">View Customers</a>
            </div>
            
            <!-- Employees Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">👔</div>
                <h3>Employees</h3>
                <p>Manage employee records and salaries</p>
                <a href="${pageContext.request.contextPath}/employee?action=list" class="card-link">View Employees</a>
            </div>
            
            <!-- Packages Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">📦</div>
                <h3>Packages</h3>
                <p>Manage banking service packages</p>
                <a href="${pageContext.request.contextPath}/package" class="card-link">View Packages</a>
            </div>
            
            <!-- Shifts Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">⏰</div>
                <h3>Shifts</h3>
                <p>Manage work shifts</p>
                <a href="${pageContext.request.contextPath}/shift" class="card-link">View Shifts</a>
            </div>
            
            <!-- Payments Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">💳</div>
                <h3>Payments</h3>
                <p>Process and manage payments</p>
                <a href="${pageContext.request.contextPath}/payment" class="card-link">View Payments</a>
            </div>
            
            <!-- Transactions Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">📊</div>
                <h3>Transactions</h3>
                <p>View and manage transactions</p>
                <a href="${pageContext.request.contextPath}/transaction" class="card-link">View Transactions</a>
            </div>
            
            <!-- Reports Card -->
            <div class="dashboard-card">
                <div style="font-size: 40px;">📄</div>
                <h3>Reports</h3>
                <p>Generate and export reports</p>
                <a href="${pageContext.request.contextPath}/report" class="card-link">Generate Report</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
