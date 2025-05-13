package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.LoginService;
import com.ArtisanRoots7.service.PortfolioService;
import com.ArtisanRoots7.util.PasswordUtil;
import com.ArtisanRoots7.util.ValidationUtil;

/**
 * Servlet implementation class UpdatePasswordController
 */
@WebServlet("/updatepassword")
public class UpdatePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdatePasswordController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[GET] /updatepassword");
        request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
 
        // 1. Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "New passwords don't match");
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
            return;
        }

        // 2. Verify current password
        String storedEncryptedPassword = user.getPassword();
        String decryptedPassword = PasswordUtil.decrypt(storedEncryptedPassword, user.getEmail());

        if (!ValidationUtil.matchesPassword(currentPassword, decryptedPassword)) {
            request.setAttribute("newPasswordError", "Current password is incorrect");
            request.getRequestDispatcher("/WEB-INF/pages/portfolio.jsp").forward(request, response);
            return;
        }

        // 3. Encrypt and update new password
        String newEncryptedPassword = PasswordUtil.encrypt(user.getEmail(), newPassword);
        boolean updated = PortfolioService.updatePassword(user.getEmail(), newEncryptedPassword);

        if (updated) {
            // Update user in session
        	String activeTab = request.getParameter("activeTab"); // should be "account"
        	request.setAttribute("activeTab", activeTab);
            user.setPassword(newEncryptedPassword);
            session.setAttribute("user", user);
            request.setAttribute("successMessage", "Password updated successfully");
        }

        request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
    }
}


