package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void onButtonBackClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
        startActivity(myIntent);
    }

    public void onButtonJoinClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Lobby.class);
        startActivity(myIntent);
    }
}