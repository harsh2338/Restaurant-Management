package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TableType extends AppCompatActivity {

    Button _8Seater, _4Seater, _2Seater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type);


        _2Seater = findViewById(R.id._2SeaterBtn);
        _4Seater = findViewById(R.id._4SeaterBtn);
        _8Seater = findViewById(R.id._8SeaterBtn);

        _2Seater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableType.this, TwoSeaterTimings.class);
                intent.putExtra("No_of_tables_in_given_type", (byte) 6);

                Intent intentSetTime = new Intent(TableType.this, Set_Time.class);
                intentSetTime.putExtra("No_of_tables_in_given_type", (byte) 6);
                startActivity(intent);

            }
        });
        _4Seater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableType.this, TwoSeaterTimings.class);
                intent.putExtra("No_of_tables_in_given_type", (byte) 4);

                Intent intentSetTime = new Intent(TableType.this, Set_Time.class);
                intentSetTime.putExtra("No_of_tables_in_given_type", (byte) 6);

                startActivity(intent);
            }
        });
        _8Seater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableType.this, TwoSeaterTimings.class);
                intent.putExtra("No_of_tables_in_given_type", (byte) 2);

                Intent intentSetTime = new Intent(TableType.this, Set_Time.class);
                intentSetTime.putExtra("No_of_tables_in_given_type", (byte) 6);

                startActivity(intent);
            }
        });
    }
}
