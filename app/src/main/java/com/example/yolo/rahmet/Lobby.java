package com.example.yolo.rahmet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

public class Lobby extends AppCompatActivity {
    String aty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        final Button fill_button;
        TextView name_of_k = (TextView)findViewById(R.id.name_k);
        final TextView all_sum = (TextView)findViewById(R.id.all_sum_k);
        final TextView left_sum = (TextView)findViewById(R.id.left_sum);
        final TextView history_txt;
        history_txt = (TextView)findViewById(R.id.history_txt);
        history_txt.setMovementMethod(new ScrollingMovementMethod());
        final Button refresh_button;
        final Button btn_end;
        refresh_button = (Button)findViewById(R.id.button2);
        btn_end = (Button)findViewById(R.id.button5);
        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";

        Intent intent = getIntent();
        aty = intent.getStringExtra("NAME");
        name_of_k.setText(aty);

        JSONObject obj = new JSONObject();

        try {
            obj.put("procedure", "refresh");
            obj.put("name_of_kazan", aty);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Response");
                    int left_price = jsonArray.getJSONObject(0).getInt("left_price");
                    int price = jsonArray.getJSONObject(0).getInt("price");
                    JSONObject his = jsonArray.getJSONObject(0);
                    JSONArray history_of_payment  = his.getJSONArray("history");

                    all_sum.setText(String.valueOf(price));
                    if (left_price == 0)
                    {
                        left_sum.setText("Казан успешно пополнен:) нажмите кнопу завершить");
                    }
                    else
                    {
                        left_sum.setText(String.valueOf(left_price));
                    }
                    btn_end.setEnabled(left_price == 0);



                    String text = "";

                    for (int j = 0; j<his.length(); j++)
                    {
                        JSONObject last_his = history_of_payment.getJSONObject(j);
                        String username_l = last_his.getString("username");
                        int payy = last_his.getInt("payed");
                        text = text.concat(username_l + " закинул " + String.valueOf(payy) + " тенге\n");
                        history_txt.setText(text);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError goodnews) {
                Toast.makeText(Lobby.this, goodnews.toString(), Toast.LENGTH_LONG).show();

            }
        });
        MySingleton.getmInstance(Lobby.this).addToRequestque(jsonObjectRequest);




        fill_button = (Button)findViewById(R.id.fill_kazan);
        fill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView name_of_k = (TextView)findViewById(R.id.name_k);
                Intent intent = new Intent(Lobby.this, FillKazan.class);
                String name_of_kazan = name_of_k.getText().toString();
                intent.putExtra("NAME", name_of_kazan);
                startActivity(intent);
            }
        });




        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();

                try {
                    obj.put("procedure", "refresh");
                    obj.put("name_of_kazan", aty);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Response");
                            int left_price = jsonArray.getJSONObject(0).getInt("left_price");
                            int price = jsonArray.getJSONObject(0).getInt("price");
                            JSONObject his = jsonArray.getJSONObject(0);
                            JSONArray history_of_payment  = his.getJSONArray("history");

                            all_sum.setText(String.valueOf(price));
                            left_sum.setText(String.valueOf(left_price));
                            btn_end.setEnabled(left_price == 0);
                            String text = "";

                            for (int j = 0; j<his.length(); j++)
                            {
                                JSONObject last_his = history_of_payment.getJSONObject(j);
                                String username_l = last_his.getString("username");
                                int payy = last_his.getInt("payed");
                                text = text.concat(username_l + " закинул " + String.valueOf(payy) + " тенге\n");
                                history_txt.setText(text);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError goodnews) {
                        Toast.makeText(Lobby.this, goodnews.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                MySingleton.getmInstance(Lobby.this).addToRequestque(jsonObjectRequest);
            }
        });

    }


    public void onButtonAbortClick(View v){
        //some action
        Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
        startActivity(myIntent);
    }

    public void onButtonEndClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   Success.class);
        myIntent.putExtra("NAME", aty);
        startActivity(myIntent);
    }
}
