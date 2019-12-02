package com.example.smartcook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    private static final int SELECT_PHOTO = 100;

    private String aImageStringPath;

    private Button uploadButton;
    private EditText pavadinimasText;
    private EditText receptasEditText;
    private ImageView previewImage;

    private ArrayList<Product> productList;
    private DatabaseHelper myDB;
    private UriChanger newUri = new UriChanger();

    private AboutAdapter aAdapter;
    private RecyclerView.LayoutManager aLayoutManager;
    private RecyclerView produktasRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        myDB = new DatabaseHelper(this);

        productList = myDB.getProducts();

        uploadButton = findViewById(R.id.uploadButton);
        previewImage = findViewById(R.id.previewImage);
        pavadinimasText = findViewById(R.id.pavadinimasText);
        receptasEditText = findViewById(R.id.receptasText);
        produktasRecyclerView = findViewById(R.id.produktasRecyclerView);

        accessAdapter();
        focusChange();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });




    }

    public void startDeleteActivity(View v){
        Intent intent = new Intent(this,DeleteActivity.class);
        startActivity(intent);
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PHOTO){
                Uri aaImageUri = data.getData();
            aImageStringPath = newUri.getRealPathFromURI(data.getData(),this);
            Picasso.get().load(aaImageUri).fit().centerCrop().into(previewImage);
            }
        }
    }



    public void addNewDishToDB(View v){
        Dish newDish = new Dish();

        newDish.setName(pavadinimasText.getText().toString());
        newDish.setDescription(receptasEditText.getText().toString());
        newDish.setImage("file://"+aImageStringPath);

        myDB.addDish(newDish,productList);

        Toast toast = Toast.makeText(getApplicationContext(), "Pridėtas naujas patiekalas!",Toast.LENGTH_SHORT);
        toast.show();
    }



    public void focusChange(){
        pavadinimasText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        receptasEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void accessAdapter(){
        produktasRecyclerView.setHasFixedSize(true);
        aLayoutManager = new LinearLayoutManager(this);
        aAdapter = new AboutAdapter(productList);

        produktasRecyclerView.setLayoutManager(aLayoutManager);
        produktasRecyclerView.setAdapter(aAdapter);

        aAdapter.setOnItemClickListiner(new AboutAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(View v, int position) {
                if(productList.get(position).isChoose() == false) {
                    productList.get(position).setChoose(true);

                    aAdapter.notifyItemChanged(position);

                }
                else if(productList.get(position).isChoose() == true) {
                    productList.get(position).setChoose(false);

                    aAdapter.notifyItemChanged(position);
                }
            }
        });
    }


}
