package com.ArtisanRoots7.controller;

import java.io.IOException;

import com.ArtisanRoots7.model.UserModel;
import com.ArtisanRoots7.service.LoginService;
import com.ArtisanRoots7.service.UpdateService;
import com.ArtisanRoots7.util.CookieUtil;
import com.ArtisanRoots7.util.PasswordUtil;
import com.ArtisanRoots7.util.SessionUtil;
import com.ArtisanRoots7.util.ValidationUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author dixitabajracharya
 * Servlet implementation class Login
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" , "/" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		

		LoginService obj=new LoginService();
		String encrypted=obj.getEncryptedPassword(email);
		
			 String decrypted = PasswordUtil.decrypt(encrypted, email);
		

		
		boolean emailAvailable=true;
		emailAvailable=obj.check(email);
		System.out.println("the encrypted"+encrypted);
		System.out.println("password entered by the user " + password);
		System.out.println("decrypted password " + decrypted);
		System.out.println("email entered by the user:" + email);
		String passwordCheck=obj.getEncryptedPassword(email);
		System.out.println(passwordCheck);
		
		 if (emailAvailable) {
			if (password.equals(decrypted)) {
				System.out.println("Login successful");
                UserModel loggedInUser = obj.getUserByEmail(email);
                String role = setUserSession(request, response, loggedInUser);
                try {
					SessionUtil.setAttribute(request, "profileImage", UpdateService.getImage(loggedInUser));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
                redirectUserBasedOnRole(request, response, role);

			}
			else {
				System.out.println(decrypted);
				System.out.println("password isnt correct");
				request.setAttribute("loginError", "Invalid email or password");
				request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
				return;
			}
		}
		else {
			request.setAttribute("loginError", "Invalid email or password");
			request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);

			System.out.println("email isnt correct");
			return;
		}
		
    }
	private String setUserSession(HttpServletRequest request, HttpServletResponse response, UserModel loggedInUser) {
		SessionUtil.setAttribute(request, "user", loggedInUser);
		CookieUtil.addCookie(response, "role", loggedInUser.getRole(), 60 * 60);

		return loggedInUser.getRole();
	}		
	private void redirectUserBasedOnRole(HttpServletRequest request, HttpServletResponse response, String role)
			throws IOException {
		if ("admin".equalsIgnoreCase(role)) {
			response.sendRedirect(request.getContextPath() + "/dashboard");
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
}
}