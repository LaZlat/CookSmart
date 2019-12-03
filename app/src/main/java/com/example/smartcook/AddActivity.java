package com.example.smartcook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    private int STORAGE_PREMISSION_CODE = 1;

    private int loginInfo;
    private String aImageStringPath;
    private Boolean isProductOk;

    private Button delButton;
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

        Intent intent = getIntent();
        loginInfo = intent.getIntExtra("IntVar",0);

        productList = myDB.getProducts();

        uploadButton = findViewById(R.id.uploadButton);
        previewImage = findViewById(R.id.previewImage);
        pavadinimasText = findViewById(R.id.pavadinimasText);
        receptasEditText = findViewById(R.id.receptasText);
        produktasRecyclerView = findViewById(R.id.produktasRecyclerView);
        delButton = findViewById(R.id.delButton);

        if(loginInfo == 0){
            delButton.setEnabled(false);
        }

        accessAdapter();
        focusChange();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                }
                else {
                    ActivityCompat.requestPermissions(AddActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PREMISSION_CODE);
                }
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

        if(!pavadinimasText.getText().toString().isEmpty() && !receptasEditText.getText().toString().isEmpty() && aImageStringPath != null && isProductOk==true ) {
            newDish.setName(pavadinimasText.getText().toString());
            newDish.setDescription(receptasEditText.getText().toString());
            newDish.setImage("file://" + aImageStringPath);

            myDB.addDish(newDish, productList);

            Toast toast = Toast.makeText(getApplicationContext(), "Pridėtas naujas patiekalas!", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            Toast toast = Toast.makeText(getApplicationContext(), "Įveskite visus duomenis!", Toast.LENGTH_SHORT);
            toast.show();
        }
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
                    isProductOk = true;
                    aAdapter.notifyItemChanged(position);

                }
                else if(productList.get(position).isChoose() == true) {
                    productList.get(position).setChoose(false);
                    isProductOk = false;
                    aAdapter.notifyItemChanged(position);
                }
            }
        });
    }

}
