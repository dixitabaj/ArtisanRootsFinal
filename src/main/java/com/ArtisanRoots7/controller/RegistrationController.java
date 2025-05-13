package com.ArtisanRoots7.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.LoginService;
import com.ArtisanRoots7.service.RegistrationService;
import com.ArtisanRoots7.service.UpdateService;
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
 * Servlet implementation class RegistrationController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/registration2"})
@MultipartConfig
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/registration.jsp").forward(request, response);
	}
	

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String firstName=request.getParameter("firstName");
	    String lastName=request.getParameter("lastName");
	    String phone=request.getParameter("phone");
	    String email=request.getParameter("email");
	    String gender=request.getParameter("gender");
	    String retypePassword=request.getParameter("confirmPassword");
	    Part file=request.getPart("image");
	    String imageFilename= null;
	    if (file != null) {
	    	 imageFilename= file.getSubmittedFileName();
	        String uploadPath =  "/Users/dixitabajracharya/eclipse-workspace/ArtisanRoots3/src/main/webapp/resource/images/users" + File.separator + imageFilename;

	        System.out.println("Uploaded file: " + imageFilename);
	        System.out.println("profileImage in session: " + request.getSession().getAttribute("profileImage"));

	        SessionUtil.setAttribute(request, "profileImage", imageFilename);  // fileName = "aboutUs1.png"

	        System.out.println("Uploaded file: " + uploadPath);
	        if (!isValidImage(file, request)) {
	            request.getRequestDispatcher("WEB-INF/pages/registration.jsp").forward(request, response);
	            return;
	        }

	            
	            
	            // Create directory if it doesn't exist
//	            File uploadDir = new File(getServletContext().getRealPath("/resources/images/users/"));
	            ImageUtil.uploadImage(file, getServletContext());

	            System.out.println("File saved to: " + uploadPath);
	        }
	        // You can proceed with file saving and other logic here
	    
	    // Encrypt the password using AES
	    String dobStr = request.getParameter("dob");
        boolean hasError = validateRegistrationForm(
                request, username, password, firstName, lastName, 
                phone, email, retypePassword, dobStr
            );

	    
System.out.print(hasError);
		if(!hasError) {
	    String encryptedPassword=null;
		try {
			encryptedPassword = PasswordUtil.encrypt(email, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("Encrypted password: " + encryptedPassword);
	    
	    System.out.println("Reached here after encryption");
	    UserModel user = new UserModel(
	        0,
	        firstName,
	       lastName,
	        username,
	        phone,
	        LocalDate.parse(dobStr),
	        email,
	        "customer",
	        encryptedPassword,  // Save encrypted password
	        gender, 
	        LocalDate.now(), 
	        "Active",
	        imageFilename
	    );
		
	    LoginService dao = new LoginService();
	    boolean inserted = false;

	        try {
				inserted = dao.insert(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("Encrypted password: " + encryptedPassword);
	        System.out.println("User inserted: " + inserted);
	   
		
	    if (inserted) {
	    	SessionUtil.setAttribute(request, "loggedInUser", user);
	    	response.sendRedirect(request.getContextPath() + "/login");
	    } else {
	        response.sendRedirect("error.jsp");
	    }
	}
	    else {
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
	
    private boolean validateRegistrationForm(
            HttpServletRequest request,
            String username, String password, String firstName, String lastName,
            String phone, String email, String confirmPassword, String dobStr
        ) {
            boolean hasError = false;
            
            // Validate name fields
            hasError |= !validateNameField(request, "firstName", firstName, "firstNameError");
            hasError |= !validateNameField(request, "lastName", lastName, "lastNameError");
            hasError |= !validateUsernameField(request, username);

            // Validate password
            hasError |= !validatePasswordFields(request, password, confirmPassword);

            // Validate contact info
            hasError |= !validateEmailField(request, email);
            hasError |= !validatePhoneField(request, phone);

            // Validate date of birth
            hasError |= !validateDateOfBirth(request, dobStr);

            return hasError;
        }

        private boolean validateNameField(HttpServletRequest request, 
                String fieldName, String value, String errorAttr) {
            if (!ValidationUtil.isValidFirstName(value)) {
                request.setAttribute(errorAttr, fieldName + " must contain only letters.");
                return false;
            }
            return true;
        }

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

        private boolean validatePasswordFields(HttpServletRequest request, 
                String password, String confirmPassword) {
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

        private boolean validateDateOfBirth(HttpServletRequest request, String dobStr) {
        	if (dobStr == null || dobStr.isBlank()) {
        	    // Handle error: send back response or set an error message
        	    throw new IllegalArgumentException("Date of birth is required.");
        	}
            try {
                LocalDate dob = LocalDate.parse(dobStr);
                if (!ValidationUtil.isValidAge(dob)) {
                    request.setAttribute("dobError", "You should be above 12 to register");
                    return false;
                }
                return true;
            } catch (DateTimeParseException e) {
                request.setAttribute("dobError", "Invalid date of birth format. Please use YYYY-MM-DD.");
                return false;
            }
        }
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

	}



