package com.bankingapp.model;

import java.util.Date;

/**
 * Employee Model Class
 * 
 * Represents an employee in the banking application.
 * Stores employee personal information, job details, and salary information.
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class Employee {
    
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String employeeCode;
    private String designation;
    private String department;
    private int shiftId;
    private double basicSalary;
    private String hireDate;
    private String employmentStatus;
    private String bankAccountNumber;
    private Date registrationDate;
    private Date modifiedDate;
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Default constructor
     */
    public Employee() {
    }
    
    /**
     * Constructor with basic information
     */
    public Employee(String firstName, String lastName, String employeeCode, 
                    String designation, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeCode = employeeCode;
        this.designation = designation;
        this.department = department;
    }
    
    /**
     * Full constructor
     */
    public Employee(int employeeId, String firstName, String lastName, String email, 
                    String phone, String dateOfBirth, String gender, String address, 
                    String city, String state, String postalCode, String country, 
                    String employeeCode, String designation, String department, 
                    int shiftId, double basicSalary, String hireDate, 
                    String employmentStatus, String bankAccountNumber, 
                    Date registrationDate, Date modifiedDate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.employeeCode = employeeCode;
        this.designation = designation;
        this.department = department;
        this.shiftId = shiftId;
        this.basicSalary = basicSalary;
        this.hireDate = hireDate;
        this.employmentStatus = employmentStatus;
        this.bankAccountNumber = bankAccountNumber;
        this.registrationDate = registrationDate;
        this.modifiedDate = modifiedDate;
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getEmployeeCode() {
        return employeeCode;
    }
    
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public int getShiftId() {
        return shiftId;
    }
    
    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }
    
    public double getBasicSalary() {
        return basicSalary;
    }
    
    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }
    
    public String getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }
    
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public Date getModifiedDate() {
        return modifiedDate;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * Get full name of employee
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                ", designation='" + designation + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                '}';
    }
}
