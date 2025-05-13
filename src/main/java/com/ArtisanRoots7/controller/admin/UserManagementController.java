

package com.ArtisanRoots7.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.ProductManagementService;
import com.ArtisanRoots7.service.SearchService;
import com.ArtisanRoots7.service.UserManageService;

/**
 * Servlet implementation class UserManagementController
 */
@WebServlet("/userManagement")
public class UserManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagementController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 UserManageService userService = new UserManageService();
	        String searchTerm = request.getParameter("searchUser");
	        List<UserModel> usersToDisplay = null;
	        
	        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
	            // Search mode
	            try {
					usersToDisplay = SearchService.displayUserByName(searchTerm);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } else {
	            // Normal mode - show all users
	            try {
					usersToDisplay = userService.displayUsers();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        // Set attributes
	        request.setAttribute("users", usersToDisplay);
	        request.setAttribute("searchTerm", searchTerm);
	        
	        request.getRequestDispatcher("WEB-INF/pages/user-management.jsp").forward(request, response);
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
