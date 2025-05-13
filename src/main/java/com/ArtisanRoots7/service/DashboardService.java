package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.model.UserModel;

public class DashboardService {
	private static List<String> recentActivities = new ArrayList<>();
	private static List<String> productDate=new ArrayList<>();
	private static List<String> productCodeActivity=new ArrayList<>();
	public static int displayLowStock() throws Exception {
	    Connection con = DbConfig.getConnection();
	    String sql = "SELECT COUNT(*) as low_stock_count FROM product WHERE product_quantity < 10";
	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery(sql);
	    
	    int lowStockCount = 0;
	    if (rs.next()) {
	        lowStockCount = rs.getInt("low_stock_count");
	    }
	    
	    rs.close();
	    st.close();
	    con.close();
	    
	    return lowStockCount;
	}
	public static int displayActiveStock() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT product_status from product";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		int activeStatusCount=0;
		while (rs.next()) {
			String status=rs.getString("product_status");
				if (status.equalsIgnoreCase("active")) {
					activeStatusCount++;
				}
			}
		
		rs.close();
		st.close();
		con.close();
		return activeStatusCount;
	}

	public static int displayTotalRevenue() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT sum(total_sales*product_price) as total_revenue from product";
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		int totalRevenue=0;
		if (rs.next()) {
			 totalRevenue=rs.getInt(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return totalRevenue;
	}
	
	public static List<ProductModel> displayTopProducts() throws Exception {
		Connection con=DbConfig.getConnection();

		List<ProductModel> productList = new ArrayList<>();
		String sql="SELECT * from product ORDER BY total_sales DESC LIMIT 5";
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
	
	public static String getMostSoldCategory() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT c.category_name, SUM(r.total_sales) from product r join category c on r.category_id=c.category_id group by c.category_name order by sum(r.total_sales) desc limit 1"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String mostSoldCategory = null;
		if (rs.next()) {
			mostSoldCategory=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return mostSoldCategory;
	}
	
	public static String getleastSoldCategory() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT c.category_name, SUM(r.total_sales) from product r join category c on r.category_id=c.category_id group by c.category_name order by sum(r.total_sales)  limit 1"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String leastSoldCategory = null;
		if (rs.next()) {
			leastSoldCategory=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return leastSoldCategory;
	}
	
	public static String getMostTrending() throws Exception {
			Connection con=DbConfig.getConnection();
			String sql="SELECT product_name from product ORDER BY total_sales DESC LIMIT 1"; 
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			String mostTrending = null;
			if (rs.next()) {
				mostTrending=rs.getString(1);
				}
			
			rs.close();
			st.close();
			con.close();
			return mostTrending;
		
	}
	public static String getLeastSold() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT product_name from product ORDER BY total_sales LIMIT 1"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String leastSold = null;
		if (rs.next()) {
			leastSold=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return leastSold;
	
}
	public static String getFemaleUser() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT COUNT(*) from user WHERE user_gender='Female'"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String femaleUser = null;
		if (rs.next()) {
			femaleUser=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return femaleUser;
	
}
	public static String getMaleUser() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT COUNT(*) from user WHERE user_gender='Male'"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String maleUser = null;
		if (rs.next()) {
			maleUser=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return maleUser;
	
}
	public static String getNewUser() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT COUNT(*) FROM user WHERE YEAR(date_joined) = YEAR(CURRENT_DATE)"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String leastSold = null;
		if (rs.next()) {
			leastSold=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return leastSold;
	
}
	public static String getTotalUser() throws Exception {
		Connection con=DbConfig.getConnection();
		String sql="SELECT COUNT(*) FROM user WHERE user_status='Active'"; 
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		String totalUser = null;
		if (rs.next()) {
			totalUser=rs.getString(1);
			}
		
		rs.close();
		st.close();
		con.close();
		return totalUser;
	
}
	
	public static void addActivity(String activity, String time) {
        recentActivities.add(activity+ " at " + time);
      

        // Limit the size of the activity list (e.g., keep only the 10 most recent activities)
        if (recentActivities.size() > 10) {
            recentActivities.remove(0);  // Removes the oldest activity
        }
    }
	
	public static LocalDate displayTime() {
		return LocalDate.now();
	}
	

	public static List<String> getRecentActivities() {
        return recentActivities;
    }
    public static int displayTotalSales() throws Exception {
    	Connection con=DbConfig.getConnection();
    	String sql="SELECT sum(total_sales) FROM product";
    	Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(sql);
    	int totalSales=0;
    	if (rs.next()) {
    		totalSales = rs.getInt(1); 
    	}
    	st.close();
    	con.close();
    	return totalSales;
    }


}

