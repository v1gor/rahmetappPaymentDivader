package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonPayClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Host.class);
        startActivity(myIntent);
    }

    public void onButtonJoinClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Join.class);
        startActivity(myIntent);
    }
}
