package com.example.smartcook;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    protected int id;
    protected String name;
    protected String image;
    protected boolean choose;

    public Product() {
    }

    public Product(int id, String name, String image){
        this.id = id;
        this.name = name;
        this.image = image;
        choose = false;
    }


    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        choose = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeByte((byte) (choose ? 1 : 0));
    }
}
