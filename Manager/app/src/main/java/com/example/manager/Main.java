package com.example.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {
    private static final String TAG = "Main";

    Button refresh, feedback, parking;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = findViewById(R.id.refresh);
        feedback = findViewById(R.id.checkFeedback);
        parking = findViewById(R.id.parking);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked refresh");
                startActivity(new Intent(Main.this, ManagerRefresh.class));
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked check feedback");
                startActivity(new Intent(Main.this, Manager_Feedback.class));
            }
        });

        parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked parking");
                startActivity(new Intent(Main.this, Manager_Parking_List.class));
            }
        });
    }
}
