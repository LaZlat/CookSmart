package com.example.smartcook;

import java.util.ArrayList;

public class Dish extends Product {
    protected String description;


    public Dish(int id, String name, String image, String description) {
        super(id, name, image);
        this.description = description;
        this.choose = true;
    }

    public Dish() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList sortDishes(ArrayList<Dish>dishList, ArrayList<Product> productList, ArrayList <Ingridient> ingridientList) {
            for (Dish x : dishList){
                for (Ingridient y : ingridientList){
                    if(x.getId() == y.getPatiekalasID()){
                        for(Product z : productList){
                            if(z.getId() == y.getProduktasID() && z.isChoose() == false){
                                x.setChoose(false);
                            }
                        }
                    }
                }
            }
        return dishList;
    }

}

