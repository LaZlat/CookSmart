package com.example.smartcook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper myDB;
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;
    private ArrayList<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        productList = myDB.getProducts();
        accessAdapter();

        if(savedInstanceState != null){
            productList = savedInstanceState.getParcelableArrayList("savedInstance");
        }

    }

    public void nextDishActivity(View view){
        Intent intent = new Intent(this,DishActivity.class);
        intent.putParcelableArrayListExtra("withBoolean", productList);
        startActivity(intent);
        isDestroyed();
    }

    public void clearAllSelection(View view){

        for(Product x : productList){
            x.setChoose(false);
        }
        mAdapter.notifyDataSetChanged();

    }



    public void accessAdapter(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutmanager = new GridLayoutManager(this,3);
        mAdapter = new ProductAdapter(productList);

        mRecyclerView.setLayoutManager(mLayoutmanager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListiner(new ProductAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(int position) {
                if(productList.get(position).isChoose() == false) {
                    productList.get(position).setChoose(true);

                    mAdapter.notifyItemChanged(position);

                }
                else if(productList.get(position).isChoose() == true) {
                    productList.get(position).setChoose(false);

                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("savedInstance", productList);
    }
}
