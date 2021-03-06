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

public class Join extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final Button join_button;
        final EditText namekazan;
        final String json_url = "https://m81kko67a8.execute-api.us-east-1.amazonaws.com/v0/rahmet-api";
        final TextView errortxt;
        errortxt = (TextView)findViewById(R.id.textView4);
        namekazan = (EditText)findViewById(R.id.editText2);
        join_button = (Button)findViewById(R.id.join_btn);
        join_button.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                JSONObject obj = new JSONObject();

                try {
                    obj.put("procedure", "join");
                    obj.put("name_of_kazan", namekazan.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Response");
                            int yespa = jsonArray.getJSONObject(0).getInt("exists");
                            if (yespa == 1) {
                                Intent intent = new Intent(Join.this, Lobby.class);
                                String name_of_kazan = namekazan.getText().toString();
                                intent.putExtra("NAME", name_of_kazan);
                                startActivity(intent);
                            }
                            else
                            {

                                errortxt.setText("Казан под таким именем не существует");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError goodnews) {
                        Toast.makeText(Join.this, goodnews.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                MySingleton.getmInstance(Join.this).addToRequestque(jsonObjectRequest);

            }
        });
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
