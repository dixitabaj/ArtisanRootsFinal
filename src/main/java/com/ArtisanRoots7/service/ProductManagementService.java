package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.ProductModel;

public class ProductManagementService {
	public void add(ProductModel product) throws Exception {
		Connection con=DbConfig.getConnection();
		String sql = "INSERT INTO product (product_id, product_name, product_price, product_quantity, total_sales, product_createdDate, product_status, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, product.getProductId());
        ps.setString(2, product.getProductName());
        ps.setFloat(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setInt(5, product.getTotalSales());
        ps.setDate(6, java.sql.Date.valueOf(product.getCreatedDate())); // Convert LocalDate to java.sql.Date
        ps.setString(7, product.getProductStatus());
        
        ps.setInt(8, product.getCategoryId());
        DashboardService.addActivity("Product " + product.getProductName() + " added", LocalDateTime.now().toString()+"\n");

        ps.executeUpdate(); // Use this for INSERT
        ps.close();
		con.close();
	}
	
	public int getCategoryId(String category) throws Exception {
	    Connection con = DbConfig.getConnection();
	    String sql = "SELECT category_id FROM category WHERE category_name = ?";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, category);
	    ResultSet rs = ps.executeQuery();

	    int categoryId = -1;
	    if (rs.next()) {
	        categoryId = rs.getInt("category_id");
	    }

	    rs.close();
	    ps.close();
	    con.close();

	    return categoryId;
	}
	public static int getProductId(String product_id) throws Exception {
	    Connection con = DbConfig.getConnection();
	    String sql = "SELECT COUNT(*) AS count FROM product WHERE product_id=?";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, product_id);
	    ResultSet rs = ps.executeQuery();

	    int count = 0;
		if (rs.next()) {
	        count = rs.getInt("count");
	    }

	    rs.close();
	    ps.close();
	    con.close();

	    return count;
	}
	public void update(ProductModel product) throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="UPDATE product SET product_name=?, product_price=?, product_quantity=?, total_sales=?, product_createdDate=?, product_status=?, category_id=? WHERE product_id=?";
		PreparedStatement ps=con.prepareStatement(sql);

		 
	        ps.setString(1, product.getProductName());
	        ps.setFloat(2, product.getPrice());
	        ps.setInt(3, product.getQuantity());
	        ps.setInt(4, product.getTotalSales());
	        ps.setDate(5, java.sql.Date.valueOf(product.getCreatedDate())); // Convert LocalDate to java.sql.Date
	        ps.setString(6, product.getProductStatus());
	        ps.setInt(7, product.getCategoryId());
	        ps.setString(8, product.getProductId());
	        int rowsAffected =ps.executeUpdate(); // Use this for INSERT
	        DashboardService.addActivity("Product 'T-shirt' updated.", LocalDateTime.now().toString());
	        ps.close();
			con.close();
	}
	
	public void delete(String productCode) throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="DELETE from product WHERE product_id=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, productCode);
		ps.executeUpdate(); // Use this for INSERT
		DashboardService.addActivity("Product 'T-shirt' deleted.", LocalDateTime.now().toString());
        
        ps.close();
		con.close();
	}
	
	public List<ProductModel> display() throws Exception {
		List<ProductModel> productList = new ArrayList<>();
		Connection con=DbConfig.getConnection();
		String sql="SELECT * FROM product";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()) {
			ProductModel product=new ProductModel(
			 rs.getString("product_id"),
	            rs.getString("product_name"),
	            rs.getFloat("product_price"),
	            rs.getInt("product_quantity"),
	            rs.getDate("product_createdDate").toLocalDate(),
	            rs.getInt("category_id"),
	            rs.getInt("total_sales"),
	            rs.getString("product_status"));
	            productList.add(product);
		}
		rs.close();
		st.close();
		con.close();
		return productList;
		
		
	}



}
