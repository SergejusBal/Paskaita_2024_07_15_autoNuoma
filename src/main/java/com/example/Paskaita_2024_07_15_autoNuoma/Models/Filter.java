package com.example.Paskaita_2024_07_15_autoNuoma.Models;

import java.time.LocalDate;

public class Filter {

    private String make;
    private String model;
    private long minPrice;
    private long maxPrice;
    private long millage;
    private LocalDate year;

    public Filter() {
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public long getMillage() {
        return millage;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMillage(long millage) {
        this.millage = millage;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }
}
