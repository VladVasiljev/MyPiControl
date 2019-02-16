package com.example.mypicontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseBuzzerControl extends AppCompatActivity {

    private Button offButton;
    private Button onButton;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_buzzer_control);

        offButton = findViewById(R.id.offButton);
        onButton = findViewById(R.id.onButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("BuzzerValue").setValue(0);
            }
        });

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("BuzzerValue").setValue(1);
            }
        });


    }


}
