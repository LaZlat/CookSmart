package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        productList = myDB.getProducts();
        accessAdapter();

    }

    public void nextDishActivity(View view){
        Intent intent = new Intent(this,DishActivity.class);
        intent.putParcelableArrayListExtra("withBoolean", productList);
        startActivity(intent);
        finish();
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

                    mAdapter.notifyDataSetChanged();

                }
                else if(productList.get(position).isChoose() == true) {
                    productList.get(position).setChoose(false);
//
                    mAdapter.notifyDataSetChanged();

                }
            }
        });
    }
}
