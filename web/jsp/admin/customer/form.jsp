<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingapp.model.Customer" %>
<%@ page import="com.bankingapp.model.Package" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Form - Online Banking</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
        }
        .header h1 {
            margin: 0;
        }
        .container {
            padding: 30px;
            max-width: 800px;
            margin: 0 auto;
        }
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        .form-group input[type="text"],
        .form-group input[type="email"],
        .form-group input[type="date"],
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }
        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.3);
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .btn-container {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary {
            background-color: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background-color: #764ba2;
        }
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
    </style>
</head>
<body>

<!-- Header -->
<div class="header">
    <h1><%
        Boolean isEdit = (Boolean) request.getAttribute("isEdit");
        if (isEdit != null && isEdit) {
    %>Edit Customer<% } else { %>Add New Customer<% } %></h1>
</div>

<!-- Container -->
<div class="container">
    <div class="form-container">
        <%
            Customer customer = (Customer) request.getAttribute("customer");
            Boolean isEditMode = (Boolean) request.getAttribute("isEdit");
            isEditMode = isEditMode != null ? isEditMode : false;
        %>
        
        <form method="POST" action="${pageContext.request.contextPath}/customer?action=save">
            <% if (isEditMode) { %>
            <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
            <% } %>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name *</label>
                    <input type="text" id="firstName" name="firstName" required 
                           value="<%= isEditMode && customer != null ? customer.getFirstName() : "" %>">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name *</label>
                    <input type="text" id="lastName" name="lastName" required
                           value="<%= isEditMode && customer != null ? customer.getLastName() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" required
                           value="<%= isEditMode && customer != null ? customer.getEmail() : "" %>">
                </div>
                <div class="form-group">
                    <label for="phone">Phone *</label>
                    <input type="text" id="phone" name="phone" required
                           value="<%= isEditMode && customer != null ? customer.getPhone() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth"
                           value="<%= isEditMode && customer != null ? customer.getDateOfBirth() : "" %>">
                </div>
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select id="gender" name="gender">
                        <option value="">Select...</option>
                        <option value="MALE" <%= isEditMode && customer != null && "MALE".equals(customer.getGender()) ? "selected" : "" %>>Male</option>
                        <option value="FEMALE" <%= isEditMode && customer != null && "FEMALE".equals(customer.getGender()) ? "selected" : "" %>>Female</option>
                        <option value="OTHER" <%= isEditMode && customer != null && "OTHER".equals(customer.getGender()) ? "selected" : "" %>>Other</option>
                    </select>
                </div>
            </div>
            
            <div class="form-group">
                <label for="address">Address</label>
                <textarea id="address" name="address"><%= isEditMode && customer != null && customer.getAddress() != null ? customer.getAddress() : "" %></textarea>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="city">City</label>
                    <input type="text" id="city" name="city"
                           value="<%= isEditMode && customer != null ? customer.getCity() : "" %>">
                </div>
                <div class="form-group">
                    <label for="state">State</label>
                    <input type="text" id="state" name="state"
                           value="<%= isEditMode && customer != null ? customer.getState() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="postalCode">Postal Code</label>
                    <input type="text" id="postalCode" name="postalCode"
                           value="<%= isEditMode && customer != null ? customer.getPostalCode() : "" %>">
                </div>
                <div class="form-group">
                    <label for="country">Country</label>
                    <input type="text" id="country" name="country"
                           value="<%= isEditMode && customer != null ? customer.getCountry() : "" %>">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="packageId">Package</label>
                    <select id="packageId" name="packageId">
                        <option value="">Select Package...</option>
                        <%
                            List<Package> packages = (List<Package>) request.getAttribute("packages");
                            if (packages != null) {
                                for (Package pkg : packages) {
                        %>
                        <option value="<%= pkg.getPackageId() %>" 
                                <%= isEditMode && customer != null && customer.getPackageId() == pkg.getPackageId() ? "selected" : "" %>>
                            <%= pkg.getPackageName() %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="accountStatus">Account Status</label>
                    <select id="accountStatus" name="accountStatus">
                        <option value="ACTIVE" <%= isEditMode && customer != null && "ACTIVE".equals(customer.getAccountStatus()) ? "selected" : "" %>>Active</option>
                        <option value="INACTIVE" <%= isEditMode && customer != null && "INACTIVE".equals(customer.getAccountStatus()) ? "selected" : "" %>>Inactive</option>
                        <option value="SUSPENDED" <%= isEditMode && customer != null && "SUSPENDED".equals(customer.getAccountStatus()) ? "selected" : "" %>>Suspended</option>
                    </select>
                </div>
            </div>
            
            <div class="btn-container">
                <button type="submit" class="btn btn-primary"><%= isEditMode ? "Update Customer" : "Add Customer" %></button>
                <a href="${pageContext.request.contextPath}/customer?action=list" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
