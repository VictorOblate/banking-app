# 🚀 Quick Deployment Guide

## For Running on Your Laptop

Since GitHub Codespaces doesn't allow installing system services, use this guide to deploy the application on your local machine.

## Prerequisites

1. **Java JDK 8 or higher**
   - Download from: https://adoptium.net/
   - Set `JAVA_HOME` environment variable

2. **MySQL Server 5.5 or higher**
   - Ubuntu/Debian: `sudo apt-get install mysql-server`
   - macOS: `brew install mysql`
   - Windows: Download from https://dev.mysql.com/downloads/mysql/

3. **Application Server** (Choose one):
   - **Payara Server 5.x** (Recommended - modern Java compatible)
     - Download: https://repo1.maven.org/maven2/fish/payara/distributions/payara/5.2022.5/payara-5.2022.5.zip
     - Extract to `payara5/` directory in project root

   - **GlassFish 4.0** (Legacy)
     - Download: https://repo1.maven.org/maven2/org/glassfish/main/distributions/glassfish/4.0/glassfish-4.0.zip
     - Extract to `glassfish4/` directory in project root

4. **Apache Ant** (for building)
   - Ubuntu/Debian: `sudo apt-get install ant`
   - macOS: `brew install ant`
   - Windows: Download from https://ant.apache.org/

## Deployment Steps

1. **Transfer the project** to your laptop

2. **Navigate to project directory:**
   ```bash
   cd /path/to/banking-app
   ```

3. **Run the deployment script:**
   ```bash
   ./deploy.sh
   ```

   The script will:
   - ✅ Check prerequisites
   - 🗄️ Set up MySQL database
   - 🏗️ Build the application
   - 🌐 Start application server
   - 📦 Deploy the WAR file
   - 🧪 Test the deployment

## Manual Deployment (Alternative)

If you prefer manual steps:

1. **Setup Database:**
   ```bash
   mysql -u root -p
   CREATE DATABASE banking_db;
   GRANT ALL PRIVILEGES ON banking_db.* TO 'root'@'localhost';
   \q
   mysql -u root -p banking_db < database/schema.sql
   ```

2. **Build Application:**
   ```bash
   ant clean build
   ```

3. **Start Server:**
   ```bash
   # For Payara
   cd payara5/bin
   ./asadmin start-domain domain1

   # For GlassFish
   cd glassfish4/bin
   ./asadmin start-domain domain1
   ```

4. **Deploy Application:**
   ```bash
   ./asadmin deploy /path/to/banking-app/dist/OnlineBanking.war
   ```

## Access the Application

- **URL:** http://localhost:8080/OnlineBanking
- **Admin Login:**
  - Username: `admin`
  - Password: `admin123`

## Sample Data

The database includes:
- **1 Admin account**
- **4 Sample customers** (ACC001001 - ACC001004)
- **3 Sample employees**
- **4 Banking packages**
- **4 Shift schedules**

## Troubleshooting

1. **Port conflicts:** Change server ports in domain.xml if 8080/4848 are in use
2. **Database connection:** Update database credentials in application if needed
3. **Java version:** Ensure JAVA_HOME points to JDK 8+
4. **Permissions:** Run with appropriate user permissions

## Stopping the Application

```bash
# Stop the server
./asadmin stop-domain domain1

# Stop MySQL (if needed)
sudo service mysql stop
```

---

🎉 **Happy Banking!** The application should now be running on your laptop.