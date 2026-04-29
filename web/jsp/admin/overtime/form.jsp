<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Overtime Record - Banking App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container { max-width: 700px; margin: 0 auto; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        input:readonly, textarea:readonly { background-color: #f5f5f5; cursor: not-allowed; }
        button { background-color: #4CAF50; color: white; padding: 12px 20px; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #45a049; }
        a { text-decoration: none; color: #2196F3; }
        .alert { padding: 15px; margin-bottom: 20px; border-radius: 4px; }
        .alert-danger { background-color: #f8d7da; color: #721c24; }
        .info { background-color: #e7f3ff; border-left: 4px solid #2196F3; padding: 15px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <jsp:include page="/jsp/components/header.jsp" />
    
    <div class="container">
        <h1><c:if test="${isView}">View</c:if> Overtime Record</h1>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <div class="info">
            <strong>Instructions:</strong> Fill in the overtime details for an employee. The system will calculate the overtime amount based on hours worked and hourly rate.
        </div>
        
        <form method="POST" action="${pageContext.request.contextPath}/overtime" onsubmit="return validateForm();">
            <input type="hidden" name="action" value="save">
            
            <c:if test="${not empty overtime}">
                <input type="hidden" name="overtimeId" value="${overtime.overtimeId}">
            </c:if>
            
            <div class="form-group">
                <label for="employeeId">Employee ID: <span style="color: red;">*</span></label>
                <input type="number" id="employeeId" name="employeeId" required 
                       value="${not empty overtime ? overtime.employeeId : ''}"
                       placeholder="Enter employee ID" ${isView ? 'readonly' : ''}>
            </div>
            
            <div class="form-group">
                <label for="overtimeDate">Overtime Date: <span style="color: red;">*</span></label>
                <input type="date" id="overtimeDate" name="overtimeDate" required
                       value="${not empty overtime ? overtime.overtimeDate : ''}"
                       ${isView ? 'readonly' : ''}>
            </div>
            
            <div class="form-group">
                <label for="hoursWorked">Hours Worked: <span style="color: red;">*</span></label>
                <input type="number" id="hoursWorked" name="hoursWorked" step="0.5" required
                       value="${not empty overtime ? overtime.hoursWorked : ''}"
                       placeholder="Enter hours worked (e.g., 2.5)" ${isView ? 'readonly' : ''}>
            </div>
            
            <div class="form-group">
                <label for="hourlyRate">Hourly Rate (₹): <span style="color: red;">*</span></label>
                <input type="number" id="hourlyRate" name="hourlyRate" step="0.01" required
                       value="${not empty overtime ? overtime.hourlyRate : ''}"
                       placeholder="Enter hourly rate (e.g., 500)" ${isView ? 'readonly' : ''}>
            </div>
            
            <div class="form-group">
                <label for="overtimeType">Overtime Type:</label>
                <select id="overtimeType" name="overtimeType" ${isView ? 'disabled' : ''}>
                    <option value="">Select type</option>
                    <option value="DAILY" ${not empty overtime && overtime.overtimeType == 'DAILY' ? 'selected' : ''}>Daily</option>
                    <option value="WEEKLY" ${not empty overtime && overtime.overtimeType == 'WEEKLY' ? 'selected' : ''}>Weekly</option>
                    <option value="MONTHLY" ${not empty overtime && overtime.overtimeType == 'MONTHLY' ? 'selected' : ''}>Monthly</option>
                    <option value="SPECIAL" ${not empty overtime && overtime.overtimeType == 'SPECIAL' ? 'selected' : ''}>Special Project</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="remarks">Remarks:</label>
                <textarea id="remarks" name="remarks" rows="4" placeholder="Enter any additional details..." ${isView ? 'readonly' : ''}>${not empty overtime ? overtime.remarks : ''}</textarea>
            </div>
            
            <c:if test="${isView}">
                <div class="form-group">
                    <label>Status:</label>
                    <input type="text" value="${overtime.status}" readonly>
                </div>
                
                <div class="form-group">
                    <label>Overtime Amount:</label>
                    <input type="text" value="₹ ${overtime.overtimeAmount}" readonly>
                </div>
                
                <div class="form-group">
                    <label>Created Date:</label>
                    <input type="text" value="${overtime.createdDate}" readonly>
                </div>
            </c:if>
            
            <div style="margin-top: 30px;">
                <c:if test="${not isView}">
                    <button type="submit">Save Overtime Record</button>
                </c:if>
                <a href="${pageContext.request.contextPath}/overtime?action=list" style="color: #666; text-decoration: none; margin-left: 10px; padding: 12px 20px; border: 1px solid #ddd; border-radius: 4px; display: inline-block;" 
                   onmouseover="this.style.backgroundColor='#f0f0f0'" onmouseout="this.style.backgroundColor='transparent'">Back</a>
            </div>
        </form>
    </div>
    
    <jsp:include page="/jsp/components/footer.jsp" />
    
    <script>
        function validateForm() {
            var employeeId = document.getElementById("employeeId").value;
            var overtimeDate = document.getElementById("overtimeDate").value;
            var hoursWorked = document.getElementById("hoursWorked").value;
            var hourlyRate = document.getElementById("hourlyRate").value;
            
            if (employeeId === "" || employeeId <= 0) {
                alert("Please enter a valid employee ID");
                return false;
            }
            
            if (overtimeDate === "") {
                alert("Please select an overtime date");
                return false;
            }
            
            if (hoursWorked === "" || hoursWorked <= 0) {
                alert("Please enter hours worked (must be greater than 0)");
                return false;
            }
            
            if (hourlyRate === "" || hourlyRate <= 0) {
                alert("Please enter hourly rate (must be greater than 0)");
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>
