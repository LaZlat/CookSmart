package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private ArrayList<User> usersList;
    private EditText loginName;
    private EditText passName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new DatabaseHelper(this);
        usersList = myDB.getUsers();

        passName = findViewById(R.id.loginPassText);
        loginName = findViewById(R.id.loginNameText);




    }

    public void regActivity(View view){
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        usersList = myDB.getUsers();
    }

    public void clickedLoginButton(View view){
        for (User x : usersList) {
            if (x.getUsername().equals(loginName.getText().toString()) && x.getPassword().equals(passName.getText().toString())) {
                Intent intent = new Intent(this, AddActivity.class);
                if(x.getAdmin() == 1){
                    intent.putExtra("IntVar", 1);
                }
                else{
                    intent.putExtra("IntVar",0);
                }
                Toast.makeText(LoginActivity.this,"Prisijungėte kaip "+ x.getUsername(), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
            else{
            }
        }

    }
}
