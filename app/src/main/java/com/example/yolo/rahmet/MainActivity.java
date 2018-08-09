package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView cash_u, cashback_u;

        cash_u = findViewById(R.id.schet_txt);
        cashback_u = findViewById(R.id.bonus_txt);

        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";

        JSONObject obj = new JSONObject();

        try {
            obj.put("procedure", "getMoney");
            obj.put("username", StartPage.username_global);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Response");

                    int aksha = jsonArray.getJSONObject(0).getInt("cash");
                    int back = jsonArray.getJSONObject(0).getInt("cash_back");

                    cash_u.setText(String.valueOf(aksha));
                    cashback_u.setText(String.valueOf(back));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError goodnews) {
                Toast.makeText(MainActivity.this, goodnews.toString(), Toast.LENGTH_LONG).show();

            }
        });
        MySingleton.getmInstance(MainActivity.this).addToRequestque(jsonObjectRequest);
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
