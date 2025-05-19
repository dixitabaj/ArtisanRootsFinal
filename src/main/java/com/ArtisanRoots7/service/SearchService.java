package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.model.UserModel;

public class SearchService {

    /**
     * Searches for products with names that contain the given search term.
     *
     * @param searchTerm The keyword to search in product names.
     * @return A list of ProductModel objects that match the search criteria.
     * @throws Exception If a database access error occurs.
     */
    public static List<ProductModel> displayProductByName(String searchTerm) throws Exception {
        // List to hold the search result
        List<ProductModel> products = new ArrayList<>();

        // SQL query to find products where the name contains the search term
        String sql = "SELECT * FROM product WHERE product_name LIKE  LOWER(CONCAT('%', ?, '%')) OR product_id LIKE CONCAT('%', ?, '%')";
        
        // Establish database connection and prepare the statement
        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Set the search term parameter
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            
            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Iterate through the result set
                while (rs.next()) {
                    // Create ProductModel object from result set
                    ProductModel product = new ProductModel(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getFloat("product_price"),
                        rs.getInt("product_quantity"),
                        rs.getDate("product_createdDate").toLocalDate(),
                        rs.getInt("category_id"),
                        rs.getInt("total_sales"),
                        rs.getString("product_status"), 
                        rs.getString("product_image")
                    );

                    // Add the product to the list
                    products.add(product);
                }
            }
        }

        // Return the list of products matching the search
        return products;
    }

    /**
     * Searches for users by ID, first name, or last name containing the search term.
     *
     * @param searchTerm The keyword to search in user fields.
     * @return A list of UserModel objects that match the search criteria.
     * @throws Exception If a database access error occurs.
     */
    public static List<UserModel> displayUserByName(String searchTerm) throws Exception {
        // List to hold the search result
        List<UserModel> users = new ArrayList<>();

        // SQL query to find users by ID, first name, or last name
        String sql = "SELECT * FROM user WHERE user_id=? OR user_first_name LIKE LOWER(CONCAT('%', ?, '%')) OR user_last_name LIKE LOWER(CONCAT('%', ?, '%'))";

        // Establish database connection and prepare the statement
        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Set parameters for ID, first name, and last name
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            ps.setString(3, searchTerm);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                // Iterate through the result set
                while (rs.next()) {
                    // Create UserModel object from result set
                    UserModel user = new UserModel(
                        rs.getInt("user_id"),
                        rs.getString("user_first_name"),
                        rs.getString("user_last_name"),
                        rs.getString("username"),
                        rs.getString("user_phone_number"),
                        rs.getDate("user_dob").toLocalDate(),
                        rs.getString("user_email"),
                        rs.getString("user_roles"),
                        rs.getString("user_password"),
                        rs.getString("user_gender"),
                        rs.getDate("date_joined").toLocalDate(),
                        rs.getString("user_status"),
                        rs.getNString("image_path")
                    );

                    // Add the user to the list
                    users.add(user);
                }
            }
        }

        // Return the list of users matching the search
        return users;
    }
}
