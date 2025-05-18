package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ArtisanRoots7.config.DbConfig;

public class RegistrationService {
	/**
	 * Checks if a username already exists in the database.
	 *
	 * @param username The username to check.
	 * @return true if the username exists, false otherwise.
	 * @throws Exception If a database access error occurs.
	 */
	public static boolean isUsernameExists(String username) throws Exception {
		// Initialize the exists flag to false
		boolean exists = false;

		// SQL query to count the number of users with the provided username
		String query = "SELECT COUNT(*) FROM user WHERE username = ?";

		try (
				// Get a database connection
				Connection conn = DbConfig.getConnection();

				// Prepare the SQL statement
				PreparedStatement stmt = conn.prepareStatement(query)) {
			// Set the username parameter for the SQL query
			stmt.setString(1, username);

			// Execute the query
			ResultSet rs = stmt.executeQuery();

			// Check if there is any result (if count > 0, the username exists)
			if (rs.next()) {
				exists = rs.getInt(1) > 0; // If count > 0, username exists
			}

		} catch (SQLException e) {
			// Print stack trace in case of SQL errors
			e.printStackTrace();
		}

		// Return the result (true if username exists, false otherwise)
		return exists;
	}

	/**
	 * Checks if an email already exists in the database.
	 *
	 * @param email The email to check.
	 * @return true if the email exists, false otherwise.
	 * @throws Exception If a database access error occurs.
	 */
	public static boolean emailExists(String email) throws Exception {
		// Initialize the exists flag to false
		boolean exists = false;

		// SQL query to count the number of users with the provided email
		String query = "SELECT COUNT(*) FROM user WHERE user_email = ?";

		try (
				// Get a database connection
				Connection conn = DbConfig.getConnection();

				// Prepare the SQL statement
				PreparedStatement stmt = conn.prepareStatement(query)) {
			// Set the email parameter for the SQL query
			stmt.setString(1, email);

			// Execute the query
			ResultSet rs = stmt.executeQuery();

			// Check if there is any result (if count > 0, the email exists)
			if (rs.next()) {
				exists = rs.getInt(1) > 0; // If count > 0, email exists
			}

		} catch (SQLException e) {
			// Print stack trace in case of SQL errors
			e.printStackTrace();
		}

		// Return the result (true if email exists, false otherwise)
		return exists;
	}

	/**
	 * Checks if a phone number already exists in the database.
	 *
	 * @param phoneNumber The phone number to check.
	 * @return true if the phone number exists, false otherwise.
	 * @throws Exception If a database access error occurs.
	 */
	public static boolean phoneExists(String phoneNumber) throws Exception {
		// Initialize the exists flag to false
		boolean exists = false;

		// SQL query to count the number of users with the provided phone number
		String query = "SELECT COUNT(*) FROM user WHERE user_phone_number = ?";

		try (
				// Get a database connection
				Connection conn = DbConfig.getConnection();

				// Prepare the SQL statement
				PreparedStatement stmt = conn.prepareStatement(query)) {
			// Set the phone number parameter for the SQL query
			stmt.setString(1, phoneNumber);

			// Execute the query
			ResultSet rs = stmt.executeQuery();

			// Check if there is any result (if count > 0, the phone number exists)
			if (rs.next()) {
				exists = rs.getInt(1) > 0; // If count > 0, phone number exists
			}

		} catch (SQLException e) {
			// Print stack trace in case of SQL errors
			e.printStackTrace();
		}

		// Return the result (true if phone number exists, false otherwise)
		return exists;
	}
}
