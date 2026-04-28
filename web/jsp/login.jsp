<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Basotho Ownership Bank - Admin Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            background-color: var(--light-green);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 2rem;
        }
        .login-wrapper {
            width: 100%;
            max-width: 450px;
        }
        .login-box {
            background: var(--white);
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            padding: 2.5rem;
        }
        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .login-logo {
            display: flex;
            justify-content: center;
            margin-bottom: 1rem;
        }
        .login-logo svg {
            width: 50px;
            height: 50px;
        }
        .login-header h1 {
            color: var(--primary-green);
            font-size: 1.8rem;
            margin: 0 0 0.5rem 0;
        }
        .login-header p {
            color: var(--medium-gray);
            margin: 0;
            font-size: 0.95rem;
        }
        .login-form input {
            margin-bottom: 1rem;
        }
        .btn-login {
            background-color: var(--primary-green);
            color: var(--white);
            width: 100%;
            padding: 0.85rem;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .btn-login:hover {
            background-color: var(--soft-green);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(45, 122, 79, 0.2);
        }
        .demo-credentials {
            background-color: var(--light-gray);
            border-left: 4px solid var(--primary-green);
            padding: 1rem;
            border-radius: 4px;
            margin-top: 1.5rem;
            font-size: 0.9rem;
        }
        .demo-credentials strong {
            color: var(--primary-green);
        }
    </style>
</head>
<body>
    <div class="login-wrapper">
        <div class="login-box">
            <!-- Login Header -->
            <div class="login-header">
                <div class="login-logo">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200">
                        <circle cx="100" cy="100" r="95" fill="#f5f5f5" stroke="#2d7a4f" stroke-width="2"/>
                        <circle cx="100" cy="100" r="88" fill="none" stroke="#4caf50" stroke-width="1.5" opacity="0.3"/>
                        <path d="M 100 40 L 140 60 L 140 90 Q 100 130 100 130 Q 100 130 60 90 L 60 60 Z" fill="none" stroke="#2d7a4f" stroke-width="2.5"/>
                        <circle cx="100" cy="85" r="12" fill="#4caf50"/>
                        <rect x="93" y="95" width="14" height="20" fill="none" stroke="#2d7a4f" stroke-width="1.5" rx="2"/>
                        <text x="100" y="155" font-size="9" font-weight="bold" text-anchor="middle" fill="#2d7a4f">BASH</text>
                    </svg>
                </div>
                <h1>Basotho Bank</h1>
                <p>Admin Portal - Secure Login</p>
            </div>
            
            <!-- Error Message -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-error">
                    <strong>Access Denied:</strong> <%= error %>
                </div>
            <%
                }
            %>
            
            <!-- Login Form -->
            <form method="POST" action="${pageContext.request.contextPath}/login" class="login-form">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" 
                           placeholder="Enter your username" 
                           required autofocus>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" 
                           placeholder="Enter your password" 
                           required>
                </div>
                
                <button type="submit" class="btn-login">Sign In</button>
            </form>

            <!-- Footer -->
            <div style="text-align: center; margin-top: 1.5rem; color: var(--medium-gray); font-size: 0.85rem;">
                <p style="margin: 0;">
                    Basotho Ownership Bank © 2026
                </p>
                <p style="margin: 0.5rem 0 0 0;">
                    <a href="/" style="color: var(--primary-green);">Return to Home</a>
                </p>
            </div>
        </div>
    </div>
</body>
</html>
