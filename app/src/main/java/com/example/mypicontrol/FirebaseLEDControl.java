package com.example.mypicontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseLEDControl extends AppCompatActivity {

    private Button offButton;
    private Button onButton;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ledcontrol);

        offButton = findViewById(R.id.offButton);
        onButton = findViewById(R.id.onButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("LEDValue").setValue(0);
            }
        });

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("LEDValue").setValue(1);
            }
        });


    }


}