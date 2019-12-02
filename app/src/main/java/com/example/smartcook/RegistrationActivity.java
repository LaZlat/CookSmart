package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    private DatabaseHelper mydb;
    private EditText regNameText, regPassText, regPassText2, regRootNameText, regRootPassText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mydb = new DatabaseHelper(this);

        regNameText = findViewById(R.id.regNameText);
        regPassText = findViewById(R.id.regPassText);
        regPassText2 = findViewById(R.id.regPassText2);
        regRootNameText = findViewById(R.id.regRootNameText);
        regRootPassText = findViewById(R.id.regRootPassText);

    }

    public void clickRegister(View view){
        User rootUser = mydb.getRootUser();

        if(regPassText.getText().toString().equals(regPassText2.getText().toString())){
            if(rootUser.getUsername().equals(regRootNameText.getText().toString()) && rootUser.getPassword().equals(regRootPassText.getText().toString())){
                mydb.addUser(regNameText.getText().toString(),regPassText.getText().toString(),1);
                Toast.makeText(RegistrationActivity.this,"Priregistruotas naujas administratorius", Toast.LENGTH_SHORT).show();
            }
            else if(regRootNameText.getText().toString().isEmpty() && regRootPassText.getText().toString().isEmpty()){
                mydb.addUser(regNameText.getText().toString(),regPassText.getText().toString(),0);
                Toast.makeText(RegistrationActivity.this,"Priregistruotas naujas vartotojas", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RegistrationActivity.this,"Klaidingi Root duomenys", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(RegistrationActivity.this,"Nesutampa slaptažodžiai", Toast.LENGTH_SHORT).show();

        }
        finish();
    }
}
