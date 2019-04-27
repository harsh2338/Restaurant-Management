package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Parking extends AppCompatActivity {
    //widget
    Button TwoWheeler, FourWheeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        //defining widgets
        TwoWheeler = findViewById(R.id.TwoWheeler);
        FourWheeler = findViewById(R.id.FourWheeler);

        TwoWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetParkingTime.class);
                intent.putExtra("TypeWheeler", "TwoWheeler");
                startActivity(intent);
            }

        });

        FourWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetParkingTime.class);
                intent.putExtra("TypeWheeler", "FourWheeler");
                startActivity(intent);
            }

        });

    }
}
