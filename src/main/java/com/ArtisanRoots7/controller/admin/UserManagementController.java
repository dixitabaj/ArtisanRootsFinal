package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.SearchService;
import com.ArtisanRoots7.service.UserManageService;

/**
 * Servlet implementation class UserManagementController
 * 
 * Handles user management operations such as displaying all users
 * and searching users by name.
 */
@WebServlet("/userManagement")
public class UserManagementController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UserManagementController() {
        super();
    }

    /**
     * Handles the HTTP GET request to display users.
     * Supports searching users by their name.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManageService userService = new UserManageService();
        String searchTerm = request.getParameter("searchUser");  // Get search input from request
        List<UserModel> usersToDisplay = null;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            // User submitted a non-empty search term
            searchTerm = searchTerm.trim();
            try {
                // Retrieve users matching the search term using SearchService
                usersToDisplay = SearchService.displayUserByName(searchTerm);

                if (usersToDisplay == null || usersToDisplay.isEmpty()) {
                    // No matching users found
                    request.setAttribute("searchUserError", "No users found for: " + searchTerm);
                } else {
                    // Set found users as request attribute to display
                    request.setAttribute("users", usersToDisplay);
                }
            } catch (Exception e) {
                // Log any exceptions during search
                e.printStackTrace();
            }
        } else if (searchTerm != null) {
            // Search term is present but empty (user clicked search without input)
            request.setAttribute("searchUserError", "Please enter a user name to search.");
            try {
                // Display all users as fallback
                usersToDisplay = userService.displayUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("users", usersToDisplay);
        } else {
            // First page load or reset (no search term)
            try {
                // Display all users by default
                usersToDisplay = userService.displayUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("users", usersToDisplay);
        }

        // Preserve the search input value in the form to improve UX
        request.setAttribute("searchTerm", searchTerm);

        // Forward request and response to the JSP page to render the view
        request.getRequestDispatcher("WEB-INF/pages/user-management.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests by redirecting them to doGet,
     * so both GET and POST behave identically for this controller.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
