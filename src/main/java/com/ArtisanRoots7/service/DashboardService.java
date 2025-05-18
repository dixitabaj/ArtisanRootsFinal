package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.ProductModel;

public class DashboardService {
    /**
     * List to store recent activity descriptions.
     */
    private static List<String> recentActivities = new ArrayList<>();
    
    /**
     * Not used in current methods but declared for potential future use.
     */
    private static List<String> productDate = new ArrayList<>();
    private static List<String> productCodeActivity = new ArrayList<>();

    /**
     * Returns the count of products with quantity less than 10 (low stock).
     * 
     * @return number of products with low stock
     * @throws Exception if database connection or query fails
     */
    public static int displayLowStock() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT COUNT(*) as low_stock_count FROM product WHERE product_quantity < 10";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        int lowStockCount = 0;
        if (rs.next()) {
            lowStockCount = rs.getInt("low_stock_count");
        }
        
        // Close resources to avoid memory leaks
        rs.close();
        st.close();
        con.close();
        
        return lowStockCount;
    }

    /**
     * Returns count of products with 'active' status.
     * 
     * @return number of active products
     * @throws Exception if database connection or query fails
     */
    public static int displayActiveStock() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT product_status from product";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int activeStatusCount = 0;
        
        // Iterate through results and count products with status 'active'
        while (rs.next()) {
            String status = rs.getString("product_status");
            if (status.equalsIgnoreCase("active")) {
                activeStatusCount++;
            }
        }
        
        rs.close();
        st.close();
        con.close();
        return activeStatusCount;
    }

    /**
     * Calculates total revenue = sum of (total_sales * product_price) for all products.
     * 
     * @return total revenue from product sales
     * @throws Exception if database connection or query fails
     */
    public static int displayTotalRevenue() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT sum(total_sales*product_price) as total_revenue from product";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int totalRevenue = 0;
        if (rs.next()) {
            totalRevenue = rs.getInt(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return totalRevenue;
    }
    
    /**
     * Returns a list of top 5 products ordered by total_sales descending.
     * 
     * @return List of top 5 ProductModel objects
     * @throws Exception if database connection or query fails
     */
    public static List<ProductModel> displayTopProducts() throws Exception {
        Connection con = DbConfig.getConnection();

        List<ProductModel> productList = new ArrayList<>();
        String sql = "SELECT * from product ORDER BY total_sales DESC LIMIT 5";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        // Create ProductModel objects from result set and add to list
        while(rs.next()) {
            ProductModel product = new ProductModel(
                rs.getString("product_id"),
                rs.getString("product_name"),
                rs.getFloat("product_price"),
                rs.getInt("product_quantity"),
                rs.getDate("product_createdDate").toLocalDate(),
                rs.getInt("category_id"),
                rs.getInt("total_sales"),
                rs.getString("product_status"),
                rs.getNString("product_image")
            );
            productList.add(product);
        }
        
        rs.close();
        st.close();
        con.close();
        return productList;
    }
    
    /**
     * Returns the category name with the highest total sales.
     * 
     * @return category name with highest sales
     * @throws Exception if database connection or query fails
     */
    public static String getMostSoldCategory() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT c.category_name, SUM(r.total_sales) from product r join category c on r.category_id=c.category_id group by c.category_name order by sum(r.total_sales) desc limit 1"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String mostSoldCategory = null;
        if (rs.next()) {
            mostSoldCategory = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return mostSoldCategory;
    }
    
    /**
     * Returns the category name with the lowest total sales.
     * 
     * @return category name with lowest sales
     * @throws Exception if database connection or query fails
     */
    public static String getleastSoldCategory() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT c.category_name, SUM(r.total_sales) from product r join category c on r.category_id=c.category_id group by c.category_name order by sum(r.total_sales) limit 1"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String leastSoldCategory = null;
        if (rs.next()) {
            leastSoldCategory = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return leastSoldCategory;
    }
    
    /**
     * Returns the product name with the highest total sales (most trending).
     * 
     * @return product name that is most trending
     * @throws Exception if database connection or query fails
     */
    public static String getMostTrending() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT product_name from product ORDER BY total_sales DESC LIMIT 1"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String mostTrending = null;
        if (rs.next()) {
            mostTrending = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return mostTrending;
    }

    /**
     * Returns the product name with the lowest total sales.
     * 
     * @return product name with lowest sales
     * @throws Exception if database connection or query fails
     */
    public static String getLeastSold() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT product_name from product ORDER BY total_sales LIMIT 1"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String leastSold = null;
        if (rs.next()) {
            leastSold = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return leastSold;
    }

    /**
     * Returns the count of users where gender is 'Female'.
     * 
     * @return number of female users as a String
     * @throws Exception if database connection or query fails
     */
    public static String getFemaleUser() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT COUNT(*) from user WHERE user_gender='Female'"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String femaleUser = null;
        if (rs.next()) {
            femaleUser = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return femaleUser;
    }

    /**
     * Returns the count of users where gender is 'Male'.
     * 
     * @return number of male users as a String
     * @throws Exception if database connection or query fails
     */
    public static String getMaleUser() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT COUNT(*) from user WHERE user_gender='Male'"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String maleUser = null;
        if (rs.next()) {
            maleUser = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return maleUser;
    }

    /**
     * Returns count of users joined in the current year.
     * 
     * @return number of new users this year as a String
     * @throws Exception if database connection or query fails
     */
    public static String getNewUser() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT COUNT(*) FROM user WHERE YEAR(date_joined) = YEAR(CURRENT_DATE)"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String newUser = null;
        if (rs.next()) {
            newUser = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return newUser;
    }

    /**
     * Returns count of users with status 'Active'.
     * 
     * @return number of active users as a String
     * @throws Exception if database connection or query fails
     */
    public static String getTotalUser() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT COUNT(*) FROM user WHERE user_status='Active'"; 
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String totalUser = null;
        if (rs.next()) {
            totalUser = rs.getString(1);
        }
        
        rs.close();
        st.close();
        con.close();
        return totalUser;
    }

    /**
     * Adds a new activity to the recentActivities list, maintaining max size of 6.
     * Note: The method currently adds the activity twice due to duplicate add call.
     * 
     * @param activity description of the activity to add
     */
    public static void addActivity(String activity) {
        recentActivities.add(activity);
      
        if (recentActivities.size() >= 6) {
            recentActivities.remove(0); // Remove the oldest one when list is too big
        }
        recentActivities.add(activity);  // <-- this adds activity again (likely unintentional)
    }
    
    /**
     * Returns the current date as LocalDate.
     * 
     * @return current date
     */
    public static LocalDate displayTime() {
        return LocalDate.now();
    }

    /**
     * Returns the list of recent activities.
     * 
     * @return List of recent activity strings
     */
    public static List<String> getRecentActivities() {
        return recentActivities;
    }

    /**
     * Returns the total sum of sales across all products.
     * 
     * @return total sales count
     * @throws Exception if database connection or query fails
     */
    public static int displayTotalSales() throws Exception {
        Connection con = DbConfig.getConnection();
        String sql = "SELECT sum(total_sales) FROM product";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int totalSales = 0;
        if (rs.next()) {
            totalSales = rs.getInt(1); 
        }
        st.close();
        con.close();
        return totalSales;
    }

}
