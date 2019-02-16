package com.example.mypicontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mypicontrol.Models.modelDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseData extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ValueEventListener databaseListener;
    TextView seekBarValueTV;
    private TextView temeratureValue;
    private TextView humidityValue;
    private TextView ultrasonicValue;
    private TextView lightValue;
    public static int getSeekBarValue;//Gets the value of the seekBark and then it gets passed to the url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_data);

        temeratureValue = findViewById(R.id.temp);
        humidityValue = findViewById(R.id.hum);
        ultrasonicValue = findViewById(R.id.ultra);
        lightValue = findViewById(R.id.light);
        Button updateSampleRate = findViewById(R.id.updateSampleRate);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Seekbar was implemented using this https://stackoverflow.com/questions/8629535/implementing-a-slider-seekbar-in-android
        SeekBar seekBar = findViewById(R.id.seekBar);//Assigning seekbar as seekBar
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int seekBarValue = seekBar.getProgress();//Getting value of the seekbar on create of the dweetResponseMessage
        seekBarValueTV = findViewById(R.id.seekBarValue);
        seekBarValueTV.setText("Current Value: " + seekBarValue);

        updateSampleRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("SampleRateValue").setValue(getSeekBarValue);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Computer Name from FireBase
        ValueEventListener databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    modelDatabase databaseReader = dataSnapshot.getValue(modelDatabase.class);


                    temeratureValue.setText(String.valueOf(databaseReader.Temperature));
                    humidityValue.setText(String.valueOf(databaseReader.Humidity));
                    ultrasonicValue.setText(String.valueOf(databaseReader.Ultrasonic));
                    lightValue.setText(String.valueOf(databaseReader.LightLevel));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                //Log.e(TAG, "onCancelled: Failed to read message");

                temeratureValue.setText("Not Found");
            }
        };
        databaseReference.addValueEventListener(databaseListener);

        // copy for removing at onStop()
        this.databaseListener = databaseListener;
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


}




