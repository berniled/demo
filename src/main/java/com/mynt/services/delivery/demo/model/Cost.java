package com.mynt.services.delivery.demo.model;

import java.util.Optional;

public class Cost {
    private String name; 
    private double weight; //in kilogram
    private double height; //in cm
    private double width; //in cm
    private double length; //in cm
    private double cost; //in PHP currency
    private Optional<String> discountCode;
    private String status;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public Optional<String> getDiscountCode() {
        return discountCode;
    }
    public void setDiscountCode(Optional<String> discountCode) {
        this.discountCode = discountCode;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    

}
