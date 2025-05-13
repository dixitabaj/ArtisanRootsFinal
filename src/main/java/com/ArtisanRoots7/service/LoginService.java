package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.UserModel;

public class LoginService {
	public boolean insert(UserModel user) throws Exception{
		    Connection con = null;
		    boolean result = false;
		    
		    try {
		        System.out.println("Trying to connect...");
		        con = DbConfig.getConnection();
		        con.setAutoCommit(false); // Start transaction
		        System.out.println("Connected!");

		        String query = "INSERT INTO user(user_first_name, user_last_name, username, user_phone_number, user_dob, user_email, user_roles, user_password, user_gender, date_joined, user_status, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        
		        try (PreparedStatement ps = con.prepareStatement(query)) {
		            ps.setString(1, user.getFirstName());
		            ps.setString(2, user.getLastName());
		            ps.setString(3, user.getUsername());
		            ps.setString(4, user.getPhone());
		            ps.setDate(5,  Date.valueOf(user.getDob()));
		            ps.setString(6, user.getEmail());
		            ps.setString(7, "customer"); // Default role or use user.getRole()
		            ps.setString(8, user.getPassword());
		            ps.setString(9, user.getGender());
		            ps.setDate(10,  java.sql.Date.valueOf(user.getJoinedDate()));
		            ps.setString(11, user.getStatus());
		            ps.setString(12, user.getUserImage());
		            int rowsAffected = ps.executeUpdate();
		            con.commit(); // Explicit commit
		            
		            System.out.println("Rows affected: " + rowsAffected);
		            result = (rowsAffected > 0);
		            
		            // Verify insertion
		            try (PreparedStatement verifyStmt = con.prepareStatement(
		                    "SELECT * FROM user WHERE  user_email= ?")) {
		                verifyStmt.setString(1, user.getUsername());
		                ResultSet rs = verifyStmt.executeQuery();
		                System.out.println(rs.next() ? "User verified in DB" : "Verification failed");
		            }
		        }
		    } catch (SQLException e) {
		        if (con != null) con.rollback();
		        System.err.println("SQL Error: " + e.getMessage());
		        throw e;
		    } finally {
		        if (con != null) con.close();
		    }
		    return result;
		
	
	}
	
	public boolean check(String email) {
		 Connection con = null;
		    boolean result = false;
		    ResultSet rs = null;
		    try {
		        System.out.println("Trying to connect...");
		        con = DbConfig.getConnection();
		        con.setAutoCommit(false); // Start transaction
		        System.out.println("Connected!");
		        String query = "SELECT user_email FROM user WHERE user_email = ?";
		        PreparedStatement stmt = con.prepareStatement(query);
		        stmt.setString(1, email); // Set the email in the query

		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            // Email exists
		        	
		            result = true;
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } 

		    return result;
		    }
	
	public String getEncryptedPassword(String email) {
	    Connection con = null;
	    String result = null;  // This will hold the password fetched from the database
	    ResultSet rs = null;
	    
	    try {
	        System.out.println("Trying to connect...");
	        con = DbConfig.getConnection();
	        System.out.println("Connected!");

	        String query = "SELECT user_password FROM user WHERE user_email = ?";
	        PreparedStatement stmt = con.prepareStatement(query);
	        stmt.setString(1, email); // Set the email in the query

	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            // Retrieve the password from the ResultSet
	            result = rs.getString("user_password");  // The actual password stored in the database
	            System.out.println("the password "+result);
	        } else {
	            System.out.println("No user found with this email.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return result;  // Return the actual password from the database
	}
	public UserModel getUserByEmail(String email) {
	    Connection con = null;
	    ResultSet rs = null;
	    UserModel user = null;

	    try {
	        con = DbConfig.getConnection();
	        String query = "SELECT * FROM user WHERE user_email = ?";
	        PreparedStatement stmt = con.prepareStatement(query);
	        stmt.setString(1, email);

	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            user = new UserModel();
	            user.setFirstName(rs.getString("user_first_name"));
	            user.setLastName(rs.getString("user_last_name"));
	            user.setUsername(rs.getString("username"));
	            user.setPhone(rs.getString("user_phone_number"));
	            user.setDob(rs.getDate("user_dob").toLocalDate()); // adjust if dob is stored differently
	            user.setEmail(rs.getString("user_email"));
	            user.setRole(rs.getString("user_roles"));
	            user.setPassword(rs.getString("user_password")); // optional, depends if you want this
	            user.setGender(rs.getString("user_gender"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return user;
	}

	
	}

