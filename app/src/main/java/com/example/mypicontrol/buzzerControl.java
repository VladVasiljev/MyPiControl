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

public class buzzerControl extends AppCompatActivity implements View.OnClickListener, Response.Listener, Response.ErrorListener {

    private RequestQueue mQueue;
    private TextView view;
    public static String getBuzzerStatusValue;
    TextView tvProgressLabel;
    int finalprogress;

    //Class that controls the buzzer simple on and off function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzzer_control);

        Button offButton = findViewById(R.id.offButton);
        Button onButton = findViewById(R.id.onButton);
        Button sampleRateButton = findViewById(R.id.updateSampleRate);

        offButton.setOnClickListener(this);//listener for on button
        onButton.setOnClickListener(this);//listener for off button
        sampleRateButton.setOnClickListener(this);//listener for sample rate button

        //Seekbar was implemented using this https://stackoverflow.com/questions/8629535/implementing-a-slider-seekbar-in-android
        // set a change listener on the SeekBar
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int progress = seekBar.getProgress();
        tvProgressLabel = findViewById(R.id.progressTxt);
        tvProgressLabel.setText("Progress: " + progress);


        view = findViewById(R.id.textView);

        mQueue = CustomQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

    }

    //https://stackoverflow.com/questions/8629535/implementing-a-slider-seekbar-in-android
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setText("Progress: " + progress);
            finalprogress = progress;
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


    String LEDStatusValue = LEDControl.getLEDStatusValue;//Getting LEDStatus value from ledcontrol class

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.offButton:
                String url = "https://dweet.io/dweet/for/mypicontrolboard?BuzzerStatus=false&LEDStatus=" + LEDStatusValue + "&SampleRate=0";
                CustomJSONRequest jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                //jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getBuzzerStatusValue = "false";
                break;

            case R.id.onButton:
                url = "https://dweet.io/dweet/for/mypicontrolboard?BuzzerStatus=true&LEDStatus=" + LEDStatusValue + "&SampleRate=0";
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                // jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getBuzzerStatusValue = "true";
                break;

                case R.id.updateSampleRate:
                url = "https://dweet.io/dweet/for/mypicontrolboard?BuzzerStatus=true&LEDStatus=" + LEDStatusValue + "&SampleRate=" +finalprogress ;
                jsonRequest = new CustomJSONRequest(Request.Method.GET, url,
                        new JSONObject(), this, this);
                // jsonRequest.setTag("test");
                mQueue.add(jsonRequest);
                getBuzzerStatusValue = "true";
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
        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
        view.setText(response.toString());
    }


}
