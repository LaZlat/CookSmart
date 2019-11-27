package com.example.smartcook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, "CookSmart.db",null,1);
    }


    public ArrayList getProducts(){
        ArrayList<Product> productList = new ArrayList<Product>();
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM produktas_table", null);
            while (res.moveToNext()){
                Product mProduct = new Product(res.getString(1),res.getString(2));
                productList.add(mProduct);
            }
            return productList;
        } catch (SQLException e){ }
        return null;
    }


    public ArrayList getDishes(){
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM patiekalas_table", null);
            while (res.moveToNext()){
                Dish dDish = new Dish(res.getString(1),res.getString(3),res.getString(2));
                dishList.add(dDish);
            }
            return dishList;
        } catch (SQLException e){}
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + "patiekalas_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIBE TEXT, IMAGE TEXT)");
        db.execSQL("CREATE TABLE " + "produktas_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, IMAGE TEXT)");
        db.execSQL("CREATE TABLE " + "ingridientas_table" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PATIEKALAS_ID INTEGER, PRODUKTAS_ID INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "patiekalas_table");
        db.execSQL("DROP TABLE IF EXISTS " + "produktas_table");
        db.execSQL("DROP TABLE IF EXISTS " + "ingridientas_table");
        onCreate(db);
    }
}
