# Online Banking Application - Windows Setup Guide (GlassFish 4.0)

## ✅ Complete Step-by-Step Installation

Your Online Banking Web Application is ready to deploy on Windows using **GlassFish 4.0**.

This guide walks you through installing all requirements and deploying the application.

---

# 🖥️ COMPLETE WINDOWS SETUP

## STEP 1: Install Java Development Kit (JDK 8)

**Why:** GlassFish 4.0 requires Java 8 or higher.

### 1.1 Download Java
- Open browser: https://adoptium.net/
- Select **Java 8 (LTS)**
- Download **Windows x64 Installer** (`.msi` file)

### 1.2 Install Java
- Double-click the downloaded `.msi` file
- Click "Next" through all screens
- **Important:** Note the installation path (usually `C:\Program Files\Eclipse Adoptium\jdk-8.0.x-hotspot`)
- Finish installation

### 1.3 Set JAVA_HOME Environment Variable
- Press **Windows Key + Pause/Break** or right-click "This PC" → **Properties**
- Click **Advanced system settings** (left panel)
- Click **Environment Variables** button
- Under "System variables", click **New**
- **Variable name:** `JAVA_HOME`
- **Variable value:** `C:\Program Files\Eclipse Adoptium\jdk-8.0.x-hotspot` (your Java path)
- Click **OK** → **OK** → **OK**

### 1.4 Verify Java Installation
- Press **Win + R**, type `cmd`, press **Enter**
- Type: `java -version`
- Should show: `openjdk version "1.8.0_*"`

**✅ Java Installed!**

---

## STEP 2: Install MySQL Server

**Why:** Database for your banking application.

### 2.1 Download MySQL
- Open browser: https://dev.mysql.com/downloads/mysql/
- Select **MySQL Community Server** (latest 8.0.x version)
- Download **Windows (x86, 64-bit) MSI Installer**

### 2.2 Install MySQL
- Double-click the downloaded `.msi` file
- Click "Next"
- Select **MySQL Server** (checkbox should be checked)
- Continue clicking "Next" and "Execute"
- When asked for configuration:
  - **Port:** `3306` (default)
  - **Root password:** `root` (important - matches your database config)
  - Check **"Configure MySQL Server as a Windows Service"**
  - Finish installation

### 2.3 Verify MySQL Installation
- Press **Win + R**, type `cmd`, press **Enter**
- Type: `mysql -u root -p`
- Enter password: `root`
- You should see: `mysql>`
- Type: `EXIT` to quit

**✅ MySQL Installed!**

---

## STEP 3: Install Apache Ant

**Why:** Build automation tool to create the WAR file.

### 3.1 Download Ant
- Open browser: https://ant.apache.org/bindownload.cgi
- Download **Binary Distribution** (`.zip` file, e.g., `apache-ant-1.10.14-bin.zip`)

### 3.2 Extract Ant
- Right-click the downloaded `.zip` file
- Select **Extract All**
- Extract to: `C:\apache-ant-1.10.14` (or similar version number)
- Note the exact path

### 3.3 Set ANT_HOME Environment Variable
- Press **Windows Key + Pause/Break** or right-click "This PC" → **Properties**
- Click **Advanced system settings**
- Click **Environment Variables**
- Under "System variables", click **New**
- **Variable name:** `ANT_HOME`
- **Variable value:** `C:\apache-ant-1.10.14` (your Ant path)
- Click **OK** → **OK** → **OK**

### 3.4 Add Ant to PATH
- In **Environment Variables** dialog, find `PATH` variable and click **Edit**
- Click **New**
- Enter: `%ANT_HOME%\bin`
- Click **OK** → **OK** → **OK**

### 3.5 Verify Ant Installation
- **Close all Command Prompts completely**
- Open **new** Command Prompt: Press **Win + R**, type `cmd`, press **Enter**
- Type: `ant -version`
- Should show: `Apache Ant(TM) version 1.10.14 compiled on ...`

**✅ Ant Installed!**

---

## STEP 4: Download and Extract GlassFish 4.0

**Why:** Application server to run your Java web application.

### 4.1 Download GlassFish
- Open browser: https://repo1.maven.org/maven2/org/glassfish/main/distributions/glassfish/4.0/glassfish-4.0.zip
- The download will start automatically (~97 MB)

### 4.2 Extract GlassFish
- Navigate to your **banking-app** project folder (where you cloned/extracted it)
- Right-click the downloaded `glassfish-4.0.zip` file
- Select **Extract All**
- Extract to your project folder
- Should create: `C:\Users\YourUsername\Documents\banking-app\glassfish4`

### 4.3 Verify GlassFish
- Check that this file exists: `C:\...\banking-app\glassfish4\bin\asadmin.bat`

**✅ GlassFish Installed!**

---

## STEP 5: Setup Database Schema

**Why:** Create database tables and populate sample data.

### 5.1 Open Command Prompt (as Administrator)
- Press **Win + X**
- Select **Command Prompt (Admin)** or **PowerShell (Admin)**

### 5.2 Navigate to Your Project
```bash
cd C:\Users\YourUsername\Documents\banking-app
```
(Replace `YourUsername` with your actual Windows username)

### 5.3 Import Database Schema
```bash
mysql -u root -p root banking_db < database\schema.sql
```
- If database doesn't exist yet, MySQL will create it
- This creates 8 tables + sample data

### 5.4 Verify Database
```bash
mysql -u root -p root banking_db -e "SHOW TABLES;"
```
- Should list 8 tables:
  - admin
  - customers
  - employees
  - packages
  - shifts
  - transactions
  - payments
  - activity_log

**✅ Database Setup Complete!**

---

## STEP 6: Build the Application

**Why:** Compile Java code and package it into a WAR file.

### 6.1 Open Command Prompt
- Press **Win + R**, type `cmd`, press **Enter**

### 6.2 Navigate to Project
```bash
cd C:\Users\YourUsername\Documents\banking-app
```

### 6.3 Clean and Build
```bash
ant clean build
```

### 6.4 Wait for Build to Complete
- Should see: `BUILD SUCCESSFUL`
- Creates: `dist\OnlineBanking.war`

**✅ Application Built!**

---

## STEP 7: Start GlassFish Server

**Why:** Run the application server.

### 7.1 Open Command Prompt
- Press **Win + R**, type `cmd`, press **Enter**

### 7.2 Navigate to GlassFish
```bash
cd C:\Users\YourUsername\Documents\banking-app\glassfish4\bin
```

### 7.3 Start Domain
```bash
asadmin.bat start-domain domain1
```

### 7.4 Wait for Startup
- Takes 10-30 seconds
- Should see: `Domain domain1 is running`
- Leave this window open (server is running)

**✅ GlassFish Running!**

---

## STEP 8: Deploy Application

**Why:** Copy the WAR file to GlassFish to make the application accessible.

### 8.1 Open **New** Command Prompt (don't close the first one!)
- Press **Win + R**, type `cmd`, press **Enter**

### 8.2 Navigate to GlassFish
```bash
cd C:\Users\YourUsername\Documents\banking-app\glassfish4\bin
```

### 8.3 Deploy WAR File
```bash
asadmin.bat deploy ..\..\dist\OnlineBanking.war
```

### 8.4 Verify Deployment
```bash
asadmin.bat list-applications
```
- Should show: `OnlineBanking <web>`
- Application is now deployed!

**✅ Application Deployed!**

---

## STEP 9: Access the Application

### 9.1 Open Browser
- Open **Chrome**, **Firefox**, **Edge**, or **Safari**

### 9.2 Navigate to Application
```
http://localhost:8080/OnlineBanking
```

### 9.3 You Should See
- **Login Page** with username and password fields

### 9.4 Login
- **Username:** `admin`
- **Password:** `admin123`
- Click **Login**

### 9.5 You Should See
- **Admin Dashboard** with navigation menu:
  - Customers
  - Employees
  - Packages
  - Shifts
  - Transactions
  - Payments

**✅ Application Running!**

---

## 📊 SAMPLE DATA AVAILABLE

Your application includes sample data ready to test:

**Admin Login:**
- Username: `admin`
- Password: `admin123`

**Sample Customers (4 with auto-generated account numbers):**
- ACC001001
- ACC001002
- ACC001003
- ACC001004

**Sample Employees (3 with auto-generated employee codes):**
- EMP001
- EMP002
- EMP003

**Sample Banking Packages (4):**
1. Basic Savings - Monthly: $0, Annual: $0
2. Premium Checking - Monthly: $5, Annual: $50
3. Student Account - Monthly: $0, Annual: $0
4. Business Pro - Monthly: $10, Annual: $100

**Sample Work Shifts (4):**
1. Morning - 6:00 AM to 2:00 PM
2. Evening - 2:00 PM to 10:00 PM
3. Night - 10:00 PM to 6:00 AM
4. Part-time - Flexible hours

---

## 🧪 QUICK TEST CHECKLIST

After logging in, test these features:

### Customers
- [ ] Click **Customers** → View list (4 sample customers)
- [ ] Click **Add Customer** → Fill form → Submit
- [ ] Verify new customer appears in list
- [ ] Click **Edit** on any customer → Change details
- [ ] Verify changes saved
- [ ] Click **Delete** → Confirm deletion

### Employees
- [ ] Click **Employees** → View list (3 sample employees)
- [ ] Click **Add Employee** → Fill form → Select shift
- [ ] Verify new employee added
- [ ] Test salary calculation with overtime

### Packages & Shifts
- [ ] Click **Packages** → View 4 sample packages
- [ ] Click **Shifts** → View 4 sample shifts

### Other Features
- [ ] View **Transactions** → See transaction list
- [ ] View **Payments** → See payment records
- [ ] Click **Logout** → Return to login page

---

## 🛑 STOPPING THE APPLICATION

### Stop GlassFish
1. Find the Command Prompt window running GlassFish
2. Press **Ctrl + C** to stop the server
3. Or, from another Command Prompt:
```bash
cd C:\Users\YourUsername\Documents\banking-app\glassfish4\bin
asadmin.bat stop-domain domain1
```

### Stop MySQL
```bash
net stop MySQL80
```

### Start MySQL Again (for next session)
```bash
net start MySQL80
```

---

## 🐛 TROUBLESHOOTING

### ❌ "Java is not recognized"
**Fix:**
- Verify `JAVA_HOME` is set: Open Command Prompt, type `echo %JAVA_HOME%`
- Should show your Java path
- If empty, environment variable not set correctly
- Close Command Prompt and set `JAVA_HOME` again (Step 1.3)
- Open **new** Command Prompt and retry

### ❌ "Ant is not recognized"
**Fix:**
- Verify `ANT_HOME` and `PATH` set: Type `echo %ANT_HOME%` and `echo %PATH%`
- Close all Command Prompts
- Open **new** Command Prompt and retry

### ❌ "MySQL Access Denied"
**Fix:**
- Verify MySQL is running: `net start MySQL80`
- Check password is correct: Try `mysql -u root -p` then enter `root`
- Verify database exists: `mysql -u root -p root -e "SHOW DATABASES;"`

### ❌ "Port 8080 already in use"
**Fix:**
- Another application is using port 8080
- Option 1: Find and close that application
- Option 2: Change GlassFish port (advanced)

### ❌ "Build fails with compilation errors"
**Fix:**
- Verify all prerequisites installed (Java, Ant)
- Ensure `JAVA_HOME` is set to JDK (not JRE)
- Try: `ant clean build -v` to see verbose output

### ❌ "Cannot deploy WAR file"
**Fix:**
- Ensure GlassFish is running: `asadmin.bat list-applications`
- Try: `asadmin.bat undeploy OnlineBanking` then redeploy
- Rebuild: `ant clean build`

### ❌ "Application shows 404 error"
**Fix:**
- Verify deployment: `asadmin.bat list-applications`
- Check URL: Should be exactly `http://localhost:8080/OnlineBanking`
- Wait 5-10 seconds after deployment for GlassFish to fully load app

### ❌ "Database tables not found"
**Fix:**
- Run schema import command again:
```bash
mysql -u root -p root banking_db < database\schema.sql
```
- Verify import completed without errors

---

## 📝 QUICK REFERENCE - KEY COMMANDS

```bash
# Build project
cd C:\path\to\banking-app
ant clean build

# Start GlassFish
cd glassfish4\bin
asadmin.bat start-domain domain1

# Deploy application (from new Command Prompt)
asadmin.bat deploy ..\..\dist\OnlineBanking.war

# Stop GlassFish
asadmin.bat stop-domain domain1

# List deployed applications
asadmin.bat list-applications

# Access GlassFish admin console
http://localhost:4848

# Import database schema
mysql -u root -p root banking_db < database\schema.sql

# Check MySQL running
net start MySQL80
net stop MySQL80
```

---

## 📍 IMPORTANT PATHS

| Component | Path |
|-----------|------|
| Java | `C:\Program Files\Eclipse Adoptium\jdk-8.0.x-hotspot` |
| MySQL | `C:\Program Files\MySQL\MySQL Server 8.0` |
| Ant | `C:\apache-ant-1.10.14` |
| GlassFish | `C:\Users\YourUsername\Documents\banking-app\glassfish4` |
| Project | `C:\Users\YourUsername\Documents\banking-app` |
| Database | `C:\ProgramData\MySQL\MySQL Server 8.0\Data\banking_db` |
| Built Application | `C:\...\banking-app\dist\OnlineBanking.war` |
| Application URL | `http://localhost:8080/OnlineBanking` |
| Admin Console URL | `http://localhost:4848` |

---

## ✨ NEXT STEPS

1. ✅ Install Java, MySQL, Ant, GlassFish (this guide)
2. ✅ Setup database with schema
3. ✅ Build application with Ant
4. ✅ Deploy to GlassFish
5. ✅ Access at `http://localhost:8080/OnlineBanking`
6. ✅ Login with `admin`/`admin123`

---

## 🎉 YOU'RE DONE!

Your complete Online Banking Web Application is now running on Windows with GlassFish 4.0!

**Access the application:**
```
http://localhost:8080/OnlineBanking
```

**Test with:**
- Username: `admin`
- Password: `admin123`

**Features available:**
- ✅ Customer Management (CRUD)
- ✅ Employee Management (CRUD)
- ✅ Package Management
- ✅ Shift Management
- ✅ Transaction Management
- ✅ Payment Management
- ✅ Secure Session Management
- ✅ Input Validation
- ✅ Sample Data Ready to Test

---

**Happy Banking!** 🏦

*For more information, see:*
- *README.md - Project overview*
- *ARCHITECTURE.md - System design*
- *REQUIREMENTS_COMPLIANCE.md - Feature verification*

**Happy Banking!** 🏦