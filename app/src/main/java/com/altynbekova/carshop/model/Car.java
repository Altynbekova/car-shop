package com.altynbekova.carshop.model;

public class Car {
    private String brand;
    private String model;
    private int year;
    private String description;
    private int cost;
    private String photo;

    public Car(String brand, String model, int year, String description, int cost, String photo) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.description = description;
        this.cost = cost;
        this.photo = photo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
