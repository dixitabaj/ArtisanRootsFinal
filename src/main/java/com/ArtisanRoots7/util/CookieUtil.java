package com.ArtisanRoots7.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility class for managing cookies in a web application.
 * Provides methods to add, retrieve, and delete cookies.
 */
public class CookieUtil {

    /**
     * Adds a cookie with the specified name, value, and maximum age.
     *
     * @param response the HttpServletResponse to add the cookie to
     * @param name     the name of the cookie
     * @param value    the value of the cookie
     * @param maxAge   the maximum age of the cookie in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        // Create a new cookie with the given name and value
        Cookie cookie = new Cookie(name, value);

        // Set the maximum age of the cookie (in seconds)
        cookie.setMaxAge(maxAge);

        // Set path to root to make cookie accessible throughout the app
        cookie.setPath("/");

        // Add the cookie to the response
        response.addCookie(cookie);
    }

    /**
     * Retrieves a cookie by its name from the HttpServletRequest.
     *
     * @param request the HttpServletRequest to get the cookie from
     * @param name    the name of the cookie to retrieve
     * @return the Cookie object if found, otherwise null
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        // Check if cookies exist in the request
        if (request.getCookies() != null) {
            // Search for a cookie with the matching name
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null); // Return null if not found
        }
        // Return null if no cookies are present
        return null;
    }

    /**
     * Deletes a cookie by setting its max age to 0.
     *
     * @param response the HttpServletResponse to add the deletion cookie to
     * @param name     the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        // Create a new cookie with same name and null value
        Cookie cookie = new Cookie(name, null);

        // Set max age to 0 to delete the cookie
        cookie.setMaxAge(0);

        // Set path to root to ensure the correct cookie is deleted
        cookie.setPath("/");

        // Add the cookie to response to trigger deletion on client
        response.addCookie(cookie);
    }
}
