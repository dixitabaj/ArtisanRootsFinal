package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.ProductModel;

public class HomeService {

	public static List<ProductModel> displayLatestFive() throws Exception {
	    List<ProductModel> productList = new ArrayList<>();
	    Connection con = DbConfig.getConnection();

	    String sql = "SELECT * FROM product";
	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery(sql);

	    while (rs.next()) {
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
	        productList.add(product);
	    }

	    return productList;
	}

	public static List<ProductModel> displayProductByName(String searchTerm) throws Exception {
        // List to hold the search result
        List<ProductModel> products = new ArrayList<>();

        // SQL query to find products where the name contains the search term
        String sql = "SELECT * FROM product WHERE product_name LIKE CONCAT('%', ?, '%')";
        
        // Establish database connection and prepare the statement
        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Set the search term parameter
            ps.setString(1, searchTerm);
            
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
}