package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ArrayList<Product> haha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        productList = myDB.getProducts();
        accessAdapter();

    }

    public void accessAdapter(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(this);
        mAdapter = new ProductAdapter(productList);

        mRecyclerView.setLayoutManager(mLayoutmanager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListiner(new ProductAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(int position) {
                if(productList.get(position).isChoose() == false) {
                    productList.get(position).setChoose(true);

                    System.out.println(productList.get(position).getName());
                    ImageView img = findViewById(R.id.checkImageView);
                    img.setImageResource(R.drawable.ic_check);
                    mAdapter.notifyItemChanged(position);


                }
                else if(productList.get(position).isChoose() == true) {
                    productList.get(position).setChoose(false);
                    System.out.println(productList.get(position).getName());

                    ImageView img = findViewById(R.id.checkImageView);
                    img.setImageResource(0);
                    mAdapter.notifyItemChanged(position);

                }
            }
        });
    }
}
