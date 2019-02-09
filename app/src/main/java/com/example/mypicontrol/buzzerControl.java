package com.example.mypicontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

// Volley sample code is adapted from a tutorial @ http://www.truiton.com/2015/02/android-volley-example/
// This example is simple and easy to follow, and it is all we need for a simple HTTP request and callback through Volley

public class buzzerControl extends AppCompatActivity implements View.OnClickListener, Response.Listener, Response.ErrorListener {

    private RequestQueue mQueue;
    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzzer_control);

        Button offButton = (Button) findViewById(R.id.offButton);
        Button onButton = (Button) findViewById(R.id.onButton);

        offButton.setOnClickListener(this);
        onButton.setOnClickListener(this);

        view = (TextView) findViewById(R.id.textView);

        mQueue = CustomQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.offButton:
                String url = "https://dweet.io/dweet/for/mypicontrolboard?BuzzerStatus=false";
                CustomJSONRequest jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                break;

            case R.id.onButton:
                url = "https://dweet.io/dweet/for/mypicontrolboard?BuzzerStatus=true";
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                break;

            default:
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {
        Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
        view.setText(response.toString());
    }





}
