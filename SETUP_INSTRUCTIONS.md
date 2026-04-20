# 🚀 Setup & Deployment Guide

## Online Banking Web Application

This document provides detailed instructions for setting up and deploying the Online Banking Web Application on your development environment.

---

## 📋 Prerequisites Checklist

Before you begin, ensure you have the following installed:

```
☐ Java Development Kit (JDK) 1.8 or higher
☐ MySQL Server 5.5 or higher
☐ GlassFish 4.0 Application Server
☐ Apache Ant 1.8 or higher
☐ Git (optional, for version control)
☐ Text Editor / IDE (VS Code, Eclipse, IntelliJ)
```

### Verify Java Installation:
```bash
java -version
javac -version
```

### Verify MySQL Installation:
```bash
mysql --version
```

### Verify Ant Installation:
```bash
ant -version
```

---

## 🗄️ Step 1: Database Setup

### 1.1 Create Database and User

Start MySQL from terminal:
```bash
mysql -u root -p
```

Execute the following SQL commands:
```sql
CREATE DATABASE banking_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON banking_db.* TO 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
EXIT;
```

### 1.2 Import Database Schema

Navigate to project directory and import the schema:
```bash
cd /workspaces/banking-app
mysql -u root -p banking_db < database/schema.sql
```

### 1.3 Verify Database Creation

Connect to database and verify tables:
```bash
mysql -u root -p banking_db
USE banking_db;
SHOW TABLES;
```

Expected output (8 tables):
```
+-------------------+
| Tables_in_banking_db |
+-------------------+
| admin             |
| activity_log      |
| customers         |
| employees         |
| packages          |
| payments          |
| shifts            |
| transactions      |
+-------------------+
```

### Sample Test Data

The schema.sql automatically populates test data:

**Admin Account:**
- Username: `admin`
- Password: `admin123`

**Sample Customers (4):**
- ACC001001, ACC001002, ACC001003, ACC001004

**Sample Employees (3):**
- EMP001, EMP002, EMP003

**Sample Packages (4):**
- Basic Savings, Premium Checking, Student Account, Business Pro

**Sample Shifts (4):**
- Morning (6 AM - 2 PM)
- Evening (2 PM - 10 PM)
- Night (10 PM - 6 AM)
- Part-time (Flexible)

---

## 🔨 Step 2: Build the Application

### 2.1 Navigate to Project Directory
```bash
cd /workspaces/banking-app
```

### 2.2 Build with Apache Ant
```bash
ant build
```

This command:
- Compiles all Java source files
- Creates the `build/` directory structure
- Generates `OnlineBanking.war` file
- Packages all resources (JSP, CSS, etc.)

### 2.3 Verify Build Success

After successful build, you should see:
```
OnlineBanking.war created successfully
```

Check the WAR file:
```bash
ls -lh build/OnlineBanking.war
```

---

## 🌐 Step 3: GlassFish Configuration

### 3.1 Start GlassFish Server

```bash
cd $GLASSFISH_HOME/bin
./asadmin start-domain domain1
```

Or if using Windows:
```
asadmin.bat start-domain domain1
```

Verify startup (should show):
```
Domain domain1 is running...
```

### 3.2 Access Admin Console

Open browser and navigate to:
```
http://localhost:4848
```

**Default Credentials:**
- Username: `admin`
- Password: `adminadmin`

### 3.3 Configure JDBC Connection Pool (Optional)

If ManualDataSource setup is required:

1. Go to: **Resources** → **JDBC** → **Connection Pools**
2. Click **New...**
3. Enter following details:
   - **Name:** `BankingDB`
   - **Resource Type:** `javax.sql.DataSource`
   - **Database Driver Vendor:** `MySQL`
4. Click **Next** and configure:
   - **Server Name:** localhost
   - **Port:** 3306
   - **Database Name:** banking_db
   - **User:** root
   - **Password:** root
5. Click **Finish**

---

## 📦 Step 4: Deploy Application

### Method 1: Auto Deploy (Recommended)

```bash
cp build/OnlineBanking.war $GLASSFISH_HOME/domains/domain1/autodeploy/
```

GlassFish will automatically deploy the WAR file.

### Method 2: Manual Deploy via Admin Console

1. Open GlassFish Admin Console: `http://localhost:4848`
2. Navigate to: **Applications**
3. Click **Deploy...**
4. Select `build/OnlineBanking.war`
5. Click **Deploy**

### Method 3: Command Line Deploy

```bash
asadmin deploy build/OnlineBanking.war
```

### Verify Deployment

```bash
asadmin list-applications
```

Expected output:
```
OnlineBanking      <web>
```

---

## ✅ Step 5: Application Verification

### 5.1 Access Application

Open browser and navigate to:
```
http://localhost:8080/OnlineBanking
```

You should be redirected to login page.

### 5.2 Login Test

Use demo credentials:
- **Username:** `admin`
- **Password:** `admin123`

### 5.3 Basic Functionality Test

**Test the following workflows:**

1. **View Customers**
   - Navigate: Dashboard → Customers → View Customers
   - Should display 4 sample customers

2. **Add New Customer**
   - Navigate: Dashboard → Customers → Add New Customer
   - Fill form with sample data
   - Account number should auto-generate (ACC + 6 digits)
   - Click Add Customer

3. **Edit Customer**
   - Select customer from list
   - Click Edit button
   - Modify information
   - Click Update

4. **Delete Customer**
   - Select customer from list
   - Click Delete button
   - Confirm deletion

5. **Employee Management**
   - Similar workflow as Customers
   - Test salary calculation preview

6. **Logout**
   - Click Logout button
   - Should redirect to login page

---

## 🔧 Troubleshooting

### Issue 1: Database Connection Failed

**Error:** `SQLException: Cannot get a connection, pool error`

**Solution:**
```sql
-- Verify MySQL running
mysql -u root -p banking_db -e "SELECT 1;"

-- If password incorrect, update DBConnection.java
cd /workspaces/banking-app/src/com/bankingapp/util
# Edit DBConnection.java line with password
```

### Issue 2: GlassFish Not Starting

**Error:** `address already in use: 8080`

**Solution:**
```bash
# Stop existing process
sudo lsof -i :8080
sudo kill -9 <PID>

# Or use different port
asadmin start-domain --port 8081 domain1
```

### Issue 3: WAR File Won't Deploy

**Error:** `Cannot deploy OnlineBanking.war`

**Solution:**
```bash
# Clean and rebuild
ant clean
ant build

# Restart GlassFish
asadmin stop-domain domain1
asadmin start-domain domain1

# Redeploy
asadmin deploy build/OnlineBanking.war
```

### Issue 4: Session Expired Error

**Error:** `Session expired. Please login again`

**Solution:**
- Default timeout is 30 minutes
- To extend, edit `Constants.java`:
  ```java
  public static final int SESSION_TIMEOUT = 60; // 60 minutes
  ```
- Rebuild and redeploy

### Issue 5: Form Validation Errors

**Error:** `Validation failed for email/phone`

**Solution:**
- Use valid formats:
  - **Email:** standard@example.com
  - **Phone:** 10 digit number (e.g., 9999999999)
  - **Name:** 2-100 characters, letters only
  - **Password:** 6-20 characters, alphanumeric

---

## 📊 Database Backup & Restore

### Backup Database

```bash
mysqldump -u root -p banking_db > banking_db_backup.sql
```

### Restore Database

```bash
mysql -u root -p banking_db < banking_db_backup.sql
```

---

## 🚀 Performance Optimization

### Enable Query Optimization

Edit `database/schema.sql` section where indexes are created:

```sql
-- Recommended indexes for performance
ALTER TABLE customers ADD INDEX idx_email (email);
ALTER TABLE customers ADD INDEX idx_phone (phone);
ALTER TABLE employees ADD INDEX idx_employee_code (employee_code);
ALTER TABLE transactions ADD INDEX idx_customer_id (customer_id);
ALTER TABLE transactions ADD INDEX idx_status (status);
ALTER TABLE payments ADD INDEX idx_payment_date (payment_date);
```

### Connection Pool Tuning

In GlassFish Admin Console:
- **Resources** → **JDBC** → **Connection Pools**
- Set appropriate values:
  - **Initial Pool Size:** 10
  - **Maximum Pool Size:** 50
  - **Max Wait Time:** 30000 ms

---

## 🔐 Security Hardening

### 1. Change Default Credentials

**GlassFish Admin:**
```bash
asadmin change-admin-password
```

**MySQL:**
```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'strong_password';
FLUSH PRIVILEGES;
```

**Update Application:**
Edit `DBConnection.java` with new credentials.

### 2. Enable HTTPS

```bash
asadmin create-ssl
asadmin set server.security-manager.enabled=true
```

### 3. Firewall Rules

```bash
# Allow only required ports
sudo ufw allow 8080/tcp      # GlassFish
sudo ufw allow 3306/tcp      # MySQL
sudo ufw allow 22/tcp        # SSH
```

---

## 📝 Logging & Monitoring

### Enable Detailed Logging

In GlassFish Admin Console:
- **Configurations** → **server-config** → **Logger Settings**
- Set **Log Level:** FINE or FINER
- Check logs: `$GLASSFISH_HOME/domains/domain1/logs/server.log`

### Monitor Application

```bash
tail -f $GLASSFISH_HOME/domains/domain1/logs/server.log
```

---

## 🧹 Cleanup & Maintenance

### Clean Application

```bash
cd /workspaces/banking-app
ant clean
```

Removes:
- Compiled classes
- WAR file
- Build directory

### Undeploy Application

```bash
asadmin undeploy OnlineBanking
```

### Stop GlassFish

```bash
asadmin stop-domain domain1
```

---

## 📈 Next Steps

After successful deployment:

1. **Customize Configuration**
   - Update `Constants.java` for your settings
   - Modify session timeout, validation rules, etc.

2. **Add More Users**
   ```sql
   INSERT INTO admin VALUES(2, 'manager', 'manager123', ..., NOW(), NULL, 1);
   ```

3. **Extend Functionality**
   - Implement Report generation (Excel/PDF export)
   - Add additional validation rules
   - Implement payment processing

4. **Performance Testing**
   - Load test with Apache JMeter
   - Profile application with JProfiler

5. **Production Deployment**
   - Use SSL certificates
   - Enable firewall rules
   - Set up automated backups
   - Configure monitoring alerts

---

## 📞 Additional Resources

- **GlassFish Documentation:** https://glassfish.java.net/
- **MySQL Documentation:** https://dev.mysql.com/doc/
- **Java Servlets:** https://docs.oracle.com/javaee/7/api/javax/servlet/
- **JSP Specification:** https://projects.eclipse.org/projects/ee4j.jsp

---

## ✨ You're Ready!

Your Online Banking Application is now ready for use. Access it at:

```
http://localhost:8080/OnlineBanking
```

**Happy Banking! 🎉**
