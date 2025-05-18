package com.ArtisanRoots7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.RegistrationService;
import com.ArtisanRoots7.service.UpdateService;
import com.ArtisanRoots7.util.ValidationUtil;

/**
 * Handles portfolio management including viewing and updating user profile
 * information.
 */
@WebServlet("/portfolio")
public class PortfolioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes a new PortfolioController instance.
	 */
	public PortfolioController() {
		super();
	}

	/**
	 * Displays the portfolio page with success messages if present.
	 * 
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException if the portfolio page cannot be displayed
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get and clear any success message from session
		HttpSession session = request.getSession();
		String successMessage = (String) session.getAttribute("successMessage");
		if (successMessage != null) {
			request.setAttribute("successMessage", successMessage);
			session.removeAttribute("successMessage");
		}
		request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
	}

	/**
	 * Processes profile update requests. Validates and updates user information
	 * 
	 * @param request  contains the updated user information
	 * @param response used for redirecting after update
	 * @throws ServletException if update processing fails
	 * @throws IOException      if an I/O error occurs during processing
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserModel currentUser = (UserModel) session.getAttribute("user");

		// Check authentication
		if (currentUser == null) {
			response.sendRedirect("login");
			return;
		}

		try {
			// Validate input fields
			if (validateFields(request, currentUser)) {
				request.setAttribute("user", currentUser);
				request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
				return;
			}

			// Create and save updated user
			UserModel updatedUser = createUpdatedUser(request, currentUser);
			UpdateService dao = new UpdateService();
			boolean updated = dao.updateUser(updatedUser);

			// Handle update result
			if (updated) {
				session.setAttribute("successMessage", "Information updated successfully!!");
				session.setAttribute("user", updatedUser);
				response.sendRedirect(request.getContextPath() + "/portfolio?activeTab=password");
			} else {
				request.setAttribute("errorMessage", "Failed to update information");
				request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// Error handling
			request.setAttribute("errorMessage", "An error occurred during update");
			request.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(request, response);
		}
	}

	/**
	 * Validates user input fields for profile updates.
	 * 
	 * @param request     contains the field values to validate
	 * @param currentUser the currently logged in user
	 * @return true if validation errors exist, false otherwise
	 * @throws Exception if validation processing fails
	 */
	private Boolean validateFields(HttpServletRequest request, UserModel currentUser) throws Exception {
		boolean hasErrors = false;

		// Get parameters
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String username = request.getParameter("username");

		// Store values for form repopulation
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		request.setAttribute("phone", phone);
		request.setAttribute("username", username);

		// First name validation
		if (ValidationUtil.isNull(firstName)) {
			request.setAttribute("firstNameError", "First name cannot be empty");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(firstName)) {
			request.setAttribute("firstNameError", "First name must contain alphabets only");
			hasErrors = true;
		}

		// Last name validation
		if (ValidationUtil.isNull(lastName)) {
			request.setAttribute("lastNameError", "Last name cannot be empty");
			hasErrors = true;
		} else if (!ValidationUtil.isAlphabetic(lastName)) {
			request.setAttribute("lastNameError", "Last name must contain alphabets only");
			hasErrors = true;
		}

		// Username validation
		if (ValidationUtil.isNull(username)) {
			request.setAttribute("usernameError", "Username cannot be empty");
			hasErrors = true;
		} else if (!username.equals(currentUser.getUsername()) && RegistrationService.isUsernameExists(username)) {
			request.setAttribute("usernameError", "Username already exists");
			hasErrors = true;
		}

		// Phone validation
		if (ValidationUtil.isNull(phone)) {
			request.setAttribute("phoneNoError", "Phone number cannot be empty");
			hasErrors = true;
		} else if (!ValidationUtil.isNumeric(phone)) {
			request.setAttribute("phoneNoError", "Phone Number must be numeric");
			hasErrors = true;
		} else if (!ValidationUtil.isValidPhoneNumber(phone)) {
			request.setAttribute("phoneNoError", "Phone Number isn't valid");
			hasErrors = true;
		} else if (!phone.equals(currentUser.getPhone()) && RegistrationService.phoneExists(phone)) {
			request.setAttribute("phoneNoError", "Phone number already exists");
			hasErrors = true;
		}

		return hasErrors;
	}

	/**
	 * Creates an updated UserModel with the new field values.
	 * 
	 * @param request     contains the updated field values
	 * @param currentUser the current user data
	 * @return UserModel with updated fields
	 */
	private UserModel createUpdatedUser(HttpServletRequest request, UserModel currentUser) {
		return new UserModel(currentUser.getUserId(), 
				request.getParameter("firstName"),
				request.getParameter("lastName"), 
				request.getParameter("username"), 
				request.getParameter("phone"),
				currentUser.getDob(), 
				currentUser.getEmail(), 
				currentUser.getRole(), 
				currentUser.getPassword(),
				request.getParameter("gender"), 
				currentUser.getJoinedDate(), 
				currentUser.getStatus(),
				currentUser.getUserImage());
	}
}