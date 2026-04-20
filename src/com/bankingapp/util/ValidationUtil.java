package com.bankingapp.util;

import java.util.regex.Pattern;

/**
 * Input Validation Utility Class
 * 
 * This class provides methods for validating user input to prevent
 * invalid data from being processed and to enhance security.
 * 
 * It includes validators for:
 * - Email addresses
 * - Phone numbers
 * - Names
 * - Numeric values
 * - Password strength
 * 
 * @author Banking App Development Team
 * @version 1.0
 */
public class ValidationUtil {
    
    // Email regex pattern
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    // Phone regex pattern (10 digits)
    private static final String PHONE_PATTERN = "^[0-9]{10}$";
    
    /**
     * Validate if string is null or empty
     * 
     * @param value The string to validate
     * @return true if string is null or empty, false otherwise
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
    
    /**
     * Validate email address format
     * 
     * @param email The email address to validate
     * @return true if email format is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return Pattern.matches(EMAIL_PATTERN, email);
    }
    
    /**
     * Validate phone number (must be 10 digits)
     * 
     * @param phone The phone number to validate
     * @return true if phone number is valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        return Pattern.matches(PHONE_PATTERN, phone.replaceAll("\\D", ""));
    }
    
    /**
     * Validate person name (2-100 characters, only letters and spaces)
     * 
     * @param name The name to validate
     * @return true if name is valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (isEmpty(name)) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.length() < Constants.MIN_NAME_LENGTH || 
            trimmed.length() > Constants.MAX_NAME_LENGTH) {
            return false;
        }
        // Allow letters, spaces, and hyphens
        return trimmed.matches("^[a-zA-Z\\s-]+$");
    }
    
    /**
     * Validate password strength
     * 
     * Password requirements:
     * - Minimum 6 characters
     * - Maximum 20 characters
     * - Must contain at least one letter
     * - Must contain at least one digit
     * 
     * @param password The password to validate
     * @return true if password meets requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        
        if (password.length() < Constants.MIN_PASSWORD_LENGTH || 
            password.length() > Constants.MAX_PASSWORD_LENGTH) {
            return false;
        }
        
        // Check for at least one letter and one digit
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        
        return hasLetter && hasDigit;
    }
    
    /**
     * Validate numeric value
     * 
     * @param value The string to validate
     * @return true if string can be parsed as a number, false otherwise
     */
    public static boolean isNumeric(String value) {
        if (isEmpty(value)) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate positive integer
     * 
     * @param value The string to validate
     * @return true if string is a positive integer, false otherwise
     */
    public static boolean isPositiveInteger(String value) {
        if (isEmpty(value)) {
            return false;
        }
        try {
            int num = Integer.parseInt(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate positive decimal number
     * 
     * @param value The string to validate
     * @return true if string is a positive decimal number, false otherwise
     */
    public static boolean isPositiveDecimal(String value) {
        if (isEmpty(value)) {
            return false;
        }
        try {
            double num = Double.parseDouble(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Sanitize string input to prevent injection attacks
     * 
     * This method removes potentially dangerous characters from input.
     * It does NOT provide complete SQL injection prevention - use
     * PreparedStatements for that.
     * 
     * @param input The input string to sanitize
     * @return Sanitized string
     */
    public static String sanitizeInput(String input) {
        if (isEmpty(input)) {
            return "";
        }
        // Remove any HTML/script tags and dangerous characters
        return input.replaceAll("<.*?>", "")
                   .replaceAll("[<>\"'%;()&+]", "");
    }
}
