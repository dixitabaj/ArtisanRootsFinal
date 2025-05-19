package com.ArtisanRoots7.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.LoginService;
import com.ArtisanRoots7.service.RegistrationService;
import com.ArtisanRoots7.util.ImageUtil;
import com.ArtisanRoots7.util.PasswordUtil;
import com.ArtisanRoots7.util.SessionUtil;
import com.ArtisanRoots7.util.ValidationUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * Handles user registration including form processing, validation, and account
 * creation.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/registration2" })
@MultipartConfig
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RegistrationController() {
		super();
	}

	/**
	 * Displays the registration form.
	 * 
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/pages/registration.jsp").forward(request, response);
	}

	/**
	 * Processes registration form submission.
	 * 
	 * @param request  servlet request containing form data
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Extract form parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String retypePassword = request.getParameter("confirmPassword");
		Part file = request.getPart("image");

		// Handle image upload
		String imageFilename = handleImageUpload(request, file);

		// Get date of birth
		String dobStr = request.getParameter("dob");

		// Validate all inputs
		boolean hasError = validateRegistrationForm(request, username, password, firstName, lastName, phone, email,
				retypePassword, dobStr);

		System.out.print(hasError);

		if (!hasError) {
			// Encrypt password using AES
			String encryptedPassword = null;
			try {
				encryptedPassword = PasswordUtil.encrypt(email, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Encrypted password: " + encryptedPassword);

			// Create a UserModel object
			UserModel user = new UserModel(0, firstName, lastName, username, phone, LocalDate.parse(dobStr), email,
					"customer", encryptedPassword, gender, LocalDate.now(), "Active", imageFilename);

			// Insert user into database
			LoginService dao = new LoginService();
			boolean inserted = false;
			try {
				inserted = dao.insert(user);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (inserted) {
				SessionUtil.setAttribute(request, "loggedInUser", user);
				response.sendRedirect(request.getContextPath() + "/login");
			} else {
				response.sendRedirect("error.jsp");
			}
		} else {
			// If there are validation errors, return to form with user input
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("username", username);
			request.setAttribute("phone", phone);
			request.setAttribute("email", email);
			request.setAttribute("gender", gender);
			request.setAttribute("dob", dobStr);
			request.setAttribute("image", imageFilename);
			request.getRequestDispatcher("WEB-INF/pages/registration.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * Validates the registration form fields.
	 * 
	 * @param request         HTTP request
	 * @param username        entered username
	 * @param password        entered password
	 * @param firstName       user's first name
	 * @param lastName        user's last name
	 * @param phone           user's phone number
	 * @param email           user's email address
	 * @param confirmPassword retyped password
	 * @param dobStr          date of birth string
	 * @return true if any field has error, false otherwise
	 */
	private boolean validateRegistrationForm(HttpServletRequest request, String username, String password,
			String firstName, String lastName, String phone, String email, String confirmPassword, String dobStr) {
		boolean hasError = false;

		hasError |= !validateNameField(request, "firstName", firstName, "firstNameError");
		hasError |= !validateNameField(request, "lastName", lastName, "lastNameError");
		hasError |= !validateUsernameField(request, username);
		hasError |= !validatePasswordFields(request, password, confirmPassword);
		hasError |= !validateEmailField(request, email);
		hasError |= !validatePhoneField(request, phone);
		hasError |= !validateDateOfBirth(request, dobStr);

		return hasError;
	}

	/**
	 * Validates if the provided name contains only letters.
	 * 
	 * @param request   the HTTP request to set error attributes if validation fails
	 * @param fieldName the display name of the field for error messaging
	 * @param value     the actual input value to validate
	 * @param errorAttr the attribute name to store error messages in the request
	 * @return true if the name is valid, false otherwise
	 */
	private boolean validateNameField(HttpServletRequest request, String fieldName, String value, String errorAttr) {
		if (!ValidationUtil.isValidFirstName(value)) {
			// Set error message if the name is invalid
			request.setAttribute(errorAttr, fieldName + " must contain only letters.");
			return false;
		}
		return true;
	}

	/**
	 * Validates the username for format and uniqueness.
	 * 
	 * @param request  the HTTP request to set error attributes if validation fails
	 * @param username the username to validate
	 * @return true if the username is valid and not already taken, false otherwise
	 * @throws Exception if an error occurs while checking username availability
	 */
	private boolean validateUsernameField(HttpServletRequest request, String username) {
		if (!ValidationUtil.isValidUsername(username)) {
			request.setAttribute("usernameError", "Username must be alphanumeric.");
			return false;
		}
		try {
			if (RegistrationService.isUsernameExists(username)) {
				request.setAttribute("usernameError", "This username is already taken");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error checking username availability");
			return false;
		}
		return true;
	}

	/**
	 * Validates the password and confirm password fields.
	 * 
	 * @param request         the HTTP request to set error attributes if validation
	 *                        fails
	 * @param password        the password to validate
	 * @param confirmPassword the repeated password to match
	 * @return true if both passwords are valid and match, false otherwise
	 */
	private boolean validatePasswordFields(HttpServletRequest request, String password, String confirmPassword) {
		if (!ValidationUtil.isValidPassword(password)) {
			request.setAttribute("passwordError",
					"Password must contain at least one number, uppercase, lowercase and special character");
			return false;
		}
		if (!ValidationUtil.matchesPassword(password, confirmPassword)) {
			request.setAttribute("confirmPasswordError", "Mismatched password");
			return false;
		}
		return true;
	}

	/**
	 * Validates the email format and checks for duplicates.
	 * 
	 * @param request the HTTP request to set error attributes if validation fails
	 * @param email   the email to validate
	 * @return true if the email is valid and not already in use, false otherwise
	 * @throws Exception if an error occurs while checking email availability
	 */
	private boolean validateEmailField(HttpServletRequest request, String email) {
		if (!ValidationUtil.isValidEmail(email)) {
			request.setAttribute("emailError", "Please enter a valid email");
			return false;
		}
		try {
			if (RegistrationService.emailExists(email)) {
				request.setAttribute("emailError", "Email already in use. Please try another");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error checking email availability");
			return false;
		}
		return true;
	}

	/**
	 * Validates the phone number format and checks for duplicates.
	 * 
	 * @param request the HTTP request to set error attributes if validation fails
	 * @param phone   the phone number to validate
	 * @return true if the phone number is valid and not already in use, false
	 *         otherwise
	 * @throws Exception if an error occurs while checking phone availability
	 */
	private boolean validatePhoneField(HttpServletRequest request, String phone) {
		if (!ValidationUtil.isValidPhoneNumber(phone)) {
			request.setAttribute("phoneError", "Please enter a valid phone number");
			return false;
		}
		try {
			if (RegistrationService.phoneExists(phone)) {
				request.setAttribute("phoneError", "Phone number already in use. Please use another");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error checking phone number availability");
			return false;
		}
		return true;
	}

	/**
	 * Validates the date of birth for format, existence, and age constraints.
	 * 
	 * @param request the HTTP request to set error attributes if validation fails
	 * @param dobStr  the date of birth string in YYYY-MM-DD format
	 * @return true if the date of birth is valid and user is at least 12 years old
	 * @throws IllegalArgumentException if the date of birth is null or blank
	 */
	private boolean validateDateOfBirth(HttpServletRequest request, String dobStr) {
		if (dobStr == null || dobStr.isBlank()) {
			throw new IllegalArgumentException("Date of birth is required.");
		}
		try {
			LocalDate dob = LocalDate.parse(dobStr); // Parse date string
			if (!ValidationUtil.isValidAge(dob)) {
				request.setAttribute("dobError", "Please enter a valid date");
				return false;
			}
			if (!ValidationUtil.isAbove12(dob)) {
				request.setAttribute("dobError", "You need to be 12 or over to register.");
				return false;
			}
			return true;
		} catch (DateTimeParseException e) {
			request.setAttribute("dobError", "Invalid date of birth format. Please use YYYY-MM-DD.");
			return false;
		}
	}

	/**
	 * Validates if the uploaded file is an acceptable image.
	 * 
	 * @param part    uploaded file
	 * @param request HTTP request
	 * @return true if valid, false otherwise
	 */
	public static boolean isValidImage(Part part, HttpServletRequest request) {
		String contentType = part.getContentType();
		if (!contentType.startsWith("image/")) {
			request.setAttribute("imageError", "Only image files are allowed");
			return false;
		}
		if (part.getSize() > 10_000_000) {
			request.setAttribute("imageError", "Image too large (max 10MB)");
			return false;
		}
		return true;
	}

	/**
	 * Handles image upload process.
	 * 
	 * @param request HTTP request
	 * @param file    uploaded image file
	 * @return the name of the uploaded image file
	 * @throws IOException      if an I/O error occurs
	 * @throws ServletException if the image is invalid or upload fails
	 */
	private String handleImageUpload(HttpServletRequest request, Part file) throws IOException, ServletException {

		String imageFilename = null;

		if (file != null && file.getSize() > 0) {
			if (!isValidImage(file, request)) {
				throw new ServletException("Invalid image file");
			}

			imageFilename = ImageUtil.getImageNameFromPart(file);
			boolean uploadSuccess = ImageUtil.uploadImage(file, getServletContext());

			if (!uploadSuccess) {
				throw new ServletException("Failed to upload image");
			}
		}
		return imageFilename;
	}
}
