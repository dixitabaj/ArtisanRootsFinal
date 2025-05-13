package com.ArtisanRoots7.util;

import java.sql.Date;
import java.time.LocalDate;

public class ValidationUtil {


	   public static boolean isNull(String value) {
		   return value==null || value.isEmpty();
	    }
	   


	   public static boolean isValidFirstName(String value) {
		   return !isNull(value) && value.trim().matches("^[a-zA-Z]+$");
	   }
	   
	   public static boolean isValidUsername(String value) {
		   return !isNull(value) &&value.trim().matches("^[a-zA-Z0-9]+$");
	   }

	   public static boolean isValidEmail(String value) {
		    return !isNull(value) &&value.trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
		}

	   public static boolean isValidPhoneNumber(String number) {
		   return !isNull(number) &&number.trim().matches("^[9][0-9]{9}$");
	   }
	   public static boolean isValidPassword(String password) {
		   return !isNull(password) &&password.trim().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$");
 }
	   
	   public static boolean isValidDate(LocalDate dob) {
		   LocalDate minDate = LocalDate.of(2000, 1, 1);
		   return !dob.isAfter(LocalDate.now()) && dob.compareTo(minDate) > 0 ;
	   }
	   public static boolean isNumeric(String value) {
		   return value.trim().matches("[0-9]+");
	   }
	   
	   public static boolean isAlphabetic(String value) {
		   return value.trim().matches("^[a-zA-Z\\s]+$");
	   }
	   
	   public static boolean isNegative(String value) {
		   return Integer.parseInt(value)<0;
	   }
	   public static boolean matchesPassword(String password, String retypePassword) {
		   return password.equals(retypePassword);
	   }
	   public static boolean isValidAge(LocalDate dob) {

		    
		   
		   LocalDate today = LocalDate.now();
		    int age = today.getYear() - dob.getYear();
		    if (dob.plusYears(age).isAfter(today)) {
		        age--;
		    }

		    return age >= 13;
	   }
	}

