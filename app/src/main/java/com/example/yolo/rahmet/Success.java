package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        final TextView sum_of_payment, user_pay, user_back;
        final Button okay;

        sum_of_payment = (TextView) findViewById(R.id.sum_of_payment);
        user_pay = (TextView) findViewById(R.id.user_pay);
        user_back = (TextView) findViewById(R.id.user_back);
        okay = (Button) findViewById(R.id.okay);

        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";
        Intent myIntent = getIntent();
        final String aty = myIntent.getStringExtra("NAME");
        JSONObject obj = new JSONObject();

        try {
            obj.put("procedure", "end");
            obj.put("name_of_kazan", aty);
            obj.put("username", StartPage.username_global);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Response");

                    int sum = jsonArray.getJSONObject(0).getInt("sum_of_kazan");
                    int user_sum = jsonArray.getJSONObject(0).getInt("your_payment");
                    int back = jsonArray.getJSONObject(0).getInt("your_cash_back");

                    sum_of_payment.setText("Sum: " + String.valueOf(sum));
                    user_pay.setText("You payed: " + String.valueOf(user_sum));
                    user_back.setText("Your cashback is: " + String.valueOf(back));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError goodnews) {
                Toast.makeText(Success.this, goodnews.toString(), Toast.LENGTH_LONG).show();

            }
        });
        MySingleton.getmInstance(Success.this).addToRequestque(jsonObjectRequest);



        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Success.this, Screen.class);
                startActivity(intent);
            }
        });


    }
}
