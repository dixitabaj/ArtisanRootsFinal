package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;

public class PortfolioService {
    
    /**
     * Updates the password of a user identified by their email.
     * 
     * @param email The email of the user whose password is to be updated.
     * @param password The new password to set for the user.
     * @return true if the password was successfully updated, false otherwise.
     * @throws SQLException If a database access error occurs or the transaction fails.
     */
    public static boolean updatePassword(String email, String password) {
        Connection con = null;
        boolean result = false;
        
        try {
            System.out.println("Trying to connect...");
            
            // Establish connection to the database
            con = DbConfig.getConnection();
            
            // Disable auto-commit for explicit transaction control
            con.setAutoCommit(false);
            System.out.println("Connected!");

            // SQL query to update the user's password based on email
            String query = "UPDATE user SET user_password = ? WHERE user_email = ?";

            // Use try-with-resources for PreparedStatement to ensure it closes automatically
            try (PreparedStatement ps = con.prepareStatement(query)) {
                // Set the new password parameter in the query
                ps.setString(1, password); 
                
                // Set the user's email parameter in the query
                ps.setString(2, email);   

                // Execute the update query
                int rowsAffected = ps.executeUpdate();
                
                // Commit the transaction explicitly
                con.commit(); 
                
                System.out.println("Rows affected: " + rowsAffected);
                
                // If at least one row is updated, set result to true
                result = (rowsAffected > 0);
            }
        } catch (Exception ex) {
            try {
                // Rollback the transaction if any exception occurs to maintain DB integrity
                if (con != null) con.rollback();
            } catch (SQLException e) {
                // Print stack trace if rollback fails
                e.printStackTrace();
            }
            // Print the original exception stack trace for debugging
            ex.printStackTrace();
        } finally {
            try {
                // Close the connection resource to avoid leaks
                if (con != null) con.close();
            } catch (SQLException e) {
                // Print stack trace if connection closing fails
                e.printStackTrace();
            }
        }

        // Return whether the password update was successful
        return result;
    }
    
}
