package com.example.mypicontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView dweetResponseMessage;//TV for displaying dweets responses
    public static String getLEDStatusValue;//string that gets the status of the led light true or false
    String BuzzerStatusValue = buzzerControl.getBuzzerStatusValue;//Getting BuzzerStatusValue from buzzerControl class so we can use this as a memory feature (allows us to
    // remember the state of sensors and pass them on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledcontrol);

        //Assigning names to buttons
        Button offButton = findViewById(R.id.offButton);
        Button onButton = findViewById(R.id.onButton);
        //Creating a on click listener for buttons
        offButton.setOnClickListener(this);
        onButton.setOnClickListener(this);

        dweetResponseMessage = findViewById(R.id.dweetResponseTV);//Assigning name to textview for dweet responses

        mQueue = CustomQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.offButton:
                String url = "https://dweet.io/dweet/for/mypicontrolboard?LEDStatus=false&BuzzerStatus=" + BuzzerStatusValue;
                CustomJSONRequest jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getLEDStatusValue = "false";
                break;

            case R.id.onButton:
                url = "https://dweet.io/dweet/for/mypicontrolboard?LEDStatus=true&BuzzerStatus=" + BuzzerStatusValue;
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getLEDStatusValue = "true";
                break;

            default:
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dweetResponseMessage.setText("Volley Error has happened, Oops, please check your internet connection and tap on the button again"+"\n \n"+getApplicationContext()+ error.toString());

    }

    @Override
    public void onResponse(Object response) {
        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
        dweetResponseMessage.setText(response.toString());
    }


}