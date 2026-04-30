<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bulk Overtime Payment - Banking App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { background-color: #4CAF50; color: white; padding: 12px 20px; border: none; border-radius: 4px; cursor: pointer; margin-right: 10px; }
        button:hover { background-color: #45a049; }
        .btn-secondary { background-color: #666; }
        .btn-secondary:hover { background-color: #555; }
        .alert { padding: 15px; margin-bottom: 20px; border-radius: 4px; }
        .alert-danger { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .info-box { background-color: #e7f3ff; border-left: 4px solid #2196F3; padding: 15px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <%@ include file="/jsp/components/header.jsp" %>
    
    <div class="container">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
            <h1>Bulk Overtime Payment</h1>
            <a href="${pageContext.request.contextPath}/payment?action=batch_list" style="text-decoration: none; color: #2196F3;">View All Batches</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <div class="info-box">
            <strong>Bulk Overtime Payment:</strong> This feature allows you to process overtime payments for all approved overtime records. 
            The system will gather all approved overtime entries and create a batch for payment processing.
        </div>
        
        <form method="POST" action="${pageContext.request.contextPath}/payment" onsubmit="return validateForm();">
            <input type="hidden" name="action" value="process_bulk_overtime">
            
            <div class="form-group">
                <label for="batchName">Batch Name:</label>
                <input type="text" id="batchName" name="batchName" required placeholder="e.g., January 2026 Overtime">
                <small>A descriptive name for this overtime payment batch</small>
            </div>
            
            <div class="form-group">
                <label for="paymentDate">Payment Date:</label>
                <input type="date" id="paymentDate" name="paymentDate" required>
                <small>The date when these overtime payments will be made</small>
            </div>
            
            <div class="form-group">
                <label for="description">Description (Optional):</label>
                <textarea id="description" name="description" rows="4" placeholder="Enter any additional details about this batch..."></textarea>
            </div>
            
            <div class="info-box" style="margin-top: 20px;">
                <strong>Note:</strong> Only overtime records with "APPROVED" status will be included in this batch.
            </div>
            
            <div style="margin-top: 30px;">
                <button type="submit">Create and Process Batch</button>
                <a href="${pageContext.request.contextPath}/payment?action=list" style="color: #666; text-decoration: none; padding: 12px 20px; border: 1px solid #ddd; border-radius: 4px; display: inline-block;" 
                   onmouseover="this.style.backgroundColor='#f0f0f0'" onmouseout="this.style.backgroundColor='transparent'">Cancel</a>
            </div>
        </form>
    </div>
    
    <%@ include file="/jsp/components/footer.jsp" %>
    
    <script>
        function validateForm() {
            var batchName = document.getElementById("batchName").value.trim();
            var paymentDate = document.getElementById("paymentDate").value;
            
            if (batchName === "") {
                alert("Please enter a batch name");
                return false;
            }
            
            if (paymentDate === "") {
                alert("Please select a payment date");
                return false;
            }
            
            // Verify date is not in the past
            var selectedDate = new Date(paymentDate);
            var today = new Date();
            today.setHours(0, 0, 0, 0);
            
            if (selectedDate < today) {
                alert("Payment date cannot be in the past");
                return false;
            }
            
            if (!confirm("Are you sure you want to create this overtime payment batch?\n\nThis will process payments for all approved overtime records.")) {
                return false;
            }
            
            return true;
        }
        
        // Set minimum date to today
        document.addEventListener('DOMContentLoaded', function() {
            var today = new Date();
            var dd = String(today.getDate()).padStart(2, '0');
            var mm = String(today.getMonth() + 1).padStart(2, '0');
            var yyyy = today.getFullYear();
            
            document.getElementById('paymentDate').min = yyyy + '-' + mm + '-' + dd;
        });
    </script>
</body>
</html>
