package com.example.Paskaita_2024_07_15_autoNuoma.Repositories;


import com.example.Paskaita_2024_07_15_autoNuoma.Models.User;
import com.example.Paskaita_2024_07_15_autoNuoma.Security.JwtGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;


@Repository
public class UserRepository {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;



    public String registerUser(User user){

        if(user.getName() == null || user.getUsername() == null || user.getPassword() == null) return "Invalid data";

        String sql = "INSERT INTO user (name,username,password)\n" +
                "VALUES (?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3,user.getPassword());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            if (e.getErrorCode() == 1062) return "User already exists";

            return "Database connection failed";
        }

        return "Car was successfully added ";

    }

    public String updateUser(User user, Long id){

        if(user.getName() == null || user.getPassword() == null) return "Invalid data";

        String sql = "UPDATE user SET name = ?, password = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setLong(3,id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            if (e.getErrorCode() == 1062) return "User already exists";

            return "Database connection failed";
        }

        return "Car was successfully added ";

    }


    public String login(User user){

        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            ResultSet resultSet =  preparedStatement.executeQuery();

            if(!resultSet.next()) return "Invalid username or password";
            user.setId(resultSet.getInt("id"));

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return "Database connection failed";
        }

        return JwtGenerator.generateJwt(user.getId());
    }


    public String getUserId(String user){


        String UserId;
        String sql = "SELECT id FROM user WHERE username = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user);
            ResultSet resultSet =  preparedStatement.executeQuery();

            if(!resultSet.next()) return "Invalid username or password";
            UserId = resultSet.getInt("id") + "";

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return "Database connection failed";
        }

        return UserId;
    }




}
