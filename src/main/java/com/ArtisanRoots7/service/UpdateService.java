package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.UserModel;

public class UpdateService {
	public boolean updateUser(UserModel user) throws Exception {
		Connection con = null;
	    boolean result = false;
	    
	    try {
	        System.out.println("Trying to connect...");
	        con = DbConfig.getConnection();
	        con.setAutoCommit(false); // Start transaction
	        System.out.println("this is update!");

	        String query = "UPDATE user SET user_first_name = ?,  user_last_name = ?, username = ?, user_phone_number = ?,  user_gender = ? WHERE user_email = ?";

	        try (PreparedStatement ps = con.prepareStatement(query)) {
	        	
	        	 ps.setString(1, user.getFirstName());
	             ps.setString(2, user.getLastName());
	             ps.setString(3, user.getUsername());
	             ps.setString(4, user.getPhone());
	             ps.setString(5, user.getGender());  // Set the actual gender from user object
	             ps.setString(6, user.getEmail());  
	            int rowsAffected = ps.executeUpdate();
	            con.commit(); 
	            
	            System.out.println("Rows affected: " + rowsAffected);
	            result = (rowsAffected > 0);
	            
	            // Verify the update by checking if the user still exists
	            try (PreparedStatement verifyStmt = con.prepareStatement("SELECT * FROM user WHERE user_email = ?")) {
	                verifyStmt.setString(1, user.getEmail());
	                ResultSet rs = verifyStmt.executeQuery();
	                System.out.println(rs.next() ? "User updated successfully" : "Update failed");
	            }
	        }
	    } catch (SQLException e) {
	        if (con != null) con.rollback(); // Rollback in case of failure
	        System.err.println("SQL Error: " + e.getMessage());
	        throw e;
	    } finally {
	        if (con != null) con.close();  // Close the connection
	    }
	    return result;
	}
	
	public static String getImage(UserModel user) throws Exception {
		 Connection con = DbConfig.getConnection();
		 String query = "SELECT image_path from USER WHERE user_email=?";
		 PreparedStatement ps=con.prepareStatement(query);
		 ps.setString(1, user.getEmail());
		 ResultSet rs = ps.executeQuery();
		 String image = null;
		 if (rs.next()) {
			 image = rs.getString("image_path");
		 }
		 rs.close();
		 ps.close();
		 con.close();
		 return image;
	}
}
