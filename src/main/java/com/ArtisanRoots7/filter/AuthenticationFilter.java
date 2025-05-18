package com.ArtisanRoots7.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.ArtisanRoots7.util.CookieUtil;
import com.ArtisanRoots7.util.SessionUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/registration2";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/dashboard";
	private static final String MANAGE_PRODUCTS = "/productmanage";
	private static final String USER_MANAGE = "/userManagement";
	private static final String ADMIN_ORDER = "/adminOrder";
	private static final String ABOUT = "/aboutus";
	private static final String PORTFOLIO = "/portfolio";
	private static final String UPDATEPASS = "/updatepassword";
	private static final String CONTACT = "/contactus";
	private static final String LOGOUT = "/logout"; // Add this with other path constants

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if required
	}

	/**
	 * Filters incoming HTTP requests to handle authentication and authorization.
	 *
	 * @param request  the ServletRequest object, cast to HttpServletRequest inside
	 *                 the method
	 * @param response the ServletResponse object, cast to HttpServletResponse
	 *                 inside the method
	 * @param chain    the FilterChain to pass control to the next filter or
	 *                 resource
	 * @throws IOException      if an input or output error occurs during the
	 *                          filtering process
	 * @throws ServletException if the request could not be handled
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getRequestURI();

		if (path.startsWith(req.getContextPath() + "/resource/") || path.startsWith(req.getContextPath() + "/css/")
				|| path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".js") || path.endsWith(".css")) {
			// Allow static content to be served
			chain.doFilter(request, response);
			return;
		}

		String uri = req.getRequestURI();

		// Handle logout explicitly
		if (uri.endsWith(LOGOUT)) {
			chain.doFilter(request, response);
			return;
			// Redirect to login page after logout

		}

		// Allow access to static resources
		if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".css")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = req.getSession(false);
		boolean isLoggedIn = session != null && session.getAttribute("user") != null;
		String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
				: null;

		System.out.println("User Role: " + userRole); // Debugging line
		System.out.println("Session Active: " + isLoggedIn); // Debugging line

		if (isLoggedIn && userRole != null) {
			if ("admin".equals(userRole)) {
				// Admin should not access login, registration or other user-only pages
				if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
					res.sendRedirect(req.getContextPath() + DASHBOARD); // Redirect to dashboard for admin
				} else if (uri.endsWith(DASHBOARD) || uri.endsWith(MANAGE_PRODUCTS) || uri.endsWith(USER_MANAGE)
						|| uri.endsWith(ADMIN_ORDER) || uri.endsWith(HOME) || uri.endsWith(ROOT)
						|| uri.endsWith(PORTFOLIO) || uri.endsWith(UPDATEPASS)) {
					chain.doFilter(request, response);
				} else {
					chain.doFilter(request, response);
				}
			} else if ("customer".equals(userRole)) {
				Cookie[] cookies = req.getCookies();
				if (cookies != null) {
					for (Cookie c : cookies) {
						System.out.println("Cookie found: " + c.getName() + " = " + c.getValue());
					}
				}

				// User should not access login, registration, or admin pages
				if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
					res.sendRedirect(req.getContextPath() + HOME); // Redirect to home for user
				} else if (uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT) || uri.endsWith(CONTACT)
						|| uri.endsWith(PORTFOLIO) || uri.endsWith(UPDATEPASS)) {
					// Redirect user to the appropriate pages if needed (or add other restrictions)
					chain.doFilter(request, response);
				} else if (uri.endsWith(DASHBOARD) || uri.endsWith(USER_MANAGE) || uri.endsWith(MANAGE_PRODUCTS)
						|| uri.endsWith(ADMIN_ORDER)) {
					res.sendRedirect(req.getContextPath() + HOME);
				} else {
					res.sendRedirect(req.getContextPath() + HOME);
				}
			}
		} else {
			System.out.println("LogoutServlet called");

			// For logged-out users, allow access to login, registration, home, or root
			// pages
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(ROOT) || uri.endsWith(LOGOUT)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + LOGIN); // Redirect to login for all other pages
			}
		}

	}

	@Override
	public void destroy() {
		// Cleanup logic, if required
	}
}
