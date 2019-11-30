package com.example.smartcook;

public class Ingridient {
    private int patiekalasID;
    private int produktasID;

    public Ingridient(int patiekalasID, int produktasID){
        this.patiekalasID = patiekalasID;
        this.produktasID = produktasID;
    }

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
}
