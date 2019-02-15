package com.example.mypicontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
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
    TextView seekBarValueTV;
    public static int getSeekBarValue;//Gets the value of the seekBark and then it gets passed to the url
//    public static String getLEDStatusValue;//string that gets the status of the led light true or false

    //Old implemention of the system
//    String BuzzerStatusValue = buzzerControl.getBuzzerStatusValue;//Getting BuzzerStatusValue from buzzerControl class so we can use this as a memory feature (allows us to
//    int getSeekBarValue = buzzerControl.getSeekBarValue;
    // remember the state of sensors and pass them on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledcontrol);

        //Assigning names to buttons
        Button offButton = findViewById(R.id.offButton);
        Button onButton = findViewById(R.id.onButton);
        Button updateLight = findViewById(R.id.updateLight);
        //Creating a on click listenerBuzzer for buttons
        offButton.setOnClickListener(this);
        onButton.setOnClickListener(this);
        updateLight.setOnClickListener(this);


        dweetResponseMessage = findViewById(R.id.dweetResponseTV);//Assigning name to textview for dweet responses

        //Seekbar was implemented using this https://stackoverflow.com/questions/8629535/implementing-a-slider-seekbar-in-android
        SeekBar seekBar = findViewById(R.id.seekBar);//Assigning seekbar as seekBar
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int seekBarValue = seekBar.getProgress();//Getting value of the seekbar on create of the dweetResponseMessage
        seekBarValueTV = findViewById(R.id.seekBarValue);
        seekBarValueTV.setText("Current Value: " + seekBarValue);

        mQueue = CustomQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

    }

    //https://stackoverflow.com/questions/8629535/implementing-a-slider-seekbar-in-android
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int seekBarValue, boolean fromUser) {
            // updated continuously as the user slides the thumb
            seekBarValueTV.setText("Progress: " + seekBarValue);
            getSeekBarValue = seekBarValue;

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.offButton:
                String url = "https://dweet.io/dweet/for/mypicontrolboardLED?LEDStatus=false"+"&LightLevel=0";
                CustomJSONRequest jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
//                getLEDStatusValue = "false";
                break;

            case R.id.onButton:
                url = "https://dweet.io/dweet/for/mypicontrolboardLED?LEDStatus=true"+"&LightLevel=1000";
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
//                getLEDStatusValue = "true";
                break;

            case R.id.updateLight:
                url = "https://dweet.io/dweet/for/mypicontrolboardLED?LEDStatus=true&"+"&LightLevel=" + getSeekBarValue;
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                // jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
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