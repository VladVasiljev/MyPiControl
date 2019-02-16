package com.example.mypicontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseBuzzerControl extends AppCompatActivity {

    private Button mFirebaseBtn;
    private Button mFirebaseBtn2;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_buzzer_control);

        mFirebaseBtn = findViewById(R.id.offButton);
        mFirebaseBtn2 = findViewById(R.id.onButton);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mDatabase.child("BuzzerValue").setValue(0);
            }
        });

        mFirebaseBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mDatabase.child("BuzzerValue").setValue(1);
            }
        });





    }


}
