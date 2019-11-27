package com.example.smartcook;

public class Product {
    protected String name;
    protected String image;
    protected boolean choose;

    public Product(String name, String image){
        this.name = name;
        this.image = image;
        choose = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
