package com.ArtisanRoots7.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP session attributes in a web application.
 * Provides methods to set, get, remove attributes and invalidate the session.
 */
public class SessionUtil {

    /**
     * Sets an attribute in the HTTP session with a default timeout.
     * If a session does not exist, it will be created.
     *
     * @param request the HttpServletRequest object from which to get the session
     * @param key     the name of the session attribute
     * @param value   the value to be stored in the session under the specified key
     * 
     * Note: The session timeout is set to 30 hours (30 * 60 * 60 seconds).
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        final int DEFAULT_SESSION_TIMEOUT = 30 * 60 * 60; // 30 hours in seconds
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(DEFAULT_SESSION_TIMEOUT);
        session.setAttribute(key, value);
    }

    /**
     * Retrieves the value of a session attribute by its key.
     * Returns null if the attribute does not exist or session is invalidated.
     *
     * @param request the HttpServletRequest object from which to get the session
     * @param key     the name of the session attribute to retrieve
     * @return the Object stored in the session for the given key, or null if not found
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return session.getAttribute(key);
    }

    /**
     * Invalidates the current HTTP session if one exists.
     * This removes all session attributes and marks the session as invalid.
     *
     * @param request the HttpServletRequest object from which to get the session
     */
    public static void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Removes a session attribute by its key if the session exists.
     *
     * @param request the HttpServletRequest object from which to get the session
     * @param key     the name of the session attribute to remove
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }
}
