package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;

public class RegistrationService {
	public static boolean isUsernameExists(String username) throws Exception {
	    boolean exists = false;
	    String query = "SELECT COUNT(*) FROM user WHERE username = ?";
	    
	    try (Connection conn = DbConfig.getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, username);  // Set the username parameter
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            exists = rs.getInt(1) > 0;  // If count > 0, username exists
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}
	public static boolean emailExists(String email) throws Exception {
	    boolean exists = false;
	    String query = "SELECT COUNT(*) FROM user WHERE user_email = ?";
	    
	    try (Connection conn = DbConfig.getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, email);  // Set the username parameter
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            exists = rs.getInt(1) > 0;  // If count > 0, username exists
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}
	public static boolean phoneExists(String phoneNumber) throws Exception {
	    boolean exists = false;
	    String query = "SELECT COUNT(*) FROM user WHERE user_phone_number = ?";
	    
	    try (Connection conn = DbConfig.getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, phoneNumber);  // Set the username parameter
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            exists = rs.getInt(1) > 0;  // If count > 0, username exists
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}
}
