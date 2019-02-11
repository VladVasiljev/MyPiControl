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

public class LEDControl extends AppCompatActivity implements View.OnClickListener, Response.Listener, Response.ErrorListener {

    private RequestQueue mQueue;
    private TextView view;
    public static String getLEDStatusValue;
    //Class that controls the buzzer simple on and off function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledcontrol);

        Button offButton = findViewById(R.id.offButton);
        Button onButton = findViewById(R.id.onButton);

        offButton.setOnClickListener(this);
        onButton.setOnClickListener(this);

        view = findViewById(R.id.textView2);

        mQueue = CustomQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

    }

    String BuzzerStatusValue = buzzerControl.getBuzzerStatusValue;
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.offButton:
                String url = "https://dweet.io/dweet/for/mypicontrolboard?LEDStatus=false&BuzzerStatus="+BuzzerStatusValue;
                CustomJSONRequest jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getLEDStatusValue= "false";
                break;

            case R.id.onButton:
                url = "https://dweet.io/dweet/for/mypicontrolboard?LEDStatus=true&BuzzerStatus="+BuzzerStatusValue;
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getLEDStatusValue= "true";
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