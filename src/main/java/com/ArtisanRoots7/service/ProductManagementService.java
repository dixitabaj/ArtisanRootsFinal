package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.ProductModel;

public class ProductManagementService {

    /**
     * Adds a new product to the database.
     * 
     * @param product The ProductModel object containing product details.
     * @throws Exception If any database error occurs.
     */
    public void add(ProductModel product) throws Exception {
        // Establish connection to the database
        Connection con = DbConfig.getConnection();

        // SQL query to insert new product
        String sql = "INSERT INTO product (product_id, product_name, product_price, product_quantity, total_sales, product_createdDate, product_status, category_id, product_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        // Set parameters for the prepared statement from product object
        ps.setString(1, product.getProductId());
        ps.setString(2, product.getProductName());
        ps.setFloat(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setInt(5, product.getTotalSales());

        // Convert LocalDate to java.sql.Date before setting
        ps.setDate(6, java.sql.Date.valueOf(product.getCreatedDate()));

        ps.setString(7, product.getProductStatus());
        ps.setInt(8, product.getCategoryId());
        ps.setString(9, product.getImagePath());

        // Log activity with current timestamp
        String activityMessage = "Product " + product.getProductId() + " added on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DashboardService.addActivity(activityMessage);

        // Execute the insert statement
        ps.executeUpdate();

        // Close resources to prevent leaks
        ps.close();
        con.close();
    }

    /**
     * Retrieves the category ID from the category name.
     * 
     * @param category The category name.
     * @return The category ID if found, otherwise -1.
     * @throws Exception If any database error occurs.
     */
    public int getCategoryId(String category) throws Exception {
        Connection con = DbConfig.getConnection();

        // Query to fetch category_id based on category_name
        String sql = "SELECT category_id FROM category WHERE category_name = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, category);
        ResultSet rs = ps.executeQuery();

        int categoryId = -1; // Default if not found

        if (rs.next()) {
            categoryId = rs.getInt("category_id");
        }

        // Close resources
        rs.close();
        ps.close();
        con.close();

        return categoryId;
    }

    /**
     * Checks the count of products with the given product_id.
     * 
     * @param product_id The product ID to check.
     * @return The count of products with that ID.
     * @throws Exception If any database error occurs.
     */
    public static int getProductId(String product_id) throws Exception {
        Connection con = DbConfig.getConnection();

        // Query to count rows matching the product_id
        String sql = "SELECT COUNT(*) AS count FROM product WHERE product_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, product_id);

        ResultSet rs = ps.executeQuery();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt("count");
        }

        // Close resources
        rs.close();
        ps.close();
        con.close();

        return count;
    }

    /**
     * Updates the details of an existing product.
     * 
     * @param product The ProductModel object containing updated details.
     * @throws Exception If any database error occurs.
     */
    public void update(ProductModel product) throws Exception {
        Connection con = DbConfig.getConnection();

        // SQL query to update product details by product_id
        String sql = "UPDATE product SET product_name=?, product_price=?, product_quantity=?, total_sales=?, product_createdDate=?, product_status=?, category_id=? WHERE product_id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        // Set updated values
        ps.setString(1, product.getProductName());
        ps.setFloat(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.setInt(4, product.getTotalSales());

        // Convert LocalDate to java.sql.Date
        ps.setDate(5, java.sql.Date.valueOf(product.getCreatedDate()));

        ps.setString(6, product.getProductStatus());
        ps.setInt(7, product.getCategoryId());
        ps.setString(8, product.getProductId());

        // Execute update
        int rowsAffected = ps.executeUpdate();

        // Log update activity
        String activityMessage = "Product " + product.getProductId() + " updated on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DashboardService.addActivity(activityMessage);

        // Close resources
        ps.close();
        con.close();
    }

    /**
     * Deletes a product from the database using the product ID.
     * 
     * @param productCode The product ID of the product to delete.
     * @throws Exception If any database error occurs.
     */
    public void delete(String productCode) throws Exception {
        Connection con = DbConfig.getConnection();

        // SQL query to delete product by product_id
        String sql = "DELETE FROM product WHERE product_id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, productCode);

        // Execute delete
        ps.executeUpdate();

        // Log delete activity
        String activityMessage = "Product " + productCode + " deleted on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DashboardService.addActivity(activityMessage);

        // Close resources
        ps.close();
        con.close();
    }

    /**
     * Retrieves a list of up to 10 products from the database.
     * 
     * @return List of ProductModel objects.
     * @throws Exception If any database error occurs.
     */
    public List<ProductModel> display() throws Exception {
        List<ProductModel> productList = new ArrayList<>();

        // Establish connection
        Connection con = DbConfig.getConnection();

        // SQL query to select all products limited to 10 rows
        String sql = "SELECT * FROM product  ORDER BY product_createdDate LIMIT 10";
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(sql);

        // Iterate through the result set and build product objects
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

        // Close resources
        rs.close();
        st.close();
        con.close();

        return productList;
    }
}
