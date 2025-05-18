package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.UserModel;

public class UserManageService {

    /**
     * Retrieves a list of up to 20 users from the database.
     *
     * @return A list of UserModel objects representing users.
     * @throws Exception If a database access error occurs.
     */
    public List<UserModel> displayUsers() throws Exception {
        // Create a list to store the user data
        List<UserModel> userList = new ArrayList<>();

        // Establish database connection
        Connection con = DbConfig.getConnection();

        // SQL query to fetch user records (limit to 20)
        String sql = "SELECT * FROM user LIMIT 20";

        // Create a Statement object to execute the query
        Statement st = con.createStatement();

        // Execute the query and store the result in ResultSet
        ResultSet rs = st.executeQuery(sql);

        // Loop through the result set and create UserModel objects
        while (rs.next()) {
            UserModel user = new UserModel(
                rs.getInt("user_id"),
                rs.getString("user_first_name"),
                rs.getString("user_last_name"),
                rs.getString("username"),
                rs.getString("user_phone_number"),
                rs.getDate("user_dob").toLocalDate(),
                rs.getString("user_email"),
                rs.getString("user_roles"),
                rs.getString("user_password"),
                rs.getString("user_gender"),
                rs.getDate("date_joined").toLocalDate(),
                rs.getString("user_status"),
                rs.getString("image_path")
            );

            // Add each user to the list
            userList.add(user);
        }

        // Close the resources to prevent memory leaks
        rs.close();
        st.close();
        con.close();

        // Return the list of users
        return userList;
    }
}
