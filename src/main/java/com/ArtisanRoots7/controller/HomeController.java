package com.ArtisanRoots7.controller;

import java.io.IOException;
import java.util.List;

import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.service.HomeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles requests for the application's home page.
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public HomeController() {
        super();
    }

    /**
     * Serves the home page view.
     * 
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the view cannot be processed
     * @throws IOException if an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String searchItem = request.getParameter("searchItem");
        List<ProductModel> recentProducts = null;

        try {
            if (searchItem != null && !searchItem.trim().isEmpty()) {
                // If user searched for something
            	
                recentProducts = HomeService.displayProductByName(searchItem);
                if (recentProducts == null || recentProducts.isEmpty()) {
                    request.setAttribute("searchError", "No products found for: " + searchItem);
                }
            } else {
                recentProducts = HomeService.displayLatestFive();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("recentProducts", recentProducts);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }


    /**
     * Goes to doGet() for consistent rendering.
     * 
     * @param request the HttpServletRequest containing form data
     * @param response the HttpServletResponse object
     * @throws ServletException if the request cannot be processed
     * @throws IOException if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}