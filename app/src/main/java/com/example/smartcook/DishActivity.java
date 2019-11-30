package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class DishActivity extends AppCompatActivity {
    private RecyclerView dRecyclerView;
    private DishAdapter dAdapter;
    private RecyclerView.LayoutManager dLayoutmanager;

    private ArrayList<Dish> dishList;
    private ArrayList<Product> productList;
    private ArrayList<Ingridient> ingridientList;
    private DatabaseHelper myDB;
    private Dish choosenDishes = new Dish();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        Intent intent = getIntent();
        productList = intent.getParcelableArrayListExtra("withBoolean");

        myDB = new DatabaseHelper(this);
        dishList = myDB.getDishes();
        ingridientList = myDB.getIngridients();

        dishList = choosenDishes.sortDishes(dishList,productList,ingridientList);
        checkForTruth();
        accessAdapter();

    }


    public void checkForTruth(){
        for (Dish x : dishList){
            if(x.isChoose() == true){
                accessAdapter();
                return;
            }
        }
        Intent intent = new Intent(this, HungryActivity.class);
        startActivity(intent);
        finish();
    }

    public void accessAdapter(){


        dRecyclerView = findViewById(R.id.dishRecyclerView);
        dRecyclerView.setHasFixedSize(true);
        dLayoutmanager = new LinearLayoutManager(this);
        dAdapter = new DishAdapter(dishList);

        dRecyclerView.setLayoutManager(dLayoutmanager);
        dRecyclerView.setAdapter(dAdapter);
    }
}
