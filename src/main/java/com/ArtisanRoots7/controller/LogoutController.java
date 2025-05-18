package com.ArtisanRoots7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.ArtisanRoots7.util.CookieUtil;
import com.ArtisanRoots7.util.SessionUtil;

/**
 * Handles user logout functionality by clearing session and authentication cookies.
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

 
    public LogoutController() {
        super();
    }

    /**
     * Processes logout requests by: 
     * Invalidating the current user session 
     * Removing the role cookie
     * Redirecting to login page
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if session invalidation fails
     * @throws IOException if redirection fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Clear all session data
        SessionUtil.invalidate(request);
        
        // Remove authentication cookie
        CookieUtil.deleteCookie(response, "role");
        
        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
}