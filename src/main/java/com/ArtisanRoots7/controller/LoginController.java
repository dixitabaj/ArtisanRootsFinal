package com.ArtisanRoots7.controller;

import java.io.IOException;
import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.LoginService;
import com.ArtisanRoots7.service.UpdateService;
import com.ArtisanRoots7.util.CookieUtil;
import com.ArtisanRoots7.util.PasswordUtil;
import com.ArtisanRoots7.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles user authentication and session management.
 * Supports both GET requests for login page display and POST requests for login processing.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login", "/" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Initializes a new LoginController instance.
     */
    public LoginController() {
        super();
    }

    /**
     * Displays the login page.
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if the login page cannot be displayed
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Processes login form submissions.
     * Validates credentials, manages user sessions, and redirects based on user role.
     * @param request contains the submitted email and password
     * @param response used for redirecting after successful login
     * @throws ServletException if authentication processing fails
     * @throws IOException if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        LoginService loginService = new LoginService();
        String encryptedPassword = loginService.getEncryptedPassword(email);
        String decryptedPassword = PasswordUtil.decrypt(encryptedPassword, email);
        
        boolean isValidEmail = loginService.check(email);
        
        if (isValidEmail && password.equals(decryptedPassword)) {
            UserModel loggedInUser = loginService.getUserByEmail(email);
            String role = setUserSession(request, response, loggedInUser);
            
            try {
                SessionUtil.setAttribute(request, "profileImage", UpdateService.getImage(loggedInUser));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            redirectUserBasedOnRole(request, response, role);
        } else {
            request.setAttribute("loginError", "Invalid email or password");
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
        }
    }

    /**
     * Establishes user session and sets role cookie.
     * @param request the current HttpServletRequest
     * @param response the HttpServletResponse for cookie operations
     * @param loggedInUser the authenticated user
     * @return the user's role
     */
    private String setUserSession(HttpServletRequest request, HttpServletResponse response, UserModel loggedInUser) {
        SessionUtil.setAttribute(request, "user", loggedInUser);
        CookieUtil.addCookie(response, "role", loggedInUser.getRole(), 60 * 60);
        return loggedInUser.getRole();
    }

    /**
     * Redirects user to appropriate page based on their role.
     * @param request the current HttpServletRequest
     * @param response the HttpServletResponse for redirection
     * @param role the user's role (admin or regular user)
     * @throws IOException if redirection fails
     */
    private void redirectUserBasedOnRole(HttpServletRequest request, HttpServletResponse response, String role) 
            throws IOException {
        if ("admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}