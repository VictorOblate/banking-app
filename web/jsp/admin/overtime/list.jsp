<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Overtime Management - Banking App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        .header-section { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .filter-section { margin-bottom: 20px; display: flex; gap: 10px; }
        .filter-section select { padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .btn-new { background-color: #4CAF50; color: white; padding: 10px 20px; border-radius: 4px; text-decoration: none; display: inline-block; }
        .btn-new:hover { background-color: #45a049; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th { background-color: #f5f5f5; padding: 12px; text-align: left; border-bottom: 2px solid #ddd; }
        td { padding: 12px; border-bottom: 1px solid #ddd; }
        tr:hover { background-color: #f9f9f9; }
        .status-badge { padding: 5px 10px; border-radius: 12px; font-size: 12px; font-weight: bold; }
        .status-pending { background-color: #ffeaa7; color: #d63031; }
        .status-approved { background-color: #55efc4; color: #00b894; }
        .status-rejected { background-color: #ff7675; color: white; }
        .status-processed { background-color: #74b9ff; color: white; }
        .action-buttons { display: flex; gap: 5px; }
        .btn-view, .btn-approve, .btn-reject { padding: 6px 12px; border-radius: 4px; text-decoration: none; color: white; font-size: 12px; cursor: pointer; border: none; }
        .btn-view { background-color: #2196F3; }
        .btn-view:hover { background-color: #0b7dda; }
        .btn-approve { background-color: #28a745; }
        .btn-approve:hover { background-color: #218838; }
        .btn-reject { background-color: #dc3545; }
        .btn-reject:hover { background-color: #c82333; }
        .empty-message { text-align: center; padding: 40px; color: #666; }
    </style>
</head>
<body>
    <jsp:include page="/jsp/components/header.jsp" />
    
    <div class="container">
        <div class="header-section">
            <h1>Overtime Management</h1>
            <a href="${pageContext.request.contextPath}/overtime?action=add" class="btn-new">Record New Overtime</a>
        </div>
        
        <div class="filter-section">
            <form method="GET" action="${pageContext.request.contextPath}/overtime" style="display: flex; gap: 10px;">
                <input type="hidden" name="action" value="list">
                <select name="status">
                    <option value="">All Status</option>
                    <option value="PENDING">Pending</option>
                    <option value="APPROVED">Approved</option>
                    <option value="REJECTED">Rejected</option>
                    <option value="PROCESSED">Processed</option>
                </select>
                <button type="submit" style="padding: 8px 15px; background-color: #666; color: white; border: none; border-radius: 4px; cursor: pointer;">Filter</button>
                <a href="${pageContext.request.contextPath}/overtime?action=list" style="padding: 8px 15px; background-color: #999; color: white; border-radius: 4px; text-decoration: none; display: inline-block;">Reset</a>
            </form>
        </div>
        
        <c:if test="${empty overtimes}">
            <div class="empty-message">
                <p>No overtime records found.</p>
                <a href="${pageContext.request.contextPath}/overtime?action=add" class="btn-new">Record First Overtime</a>
            </div>
        </c:if>
        
        <c:if test="${not empty overtimes}">
            <table>
                <thead>
                    <tr>
                        <th>Employee ID</th>
                        <th>Employee Name</th>
                        <th>Overtime Date</th>
                        <th>Hours Worked</th>
                        <th>Hourly Rate</th>
                        <th>Overtime Amount</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="overtime" items="${overtimes}">
                        <tr>
                            <td>${overtime.employeeId}</td>
                            <td>${overtime.employeeId}</td>
                            <td><fmt:formatDate value="${overtime.overtimeDate}" pattern="dd-MMM-yyyy"/></td>
                            <td>${overtime.hoursWorked}</td>
                            <td>₹<fmt:formatNumber value="${overtime.hourlyRate}" type="number" maxFractionDigits="2"/></td>
                            <td>₹<fmt:formatNumber value="${overtime.overtimeAmount}" type="number" maxFractionDigits="2"/></td>
                            <td>
                                <span class="status-badge status-${overtime.status.toLowerCase()}">
                                    ${overtime.status}
                                </span>
                            </td>
                            <td>
                                <div class="action-buttons">
                                    <a href="${pageContext.request.contextPath}/overtime?action=view&id=${overtime.overtimeId}" class="btn-view">View</a>
                                    <c:if test="${overtime.status == 'PENDING'}">
                                        <form method="POST" action="${pageContext.request.contextPath}/overtime" style="display: inline;">
                                            <input type="hidden" name="action" value="approve">
                                            <input type="hidden" name="id" value="${overtime.overtimeId}">
                                            <button type="submit" class="btn-approve" onclick="return confirm('Approve this overtime?');">Approve</button>
                                        </form>
                                        <form method="POST" action="${pageContext.request.contextPath}/overtime" style="display: inline;">
                                            <input type="hidden" name="action" value="reject">
                                            <input type="hidden" name="id" value="${overtime.overtimeId}">
                                            <button type="submit" class="btn-reject" onclick="return confirm('Reject this overtime?');">Reject</button>
                                        </form>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <jsp:include page="/jsp/components/footer.jsp" />
</body>
</html>
