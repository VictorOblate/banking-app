package com.bankingapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class
 * 
 * This class manages MySQL database connections for the Online Banking Application.
 * It provides a centralized connection pool mechanism and handles connection lifecycle.
 * 
 * All database operations should use this class to obtain connections.
 * 
 * Database Configuration:
 * - Driver: MySQL Connector/J
 * - Host: localhost
 * - Port: 3306
 * - Database: banking_db
 * - Username: root
 * - Password: (configured below)
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class DBConnection {
    
    // Database Configuration Constants
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/banking_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    
    /**
     * Get a database connection
     * 
     * This method loads the MySQL JDBC driver and creates a new connection
     * to the banking database. Each call returns a fresh connection.
     * 
     * @return Connection object to the database
     * @throws SQLException if connection fails
     * @throws ClassNotFoundException if MySQL driver is not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DB_DRIVER);
            
            // Create and return database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            
            System.out.println("Database connection established successfully");
            return connection;
            
        } catch (ClassNotFoundException e) {
            // MySQL driver not found in classpath
            System.err.println("MySQL Driver not found: " + e.getMessage());
            throw new ClassNotFoundException("Unable to load MySQL driver", e);
            
        } catch (SQLException e) {
            // Connection failed (database might be down, wrong credentials, etc.)
            System.err.println("Database connection failed: " + e.getMessage());
            throw new SQLException("Unable to establish database connection", e);
        }
    }
    
    /**
     * Close a database connection
     * 
     * This method safely closes a database connection and handles any exceptions.
     * Should always be called in finally block or try-with-resources.
     * 
     * @param connection The connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test the database connection
     * 
     * This method can be used to verify that the database is accessible
     * and the connection configuration is correct.
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        Connection connection = null;
        try {
            connection = getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection test successful!");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        } finally {
            closeConnection(connection);
        }
        return false;
    }
}
