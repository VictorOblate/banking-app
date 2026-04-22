# Pre-Deployment Checklist - Online Banking App

## ✅ BEFORE YOU DEPLOY (Complete All Steps)

### Step 1: Download MySQL JDBC Driver

- [ ] Download mysql-connector-java-5.1.49-bin.jar from: https://dev.mysql.com/downloads/connector/j/
- [ ] Copy JAR to: `banking-app/lib/mysql-connector-java-5.1.49-bin.jar`
- [ ] Verify file exists: `ls lib/mysql-connector-java-5.1.49-bin.jar` (Windows: `dir lib`)

### Step 2: Create MySQL Database

#### On Windows:
```powershell
# Open Command Prompt as Administrator or PowerShell
mysql -u root -p
# Enter password: root

# In MySQL prompt:
CREATE DATABASE banking_db;
USE banking_db;
SOURCE C:\Users\Hp-Pavilion Core-i5\Documents\Banking App Assignment\banking-app\database\schema.sql;
SHOW TABLES;
# Should show: 8 tables
EXIT;
```

- [ ] Database created
- [ ] Schema imported successfully
- [ ] All 8 tables visible:
  - [ ] admin
  - [ ] customer
  - [ ] employee
  - [ ] bank_package
  - [ ] shift
  - [ ] transaction
  - [ ] payment
  - [ ] user

### Step 3: Verify GlassFish Installation

- [ ] GlassFish 4.0 installed at: `C:\Program Files\glassfish-4.0` (or your location)
- [ ] NetBeans has GlassFish registered: Tools → Servers → Should see GlassFish Server

### Step 4: Clean Project in NetBeans

- [ ] Right-click **OnlineBanking** project
- [ ] Select **Clean and Build**
- [ ] Wait for "BUILD SUCCESSFUL" message
- [ ] Verify: `dist/OnlineBanking.war` file created

### Step 5: Start GlassFish

#### Option A: From NetBeans
- [ ] In Services tab → Servers → GlassFish Server 4.0 → Right-click → Start

#### Option B: Command Line
```powershell
# Windows
C:\Program Files\glassfish-4.0\bin\asadmin.bat start-domain

# Or if in PATH:
asadmin start-domain
```

- [ ] Wait for message: "Domain domain1 is running"
- [ ] Verify: Open http://localhost:8080 in browser (should show GlassFish page)

### Step 6: Check Internet Connectivity

- [ ] Internet working (for WebSocket/resource files if needed)
- [ ] MySQL server accessible: Test with `mysql -u root -p` from PowerShell

### Step 7: Verify All Files Present

In `banking-app` folder, verify these exist:
- [ ] `src/` folder (29 .java files)
- [ ] `web/` folder (JSP pages, CSS, JS)
- [ ] `web/WEB-INF/web.xml`
- [ ] `web/WEB-INF/sun-web.xml`
- [ ] `web/jsp/error/error404.jsp`
- [ ] `web/jsp/error/error500.jsp`
- [ ] `lib/servlet-api.jar`
- [ ] `lib/mysql-connector-java-5.1.49-bin.jar` ← CRITICAL
- [ ] `database/schema.sql`
- [ ] `dist/OnlineBanking.war`
- [ ] `nbproject/` folder
- [ ] `build.xml`

---

## 🚀 DEPLOYMENT

### Option 1: NetBeans (Recommended)

1. [ ] MySQL is running (Verify: `mysql -u root -p`)
2. [ ] GlassFish is started
3. [ ] Project is clean built
4. [ ] Right-click Project → **Deploy** (or press F6)
5. [ ] Wait for "Application deployed successfully"
6. [ ] Browser should open: http://localhost:8080/banking-app/login

### Option 2: GlassFish Admin Console

1. [ ] Open: http://localhost:4848
2. [ ] Login: admin / admin
3. [ ] Left menu → Applications
4. [ ] Click "Deploy"
5. [ ] Select: `dist/OnlineBanking.war`
6. [ ] Application Name: `OnlineBanking`
7. [ ] Context Root: `/banking-app`
8. [ ] Click "Deploy"

### Option 3: Command Line

```powershell
asadmin deploy --contextroot banking-app dist/OnlineBanking.war
```

---

## ✅ POST-DEPLOYMENT VERIFICATION

### Step 1: Access Application
- [ ] Open browser: http://localhost:8080/banking-app/login
- [ ] Should see login form (NOT 404 error)

### Step 2: Test Login
- [ ] Username: `admin`
- [ ] Password: `admin123`
- [ ] Click "Login"
- [ ] Should redirect to Dashboard

### Step 3: Test Database Connection
- [ ] On Dashboard, click "Customers"
- [ ] Should show customer list or "No data" message
- [ ] If you see error, check MySQL

### Step 4: Check GlassFish Console
- [ ] Open: http://localhost:4848
- [ ] Applications → Should see "OnlineBanking" with green checkmark

---

## ❌ If Deployment Fails

### Error: "ClassNotFoundException: com.mysql.jdbc.Driver"
- [ ] MySQL JDBC driver NOT in `lib/`
- [ ] Download from: https://dev.mysql.com/downloads/connector/j/
- [ ] Place in: `banking-app/lib/`
- [ ] Rebuild and deploy

### Error: "Cannot connect to MySQL"
- [ ] MySQL not running: `mysql -u root -p` from PowerShell
- [ ] Database not created: Run `database/schema.sql`
- [ ] Credentials wrong: Check DBConnection.java (root/root)

### Error: "404 - Page Not Found"
- [ ] Application not deployed: Check http://localhost:4848
- [ ] Wrong URL: Use http://localhost:8080/banking-app (not just /banking-app)
- [ ] Wait 10 seconds and refresh

### Error: "Page error / Cannot instantiate Servlet"
- [ ] Check GlassFish logs: 
  - Windows: `C:\Program Files\glassfish-4.0\glassfish\domains\domain1\logs\server.log`
- [ ] ClassNotFoundException: Missing JAR in lib/
- [ ] Rebuild and redeploy

### Application Runs But Database Empty
- [ ] Sample data not inserted: Run `database/schema.sql` again

---

## 📋 Final Checklist Before Submission

- [ ] Application deployed successfully to GlassFish
- [ ] Login works (admin/admin123)
- [ ] Dashboard loads
- [ ] Customer list accessible
- [ ] Employee list accessible
- [ ] No red errors in browser console
- [ ] GlassFish admin console shows app as deployed
- [ ] Project cleaned and built one final time
- [ ] All documentation in README.md

---

## 🎉 SUCCESS!

If all checkmarks complete, your application is ready!

**Application URL:** http://localhost:8080/banking-app/login  
**Admin Credentials:** admin / admin123  
**Database:** banking_db on localhost:3306

