package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {
    private Dish currentDish = new Dish();
    private ArrayList<Ingridient> ingridientsList;
    private ArrayList<Product> productsList;

    private AboutAdapter aAdapter;
    private RecyclerView.LayoutManager aLayoutManager;

    private TextView aboutTextView;
    private ImageView aboutImageView;
    private RecyclerView aboutRecyclerView;
    private TextView aboutMultilineText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Intent intent = getIntent();
        currentDish = intent.getParcelableExtra("Dish");
        ingridientsList = intent.getParcelableArrayListExtra("Ingridients");
        productsList = intent.getParcelableArrayListExtra("Products");

//        accessSmallAdapter();

        populateView();

    }

    public void accessSmallAdapter(ArrayList<Product> currentDishsProducts){
        aboutRecyclerView = findViewById(R.id.aboutRecyclerView);
        aboutRecyclerView.setHasFixedSize(true);
        aLayoutManager = new LinearLayoutManager(this);
        aAdapter = new AboutAdapter(currentDishsProducts);

        aboutRecyclerView.setLayoutManager(aLayoutManager);
        aboutRecyclerView.setAdapter(aAdapter);
    }


    public void populateView(){

        ArrayList<Product> currentDishsProducts = new ArrayList<>();

        aboutTextView = findViewById(R.id.aboutTextView);
        aboutImageView = findViewById(R.id.aboutImageView);
        aboutMultilineText = findViewById(R.id.aboutMultiLine);

        aboutTextView.setText(currentDish.getName());
        aboutMultilineText.setText(currentDish.getDescription());

        Uri path = Uri.parse(currentDish.getImage());
        String sPath = path.toString();
        Picasso.get().load(sPath).into(aboutImageView);

        for(Ingridient x : ingridientsList){
            if(x.getPatiekalasID() == currentDish.getId()){
                for (Product y : productsList){
                    if(x.getProduktasID() == y.getId() && y.isChoose() == true){
                        currentDishsProducts.add(y);
                    }
                }
            }
        }

        accessSmallAdapter(currentDishsProducts);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("withBoolean", productsList);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("withBoolean", productsList);
                finish();
                break;
        }
        return true;

    }
}
