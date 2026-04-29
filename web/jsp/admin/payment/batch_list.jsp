<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Batches - Banking App</title>
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
        .status-draft { background-color: #ffeaa7; color: #d63031; }
        .status-submitted { background-color: #74b9ff; color: white; }
        .status-approved { background-color: #55efc4; color: #00b894; }
        .status-processed { background-color: #55efc4; color: #00b894; }
        .status-rejected { background-color: #ff7675; color: white; }
        .action-buttons { display: flex; gap: 5px; }
        .btn-view, .btn-approve, .btn-process { padding: 6px 12px; border-radius: 4px; text-decoration: none; color: white; font-size: 12px; cursor: pointer; border: none; }
        .btn-view { background-color: #2196F3; }
        .btn-view:hover { background-color: #0b7dda; }
        .btn-approve { background-color: #28a745; }
        .btn-approve:hover { background-color: #218838; }
        .btn-process { background-color: #17a2b8; }
        .btn-process:hover { background-color: #138496; }
        .empty-message { text-align: center; padding: 40px; color: #666; }
    </style>
</head>
<body>
    <jsp:include page="/jsp/components/header.jsp" />
    
    <div class="container">
        <div class="header-section">
            <h1>Payment Batches</h1>
            <div>
                <a href="${pageContext.request.contextPath}/payment?action=bulk_salary" class="btn-new">New Salary Batch</a>
                <a href="${pageContext.request.contextPath}/payment?action=bulk_overtime" class="btn-new" style="background-color: #2196F3; margin-left: 10px;">New Overtime Batch</a>
            </div>
        </div>
        
        <div class="filter-section">
            <form method="GET" action="${pageContext.request.contextPath}/payment" style="display: flex; gap: 10px;">
                <input type="hidden" name="action" value="batch_list">
                <select name="type">
                    <option value="">All Types</option>
                    <option value="SALARY">Salary</option>
                    <option value="OVERTIME">Overtime</option>
                </select>
                <select name="status">
                    <option value="">All Status</option>
                    <option value="DRAFT">Draft</option>
                    <option value="SUBMITTED">Submitted</option>
                    <option value="APPROVED">Approved</option>
                    <option value="PROCESSED">Processed</option>
                    <option value="REJECTED">Rejected</option>
                </select>
                <button type="submit" style="padding: 8px 15px; background-color: #666; color: white; border: none; border-radius: 4px; cursor: pointer;">Filter</button>
                <a href="${pageContext.request.contextPath}/payment?action=batch_list" style="padding: 8px 15px; background-color: #999; color: white; border-radius: 4px; text-decoration: none; display: inline-block;">Reset</a>
            </form>
        </div>
        
        <c:if test="${empty batches}">
            <div class="empty-message">
                <p>No payment batches found.</p>
                <a href="${pageContext.request.contextPath}/payment?action=bulk_salary" class="btn-new">Create First Batch</a>
            </div>
        </c:if>
        
        <c:if test="${not empty batches}">
            <table>
                <thead>
                    <tr>
                        <th>Batch ID</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Records</th>
                        <th>Total Amount</th>
                        <th>Payment Date</th>
                        <th>Status</th>
                        <th>Created</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="batch" items="${batches}">
                        <tr>
                            <td>#${batch.batchId}</td>
                            <td>${batch.batchName}</td>
                            <td>${batch.batchType}</td>
                            <td>${batch.totalRecords}</td>
                            <td><fmt:formatNumber value="${batch.totalAmount}" type="currency" currencySymbol="₹"/></td>
                            <td><fmt:formatDate value="${batch.paymentDate}" pattern="dd-MMM-yyyy"/></td>
                            <td>
                                <span class="status-badge status-${batch.batchStatus.toLowerCase()}">
                                    ${batch.batchStatus}
                                </span>
                            </td>
                            <td><fmt:formatDate value="${batch.createdDate}" pattern="dd-MMM-yyyy HH:mm"/></td>
                            <td>
                                <div class="action-buttons">
                                    <a href="${pageContext.request.contextPath}/payment?action=batch_view&id=${batch.batchId}" class="btn-view">View</a>
                                    <c:if test="${batch.batchStatus == 'SUBMITTED'}">
                                        <form method="POST" action="${pageContext.request.contextPath}/payment" style="display: inline;">
                                            <input type="hidden" name="action" value="approve_batch">
                                            <input type="hidden" name="batchId" value="${batch.batchId}">
                                            <button type="submit" class="btn-approve" onclick="return confirm('Approve this batch?');">Approve</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${batch.batchStatus == 'APPROVED'}">
                                        <form method="POST" action="${pageContext.request.contextPath}/payment" style="display: inline;">
                                            <input type="hidden" name="action" value="process_batch">
                                            <input type="hidden" name="batchId" value="${batch.batchId}">
                                            <button type="submit" class="btn-process" onclick="return confirm('Process this batch?');">Process</button>
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
