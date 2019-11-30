package com.example.smartcook;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingridient implements Parcelable {
    private int patiekalasID;
    private int produktasID;

    public Ingridient(int patiekalasID, int produktasID){
        this.patiekalasID = patiekalasID;
        this.produktasID = produktasID;
    }

    protected Ingridient(Parcel in) {
        patiekalasID = in.readInt();
        produktasID = in.readInt();
    }

    public static final Creator<Ingridient> CREATOR = new Creator<Ingridient>() {
        @Override
        public Ingridient createFromParcel(Parcel in) {
            return new Ingridient(in);
        }

        @Override
        public Ingridient[] newArray(int size) {
            return new Ingridient[size];
        }
    };

    public int getPatiekalasID() {
        return patiekalasID;
    }

    public void setPatiekalasID(int patiekalasID) {
        this.patiekalasID = patiekalasID;
    }

    public int getProduktasID() {
        return produktasID;
    }

    public void setProduktasID(int produktasID) {
        this.produktasID = produktasID;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(patiekalasID);
        dest.writeInt(produktasID);
    }
}
