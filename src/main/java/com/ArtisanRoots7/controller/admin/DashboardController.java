package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.service.DashboardService;

/**
 * Servlet controller for handling admin dashboard statistics.
 * Gathers data such as revenue, sales, user demographics, stock info, 
 * trending and least-sold products/categories, and recent activities.
 */
@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Default constructor for DashboardController.
     */
    public DashboardController() {
        super();
    }

    /**
     * Handles HTTP GET requests to display dashboard data.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Initialize variables
		
		double totalRevenue = 0;
		String mostTrending = null;
		String leastSold = null;
		String mostSoldCategory = null;
		String leastSoldCategory = null;
		
		String femaleUsers = null;
		String maleUsers = null;
		String totalUsers = null;
		String newUsers = null;

		try {
			totalRevenue = DashboardService.displayTotalRevenue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalSales = 0;
		try {
			totalSales = DashboardService.displayTotalSales();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalActiveProducts = 0;
		try {
			totalActiveProducts = DashboardService.displayActiveStock();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalLowStock = 0;
		try {
			totalLowStock = DashboardService.displayLowStock();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<ProductModel> topProduct = null;
		try {
			topProduct = DashboardService.displayTopProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			mostTrending = DashboardService.getMostTrending();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			leastSold = DashboardService.getLeastSold();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			mostSoldCategory = DashboardService.getMostSoldCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			leastSoldCategory = DashboardService.getleastSoldCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			femaleUsers = DashboardService.getFemaleUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			maleUsers = DashboardService.getMaleUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			totalUsers = DashboardService.getTotalUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			newUsers = DashboardService.getNewUser();
		} catch (Exception e) {
			e.printStackTrace();
		}

		LocalDate updatedTime = DashboardService.displayTime();
		List<String> productActivity = DashboardService.getRecentActivities();
		
		request.setAttribute("leastSold", leastSold);
		request.setAttribute("mostSoldCategory", mostSoldCategory);
		request.setAttribute("leastSoldCategory", leastSoldCategory);
		request.setAttribute("mostTrending", mostTrending);
		request.setAttribute("femaleUsers", femaleUsers);
		request.setAttribute("maleUsers", maleUsers);
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("newUsers", newUsers);
		request.setAttribute("totalRevenue", totalRevenue);
		request.setAttribute("totalSales", totalSales);
		request.setAttribute("totalLowStock", totalLowStock);
		request.setAttribute("totalActiveSales", totalActiveProducts);
		request.setAttribute("productActivity", productActivity);
		request.setAttribute("topProduct", topProduct);
		request.setAttribute("updatedTime", updatedTime);

		request.getRequestDispatcher("WEB-INF/pages/dashboard.jsp").forward(request, response);
	}

    /**
     * Handles HTTP POST requests by refering to doGet().
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input/output error occurs
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
