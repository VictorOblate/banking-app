<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        .error-container {
            text-align: center;
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            max-width: 600px;
        }
        h1 {
            color: #333;
            font-size: 72px;
            margin: 0;
        }
        p {
            color: #666;
            font-size: 18px;
            margin: 20px 0;
        }
        .error-message {
            background: #f8f9fa;
            padding: 15px;
            border-left: 4px solid #f5576c;
            margin: 20px 0;
            text-align: left;
            font-size: 14px;
            color: #555;
            border-radius: 3px;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #f5576c;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        a:hover {
            background: #f093fb;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>500</h1>
        <p>Internal Server Error</p>
        <p>An unexpected error has occurred. Our team has been notified.</p>
        
        <% if (exception != null) { %>
        <div class="error-message">
            <strong>Error Details:</strong><br>
            <%= exception.getClass().getName() %>: <%= exception.getMessage() %>
        </div>
        <% } %>
        
        <a href="<%= request.getContextPath() %>/">Return to Home</a>
    </div>
</body>
</html>
