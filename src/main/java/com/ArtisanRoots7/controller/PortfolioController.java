package com.ArtisanRoots7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.ArtisanRoots7.model.ProductModel;
import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.LoginService;
import com.ArtisanRoots7.service.PortfolioService;
import com.ArtisanRoots7.service.ProductManagementService;
import com.ArtisanRoots7.service.RegistrationService;
import com.ArtisanRoots7.service.UpdateService;
import com.ArtisanRoots7.service.UserManageService;
import com.ArtisanRoots7.util.PasswordUtil;
import com.ArtisanRoots7.util.SessionUtil;
import com.ArtisanRoots7.util.ValidationUtil;

/**
 * Servlet implementation class PortfolioController
 */
@WebServlet("/portfolio")
public class PortfolioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortfolioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Inside UpdateController");
		getServletContext().log("Inside doPost");
	    HttpSession session = request.getSession();

	    // Check if the user is logged in
	    UserModel currentUser = (UserModel) session.getAttribute("user");
	    if (currentUser == null) {
	        response.sendRedirect("login");
	        return;
	    }
	    
        try {
			if (validateFields(request, currentUser)) {
			    request.setAttribute("user", currentUser);  // Retain the values on form reload
			    request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
			    return;
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Hardcoded values to update (this can be changed to dynamic values if needed)
		String username = request.getParameter("username");
	    
	    String firstName=request.getParameter("firstName");
	    String lastName=request.getParameter("lastName");
	    String phoneNumber = request.getParameter("phone");
	 String gender=request.getParameter("gender");
		
	    UserModel user = new UserModel(
	    		currentUser.getUserId(),
	        firstName,
	       lastName,
	        username,
	        phoneNumber,
	        currentUser.getDob(),
	        currentUser.getEmail(),
	        currentUser.getRole(),
	        currentUser.getPassword(), 
	        gender,
	        currentUser.getJoinedDate(),
	        currentUser.getStatus(),
	        currentUser.getUserImage()
	    );
	    System.out.println("Updated user info: " + user.getFirstName() + ", " + user.getUsername());

	    UpdateService dao = new UpdateService();
        boolean updated = false;

        try {
            updated = dao.updateUser(user);  // Update user in the database
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (updated) {
        	session.setAttribute("successMessage", "Password updated successfully");
        	response.sendRedirect(request.getContextPath() + "/portfolio?activeTab=password");

            // Update session with the updated user data
        	System.out.println("User updated successfully in DB.");
        	System.out.println("Session user after update: " + ((UserModel)session.getAttribute("user")).getFirstName());

        	session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/portfolio");
        } else {
        	String activeTab = "password";
        	request.setAttribute("activeTab", activeTab);
        	request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);

        }
	
        
        
        
	}
	private Boolean validateFields(HttpServletRequest request, UserModel currentUser) throws Exception {
	    boolean hasErrors = false;

	    String firstName = request.getParameter("firstName");
	    String lastName = request.getParameter("lastName");
	    String phone = request.getParameter("phone");
	    String username = request.getParameter("username");
	    
		 
	    request.setAttribute("firstName", firstName);
	    request.setAttribute("lastName", lastName);
	    request.setAttribute("phone", phone);
	    request.setAttribute("username", username);

	    if (ValidationUtil.isNull(firstName)) {
	        request.setAttribute("firstNameError", "First name cannot be empty");
	        hasErrors = true;
	    } else if (!ValidationUtil.isAlphabetic(firstName)) {
	        request.setAttribute("firstNameError", "First name must contain alphabets only");
	        hasErrors = true;
	    }

	    if (ValidationUtil.isNull(lastName)) {
	        request.setAttribute("lastNameError", "Last name cannot be empty");
	        hasErrors = true;
	    } else if (!ValidationUtil.isAlphabetic(lastName)) {
	        request.setAttribute("lastNameError", "Last name must contain alphabets only");
	        hasErrors = true;
	    }

	    if (ValidationUtil.isNull(username)) {
	        request.setAttribute("usernameError", "Username cannot be empty");
	        hasErrors = true;
	    } else if (!username.equals(currentUser.getUsername()) && RegistrationService.isUsernameExists(username)) {
	        request.setAttribute("usernameError", "Username already exists");
	        hasErrors = true;
	    }

	    if (ValidationUtil.isNull(phone)) {
	        request.setAttribute("phoneNoError", "Phone number cannot be empty");
	        hasErrors = true;
	    } else if (!ValidationUtil.isNumeric(phone)) {
	        request.setAttribute("phoneNoError", "Phone Number must be numeric.");
	        hasErrors = true;
	    }else if (!ValidationUtil.isValidPhoneNumber(phone)) {
	        request.setAttribute("phoneNoError", "Phone Number isnt valid");
	        hasErrors = true;
	    }
 else if (!phone.equals(currentUser.getPhone()) && RegistrationService.phoneExists(phone)) {
	        request.setAttribute("phoneNoError", "Phone number already exists");
	        hasErrors = true;
	    }

	    return hasErrors;
	}
	
	
	}
	
