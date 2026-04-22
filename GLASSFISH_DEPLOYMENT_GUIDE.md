# GlassFish 4.0 Deployment Guide - Online Banking App

## Pre-Deployment Checklist

### ✅ Required Setup (Do These First)

1. **MySQL JDBC Driver** - CRITICAL
   - Download: https://dev.mysql.com/downloads/connector/j/
   - Version: 5.1.49 (compatible with Java 8)
   - Place in: `banking-app/lib/mysql-connector-java-5.1.49-bin.jar`
   - Without this, you'll get: `ClassNotFoundException: com.mysql.jdbc.Driver`

2. **MySQL Database**
   ```bash
   mysql -u root -p
   CREATE DATABASE banking_db;
   USE banking_db;
   SOURCE database/schema.sql;
   \q
   ```
   - Database: `banking_db`
   - Username: `root`
   - Password: `root`
   - Verify 8 tables: admin, customer, employee, package, shift, transaction, payment, user

3. **GlassFish 4.0 Running**
   - Start GlassFish: `asadmin start-domain`
   - Admin Console: http://localhost:4848
   - Access Port: 8080

---

## Deployment Steps

### Step 1: Build the Project

In NetBeans:
```
1. Right-click Project → Clean and Build
2. Wait for "BUILD SUCCESSFUL"
3. WAR file created: dist/OnlineBanking.war
```

Or via command line:
```bash
cd banking-app
ant clean build
```

### Step 2: Deploy to GlassFish

#### Option A: NetBeans (Recommended)

1. **Configure GlassFish Server in NetBeans**
   - Tools → Servers
   - Click "Add Server"
   - Server Type: GlassFish Server
   - Next → Browse to GlassFish installation
   - Finish

2. **Set Project Server**
   - Right-click Project → Properties
   - Run tab → Server: GlassFish Server
   - OK

3. **Deploy**
   - Right-click Project → Deploy
   - Or press F6 to Run
   - Browser opens: http://localhost:8080/banking-app/login

#### Option B: Manual Deployment (GlassFish Admin Console)

1. Open: http://localhost:4848
2. Login (default: admin/admin)
3. Left menu → Applications
4. Click "Deploy"
5. Choose WAR file: `dist/OnlineBanking.war`
6. Application Name: `OnlineBanking`
7. Context Root: `/banking-app`
8. Click "Deploy"

#### Option C: Command Line (asadmin)

```bash
asadmin deploy --contextroot banking-app dist/OnlineBanking.war
```

---

## Troubleshooting Common Deployment Errors

### Error 1: ClassNotFoundException: com.mysql.jdbc.Driver

**Cause:** MySQL JDBC driver missing from lib/

**Solution:**
1. Download mysql-connector-java-5.1.49-bin.jar
2. Place in: `banking-app/lib/`
3. Right-click Project → Clean and Build
4. Re-deploy

### Error 2: Cannot connect to localhost:3306

**Cause:** MySQL not running or database doesn't exist

**Solution:**
```bash
# Check MySQL is running
mysql -u root -p
USE banking_db;
SHOW TABLES;
```

If database missing:
```bash
mysql -u root -p < database/schema.sql
```

### Error 3: 404 - Page Not Found

**Cause:** Wrong context path or application not accessible

**Solution:**
- Browser to: `http://localhost:8080/banking-app/login` (NOT just /banking-app)
- Verify application deployed: http://localhost:4848 → Applications

### Error 4: java.lang.NoSuchMethodError or ClassNotFoundException

**Cause:** Servlet or class not found

**Solution:**
1. In NetBeans → Clean and Build
2. Deploy again
3. If persists: Undeploy and redeploy

### Error 5: JSP Compilation Error

**Cause:** Wrong JSP syntax in files

**Solution:**
- Check GlassFish logs: `glassfish/domains/domain1/logs/server.log`
- Look for JSP file names in error messages
- Fix the JSP and rebuild

---

## Verifying Successful Deployment

### 1. Check GlassFish Console
- Go to: http://localhost:4848
- Login: admin/admin
- Applications → Should see "OnlineBanking" with green arrow

### 2. Access Application
- Open: http://localhost:8080/banking-app/login
- Should see login form (not 404 error)

### 3. Test Login
- Username: `admin`
- Password: `admin123`
- Should redirect to dashboard

### 4. Check Logs for Errors
```bash
# View real-time logs
tail -f glassfish/domains/domain1/logs/server.log

# Or in Windows
type glassfish\domains\domain1\logs\server.log
```

---

## Database Connection Test

Once logged in:
1. Go to **Admin Dashboard**
2. Try to view Customers
3. Should see list or "No records" message
4. If you see error, database connection failed

---

## Performance Tuning (Optional)

### sun-web.xml already configured with:
- Context root: `/banking-app`
- JSP keepgenerated: true (for debugging)
- Session persistence: file

---

## Undeploy if Needed

```bash
asadmin undeploy OnlineBanking
```

Or from Admin Console:
- Applications → Select OnlineBanking → Undeploy

---

## Quick Reference

| Component | Configuration |
|-----------|---|
| Database | banking_db on localhost:3306 |
| DB User | root / root |
| App URL | http://localhost:8080/banking-app |
| Login URL | http://localhost:8080/banking-app/login |
| Admin Credentials | admin / admin123 |
| GlassFish Console | http://localhost:4848 |
| GlassFish Admin | admin / admin |

---

## Still Having Issues?

1. **Check MySQL is running and accessible**
2. **Verify MySQL JDBC driver is in lib/**
3. **Check GlassFish logs for specific error messages**
4. **Clear GlassFish cache**: Stop domain → Delete `glassfish/domains/domain1/generated/` → Start domain**
5. **Clean and rebuild project**
6. **Restart GlassFish domain**

---

**Application is ready for production deployment!** ✅
