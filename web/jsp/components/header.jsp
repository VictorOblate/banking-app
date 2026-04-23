<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Get session admin
    com.bankingapp.model.Admin admin = null;
    Object sessionAdmin = session.getAttribute(com.bankingapp.util.Constants.ADMIN_SESSION);
    if (sessionAdmin != null) {
        admin = (com.bankingapp.model.Admin) sessionAdmin;
    } else {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<header>
    <div class="header-container">
        <div class="logo">
            <svg width="40" height="40" viewBox="0 0 200 200">
                <circle cx="100" cy="100" r="95" fill="#f5f5f5" stroke="#2d7a4f" stroke-width="2"/>
                <circle cx="100" cy="100" r="88" fill="none" stroke="#4caf50" stroke-width="1.5" opacity="0.3"/>
                <path d="M 100 40 L 140 60 L 140 90 Q 100 130 100 130 Q 100 130 60 90 L 60 60 Z" fill="none" stroke="#2d7a4f" stroke-width="2.5"/>
                <circle cx="100" cy="85" r="12" fill="#4caf50"/>
                <rect x="93" y="95" width="14" height="20" fill="none" stroke="#2d7a4f" stroke-width="1.5" rx="2"/>
            </svg>
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
