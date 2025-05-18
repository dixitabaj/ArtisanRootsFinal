 package com.ArtisanRoots7.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbConfig is a configuration class for managing database connections for ArtisanRoots.
 * It handles the connection to a MySQL database using JDBC.
 */

public class DbConfig {
	// Database configuration constants
		private static final String URL =  "jdbc:mysql://localhost:3306/artisian_roots1";
		private static final String USERNAME = "root";
		private static final String PASSWORD = "";
		/**
	     * Establishes a connection to the ArtisanRoots database.
	     *
	     * @return Connection object for the database
	     * @throws SQLException           if a database access error occurs
	     * @throws ClassNotFoundException if the JDBC driver class is not found
	     */
		public static Connection getConnection() throws SQLException, ClassNotFoundException {
			 System.out.println("Attempting to connect to database...");

		        // Load the MySQL driver
		        Class.forName("com.mysql.cj.jdbc.Driver");

		        // Establish the connection
		        Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		        System.out.println("Connected successfully!");
		        return con;
		}
}
