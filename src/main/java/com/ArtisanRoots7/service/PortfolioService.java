package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;

public class PortfolioService {
    public static boolean updatePassword(String email, String password) {
        Connection con = null;
        boolean result = false;
        
        try {
            System.out.println("Trying to connect...");
            con = DbConfig.getConnection();
            con.setAutoCommit(false); // Start transaction
            System.out.println("Connected!");

            String query = "UPDATE user SET user_password = ? WHERE user_email = ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, password); 
                ps.setString(2, email);   

                int rowsAffected = ps.executeUpdate();
                con.commit(); 
                
                System.out.println("Rows affected: " + rowsAffected);
                result = (rowsAffected > 0);
            }
        } catch (Exception ex) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    
}
