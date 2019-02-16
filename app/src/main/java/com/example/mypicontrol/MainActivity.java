package com.example.mypicontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToBuzzer();
        goToLEDLight();
        goToDweetData();
        gotToFirebaseBuzzerControl();
        goToFirebaseLEDControl();
        goToFirebaseDatabase();
    }

    private void goToBuzzer() {
        CardView buzzerControl = findViewById(R.id.BuzzerCard);
        buzzerControl.setOnClickListener(new View.OnClickListener() {//Creating a click listenerBuzzer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, buzzerControl.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }

    private void goToLEDLight() {
        CardView ledLight = findViewById(R.id.LEDLight);
        ledLight.setOnClickListener(new View.OnClickListener() {//Creating a click listenerBuzzer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LEDControl.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }

    private void goToDweetData() {
        CardView dweetData = findViewById(R.id.dweetData);
        dweetData.setOnClickListener(new View.OnClickListener() {//Creating a click listenerBuzzer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, dweetData.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }

    private void gotToFirebaseBuzzerControl() {
        CardView firebasebuzzerControl = findViewById(R.id.firebaseBuzzerControl);
        firebasebuzzerControl.setOnClickListener(new View.OnClickListener() {//Creating a click listenerBuzzer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirebaseBuzzerControl.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }

    private void goToFirebaseLEDControl() {
        CardView firebaseledcontrol = findViewById(R.id.firebaseLEDControl);
        firebaseledcontrol.setOnClickListener(new View.OnClickListener() {//Creating a click listenerBuzzer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirebaseLEDControl.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }

    private void goToFirebaseDatabase() {
        CardView firebaseDatabase = findViewById(R.id.firebaseDatabase);
        firebaseDatabase.setOnClickListener(new View.OnClickListener() {//Creating a click listenerBuzzer
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirebaseData.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }
}
