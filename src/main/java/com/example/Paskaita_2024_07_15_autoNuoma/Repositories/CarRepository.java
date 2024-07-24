package com.example.Paskaita_2024_07_15_autoNuoma.Repositories;


import com.example.Paskaita_2024_07_15_autoNuoma.Models.Car;
import com.example.Paskaita_2024_07_15_autoNuoma.Models.Filter;
import com.example.Paskaita_2024_07_15_autoNuoma.Models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CarRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;



    public String registerCar(Car car){

        String sql = "INSERT INTO car (user_id,title,make, model, year, millage, price, description, fuelType, image)\n" +
                "VALUES (?,?,?,?,?,?,?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,car.getUserEntityEmail());
            preparedStatement.setString(2,car.getTitle());
            preparedStatement.setString(3,car.getMake());
            preparedStatement.setString(4,car.getModel());
            preparedStatement.setString(5,car.getYear().toString());
            preparedStatement.setLong(6,car.getMillage());
            preparedStatement.setDouble(7,car.getPrice());
            preparedStatement.setString(8,car.getDescription());
            preparedStatement.setString(9,car.getFuelType());
            preparedStatement.setString(10,car.getImage());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return "Database connection failed";
        }

        return "Car was successfully added";

    }

    public String alterCar(Car car, Integer id){

        String sql = "UPDATE car SET " +
                     "user_id = ?, title = ?, make = ?, model = ?, year = ?, " +
                     "millage = ?, price = ?, description = ?, fuelType = ?, image = ? " +
                     "WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1,car.getUserEntityEmail());
            preparedStatement.setString(2,car.getTitle());
            preparedStatement.setString(3,car.getMake());
            preparedStatement.setString(4,car.getModel());
            preparedStatement.setString(5,car.getYear().toString());
            preparedStatement.setLong(6,car.getMillage());
            preparedStatement.setDouble(7,car.getPrice());
            preparedStatement.setString(8,car.getDescription());
            preparedStatement.setString(9,car.getFuelType());
            preparedStatement.setString(10,car.getImage());
            preparedStatement.setInt(11,id);

            preparedStatement.executeUpdate();

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return "Database connection failed";
        }

        return "Car was successfully updated";
    }


    public List<Car> getNewestCars(int offset, int limit){

        String sql = "SELECT * FROM car ORDER BY id DESC LIMIT ? OFFSET ? ";
        List<Car> carList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,limit);
            preparedStatement.setInt(2,offset);

            ResultSet resultSet =  preparedStatement.executeQuery();


            while (resultSet.next()){
                Car car = new Car();

                car.setId(resultSet.getLong("id"));
                car.setUserEntityEmail(resultSet.getLong("user_id"));
                car.setTitle(resultSet.getString("title"));
                car.setMake(resultSet.getString("make"));
                car.setModel(resultSet.getString("model"));

                LocalDate year = formatDate(resultSet.getString("year"));
                car.setYear(year);

                car.setMillage(resultSet.getLong("millage"));
                car.setPrice(resultSet.getDouble("price"));
                car.setDescription(resultSet.getString("description"));
                car.setFuelType(resultSet.getString("fuelType"));
                car.setImage(resultSet.getString("image"));

                carList.add(car);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        return carList;
    }

    public List<Car> getFilteredCars(int offset, int limit, Filter filter){


        if(filter.getMake() == null || filter.getMake().isEmpty()) filter.setMake("%");
        if(filter.getModel() == null || filter.getModel().isEmpty()) filter.setModel("%");
        if(filter.getMillage() == 0) filter.setMillage(99999999);
        if(filter.getMinPrice() == 0) filter.setMinPrice(0);
        if(filter.getMaxPrice() == 0 ) filter.setMaxPrice(99999999);

        String yearFilter;
        if(filter.getYear() == null) yearFilter = "%";
        else yearFilter = filter.getYear().getYear() + "%";

        String sql = "SELECT * FROM car " +
                    "WHERE make LIKE ? " +
                    "AND model LIKE ?" +
                    "AND year LIKE ? " +
                    "AND millage < ? " +
                    "AND price >= ? " +
                    "AND price <= ? " +
                    "ORDER BY id " +
                    "DESC LIMIT ? OFFSET ? ";

        List<Car> carList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,filter.getMake());
            preparedStatement.setString(2,filter.getModel());
            preparedStatement.setString(3,yearFilter);
            preparedStatement.setLong(4,filter.getMillage());
            preparedStatement.setLong(5,filter.getMinPrice());
            preparedStatement.setLong(6,filter.getMaxPrice());
            preparedStatement.setInt(7,limit);
            preparedStatement.setInt(8,offset);

            ResultSet resultSet =  preparedStatement.executeQuery();


            while (resultSet.next()){
                Car car = new Car();

                car.setId(resultSet.getLong("id"));
                car.setUserEntityEmail(resultSet.getLong("user_id"));
                car.setTitle(resultSet.getString("title"));
                car.setMake(resultSet.getString("make"));
                car.setModel(resultSet.getString("model"));

                LocalDate year = formatDate(resultSet.getString("year"));
                car.setYear(year);

                car.setMillage(resultSet.getLong("millage"));
                car.setPrice(resultSet.getDouble("price"));
                car.setDescription(resultSet.getString("description"));
                car.setFuelType(resultSet.getString("fuelType"));
                car.setImage(resultSet.getString("image"));

                carList.add(car);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        return carList;
    }


    public Car getCarById(int id){
        String sql = "SELECT * FROM car WHERE id = ?";

       Car car = new Car();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet resultSet =  preparedStatement.executeQuery();

            resultSet.next();

            car.setId(resultSet.getLong("id"));
            car.setUserEntityEmail(resultSet.getLong("user_id"));
            car.setTitle(resultSet.getString("title"));
            car.setMake(resultSet.getString("make"));
            car.setModel(resultSet.getString("model"));

            LocalDate year = formatDate(resultSet.getString("year"));
            car.setYear(year);

            car.setMillage(resultSet.getLong("millage"));
            car.setPrice(resultSet.getDouble("price"));
            car.setDescription(resultSet.getString("description"));
            car.setFuelType(resultSet.getString("fuelType"));
            car.setImage(resultSet.getString("image"));

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return new Car();
        }

        return car;
    }

    public String deleteCarById(int id){

        String sql = "DELETE FROM car WHERE id = ?;";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return "Database connection failed";
        }

        return "Car was successfully deleted";
    }


    public List<String> getAllMakes(){

        List<String> makeList = new ArrayList<>();

        String sql = "SELECT make FROM car GROUP BY make";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){
                String make = resultSet.getString(1);
                makeList.add(make);
            }

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        return makeList;
    }

    public List<String> getAllModelsByMake(String model){

        List<String> modelList = new ArrayList<>();

        String sql = "SELECT model FROM car WHERE make = ? GROUP BY model;";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,model);

            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){
                String modelResult = resultSet.getString(1);
                modelList.add(modelResult);
            }

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        return modelList;
    }


    public long getPrice(String make, String model, String parameter ){

        String select;
        long price;

        if(make == null) make = "%";
        if(model == null) model = "%";

        if(parameter.equals("MIN"))  select = "MIN(price)";
        else if (parameter.equals("MAX")) select = "MAX(price)";
        else return 0L;

        String sql = "SELECT " + select + " FROM car WHERE make LIKE ? AND model LIKE ?;";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            ResultSet resultSet =  preparedStatement.executeQuery();
            resultSet.next();

            price = resultSet.getLong(1);

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return 0;
        }
        return price;
    }



    private LocalDate formatDate(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate year = null;
        try {
            year = LocalDate.parse(dateTime, dateTimeFormatter);
        }catch(DateTimeParseException | NullPointerException e) {
            year = LocalDate.parse("1900-01-01",dateTimeFormatter);
        }
        return year;
    }





}
