<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.bankingapp.model.Admin" %>
<%@ page import="com.bankingapp.util.Constants" %>
<%
    // Get admin from request attribute (set by servlet) or session
    Admin admin = (Admin) request.getAttribute("admin");
    if (admin == null) {
        Object sessionAdmin = session.getAttribute(Constants.ADMIN_SESSION);
        if (sessionAdmin != null && sessionAdmin instanceof Admin) {
            admin = (Admin) sessionAdmin;
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
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
            <a href="<%= request.getContextPath() %>/package" class="<%= request.getRequestURI().contains("package") ? "active" : "" %>">
                Packages
            </a>
            <a href="<%= request.getContextPath() %>/transaction" class="<%= request.getRequestURI().contains("transaction") ? "active" : "" %>">
                Transactions
            </a>
            <a href="<%= request.getContextPath() %>/report" class="<%= request.getRequestURI().contains("report") ? "active" : "" %>">
                Reports
            </a>
        </nav>
        
        <div class="user-menu">
            <span>Welcome, <%= admin != null ? admin.getUsername() : "Admin" %>!</span>
            <a href="<%= request.getContextPath() %>/profile">Profile</a>
            <a href="<%= request.getContextPath() %>/settings">Settings</a>
            <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Logout</a>
        </div>
    </div>
</header>
