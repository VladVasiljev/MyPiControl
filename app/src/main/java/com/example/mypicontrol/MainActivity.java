package com.example.mypicontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToBuzzer();
    }

    private void goToBuzzer() {//Method that brings us to Computer stat screen
        CardView buzzerControl = findViewById(R.id.BuzzerCard);//computerStat equals computerStatCardView
        buzzerControl.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, buzzerControl.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });
    }
}
