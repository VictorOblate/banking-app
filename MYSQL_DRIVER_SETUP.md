# MySQL JDBC Driver Setup Required

## Issue
The MySQL JDBC driver is missing from the `lib/` directory. This will cause a deployment error:
```
ClassNotFoundException: com.mysql.jdbc.Driver
```

## Solution - Download MySQL Connector/J

### Option 1: Automatic Download (Recommended for Windows)
Download from: https://dev.mysql.com/downloads/connector/j/

1. Go to: https://dev.mysql.com/downloads/connector/j/
2. Select version 5.1.49 (compatible with Java 8)
3. Download the ZIP file
4. Extract `mysql-connector-java-5.1.49-bin.jar`
5. Place it in your project's `lib/` folder

### Option 2: Manual Command (If you have wget/curl)
```bash
cd lib/
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.49.tar.gz
tar xzf mysql-connector-java-5.1.49.tar.gz
cp mysql-connector-java-5.1.49/mysql-connector-java-5.1.49-bin.jar .
rm -rf mysql-connector-java-5.1.49*
```

### Option 3: Using Maven Repository (Alternative)
```bash
cd lib/
curl -O https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.49/mysql-connector-java-5.1.49.jar
```

## After Adding the JAR
1. In NetBeans: Right-click project → **Clean and Build**
2. The WAR file will be rebuilt with the driver
3. Deploy to GlassFish

## File Location
Once downloaded, place the JAR here:
```
banking-app/lib/mysql-connector-java-5.1.49-bin.jar
```

This will be automatically included in the WAR file during build!
