# Online Banking Web Application - Requirements Reference

## 🎯 Project Objective
Develop a fully functional Java Web Application for a university assignment using JSP, Servlets, MySQL Database, and GlassFish 4.0 Server.

**Academic Focus:** Student-level work with clean, readable, well-commented code and simple but correct architecture.

---

## 🧱 Functional Requirements

### 1. 🔐 Admin Module
- **Admin Login** (session-based authentication)
- **Dashboard** after successful login
- **Admin Capabilities:**
  - Add Package
  - Add Shift
  - Add Customers
  - Add Employees
  - Manage Payments
  - Manage Transactions
  - Generate Reports (Export to .XLS and .PDF)

### 2. 🏢 Administration Module
- **Add Package** (name, type, benefits)
- **Add Shift** (shift name, timing)
- **Add Customers** (full CRUD operations)
- **Add Employees** (full CRUD operations)
- **Manage Payment** (recurring salary payments)
- **Manage Transactions** (view, filter, update status)

### 3. 👨‍💼 Employment Module
- Add Employer Details
- Bulk Salary Payment
- Overtime Payment Calculation

### 4. 📊 Reports Module
- **Generate reports for:**
  - Customers
  - Employees
  - Transactions
  - Payments
- **Export formats:**
  - Excel (.XLS)
  - PDF (.PDF)

---

## 🗄️ Database Design (MySQL)

### Normalized Tables Required:
- `admin` - Admin users
- `customers` - Customer information
- `employees` - Employee information
- `packages` - Service packages
- `shifts` - Work shifts
- `transactions` - Transaction history
- `payments` - Payment records

### Database Specifications:
- Primary keys for each table
- Foreign key relationships
- Sample test data included
- Referential integrity constraints

---

## 🧩 Technical Requirements

### Backend:
- Use **Servlets** for business logic
- Use **JSP** for UI rendering
- Apply **MVC pattern** (Model-View-Controller)
- No Spring Boot or advanced frameworks

### Frontend:
- HTML + CSS (simple, clean UI)
- Basic form validation
- Client-side validation for user inputs

### Server:
- Compatible with **GlassFish 4.0**
- WAR deployment compatible

---

## 📁 Project Structure

```
/OnlineBanking
 ├── /src
 │    ├── /controller (Servlets)
 │    ├── /model (Java Beans / POJOs)
 │    ├── /dao (Database logic)
 │    ├── /util (DB connection)
 ├── /web
 │    ├── /jsp (all JSP pages)
 │    ├── /css
 │    ├── /js
 │    └── /images
 ├── /database
 │    └── schema.sql
 ├── web.xml
 ├── build.xml
 └── README.md
```

---

## ✍️ Code Quality Requirements

### Documentation:
- Every class and method must include clear comments
- Use meaningful variable and method names
- Avoid unnecessary complexity
- Follow consistent indentation and formatting

### Comments Required For:
- Database queries
- Business logic
- Complex sections
- Method purposes and parameters

### Code Standards:
- Proper naming conventions (camelCase, PascalCase)
- Single responsibility principle
- DRY (Don't Repeat Yourself)
- Clear separation of concerns

---

## 🔐 Security (Basic)

- **Session Handling** for login management
- **Prevent SQL Injection** (use PreparedStatement)
- **Basic Input Validation** on all forms
- **Session timeout** for inactive users
- **Password storage** (basic hashing)

---

## 🧪 Testing Scenarios

### Sample Test Cases:
1. Admin login with valid credentials
2. Admin login with invalid credentials
3. Add customer (valid and invalid data)
4. Add employee (valid and invalid data)
5. Process payment
6. Generate customer report
7. Generate employee report
8. Export reports to Excel and PDF
9. View transactions with filters
10. Session timeout

---

## 📊 Module Details

### Admin Module Workflow:
```
Login → Dashboard → Select Module → Perform Action → Generate Report
```

### Database Operations:
- **CRUD Operations** for: Customers, Employees, Packages, Shifts
- **Transaction Logging** for all operations
- **Payment Processing** with status tracking

### Reports Generated:
- Customer Summary Report
- Employee Salary Report
- Transaction Report
- Payment Summary Report

---

## 📄 Deliverables

### Code:
- ✅ Full source code (all .java files)
- ✅ All JSP pages
- ✅ CSS stylesheets
- ✅ Configuration files (web.xml, etc.)

### Database:
- ✅ Complete SQL script with schema
- ✅ Sample test data

### Documentation:
- ✅ Setup instructions (MySQL, GlassFish, Deployment)
- ✅ System architecture overview
- ✅ Module functionality guide
- ✅ Code comments and documentation

---

## ⚠️ Constraints & Best Practices

### Technology Stack - DO NOT DEVIATE:
- ✅ JSP (JavaServer Pages)
- ✅ Servlets (for Controllers)
- ✅ MySQL (Database)
- ✅ GlassFish 4.0 (Application Server)
- ❌ NO Spring Boot
- ❌ NO Spring Framework
- ❌ NO Hibernate ORM

### Architecture:
- **MVC Pattern** strictly followed
- **Service Layer** for business logic
- **DAO Layer** for database operations
- **Utility Classes** for common functions

### Code Style:
- Realistic for 3rd-year university student
- Clean and understandable
- Well-commented and documented
- Simple but correct
- No overengineering

---

## 🚀 Deployment Requirements

### Environment:
- JDK 7 or 8
- MySQL 5.5+
- GlassFish 4.0
- Apache Ant (for building)

### Deployment Process:
1. Create database schema
2. Build project to WAR
3. Deploy WAR to GlassFish
4. Configure database connection
5. Start GlassFish server
6. Access application via browser

---

## 📋 Success Criteria

- ✅ All functional requirements implemented
- ✅ Database properly normalized
- ✅ Clean, readable code with comments
- ✅ Proper error handling
- ✅ Basic security implemented
- ✅ Reports exportable to Excel and PDF
- ✅ Easy to deploy and run
- ✅ Student-appropriate complexity level
