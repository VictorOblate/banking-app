<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Batch Details - Banking App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container { max-width: 900px; margin: 0 auto; padding: 20px; }
        .detail-section { background-color: #f9f9f9; padding: 20px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #ddd; }
        .detail-row { display: grid; grid-template-columns: 200px 1fr; margin-bottom: 15px; }
        .detail-label { font-weight: bold; color: #333; }
        .detail-value { color: #666; }
        .status-badge { padding: 5px 10px; border-radius: 12px; font-size: 12px; font-weight: bold; display: inline-block; }
        .status-draft { background-color: #ffeaa7; color: #d63031; }
        .status-submitted { background-color: #74b9ff; color: white; }
        .status-approved { background-color: #55efc4; color: #00b894; }
        .status-processed { background-color: #55efc4; color: #00b894; }
        .status-rejected { background-color: #ff7675; color: white; }
        .amount-total { font-size: 24px; font-weight: bold; color: #28a745; }
        .action-buttons { display: flex; gap: 10px; margin-top: 20px; }
        button, a { padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; color: white; display: inline-block; font-size: 14px; }
        .btn-approve { background-color: #28a745; }
        .btn-approve:hover { background-color: #218838; }
        .btn-process { background-color: #17a2b8; }
        .btn-process:hover { background-color: #138496; }
        .btn-reject { background-color: #dc3545; }
        .btn-reject:hover { background-color: #c82333; }
        .btn-back { background-color: #666; }
        .btn-back:hover { background-color: #555; }
        .alert { padding: 15px; margin-bottom: 20px; border-radius: 4px; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .back-link { margin-bottom: 20px; }
        .back-link a { background-color: #666; text-decoration: none; padding: 8px 15px; }
        .back-link a:hover { background-color: #555; }
    </style>
</head>
<body>
    <%@ include file="/jsp/components/header.jsp" %>
    
    <div class="container">
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/payment?action=batch_list">← Back to Batches</a>
        </div>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <c:if test="${not empty batch}">
            <h1>Payment Batch Details</h1>
            
            <div class="detail-section">
                <div class="detail-row">
                    <div class="detail-label">Batch ID:</div>
                    <div class="detail-value">#${batch.batchId}</div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Batch Name:</div>
                    <div class="detail-value">${batch.batchName}</div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Type:</div>
                    <div class="detail-value">
                        <strong>${batch.batchType}</strong>
                        <c:if test="${batch.batchType == 'SALARY'}">
                            (Employee Salaries)
                        </c:if>
                        <c:if test="${batch.batchType == 'OVERTIME'}">
                            (Overtime Compensation)
                        </c:if>
                    </div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Status:</div>
                    <div class="detail-value">
                        <span class="status-badge status-${batch.batchStatus.toLowerCase()}">
                            ${batch.batchStatus}
                        </span>
                    </div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Payment Date:</div>
                    <div class="detail-value">${batch.paymentDate}</div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Total Records:</div>
                    <div class="detail-value">${batch.totalRecords} payment(s)</div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Total Amount:</div>
                    <div class="detail-value">
                        <span class="amount-total"><fmt:formatNumber value="${batch.totalAmount}" type="currency" currencySymbol="₹"/></span>
                    </div>
                </div>
                
                <div class="detail-row">
                    <div class="detail-label">Created Date:</div>
                    <div class="detail-value"><fmt:formatDate value="${batch.createdDate}" pattern="dd-MMM-yyyy HH:mm:ss"/></div>
                </div>
                
                <c:if test="${not empty batch.processedDate}">
                    <div class="detail-row">
                        <div class="detail-label">Processed Date:</div>
                        <div class="detail-value"><fmt:formatDate value="${batch.processedDate}" pattern="dd-MMM-yyyy HH:mm:ss"/></div>
                    </div>
                </c:if>
                
                <c:if test="${not empty batch.description}">
                    <div class="detail-row">
                        <div class="detail-label">Description:</div>
                        <div class="detail-value">${batch.description}</div>
                    </div>
                </c:if>
                
                <c:if test="${not empty batch.remarks}">
                    <div class="detail-row">
                        <div class="detail-label">Remarks:</div>
                        <div class="detail-value">${batch.remarks}</div>
                    </div>
                </c:if>
            </div>
            
            <div class="detail-section">
                <h2>Actions</h2>
                <div class="action-buttons">
                    <c:if test="${batch.batchStatus == 'DRAFT'}">
                        <p style="margin: 0; padding: 10px; background-color: #e3f2fd; border-radius: 4px; color: #1976d2;">
                            This batch is in draft status. You can edit or submit it for approval.
                        </p>
                    </c:if>
                    
                    <c:if test="${batch.batchStatus == 'SUBMITTED'}">
                        <p style="margin: 0; padding: 10px; background-color: #e3f2fd; border-radius: 4px; color: #1976d2;">
                            This batch is submitted and awaiting approval.
                        </p>
                        <form method="POST" action="${pageContext.request.contextPath}/payment" style="display: inline;">
                            <input type="hidden" name="action" value="approve_batch">
                            <input type="hidden" name="batchId" value="${batch.batchId}">
                            <button type="submit" class="btn-approve" onclick="return confirm('Are you sure you want to approve this batch?');">Approve Batch</button>
                        </form>
                        <form method="POST" action="${pageContext.request.contextPath}/payment" style="display: inline;">
                            <input type="hidden" name="action" value="reject_batch">
                            <input type="hidden" name="batchId" value="${batch.batchId}">
                            <button type="submit" class="btn-reject" onclick="return confirm('Are you sure you want to reject this batch?');">Reject Batch</button>
                        </form>
                    </c:if>
                    
                    <c:if test="${batch.batchStatus == 'APPROVED'}">
                        <p style="margin: 0; padding: 10px; background-color: #c8e6c9; border-radius: 4px; color: #2e7d32;">
                            This batch has been approved and is ready for processing.
                        </p>
                        <form method="POST" action="${pageContext.request.contextPath}/payment" style="display: inline;">
                            <input type="hidden" name="action" value="process_batch">
                            <input type="hidden" name="batchId" value="${batch.batchId}">
                            <button type="submit" class="btn-process" onclick="return confirm('Are you sure you want to process this batch?');">Process Batch</button>
                        </form>
                    </c:if>
                    
                    <c:if test="${batch.batchStatus == 'PROCESSED'}">
                        <p style="margin: 0; padding: 10px; background-color: #c8e6c9; border-radius: 4px; color: #2e7d32;">
                            This batch has been processed successfully.
                        </p>
                    </c:if>
                    
                    <c:if test="${batch.batchStatus == 'REJECTED'}">
                        <p style="margin: 0; padding: 10px; background-color: #ffcdd2; border-radius: 4px; color: #c62828;">
                            This batch has been rejected.
                        </p>
                    </c:if>
                    
                    <a href="${pageContext.request.contextPath}/payment?action=batch_list" class="btn-back">Back to List</a>
                </div>
            </div>
        </c:if>
        
        <c:if test="${empty batch}">
            <div style="text-align: center; padding: 40px;">
                <p>Batch not found</p>
                <a href="${pageContext.request.contextPath}/payment?action=batch_list" class="btn-back">Back to Batches</a>
            </div>
        </c:if>
    </div>
    
    <%@ include file="/jsp/components/footer.jsp" %>
</body>
</html>
