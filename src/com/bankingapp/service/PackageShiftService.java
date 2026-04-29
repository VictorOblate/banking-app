package com.bankingapp.service;

import com.bankingapp.dao.PackageDAO;
import com.bankingapp.dao.ShiftDAO;
import com.bankingapp.model.Package;
import com.bankingapp.model.Shift;
import java.util.List;

/**
 * Package and Shift Service Class
 * 
 * Contains business logic for both banking package and work shift operations.
 * This service provides methods used by customer and employee controllers.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class PackageShiftService {
    
    private PackageDAO packageDAO;
    private ShiftDAO shiftDAO;
    
    /**
     * Constructor initializing both DAOs
     */
    public PackageShiftService() {
        this.packageDAO = new PackageDAO();
        this.shiftDAO = new ShiftDAO();
    }
    
    /**
     * Get all active packages
     * 
     * @return List of all active packages
     */
    public List<Package> getAllPackages() {
        return packageDAO.getAllActivePackages();
    }

    /**
     * Get all packages including inactive for admin management
     * 
     * @return List of all packages
     */
    public List<Package> getAllPackagesForManagement() {
        return packageDAO.getAllPackages();
    }
    
    /**
     * Get package by ID
     * 
     * @param packageId The ID of the package
     * @return Package object if found, null otherwise
     */
    public Package getPackageById(int packageId) {
        if (packageId <= 0) {
            return null;
        }
        return packageDAO.getPackageById(packageId);
    }
    
    /**
     * Create a new package
     * 
     * @param pkg The Package object to create
     * @return true if package was created successfully, false otherwise
     */
    public boolean createPackage(Package pkg) {
        if (pkg == null || pkg.getPackageName() == null || pkg.getPackageName().trim().isEmpty()) {
            System.out.println("Invalid package data");
            return false;
        }
        return packageDAO.addPackage(pkg);
    }
    
    /**
     * Get all active shifts
     * 
     * @return List of all active shifts
     */
    public List<Shift> getAllShifts() {
        return shiftDAO.getAllActiveShifts();
    }
    
    /**
     * Get shift by ID
     * 
     * @param shiftId The ID of the shift
     * @return Shift object if found, null otherwise
     */
    public Shift getShiftById(int shiftId) {
        if (shiftId <= 0) {
            return null;
        }
        return shiftDAO.getShiftById(shiftId);
    }
    
    /**
     * Add a new package
     * 
     * @param pkg The Package object to add
     * @return true if package was added successfully, false otherwise
     */
    public boolean addPackage(Package pkg) {
        if (pkg == null || pkg.getPackageName() == null || pkg.getPackageName().trim().isEmpty()) {
            System.out.println("Invalid package data");
            return false;
        }
        return packageDAO.addPackage(pkg);
    }
    
    /**
     * Update an existing package
     * 
     * @param pkg The Package object with updated data
     * @return true if package was updated successfully, false otherwise
     */
    public boolean updatePackage(Package pkg) {
        if (pkg == null || pkg.getPackageId() <= 0) {
            System.out.println("Invalid package data for update");
            return false;
        }
        return packageDAO.updatePackage(pkg);
    }
    
    /**
     * Delete a package by ID
     * 
     * @param packageId The ID of the package to delete
     * @return true if package was deleted successfully, false otherwise
     */
    public boolean deletePackage(int packageId) {
        if (packageId <= 0) {
            System.out.println("Invalid package ID for deletion");
            return false;
        }
        return packageDAO.deletePackage(packageId);
    }
    
    /**
     * Add a new shift
     * 
     * @param shift The Shift object to add
     * @return true if shift was added successfully, false otherwise
     */
    public boolean addShift(Shift shift) {
        if (shift == null || shift.getShiftName() == null || shift.getShiftName().trim().isEmpty()) {
            System.out.println("Invalid shift data");
            return false;
        }
        return shiftDAO.addShift(shift);
    }
    
    /**
     * Create a new shift
     * 
     * @param shift The Shift object to create
     * @return true if shift was created successfully, false otherwise
     */
    public boolean createShift(Shift shift) {
        if (shift == null || shift.getShiftName() == null || shift.getShiftName().trim().isEmpty()) {
            System.out.println("Invalid shift data");
            return false;
        }
        return shiftDAO.addShift(shift);
    }
    
    /**
     * Update an existing shift
     * 
     * @param shift The Shift object with updated data
     * @return true if shift was updated successfully, false otherwise
     */
    public boolean updateShift(Shift shift) {
        if (shift == null || shift.getShiftId() <= 0) {
            System.out.println("Invalid shift data for update");
            return false;
        }
        return shiftDAO.updateShift(shift);
    }
    
    /**
     * Delete a shift by ID
     * 
     * @param shiftId The ID of the shift to delete
     * @return true if shift was deleted successfully, false otherwise
     */
    public boolean deleteShift(int shiftId) {
        if (shiftId <= 0) {
            System.out.println("Invalid shift ID for deletion");
            return false;
        }
        return shiftDAO.deleteShift(shiftId);
    }
    
    /**
     * Get total count of packages
     * 
     * @return Total number of packages
     */
    public int getPackageCount() {
        return packageDAO.getPackageCount();
    }
    
    /**
     * Get total count of shifts
     * 
     * @return Total number of shifts
     */
    public int getShiftCount() {
        return shiftDAO.getShiftCount();
    }
    
    /**
     * Get count of active packages
     * 
     * @return Number of active packages
     */
    public int getActivePackagesCount() {
        return packageDAO.getActivePackagesCount();
    }
    
    /**
     * Get total value from all active packages
     * 
     * @return Total value from annual fees
     */
    public double getTotalPackageValue() {
        return packageDAO.getTotalPackageValue();
    }
    
    /**
     * Get count of active shifts
     * 
     * @return Number of active shifts
     */
    public int getActiveShiftsCount() {
        return shiftDAO.getActiveShiftsCount();
    }
    
    /**
     * Get count of shifts scheduled for today
     * 
     * @return Number of shifts scheduled for today
     */
    public int getTodayShiftsCount() {
        return shiftDAO.getTodayShiftsCount();
    }
}
