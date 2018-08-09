package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FillKazan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_kazan);
    }

    public void onButtonBackClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Lobby.class);
        startActivity(myIntent);
    }

    public void onButtonPayClick(View v){
        //some action
        Intent myIntent = new Intent(getBaseContext(),   Lobby.class);
        startActivity(myIntent);
    }
}
