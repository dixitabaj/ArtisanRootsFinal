package com.ArtisanRoots7.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ArtisanRoots7.config.DbConfig;
import com.ArtisanRoots7.model.UserModel;

public class UserManageService {
    public List<UserModel> displayUsers() throws Exception {
        List<UserModel> userList = new ArrayList<>();
        Connection con = DbConfig.getConnection();
        String sql = "SELECT * FROM user";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

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
            userList.add(user);
        }

        rs.close();
        st.close();
        con.close();

        return userList;
    }
}

		
	

