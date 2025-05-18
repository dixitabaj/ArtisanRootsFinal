package com.ArtisanRoots7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles the Contact Us page Shows contact information
 */
@WebServlet("/contactus")
public class ContactUsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactUsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Shows the Contact Us page
	 * 
	 * @param request  The visitor's request information
	 * @param response Where we send the page back to browser
	 * @throws ServletException If the page can't be shown (missing or broken)
	 * @throws IOException      If there's trouble sending the response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/contact-us.jsp").forward(request, response);
	}

	/**
	 * Handles contact form submissions
	 * 
	 * @param request  Contains the submitted form data
	 * @param response Where we send the response
	 * @throws ServletException If form processing fails
	 * @throws IOException      If response can't be sent
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
