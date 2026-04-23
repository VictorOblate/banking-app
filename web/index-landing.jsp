<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Basotho Ownership Bank - Welcome</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body { background-color: #ffffff; }
        .hero { background-color: #e8f5e9; }
        nav.top-nav { display: flex; gap: 2rem; margin-left: auto; }
        .cta-buttons { display: flex; gap: 1rem; margin-left: auto; }
        .cta-buttons a { padding: 0.75rem 1.5rem; border-radius: 4px; }
    </style>
</head>
<body>
    <!-- Header -->
    <header>
        <div class="header-container">
            <div class="logo">
                <svg width="40" height="40" viewBox="0 0 200 200">
                    <circle cx="100" cy="100" r="95" fill="#f5f5f5" stroke="#2d7a4f" stroke-width="2"/>
                    <circle cx="100" cy="100" r="88" fill="none" stroke="#4caf50" stroke-width="1.5" opacity="0.3"/>
                    <path d="M 100 40 L 140 60 L 140 90 Q 100 130 100 130 Q 100 130 60 90 L 60 60 Z" fill="none" stroke="#2d7a4f" stroke-width="2.5"/>
                    <circle cx="100" cy="85" r="12" fill="#4caf50"/>
                    <rect x="93" y="95" width="14" height="20" fill="none" stroke="#2d7a4f" stroke-width="1.5" rx="2"/>
                    <text x="100" y="155" font-size="10" font-weight="bold" text-anchor="middle" fill="#2d7a4f">BASOTHO</text>
                </svg>
                <span>Basotho Ownership Bank</span>
            </div>
            <nav class="top-nav">
                <a href="#about">About</a>
                <a href="#services">Services</a>
                <a href="#contact">Contact</a>
            </nav>
            <div class="cta-buttons">
                <a href="jsp/login.jsp" class="btn btn-secondary">Login</a>
                <a href="#signup" class="btn btn-primary">Get Started</a>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Hero Section -->
        <section class="hero">
            <div class="container">
                <div class="hero-content">
                    <h1>Welcome to Basotho Ownership Bank</h1>
                    <p>Building prosperity through trusted banking services for the people of Lesotho</p>
                    <div style="margin-top: 2rem;">
                        <a href="jsp/login.jsp" class="btn btn-primary btn-large" style="padding: 1rem 2.5rem; font-size: 1.1rem;">Access Your Account</a>
                    </div>
                </div>
            </div>
        </section>

        <!-- Features Section -->
        <section style="padding: 4rem 2rem;">
            <div class="container">
                <h2 style="text-align: center; margin-bottom: 3rem;">Our Services</h2>
                <div class="features-grid">
                    <div class="feature-card card">
                        <div class="feature-icon">🏦</div>
                        <h3>Savings Accounts</h3>
                        <p>Secure savings accounts with competitive interest rates to help you grow your wealth.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">💳</div>
                        <h3>Checking Accounts</h3>
                        <p>Modern checking accounts with instant access to your funds and premium benefits.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">📊</div>
                        <h3>Investment Services</h3>
                        <p>Expert guidance and investment solutions to help you achieve your financial goals.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">🔒</div>
                        <h3>Security First</h3>
                        <p>Advanced security measures to protect your accounts and personal information.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">⏰</div>
                        <h3>24/7 Support</h3>
                        <p>Round-the-clock customer support to assist you whenever you need it.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">📱</div>
                        <h3>Mobile Banking</h3>
                        <p>Bank on the go with our user-friendly mobile application.</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- Benefits Section -->
        <section style="padding: 4rem 2rem; background-color: #f5f5f5;">
            <div class="container">
                <div class="grid grid-2">
                    <div>
                        <h2>Why Choose Us?</h2>
                        <ul style="list-style: none; margin-top: 2rem;">
                            <li style="margin-bottom: 1rem; padding-left: 2rem; position: relative;">
                                <span style="position: absolute; left: 0; color: #2d7a4f; font-weight: bold;">✓</span>
                                Trusted financial institution serving Lesotho
                            </li>
                            <li style="margin-bottom: 1rem; padding-left: 2rem; position: relative;">
                                <span style="position: absolute; left: 0; color: #2d7a4f; font-weight: bold;">✓</span>
                                Competitive interest rates on deposits
                            </li>
                            <li style="margin-bottom: 1rem; padding-left: 2rem; position: relative;">
                                <span style="position: absolute; left: 0; color: #2d7a4f; font-weight: bold;">✓</span>
                                State-of-the-art security infrastructure
                            </li>
                            <li style="margin-bottom: 1rem; padding-left: 2rem; position: relative;">
                                <span style="position: absolute; left: 0; color: #2d7a4f; font-weight: bold;">✓</span>
                                Personalized banking solutions
                            </li>
                            <li style="margin-bottom: 1rem; padding-left: 2rem; position: relative;">
                                <span style="position: absolute; left: 0; color: #2d7a4f; font-weight: bold;">✓</span>
                                Digital banking innovation
                            </li>
                        </ul>
                    </div>
                    <div>
                        <h2>Get Started Today</h2>
                        <form style="margin-top: 2rem;">
                            <div class="form-group">
                                <label for="name">Full Name</label>
                                <input type="text" id="name" name="name" placeholder="Enter your name">
                            </div>
                            <div class="form-group">
                                <label for="email">Email Address</label>
                                <input type="email" id="email" name="email" placeholder="your@email.com">
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone Number</label>
                                <input type="tel" id="phone" name="phone" placeholder="+266 ...">
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Request Account</button>
                            <p style="text-align: center; margin-top: 1rem; color: #999; font-size: 0.9rem;">
                                Already have an account? <a href="jsp/login.jsp">Login here</a>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <!-- CTA Section -->
        <section style="padding: 3rem 2rem; background-color: #e8f5e9;">
            <div class="container" style="text-align: center;">
                <h2 style="color: #2d7a4f; margin-bottom: 1rem;">Ready to Join Us?</h2>
                <p style="margin-bottom: 2rem; font-size: 1.1rem;">Experience secure, modern banking with Basotho Ownership Bank</p>
                <a href="jsp/login.jsp" class="btn btn-primary" style="padding: 1rem 2rem;">Open an Account</a>
            </div>
        </section>
    </main>

    <!-- Footer -->
    <footer>
        <div class="footer-content">
            <div class="footer-section">
                <h4>About Basotho Ownership Bank</h4>
                <p>We are a community-focused financial institution committed to empowering individuals and businesses with innovative banking solutions.</p>
            </div>
            <div class="footer-section">
                <h4>Quick Links</h4>
                <ul>
                    <li><a href="#about">About Us</a></li>
                    <li><a href="#services">Services</a></li>
                    <li><a href="#contact">Contact Us</a></li>
                    <li><a href="#careers">Careers</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h4>Services</h4>
                <ul>
                    <li><a href="#savings">Savings Accounts</a></li>
                    <li><a href="#checking">Checking Accounts</a></li>
                    <li><a href="#investment">Investment</a></li>
                    <li><a href="#loans">Loans & Credit</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h4>Contact Information</h4>
                <ul style="list-style: none;">
                    <li>📍 Maseru, Lesotho</li>
                    <li>📞 +266 220-BANK</li>
                    <li>✉️ info@bashbank.ls</li>
                    <li>🕐 Mon-Fri: 9AM-5PM</li>
                </ul>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2026 Basotho Ownership Bank. All rights reserved. | <a href="#privacy" style="color: #90caf9;">Privacy Policy</a> | <a href="#terms" style="color: #90caf9;">Terms of Service</a></p>
        </div>
    </footer>
</body>
</html>
