package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.UserModel;

public class UpdateService {

    /**
     * Updates the user details in the database for the given user model.
     * 
     * @param user The UserModel object containing updated user information.
     * @return true if the user was successfully updated, false otherwise.
     * @throws Exception If a database error occurs during update.
     */
    public boolean updateUser(UserModel user) throws Exception {
        Connection con = null;
        boolean result = false;

        try {
            System.out.println("Trying to connect...");
            
            // Establish a connection to the database
            con = DbConfig.getConnection();
            
            // Disable auto-commit for transaction management
            con.setAutoCommit(false); 
            System.out.println("this is update!");

            // SQL query to update user details by matching email
            String query = "UPDATE user SET user_first_name = ?, user_last_name = ?, username = ?, user_phone_number = ?, user_gender = ? WHERE user_email = ?";

            // Use try-with-resources to automatically close PreparedStatement
            try (PreparedStatement ps = con.prepareStatement(query)) {
                // Set parameters from UserModel object
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getUsername());
                ps.setString(4, user.getPhone());
                ps.setString(5, user.getGender());
                ps.setString(6, user.getEmail());

                // Execute the update
                int rowsAffected = ps.executeUpdate();

                // Commit transaction explicitly
                con.commit();

                System.out.println("Rows affected: " + rowsAffected);
                result = (rowsAffected > 0);

                // Verify the update by querying the user back
                try (PreparedStatement verifyStmt = con.prepareStatement("SELECT * FROM user WHERE user_email = ?")) {
                    verifyStmt.setString(1, user.getEmail());
                    ResultSet rs = verifyStmt.executeQuery();
                    System.out.println(rs.next() ? "User updated successfully" : "Update failed");
                    rs.close();
                }
            }
        } catch (SQLException e) {
            // Rollback transaction in case of error
            if (con != null) con.rollback();
            System.err.println("SQL Error: " + e.getMessage());
            throw e;  // Rethrow exception to notify caller
        } finally {
            // Always close connection to prevent leaks
            if (con != null) con.close();
        }
        return result;
    }

    /**
     * Retrieves the image path of the user identified by the given UserModel.
     * 
     * @param user The UserModel object containing the user's email.
     * @return The image path as a String, or null if not found.
     * @throws Exception If a database error occurs.
     */
    public static String getImage(UserModel user) throws Exception {
        // Establish connection to database
        Connection con = DbConfig.getConnection();

        // SQL query to fetch image_path by user email
        String query = "SELECT image_path FROM user WHERE user_email = ?";
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, user.getEmail());

        ResultSet rs = ps.executeQuery();

        String image = null;
        if (rs.next()) {
            // Get image_path from result set
            image = rs.getString("image_path");
        }

        // Close resources in correct order
        rs.close();
        ps.close();
        con.close();

        return image;
    }
}
