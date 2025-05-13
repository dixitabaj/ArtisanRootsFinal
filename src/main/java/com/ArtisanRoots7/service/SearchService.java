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
    public static List<ProductModel> displayProductByName(String searchTerm) throws Exception {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE product_name LIKE CONCAT('%', ?, '%')";
        
        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
        	 ps.setString(1, searchTerm);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getFloat("product_price"),
                        rs.getInt("product_quantity"),
                        rs.getDate("product_createdDate").toLocalDate(),
                        rs.getInt("category_id"),
                        rs.getInt("total_sales"),
                        rs.getString("product_status")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }
    public static List<UserModel> displayUserByName(String searchTerm) throws Exception {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_id=? OR user_first_name LIKE LOWER(CONCAT('%', ?, '%')) OR user_last_name LIKE LOWER(CONCAT('%', ?, '%')) ";
        
        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
        	ps.setString(1, searchTerm);
        	ps.setString(2, searchTerm);
        	ps.setString(3, searchTerm);

            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    users.add(user);
                }
            }
        }
        return users;
    }
}