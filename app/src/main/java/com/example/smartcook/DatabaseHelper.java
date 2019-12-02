package com.example.smartcook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, "CookSmart.db",null,1);
    }


    public ArrayList getProducts(){
        ArrayList<Product> productList = new ArrayList<Product>();
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT ID, NAME, IMAGE FROM produktas_table", null);
            while (res.moveToNext()){
                Product mProduct = new Product(res.getInt(0),res.getString(1),res.getString(2));
                productList.add(mProduct);
            }
            db.close();
            return productList;
        } catch (SQLException e){ }
        return null;
    }

    public ArrayList getIngridients(){
        ArrayList<Ingridient> ingridientList = new ArrayList<Ingridient>();
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT PATIEKALAS_ID, PRODUKTAS_ID FROM ingridientas_table", null);
            while (res.moveToNext()){
                Ingridient iIngridient = new Ingridient(res.getInt(0),res.getInt(1));
                ingridientList.add(iIngridient);
            }
            db.close();
            return ingridientList;
        } catch (SQLException e){ }
        return null;
    }


    public ArrayList getDishes(){
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT ID, NAME,DESCRIBE,IMAGE FROM patiekalas_table", null);
            while (res.moveToNext()){
                Dish dDish = new Dish(res.getInt(0),res.getString(1),res.getString(3),res.getString(2));
                dishList.add(dDish);
            }
            db.close();
            return dishList;
        } catch (SQLException e){}
        return null;
    }


    public void addDish(Dish newDish, ArrayList<Product> productsList){
        int newDishID;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "INSERT INTO patiekalas_table (NAME, DESCRIBE, IMAGE) VALUES (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,newDish.getName());
            statement.bindString(2,newDish.getDescription());
            statement.bindString(3,newDish.getImage());

            long rowID = statement.executeInsert();

            newDishID = returnDishID(newDish);

            for(Product x : productsList){
                if(x.isChoose() == true) {
                    String sql1 = "INSERT INTO ingridientas_table (PATIEKALAS_ID, PRODUKTAS_ID) VALUES (?, ?)";
                    SQLiteStatement statement1 = db.compileStatement(sql1);

                    statement1.bindLong(1,newDishID);
                    statement1.bindLong(2,x.getId());

                    long rowID1 = statement1.executeInsert();
                }
            }
            db.close();
        } catch (SQLException e){}

    }

    public int returnDishID(Dish newDish){
        try{
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@2");
            System.out.println(newDish.getName());
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT ID FROM patiekalas_table WHERE NAME = '" + newDish.getName() + "'",null);
            res.moveToFirst();
            int newDishID = res.getInt(0);
            return newDishID;
        } catch (SQLException e){}
        return 0;
    }

    public Integer deleteDish(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT ID FROM patiekalas_table WHERE NAME = '" + name + "'",null);
        res.moveToFirst();
        int newDishID = res.getInt(0);

        db.delete("ingridientas_table", "PATIEKALAS_ID = ?", new String[] {String.valueOf(newDishID)});

        return db.delete("patiekalas_table","NAME = ?",new String[] {name});
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
