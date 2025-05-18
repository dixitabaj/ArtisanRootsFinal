package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.PortfolioService;
import com.ArtisanRoots7.util.PasswordUtil;
import com.ArtisanRoots7.util.ValidationUtil;

/**
 * Servlet implementation class UpdatePasswordController
 */
@WebServlet("/updatepassword")
public class UpdatePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UpdatePasswordController() {
        super();
    }

    /**
     * Handles HTTP GET request to display the portfolio page.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[GET] /updatepassword");

        // Forward request to portfolio JSP page
        request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST request to process password update after validation.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        // Retrieve form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Decrypt stored password for validation
        String storedEncryptedPassword = user.getPassword();
        String decryptedPassword = PasswordUtil.decrypt(storedEncryptedPassword, user.getEmail());

        // Validate current password correctness
        if (!ValidationUtil.matchesPassword(currentPassword, decryptedPassword)) {
            request.setAttribute("enteredPassError", "Current password is incorrect");
            request.setAttribute("activeTab", "password");
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
            return;
        }

        // Prevent reusing the old password
        if (currentPassword.equals(newPassword)) {
            request.setAttribute("newPasswordError", "Your new password must be different from your old one");
            request.setAttribute("activeTab", "password");
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
            return;
        }

        // Validate new password complexity
        if (!ValidationUtil.isValidPassword(newPassword)) {
            request.setAttribute("newPasswordError", "Password must contain at least one number, uppercase, lowercase and special character");
            request.setAttribute("activeTab", "password");
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
            return;
        }

        // Confirm new password and confirmation match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "New passwords don't match");
            request.setAttribute("activeTab", "password");
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
            return;
        }

        // Encrypt new password before updating
        String newEncryptedPassword = PasswordUtil.encrypt(user.getEmail(), newPassword);

        // Attempt to update password in database
        boolean updated = PortfolioService.updatePassword(user.getEmail(), newEncryptedPassword);

        // Always set the active tab for UI focus
        request.setAttribute("activeTab", "password");

        if (updated) {
            // Update session user with new password on success
            user.setPassword(newEncryptedPassword);
            session.setAttribute("user", user);
            request.setAttribute("successPassword", "Password updated successfully");
        } else {
            // Handle update failure
            request.setAttribute("newPasswordError", "Failed to update password");
        }

        // Forward back to portfolio page
        request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
    }
}
