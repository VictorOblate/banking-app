<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.banking.model.Transaction" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Management - Basotho Ownership Bank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon">
</head>
<body>
    <!-- Include Header -->
    <%@ include file="/jsp/components/header.jsp" %>

    <main class="main-content">
        <div class="container">
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

            <!-- Page Header -->
            <div class="page-header">
                <div class="page-title">
                    <svg width="32" height="32" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M7 2v11h3v9l7-12h-4l4-8z"/>
                    </svg>
                    <h1>Transaction Management</h1>
                </div>
                <div class="page-actions">
                    <a href="${pageContext.request.contextPath}/transaction?action=add" class="btn btn-primary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                            <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                        </svg>
                        New Transaction
                    </a>
                </div>
            </div>

            <!-- Transaction Statistics -->
            <div class="stats-grid">
                <%
                    Integer totalTransactions = (Integer) request.getAttribute("totalTransactions");
                    Integer todayTransactions = (Integer) request.getAttribute("todayTransactions");
                    Double totalAmount = (Double) request.getAttribute("totalAmount");
                    Double todayAmount = (Double) request.getAttribute("todayAmount");

                    DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");
                    totalTransactions = totalTransactions != null ? totalTransactions : 0;
                    todayTransactions = todayTransactions != null ? todayTransactions : 0;
                    totalAmount = totalAmount != null ? totalAmount : 0.0;
                    todayAmount = todayAmount != null ? todayAmount : 0.0;
                %>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M7 2v11h3v9l7-12h-4l4-8z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value"><%= totalTransactions %></div>
                        <div class="stat-label">Total Transactions</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value"><%= todayTransactions %></div>
                        <div class="stat-label">Today's Transactions</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M11.8 10.9c-2.27-.59-3-1.2-3-2.15 0-1.09 1.01-1.85 2.7-1.85 1.78 0 2.44.85 2.5 2.1h2.21c-.07-1.72-1.12-3.3-3.21-3.81V3h-3v2.16c-1.94.42-3.5 1.68-3.5 3.61 0 2.31 1.91 3.46 4.7 4.13 2.5.6 3 1.48 3 2.41 0 .69-.49 1.79-2.7 1.79-2.06 0-2.87-.92-2.98-2.1h-2.2c.12 2.19 1.76 3.42 3.68 3.83V21h3v-2.15c1.95-.37 3.5-1.5 3.5-3.55 0-2.84-2.43-3.81-4.7-4.4z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value">LSL <%= currencyFormat.format(totalAmount) %></div>
                        <div class="stat-label">Total Amount</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                        </svg>
                    </div>
                    <div class="stat-content">
                        <div class="stat-value">LSL <%= currencyFormat.format(todayAmount) %></div>
                        <div class="stat-label">Today's Amount</div>
                    </div>
                </div>
            </div>

            <!-- Transaction List -->
            <div class="card">
                <div class="card-title">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M7 2v11h3v9l7-12h-4l4-8z"/>
                    </svg>
                    All Transactions
                </div>

                <%
                    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
                    if (transactions != null && !transactions.isEmpty()) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                %>
                <div class="table-responsive">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Account Number</th>
                                <th>Type</th>
                                <th>Amount</th>
                                <th>Date</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Transaction transaction : transactions) {
                            %>
                            <tr>
                                <td><%= transaction.getTransactionId() %></td>
                                <td><%= transaction.getAccountNumber() != null ? transaction.getAccountNumber() : "N/A" %></td>
                                <td>
                                    <span class="badge badge-<%= transaction.getTransactionType() != null ? transaction.getTransactionType().toLowerCase() : "default" %>">
                                        <%= transaction.getTransactionType() != null ? transaction.getTransactionType() : "N/A" %>
                                    </span>
                                </td>
                                <td class="amount">LSL <%= currencyFormat.format(transaction.getAmount()) %></td>
                                <td><%= transaction.getTransactionDate() != null ? dateFormat.format(transaction.getTransactionDate()) : "N/A" %></td>
                                <td><%= transaction.getDescription() != null ? transaction.getDescription() : "N/A" %></td>
                                <td>
                                    <span class="status status-<%= transaction.getStatus() != null ? transaction.getStatus().toLowerCase() : "pending" %>">
                                        <%= transaction.getStatus() != null ? transaction.getStatus() : "Pending" %>
                                    </span>
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="${pageContext.request.contextPath}/transaction?action=view&id=<%= transaction.getTransactionId() %>" class="btn btn-sm btn-info">
                                            <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                                <path d="M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"/>
                                            </svg>
                                            View
                                        </a>
                                        <a href="${pageContext.request.contextPath}/transaction?action=edit&id=<%= transaction.getTransactionId() %>" class="btn btn-sm btn-warning">
                                            <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor">
                                                <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/>
                                            </svg>
                                            Edit
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
                <%
                    } else {
                %>
                <div class="empty-state">
                    <svg width="64" height="64" viewBox="0 0 24 24" fill="currentColor" opacity="0.3">
                        <path d="M7 2v11h3v9l7-12h-4l4-8z"/>
                    </svg>
                    <h3>No Transactions Found</h3>
                    <p>There are no transactions to display at the moment.</p>
                    <a href="${pageContext.request.contextPath}/transaction?action=add" class="btn btn-primary">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor" style="margin-right: 8px;">
                            <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                        </svg>
                        Add First Transaction
                    </a>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <%@ include file="/jsp/components/footer.jsp" %>
</body>
</html>
