package com.example.smartcook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Dish extends Product implements Parcelable {
    protected String description;


    public Dish(int id, String name, String image, String description) {
        super(id, name, image);
        this.description = description;
        this.choose = true;
    }

    public Dish() {
    }

    protected Dish(Parcel in) {
        super(in);
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList sortDishes(ArrayList<Dish>dishList, ArrayList<Product> productList, ArrayList <Ingridient> ingridientList) {
        ArrayList<Dish> newDishList = new ArrayList<>();
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

        for (Dish x :dishList){
            if(x.isChoose() == true){
                newDishList.add(x);
            }
        }
        return newDishList;
    }


    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                '}';
    }
}

