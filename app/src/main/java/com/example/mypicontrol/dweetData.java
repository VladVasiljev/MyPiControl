package com.example.mypicontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dweetData extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    int counter = 0;

    //Code obtained from https://codinginflow.com/tutorials/android/volley/simple-get-request edit by me (Vladislavs Vasiljevs) for my usecase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dweet_data);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        String url = "https://dweet.io:443/get/latest/dweet/for/mypistats";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("with");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                                counter++;
                                String thingName = employee.getString("thing");
                                String sensorData = employee.getString("content");
                                if (counter == 6) {
                                    mTextViewResult.setText("");//Clearing text after 5 values have been added to the textview
                                    counter = 1;//Resetting counter value
                                }


                                //Added a counter that adds a number next the the append
                                mTextViewResult.append(counter + ": " + thingName + ", " + ", " + sensorData + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}