<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.bankingapp.model.Admin" %>
<%@ page import="com.bankingapp.util.Constants" %>
<%
    // Get logged-in admin from session only
    Admin loggedInAdmin = (Admin) session.getAttribute(Constants.ADMIN_SESSION);
    if (loggedInAdmin == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<header>
    <div class="header-container">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.svg" alt="Basotho Ownership Bank logo">
            <span>Basotho Ownership Bank</span>
        </div>
        
        <nav>
            <a href="<%= request.getContextPath() %>/dashboard" class="<%= request.getRequestURI().contains("dashboard") ? "active" : "" %>">
                Dashboard
            </a>
            <a href="<%= request.getContextPath() %>/customer" class="<%= request.getRequestURI().contains("customer") ? "active" : "" %>">
                Customers
            </a>
            <a href="<%= request.getContextPath() %>/employee" class="<%= request.getRequestURI().contains("employee") ? "active" : "" %>">
                Employees
            </a>
            <a href="<%= request.getContextPath() %>/overtime" class="<%= request.getRequestURI().contains("overtime") ? "active" : "" %>">
                Overtime
            </a>
            <a href="<%= request.getContextPath() %>/shift" class="<%= request.getRequestURI().contains("shift") ? "active" : "" %>">
                Shifts
            </a>
            <a href="<%= request.getContextPath() %>/package" class="<%= request.getRequestURI().contains("package") ? "active" : "" %>">
                Packages
            </a>
            <a href="<%= request.getContextPath() %>/transaction" class="<%= request.getRequestURI().contains("transaction") ? "active" : "" %>">
                Transactions
            </a>
            <a href="<%= request.getContextPath() %>/payment" class="<%= request.getRequestURI().contains("payment") ? "active" : "" %>">
                Payments
            </a>
            <a href="<%= request.getContextPath() %>/report" class="<%= request.getRequestURI().contains("report") ? "active" : "" %>">
                Reports
            </a>
            <a href="<%= request.getContextPath() %>/admin" class="<%= request.getServletPath() != null && request.getServletPath().equals("/admin") ? "active" : "" %>">
                Profile
            </a>
        </nav>
        
        <div class="user-menu">
            <span>Welcome, <%= loggedInAdmin.getUsername() %>!</span>
            <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Logout</a>
        </div>
    </div>
</header>
