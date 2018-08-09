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

public class StartPage extends AppCompatActivity {

    public static int cash;
    public static int cash_back;
    public  static  String username_global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Button next_bnt;
        final EditText login, password;
        final TextView errortxt;

        errortxt = findViewById(R.id.error_txt);
        next_bnt = (Button)findViewById(R.id.next_btn);
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";

        next_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject obj = new JSONObject();

                try {
                    obj.put("procedure", "login");
                    obj.put("username", login.getText().toString());
                    obj.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Response");
                            int yespa = jsonArray.getJSONObject(0).getInt("logged");

                            if (yespa == 1) {
                                username_global = login.getText().toString();
                                cash = jsonArray.getJSONObject(0).getInt("cash");
                                cash_back = jsonArray.getJSONObject(0).getInt("cash_back");

                                Intent myIntent = new Intent(getBaseContext(),   Screen.class);
                                startActivity(myIntent);
                            }
                            else
                            {

                                errortxt.setText("Логин или пароль введен не правильно, попробуйте еще");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError goodnews) {
                        Toast.makeText(StartPage.this, goodnews.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                MySingleton.getmInstance(StartPage.this).addToRequestque(jsonObjectRequest);
            }
        });
    }
}
