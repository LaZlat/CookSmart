package com.example.smartcook;

public class Dish extends Product {
    protected String description;


    public Dish(String name, String image, String description) {
        super(name, image);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
