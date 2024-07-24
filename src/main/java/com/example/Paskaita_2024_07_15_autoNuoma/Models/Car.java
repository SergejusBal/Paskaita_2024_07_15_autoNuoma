package com.example.Paskaita_2024_07_15_autoNuoma.Models;

import java.time.LocalDate;

public class Car {

    private long id;
    private long userEntityEmail;
    private String title;
    private String make;
    private String model;
    private LocalDate year;
    private long millage;
    private double price;
    private String description;
    private String fuelType;
    private String image;

    public Car() {
    }


    public long getId() {
        return id;
    }

    public long getUserEntityEmail() {
        return userEntityEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public LocalDate getYear() {
        return year;
    }

    public long getMillage() {
        return millage;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getImage() {
        return image;
    }


    public void setUserEntityEmail(long userEntityEmail) {
        this.userEntityEmail = userEntityEmail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public void setMillage(long millage) {
        this.millage = millage;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(long id) {
        this.id = id;
    }
}
