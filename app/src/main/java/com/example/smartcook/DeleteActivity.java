package com.example.smartcook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private ArrayList<Dish> dishList;
    private DatabaseHelper mydb;
    private EditText delName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        delName = findViewById(R.id.delText);

        mydb = new DatabaseHelper(this);
        dishList = mydb.getDishes();

    }

    public void deleteDish(View v){
        Integer result = mydb.deleteDish(delName.getText().toString());
        if(result > 0) {
            Toast.makeText(DeleteActivity.this,"Patiekalas ištrintas", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(DeleteActivity.this,"Patiekalas nerastas", Toast.LENGTH_SHORT).show();
        }
    }

}
