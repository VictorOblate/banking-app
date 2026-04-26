# Banking App Setup Instructions

## Project Overview
This is a comprehensive Java Web Application for online banking operations built with JSP, Servlets, and MySQL.

## Prerequisites
1. **Java JDK 8 or higher** - Already installed (version 11.0.14.1)
2. **MySQL Server 5.5 or higher** - Must be installed and running
3. **Payara Server 5.x or GlassFish 4.0** - Application server
4. **Apache Ant** (optional, for building)

## Step 1: Set Up MySQL Database

### Start MySQL Service
```bash
# On Ubuntu/Debian
sudo systemctl start mysql

# On macOS
brew services start mysql

# Or if already running, verify connection
mysql --version
```

### Create Database
```bash
# Connect to MySQL
mysql -u root -p

# Or use this script (no password prompt if configured)
mysql -u root -proot << 'EOF'
CREATE DATABASE IF NOT EXISTS banking_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON banking_db.* TO 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
EOF
```

### Import Database Schema
```bash
cd /workspaces/banking-app
mysql -u root -proot banking_db < database/schema.sql
```

### Verify Setup
```bash
mysql -u root -proot banking_db -e "SHOW TABLES;"
mysql -u root -proot banking_db -e "SELECT username FROM admin LIMIT 1;"
```

## Step 2: Configure Application Server

### Using Payara Server 5.x
```bash
cd payara5
./bin/asadmin start-domain domain1

# Check if running
./bin/asadmin list-domains
```

### Using GlassFish 4.0
```bash
cd glassfish4/glassfish
./bin/asadmin start-domain domain1
```

## Step 3: Build and Deploy Application

### Build WAR File
```bash
cd /workspaces/banking-app

# Using provided build.xml with ant (if available)
ant build

# Or compile manually and create structure
mkdir -p build/web/WEB-INF/classes build/web/WEB-INF/lib
javac -cp lib/servlet-api.jar:lib/mysql-connector-java-5.1.49-bin.jar \
  -d build/web/WEB-INF/classes src/com/bankingapp/**/*.java

# Copy web files
cp -r web/* build/web/

# Copy libraries
cp lib/mysql-connector-java-5.1.49-bin.jar build/web/WEB-INF/lib/

# Create WAR
cd build/web
jar cf ../OnlineBanking.war .
```

### Deploy to Server
```bash
# For Payara/GlassFish
cd /path/to/payara5
./bin/asadmin deploy /path/to/build/OnlineBanking.war
```

## Step 4: Verify Application

### Access Application
1. Open browser to: `http://localhost:8080/OnlineBanking/`
2. You should see the landing page

### Admin Login
- URL: `http://localhost:8080/OnlineBanking/login`
- Username: `admin`
- Password: `admin123`

### Pages to Test
- Login: http://localhost:8080/OnlineBanking/login
- Dashboard: http://localhost:8080/OnlineBanking/dashboard
- Customers: http://localhost:8080/OnlineBanking/customer?action=list
- Employees: http://localhost:8080/OnlineBanking/employee?action=list
- Payments: http://localhost:8080/OnlineBanking/payment?action=list
- Transactions: http://localhost:8080/OnlineBanking/transaction?action=list
- Shifts: http://localhost:8080/OnlineBanking/shift?action=list
- Packages: http://localhost:8080/OnlineBanking/package?action=list
- Reports: http://localhost:8080/OnlineBanking/report?action=list

## Database Credentials
- **Host**: localhost:3306
- **Database**: banking_db
- **Username**: root
- **Password**: root

(These are configured in `src/com/bankingapp/util/DBConnection.java`)

## Sample Data Included
- **Admin User**: username=admin, password=admin123
- **4 Customers**: Rajesh Kumar, Priya Singh, Amit Patel, Neha Gupta
- **3 Employees**: Vikram Sharma, Anjali Verma, Rohan Mishra
- **4 Packages**: Basic Savings, Premium Checking, Student Account, Business Pro
- **4 Shifts**: Morning, Evening, Night, Part-time

## Troubleshooting

### Error 500 - Database Connection Failed
- Verify MySQL is running
- Check credentials in DBConnection.java
- Ensure database schema was imported
- Check MySQL port (default 3306)

### Error 404 - Page Not Found
- Verify JSP files exist in web/jsp/
- Check servlet mapping in web/WEB-INF/web.xml
- Verify application is deployed correctly

### Login Not Working
- Check admin table has sample user
- Verify credentials: admin / admin123
- Check session configuration in web.xml

### Statistics Not Showing
- Verify database has test data
- Check transactions and payments tables are populated
- Ensure database date functions work (DATE, CURDATE)

## Application Structure
```
src/
├── com/bankingapp/
│   ├── controller/ - Servlets handling HTTP requests
│   ├── dao/ - Database access objects
│   ├── model/ - Data model classes
│   ├── service/ - Business logic layer
│   └── util/ - Utility classes
web/
├── jsp/ - JavaServer Pages
│   ├── admin/ - Admin dashboard pages
│   ├── components/ - Reusable JSP components
│   └── error/ - Error pages
└── css/ - Stylesheets
```

## Features Implemented
✓ Admin authentication and session management
✓ Customer management (CRUD operations)
✓ Employee management with salary calculation
✓ Payment processing and tracking
✓ Transaction management
✓ Shift scheduling
✓ Banking package management
✓ Dashboard with statistics
✓ Report generation
✓ Activity logging

## Next Steps
1. Complete database setup
2. Deploy application to Payara/GlassFish
3. Test login functionality
4. Verify all CRUD operations work
5. Check statistics dashboard
6. Monitor application logs for errors

## Support
For issues or questions, review server logs:
- Payara logs: `payara5/glassfish/domains/domain1/logs/server.log`
- GlassFish logs: `glassfish4/glassfish/domains/domain1/logs/server.log`
