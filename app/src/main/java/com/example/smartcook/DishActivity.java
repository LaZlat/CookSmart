package com.example.smartcook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        if(savedInstanceState != null){
            productList = savedInstanceState.getParcelableArrayList("savedInstance1");
            dishList = savedInstanceState.getParcelableArrayList("savedInstance2");
            ingridientList = savedInstanceState.getParcelableArrayList("savedInstance3");
        }

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

        dAdapter.setOnItemClickListiner(new DishAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                intent.putExtra("Dish",dishList.get(position));
                intent.putParcelableArrayListExtra("Products", productList);
                intent.putParcelableArrayListExtra("Ingridients", ingridientList);
                startActivity(intent);

            }
        });


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("savedInstance1", productList);
        outState.putParcelableArrayList("savedInstance2", dishList);
        outState.putParcelableArrayList("savedInstance3", ingridientList);
    }
}
