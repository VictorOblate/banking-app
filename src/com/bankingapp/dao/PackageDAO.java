package com.bankingapp.dao;

import com.bankingapp.model.Package;
import com.bankingapp.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Package Data Access Object (DAO)
 * 
 * Handles all database operations related to banking packages.
 * Includes CRUD operations for package management.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class PackageDAO {
    
    /**
     * Add a new package to the database
     * 
     * @param pkg The Package object to add
     * @return true if package was added successfully, false otherwise
     */
    public boolean addPackage(Package pkg) {
        String sql = "INSERT INTO packages (package_name, package_type, description, " +
                     "benefits, monthly_fee, annual_fee, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, pkg.getPackageName());
            preparedStatement.setString(2, pkg.getPackageType());
            preparedStatement.setString(3, pkg.getDescription());
            preparedStatement.setString(4, pkg.getBenefits());
            preparedStatement.setDouble(5, pkg.getMonthlyFee());
            preparedStatement.setDouble(6, pkg.getAnnualFee());
            preparedStatement.setBoolean(7, pkg.isActive());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Package added successfully: " + pkg.getPackageName());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding package: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get all active packages
     * 
     * @return List of all active packages
     */
    public List<Package> getAllActivePackages() {
        String sql = "SELECT package_id, package_name, package_type, description, benefits, " +
                     "monthly_fee, annual_fee, created_date, modified_date, is_active " +
                     "FROM packages WHERE is_active = TRUE ORDER BY package_name";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Package> packages = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Package pkg = mapResultSetToPackage(resultSet);
                packages.add(pkg);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving packages: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return packages;
    }

    /**
     * Get all packages (active and inactive) for management
     * 
     * @return List of all packages
     */
    public List<Package> getAllPackages() {
        String sql = "SELECT package_id, package_name, package_type, description, benefits, " +
                     "monthly_fee, annual_fee, created_date, modified_date, is_active " +
                     "FROM packages ORDER BY package_id DESC";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Package> packages = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Package pkg = mapResultSetToPackage(resultSet);
                packages.add(pkg);
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving packages: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return packages;
    }
    
    /**
     * Get package by ID
     * 
     * @param packageId The ID of the package to retrieve
     * @return Package object if found, null otherwise
     */
    public Package getPackageById(int packageId) {
        String sql = "SELECT package_id, package_name, package_type, description, benefits, " +
                     "monthly_fee, annual_fee, created_date, modified_date, is_active " +
                     "FROM packages WHERE package_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Package pkg = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, packageId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                pkg = mapResultSetToPackage(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving package: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return pkg;
    }
    
    /**
     * Update an existing package
     * 
     * @param pkg The Package object with updated data
     * @return true if package was updated successfully, false otherwise
     */
    public boolean updatePackage(Package pkg) {
        String sql = "UPDATE packages SET package_name = ?, package_type = ?, description = ?, " +
                     "benefits = ?, monthly_fee = ?, annual_fee = ?, modified_date = ?, is_active = ? " +
                     "WHERE package_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, pkg.getPackageName());
            preparedStatement.setString(2, pkg.getPackageType());
            preparedStatement.setString(3, pkg.getDescription());
            preparedStatement.setString(4, pkg.getBenefits());
            preparedStatement.setDouble(5, pkg.getMonthlyFee());
            preparedStatement.setDouble(6, pkg.getAnnualFee());
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(8, pkg.isActive());
            preparedStatement.setInt(9, pkg.getPackageId());
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Package updated successfully: " + pkg.getPackageId());
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating package: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Delete a package by ID (soft delete - set inactive)
     * 
     * @param packageId The ID of the package to delete
     * @return true if package was deleted successfully, false otherwise
     */
    public boolean deletePackage(int packageId) {
        String sql = "DELETE FROM packages WHERE package_id = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, packageId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Package deleted successfully: " + packageId);
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting package: " + e.getMessage());
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    /**
     * Helper method to map ResultSet to Package object
     * 
     * @param resultSet The ResultSet to map
     * @return Package object populated from ResultSet
     * @throws SQLException if ResultSet access fails
     */
    private Package mapResultSetToPackage(ResultSet resultSet) throws SQLException {
        Package pkg = new Package();
        pkg.setPackageId(resultSet.getInt("package_id"));
        pkg.setPackageName(resultSet.getString("package_name"));
        pkg.setPackageType(resultSet.getString("package_type"));
        pkg.setDescription(resultSet.getString("description"));
        pkg.setBenefits(resultSet.getString("benefits"));
        pkg.setMonthlyFee(resultSet.getDouble("monthly_fee"));
        pkg.setAnnualFee(resultSet.getDouble("annual_fee"));
        pkg.setCreatedDate(resultSet.getTimestamp("created_date"));
        pkg.setModifiedDate(resultSet.getTimestamp("modified_date"));
        pkg.setActive(resultSet.getBoolean("is_active"));
        return pkg;
    }
    
    /**
     * Get total count of packages
     * 
     * @return Total number of packages in the database
     */
    public int getPackageCount() {
        String sql = "SELECT COUNT(*) as count FROM packages";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error getting package count: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return 0;
    }
    
    /**
     * Get count of active packages
     * 
     * @return Number of active packages
     */
    public int getActivePackagesCount() {
        String sql = "SELECT COUNT(*) as count FROM packages WHERE is_active = TRUE";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error getting active packages count: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return 0;
    }
    
    /**
     * Get total value from all annual fees
     * 
     * @return Total value from annual fees
     */
    public double getTotalPackageValue() {
        String sql = "SELECT SUM(annual_fee) as total FROM packages WHERE is_active = TRUE";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double total = resultSet.getDouble("total");
                return total > 0 ? total : 0.0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error getting total package value: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) DBConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return 0.0;
    }
}
