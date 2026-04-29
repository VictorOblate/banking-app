<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bulk Overtime Payment Result - Banking App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        .result-box { padding: 30px; border-radius: 8px; text-align: center; }
        .result-box.success { background-color: #d4edda; border: 2px solid #28a745; }
        .result-box.error { background-color: #f8d7da; border: 2px solid #dc3545; }
        .result-box h2 { margin: 0 0 10px 0; }
        .result-box p { margin: 10px 0; font-size: 16px; }
        .summary { background-color: #f0f0f0; padding: 20px; border-radius: 4px; margin: 20px 0; text-align: left; }
        .summary-item { margin: 10px 0; display: flex; justify-content: space-between; }
        .summary-item strong { min-width: 200px; }
        .buttons { margin-top: 30px; display: flex; gap: 10px; justify-content: center; }
        a, button { text-decoration: none; color: white; padding: 12px 20px; border: none; border-radius: 4px; cursor: pointer; display: inline-block; }
        .btn-primary { background-color: #2196F3; }
        .btn-primary:hover { background-color: #0b7dda; }
        .btn-secondary { background-color: #666; }
        .btn-secondary:hover { background-color: #555; }
    </style>
</head>
<body>
    <jsp:include page="/jsp/components/header.jsp" />
    
    <div class="container">
        <c:if test="${not empty success}">
            <div class="result-box success">
                <h2>✓ Bulk Overtime Payment Batch Created Successfully</h2>
                <p>${success}</p>
                
                <div class="summary">
                    <div class="summary-item">
                        <strong>Batch ID:</strong>
                        <span>${batchId}</span>
                    </div>
                    <div class="summary-item">
                        <strong>Status:</strong>
                        <span>Ready for Approval</span>
                    </div>
                    <div class="summary-item">
                        <strong>Next Step:</strong>
                        <span>The batch has been created with all approved overtime records. Please review and approve it before processing payments.</span>
                    </div>
                </div>
                
                <div class="buttons">
                    <a href="${pageContext.request.contextPath}/payment?action=batch_view&id=${batchId}" class="btn-primary">View Batch Details</a>
                    <a href="${pageContext.request.contextPath}/payment?action=batch_list" class="btn-secondary">Back to Batches</a>
                </div>
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="result-box error">
                <h2>✗ Error Creating Overtime Payment Batch</h2>
                <p>${error}</p>
                
                <div class="buttons">
                    <a href="${pageContext.request.contextPath}/payment?action=bulk_overtime" class="btn-primary">Try Again</a>
                    <a href="${pageContext.request.contextPath}/payment?action=list" class="btn-secondary">Back to Payments</a>
                </div>
            </div>
        </c:if>
    </div>
    
    <jsp:include page="/jsp/components/footer.jsp" />
</body>
</html>
