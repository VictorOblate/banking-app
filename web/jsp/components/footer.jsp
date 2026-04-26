<%@ page language="java" pageEncoding="UTF-8" %>

<footer>
    <div class="footer-content">
        <div class="footer-section">
            <h4>About Basotho Ownership Bank</h4>
            <p>A trusted financial institution committed to empowering individuals and businesses with innovative banking solutions for sustainable prosperity.</p>
        </div>
        
        <div class="footer-section">
            <h4>Quick Links</h4>
            <ul>
                <li><a href="<%= request.getContextPath() %>/dashboard">Dashboard</a></li>
                <li><a href="<%= request.getContextPath() %>/customer">Customers</a></li>
                <li><a href="<%= request.getContextPath() %>/employee">Employees</a></li>
                <li><a href="<%= request.getContextPath() %>/report">Reports</a></li>
            </ul>
        </div>
        
        <div class="footer-section">
            <h4>Services</h4>
            <ul>
                <li><a href="#">Savings Accounts</a></li>
                <li><a href="#">Checking Accounts</a></li>
                <li><a href="#">Investment Services</a></li>
                <li><a href="#">Loans & Credit</a></li>
            </ul>
        </div>
        
        <div class="footer-section">
            <h4>Contact</h4>
            <ul style="list-style: none;">
                <li><strong>Address:</strong> Maseru, Lesotho</li>
                <li><strong>Phone:</strong> +266 220-BANK</li>
                <li><strong>Email:</strong> support@bashbank.ls</li>
                <li><strong>Hours:</strong> Mon-Fri 9AM-5PM</li>
            </ul>
        </div>
    </div>
    
    <div class="footer-bottom">
        <p>&copy; 2026 Basotho Ownership Bank. All rights reserved. | 
            <a href="#">Privacy Policy</a> | 
            <a href="#">Terms of Service</a> | 
            <a href="#">Security</a>
        </p>
    </div>
</footer>
