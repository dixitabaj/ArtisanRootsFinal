package com.ArtisanRoots7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AboutUsController1 handles requests related to the About Us page. This
 * servlet processes both GET and POST requests for displaying the about us
 * information.
 */
@WebServlet("/aboutus")
public class AboutUsController1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AboutUsController1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Handles HTTP GET requests for the About Us page. Forwards the request to the
	 * about-us.jsp view in the WEB-INF directory.
	 *
	 * @param request  Contains all the data sent by the user's browser
	 * @param response Used to send the page back to the user
     * @throws ServletException if the view cannot be processed
     * @throws IOException if an I/O error occurs during processing
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/about-us.jsp").forward(request, response);
	}

	/**
	 * Handles HTTP POST requests for the About Us page. Goes to the doGet method to
	 * handle the request.
	 *
	 * @param request  Contains all the data sent by the user's browser
	 * @param response Used to send the page back to the user
     * @throws ServletException if the view cannot be processed
     * @throws IOException if an I/O error occurs during processing
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
