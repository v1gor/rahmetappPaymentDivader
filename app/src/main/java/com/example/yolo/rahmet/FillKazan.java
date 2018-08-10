package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FillKazan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_kazan);

        final Intent intent = getIntent();
        final String aty = intent.getStringExtra("NAME");
        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";
        final Button pay_button;
        final EditText edit_sum;
        final TextView error_mes, schet;

        edit_sum = (EditText)findViewById(R.id.edit_sum);
        pay_button = (Button)findViewById(R.id.pay_button);
        error_mes = (TextView)findViewById(R.id.error);
        schet = (TextView)findViewById(R.id.schet_txt);

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
                    schet.setText(String.valueOf(aksha));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError goodnews) {
                Toast.makeText(FillKazan.this, goodnews.toString(), Toast.LENGTH_LONG).show();

            }
        });
        MySingleton.getmInstance(FillKazan.this).addToRequestque(jsonObjectRequest);


        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();

                try {
                    obj.put("username",StartPage.username_global);
                    obj.put("procedure", "fill_kazan");
                    obj.put("name_of_kazan", aty);
                    obj.put("client_price", Integer.parseInt(edit_sum.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Response");
                            int success = jsonArray.getJSONObject(0).getInt("success");
                            String text = jsonArray.getJSONObject(0).getString("text");

                            if (success == 1)
                            {
                                Intent intent2 = new Intent(FillKazan.this, Lobby.class);
                                intent2.putExtra("NAME", aty);
                                startActivity(intent2);
                            }
                            else
                                {
                                    error_mes.setText("Ввдена сумма больше нужного");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError goodnews) {
                        Toast.makeText(FillKazan.this, goodnews.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                MySingleton.getmInstance(FillKazan.this).addToRequestque(jsonObjectRequest);
            }
        });

    }

    public void onButtonBackClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Lobby.class);
        startActivity(myIntent);
    }


}
