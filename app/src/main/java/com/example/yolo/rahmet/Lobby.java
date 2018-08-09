package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Lobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }

    public void onButtonFillKazanClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   FillKazan.class);
        startActivity(myIntent);
    }

    public void onButtonAbortClick(View v){
        //some action
        Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
        startActivity(myIntent);
    }

    public void onButtonEndClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Success.class);
        startActivity(myIntent);
    }
}
