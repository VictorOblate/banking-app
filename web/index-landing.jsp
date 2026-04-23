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
                <img src="<%= request.getContextPath() %>/images/logo.svg" alt="Basotho Ownership Bank logo">
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
                        <div class="feature-icon">
                            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="3" y="7" width="18" height="14" rx="2" ry="2"/>
                                <path d="M7 7V5a5 5 0 0 1 10 0v2"/>
                                <path d="M12 12v4"/>
                                <circle cx="12" cy="16" r="1"/>
                            </svg>
                        </div>
                        <h3>Savings Accounts</h3>
                        <p>Secure savings accounts with competitive interest rates to help you grow your wealth.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">
                            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="2" y="7" width="20" height="14" rx="2" ry="2"/>
                                <path d="M2 11h20"/>
                                <path d="M6 7v-2"/>
                            </svg>
                        </div>
                        <h3>Checking Accounts</h3>
                        <p>Modern checking accounts with instant access to your funds and premium benefits.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">
                            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M4 19h16"/>
                                <path d="M4 15h10"/>
                                <path d="M4 11h16"/>
                                <path d="M4 7h6"/>
                                <path d="M14 7v12"/>
                            </svg>
                        </div>
                        <h3>Investment Services</h3>
                        <p>Expert guidance and investment solutions to help you achieve your financial goals.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">
                            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="3" y="11" width="18" height="10" rx="2" ry="2"/>
                                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                                <path d="M12 16v2"/>
                            </svg>
                        </div>
                        <h3>Security First</h3>
                        <p>Advanced security measures to protect your accounts and personal information.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">
                            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="12" cy="12" r="9"/>
                                <path d="M12 8v4l3 2"/>
                            </svg>
                        </div>
                        <h3>24/7 Support</h3>
                        <p>Round-the-clock customer support to assist you whenever you need it.</p>
                    </div>
                    <div class="feature-card card">
                        <div class="feature-icon">
                            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="6" y="4" width="12" height="16" rx="2" ry="2"/>
                                <path d="M12 18h.01"/>
                                <path d="M9 7h6"/>
                            </svg>
                        </div>
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
                        <ul class="benefit-list" style="list-style: none; margin-top: 2rem; padding: 0;">
                            <li>
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="position: absolute; left: 0; top: 0.2rem;">
                                    <polyline points="20 6 9 17 4 12"/>
                                </svg>
                                Trusted financial institution serving Lesotho
                            </li>
                            <li>
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="position: absolute; left: 0; top: 0.2rem;">
                                    <polyline points="20 6 9 17 4 12"/>
                                </svg>
                                Competitive interest rates on deposits
                            </li>
                            <li>
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="position: absolute; left: 0; top: 0.2rem;">
                                    <polyline points="20 6 9 17 4 12"/>
                                </svg>
                                State-of-the-art security infrastructure
                            </li>
                            <li>
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="position: absolute; left: 0; top: 0.2rem;">
                                    <polyline points="20 6 9 17 4 12"/>
                                </svg>
                                Personalized banking solutions
                            </li>
                            <li>
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="position: absolute; left: 0; top: 0.2rem;">
                                    <polyline points="20 6 9 17 4 12"/>
                                </svg>
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
                <ul class="contact-list" style="list-style: none; padding: 0;">
                    <li>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="margin-right: 0.5rem; vertical-align: middle;">
                            <path d="M21 10L12 3 3 10v10a1 1 0 0 0 1 1h3a1 1 0 0 0 1-1v-5h8v5a1 1 0 0 0 1 1h3a1 1 0 0 0 1-1V10z"/>
                        </svg>
                        Maseru, Lesotho
                    </li>
                    <li>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="margin-right: 0.5rem; vertical-align: middle;">
                            <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6A19.79 19.79 0 0 1 2.11 4.18 2 2 0 0 1 4 2h3a2 2 0 0 1 2 1.72 12.29 12.29 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.29 12.29 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                        </svg>
                        +266 220-BANK
                    </li>
                    <li>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="margin-right: 0.5rem; vertical-align: middle;">
                            <path d="M4 4h16v16H4z"/>
                            <polyline points="22,6 12,13 2,6"/>
                        </svg>
                        info@bashbank.ls
                    </li>
                    <li>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#2d7a4f" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="margin-right: 0.5rem; vertical-align: middle;">
                            <circle cx="12" cy="12" r="9"/>
                            <polyline points="12 7 12 12 15 14"/>
                        </svg>
                        Mon-Fri: 9AM-5PM
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2026 Basotho Ownership Bank. All rights reserved. | <a href="#privacy" style="color: #90caf9;">Privacy Policy</a> | <a href="#terms" style="color: #90caf9;">Terms of Service</a></p>
        </div>
    </footer>
</body>
</html>
