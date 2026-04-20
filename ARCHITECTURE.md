# 🏦 Online Banking Web Application - System Architecture

## 📋 Project Overview

The **Online Banking Web Application** is a comprehensive Java EE-based banking system built with JSP, Servlets, and MySQL. It provides administrators with the ability to manage customers, employees, banking packages, work shifts, transactions, and payments. The application is designed for academic purposes and demonstrates best practices in web application development using the MVC (Model-View-Controller) architecture.

**Technology Stack:**
- **Frontend:** JSP (JavaServer Pages)
- **Backend:** Java Servlets
- **Database:** MySQL 5.5+
- **Application Server:** GlassFish 4.0
- **Build Tool:** Apache Ant
- **JDK Version:** Java 1.8+

---

## 🏗️ Architecture Overview

The application follows a strict **layered MVC architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────┐
│           View Layer (JSP Pages)            │
│  (login.jsp, dashboard.jsp, form pages)     │
└──────────────────┬──────────────────────────┘
                   │ (HTTP Request/Response)
┌──────────────────▼──────────────────────────┐
│      Controller Layer (Servlets)            │
│  (LoginServlet, CustomerServlet, etc.)      │
└──────────────────┬──────────────────────────┘
                   │ (Method Calls)
┌──────────────────▼──────────────────────────┐
│      Service Layer (Business Logic)         │
│  (CustomerService, EmployeeService, etc.)   │
└──────────────────┬──────────────────────────┘
                   │ (Data Access)
┌──────────────────▼──────────────────────────┐
│      DAO Layer (Database Access)            │
│  (CustomerDAO, EmployeeDAO, etc.)           │
└──────────────────┬──────────────────────────┘
                   │ (SQL Queries)
┌──────────────────▼──────────────────────────┐
│         MySQL Database                      │
└─────────────────────────────────────────────┘
```

### **Architectural Benefits:**

1. **Separation of Concerns:** Each layer has a specific responsibility
2. **Maintainability:** Easy to locate and modify code
3. **Testability:** Business logic can be tested independently
4. **Reusability:** Services can be used by multiple controllers
5. **Security:** SQL injection prevention through PreparedStatements
6. **Scalability:** New features can be added without affecting existing code

---

## 🔐 Authentication & Session Management

### **Login Flow:**
1. User accesses the application (redirected to `login.jsp`)
2. Enters username and password
3. `LoginServlet` receives credentials
4. `AdminService` validates credentials via `AdminDAO.authenticate()`
5. If valid, `HttpSession` is created with admin user data
6. Session timeout: **30 minutes** (configurable in Constants.java)
7. User redirected to admin dashboard

### **Session Security:**
- HttpOnly cookies enabled (XSS protection)
- Session invalidation on logout
- Session check on all protected pages:
```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("adminUser") == null) {
    response.sendRedirect("login.jsp");
    return;
}
```

---

## 📁 Project Structure

```
/workspaces/banking-app/
│
├── web/
│   ├── WEB-INF/
│   │   └── web.xml              # Web application deployment descriptor
│   ├── jsp/
│   │   ├── login.jsp            # Login page
│   │   └── admin/
│   │       ├── dashboard.jsp    # Admin dashboard
│   │       ├── customer/
│   │       │   ├── list.jsp     # Customer list
│   │       │   └── form.jsp     # Add/Edit customer
│   │       ├── employee/
│   │       │   ├── list.jsp     # Employee list
│   │       │   └── form.jsp     # Add/Edit employee
│   │       ├── package/
│   │       ├── shift/
│   │       ├── payment/
│   │       ├── transaction/
│   │       └── report/
│   ├── error/
│   │   ├── error404.jsp         # 404 error page
│   │   └── error500.jsp         # 500 error page
│   ├── css/
│   │   └── style.css            # Main stylesheet
│   └── index.jsp                # Entry point (redirects to login)
│
├── src/
│   └── com/bankingapp/
│       ├── model/               # POJOs (Plain Old Java Objects)
│       │   ├── Admin.java
│       │   ├── Customer.java
│       │   ├── Employee.java
│       │   ├── Package.java
│       │   ├── Shift.java
│       │   ├── Transaction.java
│       │   └── Payment.java
│       ├── dao/                 # Data Access Objects
│       │   ├── AdminDAO.java
│       │   ├── CustomerDAO.java
│       │   ├── EmployeeDAO.java
│       │   ├── PackageDAO.java
│       │   ├── ShiftDAO.java
│       │   ├── TransactionDAO.java
│       │   └── PaymentDAO.java
│       ├── service/             # Business Logic Layer
│       │   ├── AdminService.java
│       │   ├── CustomerService.java
│       │   ├── EmployeeService.java
│       │   ├── PackageShiftService.java
│       │   └── TransactionPaymentService.java
│       ├── controller/          # Servlets (Controllers)
│       │   ├── LoginServlet.java
│       │   ├── LogoutServlet.java
│       │   ├── DashboardServlet.java
│       │   ├── CustomerServlet.java
│       │   ├── EmployeeServlet.java
│       │   ├── PackageServlet.java
│       │   ├── ShiftServlet.java
│       │   ├── PaymentServlet.java
│       │   ├── TransactionServlet.java
│       │   └── ReportServlet.java
│       └── util/                # Utility Classes
│           ├── DBConnection.java
│           ├── Constants.java
│           └── ValidationUtil.java
│
├── database/
│   └── schema.sql               # MySQL database schema with sample data
│
├── build.xml                    # Apache Ant build configuration
└── README.md                    # Project documentation
```

---

## 📊 Database Schema

The application uses **8 normalized tables** with proper relationships:

### **1. admin** - Administrator Users
```sql
- admin_id (PK)
- username (UNIQUE)
- password
- full_name
- email
- phone
- created_date
- last_login
- is_active
```

### **2. packages** - Banking Service Packages
```sql
- package_id (PK)
- package_name
- package_type
- monthly_fee
- annual_fee
- benefits
```

### **3. shifts** - Employee Work Shifts
```sql
- shift_id (PK)
- shift_name
- start_time
- end_time
- shift_type
```

### **4. customers** - Bank Customers
```sql
- customer_id (PK)
- first_name
- last_name
- email (UNIQUE)
- phone
- address
- account_number (UNIQUE, Auto-generated: ACC + 6 digits)
- account_status
- package_id (FK → packages)
```

### **5. employees** - Bank Employees
```sql
- employee_id (PK)
- first_name
- last_name
- email
- employee_code (UNIQUE, Auto-generated: EMP + 3 digits)
- designation
- department
- shift_id (FK → shifts)
- basic_salary
- hire_date
```

### **6. transactions** - Customer Transactions
```sql
- transaction_id (PK)
- customer_id (FK → customers)
- transaction_type
- amount
- status
- reference_number (TRX + UUID)
- balance_before
- balance_after
```

### **7. payments** - Salary and Bill Payments
```sql
- payment_id (PK)
- employee_id (FK → employees)
- customer_id (FK → customers)
- payment_type
- amount
- payment_date
- payment_status
```

### **8. activity_log** - Audit Trail
```sql
- log_id (PK)
- admin_id (FK → admin)
- action
- entity_type
- entity_id
- log_date
```

---

## 🎯 Module Functionality

### **1. Authentication Module**
- Admin login with credentials
- Session management (30-minute timeout)
- Logout functionality
- Password security with validation rules (6-20 characters, alphanumeric)

### **2. Customer Management Module**
- **List Customers:** View all customers with pagination
- **Add Customer:** Create new customer with auto-generated account number (ACC + 6 digits)
- **Edit Customer:** Update customer information
- **Delete Customer:** Remove customer records
- **Validation:** Email uniqueness, phone format, name validation

### **3. Employee Management Module**
- **List Employees:** View all employees with details
- **Add Employee:** Create new employee with auto-generated employee code (EMP + 3 digits)
- **Edit Employee:** Update employee details including salary
- **Delete Employee:** Remove employee records
- **Salary Calculation:** Basic salary + overtime allowance (1.5x hourly rate)

### **4. Package Management Module**
- Display available banking packages
- Package details including features and fees
- Associate packages with customer accounts

### **5. Shift Management Module**
- View all work shifts (Morning, Evening, Night, Part-time)
- Assign shifts to employees
- Manage shift timings

### **6. Transaction Management Module**
- Record customer transactions
- Update transaction status
- View transaction history filtered by customer or status
- Auto-generated transaction reference (TRX + UUID)

### **7. Payment Management Module**
- Process employee salary payments
- Manage customer bill payments
- Query pending payments
- Track payment history

### **8. Report Generation Module** *(In Development)*
- Generate customer reports
- Employee salary reports
- Transaction reports
- Excel (.xls) export capability
- PDF export capability

---

## 🔒 Security Features

### **1. SQL Injection Prevention**
All database queries use **PreparedStatements** with parameterized queries:
```java
String query = "SELECT * FROM customers WHERE email = ?";
PreparedStatement pstmt = connection.prepareStatement(query);
pstmt.setString(1, email);
ResultSet rs = pstmt.executeQuery();
```

### **2. Input Validation**
Comprehensive validation in `ValidationUtil.java`:
- Email validation (regex pattern)
- Phone number validation (10 digits)
- Name validation (2-100 characters, letters and spaces only)
- Password strength validation (min 6 chars, alphanumeric)
- Numeric validation for amounts and IDs

### **3. Session Security**
- HttpOnly cookies enabled (prevents XSS attacks)
- Session invalidation on logout
- Session timeout after 30 minutes of inactivity
- Session validation on all protected pages

### **4. Authentication**
- Admin credentials stored in database
- Password validation before session creation
- Last login tracking
- Active/Inactive account status

---

## 🔄 Data Flow Example: Adding a Customer

```
1. User clicks "Add Customer" button from dashboard
   ↓
2. CustomerServlet (doGet) receives request with action=add
   ↓
3. Servlet fetches list of packages via PackageShiftService
   ↓
4. Request forwarded to customer/form.jsp with packages list
   ↓
5. User fills form and submits
   ↓
6. CustomerServlet (doPost) receives form data with action=save
   ↓
7. Servlet validates session and creates Customer object
   ↓
8. CustomerService.addCustomer() performs business logic:
   - Validates email (not duplicate)
   - Validates phone format
   - Generates account number (ACC + 6 random digits)
   ↓
9. CustomerDAO.addCustomer() executes INSERT with PreparedStatement
   ↓
10. Customer inserted into database
   ↓
11. Redirect to customer?action=list
   ↓
12. CustomerServlet fetches all customers and displays list
```

---

## 🛠️ Constant Definitions (Constants.java)

**Session Constants:**
- `SESSION_TIMEOUT = 30` (minutes)
- `ADMIN_SESSION = "adminUser"`

**Action Constants:**
- `ACTION_LIST = "list"`
- `ACTION_ADD = "add"`
- `ACTION_EDIT = "edit"`
- `ACTION_SAVE = "save"`
- `ACTION_DELETE = "delete"`

**Page Constants:**
- `PAGE_LOGIN = "/jsp/login.jsp"`
- `PAGE_DASHBOARD = "/jsp/admin/dashboard.jsp"`
- `PAGE_CUSTOMER_LIST = "/jsp/admin/customer/list.jsp"`
- `PAGE_CUSTOMER_FORM = "/jsp/admin/customer/form.jsp"`
- `PAGE_EMPLOYEE_LIST = "/jsp/admin/employee/list.jsp"`
- `PAGE_EMPLOYEE_FORM = "/jsp/admin/employee/form.jsp"`

**Validation Constants:**
- `MIN_PASSWORD_LENGTH = 6`
- `MAX_PASSWORD_LENGTH = 20`
- `MIN_NAME_LENGTH = 2`
- `MAX_NAME_LENGTH = 100`

---

## 📝 Deployment Instructions

### **Prerequisites:**
- Java Development Kit (JDK) 1.8 or higher
- MySQL Server 5.5+
- GlassFish 4.0 Application Server
- Apache Ant (for building WAR file)

### **Step 1: Database Setup**
```bash
# Create MySQL database
mysql -u root -p
mysql> CREATE DATABASE banking_db;
mysql> USE banking_db;
mysql> SOURCE /path/to/database/schema.sql;
```

### **Step 2: Build the Application**
```bash
cd /workspaces/banking-app
ant build
```
This creates `OnlineBanking.war` in the build directory.

### **Step 3: Deploy to GlassFish**
```bash
# Copy WAR to GlassFish domain
cp build/OnlineBanking.war $GLASSFISH_HOME/domains/domain1/autodeploy/

# Or deploy via admin console
# http://localhost:4848
```

### **Step 4: Configure JDBC DataSource**
1. Access GlassFish Admin Console: `http://localhost:4848`
2. Navigate to: Resources → JDBC → Connection Pools
3. Create connection pool for MySQL with settings:
   - Datasource: `com.mysql.jdbc.jdbc2.optional.MysqlDataSource`
   - Database: `banking_db`
   - User: `root`
   - Password: `root`

### **Step 5: Access Application**
- URL: `http://localhost:8080/OnlineBanking`
- Demo Login:
  - Username: `admin`
  - Password: `admin123`

---

## 📖 API Reference

### **LoginServlet**
- **URL:** `/login`
- **GET:** Display login page
- **POST:** Authenticate user (username, password)
- **Response:** Redirect to dashboard on success, error page on failure

### **CustomerServlet**
- **URL:** `/customer`
- **Parameters:**
  - `action` (list|add|edit|save|delete)
  - `customerId` (for edit/delete operations)
  - Form fields: firstName, lastName, email, phone, address, packageId, accountStatus

### **EmployeeServlet**
- **URL:** `/employee`
- **Parameters:**
  - `action` (list|add|edit|save|delete)
  - `employeeId` (for edit/delete operations)
  - Form fields: firstName, lastName, email, designation, department, shiftId, basicSalary

---

## 🐛 Error Handling

| Error Code | Page | Cause |
|-----------|------|-------|
| 404 | `/error/error404.jsp` | Resource not found |
| 500 | `/error/error500.jsp` | Server error |
| Session Expired | Login redirect | Timeout after 30 minutes |
| Invalid Credentials | Login page | Wrong username/password |
| Validation Error | Form page | Invalid input data |

---

## 📚 Code Quality Standards

- **JavaDoc Comments:** All classes and public methods documented
- **Variable Naming:** camelCase for variables/methods, PascalCase for classes
- **Error Handling:** Try-catch-finally blocks with resource cleanup
- **Resource Management:** Database connections closed in finally blocks
- **Null Checks:** All objects validated before use
- **Constants:** Magic strings centralized in Constants.java

---

## 🎓 Learning Outcomes

This project demonstrates:
1. ✅ JSP & Servlet programming best practices
2. ✅ MVC architecture implementation
3. ✅ DAO pattern for database abstraction
4. ✅ Service layer for business logic
5. ✅ Session management and authentication
6. ✅ SQL injection prevention techniques
7. ✅ Input validation and sanitization
8. ✅ RESTful design principles
9. ✅ Exception handling and error management
10. ✅ Professional code organization and documentation

---

## 📜 Version History

- **v1.0 (Current)** - Initial release with core functionality
  - Authentication system
  - Customer management
  - Employee management
  - Packages and Shifts
  - Transaction and Payment tracking
  - Dashboard and reporting foundation

---

## 📧 Support & Documentation

For detailed implementation information, refer to:
- REQUIREMENTS.md - Complete functional specifications
- Individual JavaDoc comments in source code
- Database schema comments in schema.sql

---

**Created for Academic Purposes | Online Banking Management System**
