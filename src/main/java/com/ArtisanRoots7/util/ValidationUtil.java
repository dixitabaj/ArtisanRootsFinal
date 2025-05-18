package com.ArtisanRoots7.util;

import java.time.LocalDate;

/**
 * Utility class for various validation methods.
 * Provides static methods to validate strings, dates, numbers, and passwords.
 */
public class ValidationUtil {

    /**
     * Checks if the given string is null or empty.
     *
     * @param value the string to check
     * @return true if the value is null or empty, false otherwise
     */
    public static boolean isNull(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Validates if the given string is a valid first name.
     * Only alphabets (a-z, A-Z) allowed.
     *
     * @param value the string to validate as first name
     * @return true if valid first name, false otherwise
     */
    public static boolean isValidFirstName(String value) {
        return !isNull(value) && value.trim().matches("^[a-zA-Z]+$");
    }

    /**
     * Validates if the given string is a valid username.
     * Only alphanumeric characters allowed.
     *
     * @param value the string to validate as username
     * @return true if valid username, false otherwise
     */
    public static boolean isValidUsername(String value) {
        return !isNull(value) && value.trim().matches("^[a-zA-Z0-9]+$");
    }

    /**
     * Validates if the given string is a valid email address.
     *
     * @param value the string to validate as email
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String value) {
        return !isNull(value) && value.trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Validates if the given string is a valid phone number.
     * Assumes valid numbers start with 9 and have 10 digits total.
     *
     * @param number the string to validate as phone number
     * @return true if valid phone number, false otherwise
     */
    public static boolean isValidPhoneNumber(String number) {
        return !isNull(number) && number.trim().matches("^[9][0-9]{9}$");
    }

    /**
     * Validates if the given string meets password complexity requirements.
     * Must include at least one lowercase letter, uppercase letter, digit, and special character.
     *
     * @param password the password string to validate
     * @return true if password meets complexity requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return !isNull(password) && password.trim().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$");
    }

    /**
     * Validates if the given date of birth is between Jan 1, 2000 and today.
     *
     * @param dob the date of birth to validate
     * @return true if date is valid, false otherwise
     */
    public static boolean isValidDate(LocalDate dob) {
        LocalDate minDate = LocalDate.of(2000, 1, 1);
        return !dob.isAfter(LocalDate.now()) && dob.compareTo(minDate) > 0;
    }

    /**
     * Checks if the string contains only numeric characters.
     *
     * @param value the string to check
     * @return true if numeric, false otherwise
     */
    public static boolean isNumeric(String value) {
        return value.trim().matches("[0-9]+");
    }

    /**
     * Checks if the string contains only alphabets and spaces.
     *
     * @param value the string to check
     * @return true if alphabetic with spaces, false otherwise
     */
    public static boolean isAlphabetic(String value) {
        return value.trim().matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Checks if the numeric value represented by the string is negative.
     *
     * @param value the string representing a number
     * @return true if the number is negative, false otherwise
     * @throws NumberFormatException if the string is not a valid integer
     */
    public static boolean isNegative(String value) {
        return Integer.parseInt(value) < 0;
    }

    /**
     * Checks if two password strings match exactly.
     *
     * @param password       the original password
     * @param retypePassword the re-typed password to compare
     * @return true if passwords match, false otherwise
     */
    public static boolean matchesPassword(String password, String retypePassword) {
        return password.equals(retypePassword);
    }

    /**
     * Checks if the age calculated from the date of birth is above 12 years.
     *
     * @param dob the date of birth
     * @return true if age is greater than 12, false otherwise
     */
    public static boolean isAbove12(LocalDate dob) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - dob.getYear();
        return age > 12;
    }

    /**
     * Validates if the age calculated from the date of birth is within a valid range (0 to 113).
     *
     * @param dob the date of birth
     * @return true if age is valid, false otherwise
     */
    public static boolean isValidAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - dob.getYear();
        return age >= 0 && age <= 113;
    }
}
