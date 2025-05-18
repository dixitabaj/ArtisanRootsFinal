package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.UserModel;

public class LoginService {

    /**
     * Inserts a new user into the database.
     * 
     * @param user The UserModel object containing user details to insert into the database.
     * @return true if insertion was successful, false otherwise.
     * @throws Exception if any database error occurs during insertion.
     */
    public boolean insert(UserModel user) throws Exception {
        Connection con = null;
        boolean result = false;

        try {
            System.out.println("Trying to connect...");
            con = DbConfig.getConnection();
            con.setAutoCommit(false); // Start transaction
            System.out.println("Connected!");

            // SQL insert query with placeholders for user data
            String query = "INSERT INTO user(user_first_name, user_last_name, username, user_phone_number, user_dob, user_email, user_roles, user_password, user_gender, date_joined, user_status, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                // Set user data parameters into query
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getUsername());
                ps.setString(4, user.getPhone());
                ps.setDate(5, Date.valueOf(user.getDob())); // Convert LocalDate to SQL Date
                ps.setString(6, user.getEmail());
                ps.setString(7, "customer"); // Default role, can be changed if needed
                ps.setString(8, user.getPassword());
                ps.setString(9, user.getGender());
                ps.setDate(10, java.sql.Date.valueOf(user.getJoinedDate())); // Joined date
                ps.setString(11, user.getStatus());
                ps.setString(12, user.getUserImage());

                // Execute the insert statement
                int rowsAffected = ps.executeUpdate();
                con.commit(); // Commit the transaction

                System.out.println("Rows affected: " + rowsAffected);
                result = (rowsAffected > 0);

                // Verify insertion by querying with email (note: uses username here, likely a mistake)
                try (PreparedStatement verifyStmt = con.prepareStatement("SELECT * FROM user WHERE  user_email= ?")) {
                    verifyStmt.setString(1, user.getUsername()); // <-- Consider changing to user.getEmail()
                    ResultSet rs = verifyStmt.executeQuery();
                    System.out.println(rs.next() ? "User verified in DB" : "Verification failed");
                }
            }
        } catch (SQLException e) {
            if (con != null)
                con.rollback(); // Rollback transaction on error
            System.err.println("SQL Error: " + e.getMessage());
            throw e;
        } finally {
            if (con != null)
                con.close(); // Close connection
        }
        return result;
    }

    /**
     * Checks if a user with the given email exists in the database.
     * 
     * @param email The email address to check.
     * @return true if the email exists in the database, false otherwise.
     */
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
            stmt.setString(1, email); // Set the email parameter

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Email exists in the database
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves the encrypted password for a user by their email.
     * 
     * @param email The email of the user whose password is to be retrieved.
     * @return The encrypted password string if found, null otherwise.
     */
    public String getEncryptedPassword(String email) {
        Connection con = null;
        String result = null; // Holds the password fetched from the database
        ResultSet rs = null;

        try {
            System.out.println("Trying to connect...");
            con = DbConfig.getConnection();
            System.out.println("Connected!");

            String query = "SELECT user_password FROM user WHERE user_email = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email); // Set email parameter

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Retrieve the password from the result set
                result = rs.getString("user_password");
                System.out.println("the password " + result);
            } else {
                System.out.println("No user found with this email.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close ResultSet and Connection to avoid resource leaks
            try {
                if (rs != null)
                    rs.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result; // Return the password
    }

    /**
     * Retrieves the user details as a UserModel by their email.
     * 
     * @param email The email of the user to fetch.
     * @return A UserModel object containing user details, or null if no user found.
     */
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
                // Populate UserModel with data from ResultSet
                user = new UserModel();
                user.setFirstName(rs.getString("user_first_name"));
                user.setLastName(rs.getString("user_last_name"));
                user.setUsername(rs.getString("username"));
                user.setPhone(rs.getString("user_phone_number"));
                user.setDob(rs.getDate("user_dob").toLocalDate()); // Convert SQL Date to LocalDate
                user.setEmail(rs.getString("user_email"));
                user.setRole(rs.getString("user_roles"));
                user.setPassword(rs.getString("user_password")); // Optional: store password if needed
                user.setGender(rs.getString("user_gender"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user; // Return the UserModel object or null
    }

}
