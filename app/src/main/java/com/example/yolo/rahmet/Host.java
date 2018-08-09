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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Host extends AppCompatActivity {

    public static String kazanname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        final EditText namekazan, price;
        final Button button;
        final TextView errortxt;
        errortxt = (TextView)findViewById(R.id.error_txt);
        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";
        namekazan = (EditText)findViewById(R.id.NameOfKazan);
        price = (EditText)findViewById(R.id.sum);
        button = (Button)findViewById(R.id.ButtonCreate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();

                try {
                    obj.put("procedure", "create_kazan");
                    obj.put("name_of_kazan", namekazan.getText().toString());
                    obj.put("price", Integer.parseInt(price.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Response");
                            int yespa = jsonArray.getJSONObject(0).getInt("isCreated");
                            if (yespa == 1) {
                                EditText editText = findViewById(R.id.NameOfKazan);

                                String aty = editText.getText().toString();
                                kazanname = aty;

                                Intent intent = new Intent(Host.this, Lobby.class);
                                intent.putExtra("NAME",aty);
                                startActivity(intent);
                            }
                            else
                            {
                                errortxt.setText("Казан с таким именем уже существует");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError goodnews) {
                        Toast.makeText(Host.this, goodnews.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                MySingleton.getmInstance(Host.this).addToRequestque(jsonObjectRequest);


            }
        });
    }

    public void onButtonBackClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
        startActivity(myIntent);
    }


}
