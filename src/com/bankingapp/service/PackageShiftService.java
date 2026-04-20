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
}
