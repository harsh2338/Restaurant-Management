package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resmanage.Database.Database;
import com.example.resmanage.Model.RequestDine;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Payment extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    public static boolean globalfinalise = false;


    private static final String TAG = "Payment";
    TextView amt;
    Button confirm;
    String dineOrHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("ConfirmedOrders");
        confirm = findViewById(R.id.confirm);
        amt = findViewById(R.id.money);
        amt.setText("" + CartHD.globaltotal);
        dineOrHD = NaviDraw.globalDineorHD;
        //Log.d(TAG, "Please "+FoodDetail.globalfooddeets);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dineOrHD == "HomeDelivery") {
                    //databaseReference=databaseReference.child("HomeDelivery");
                    sendToDatabaseHomeDelivery();
                    startActivity(new Intent(Payment.this, NaviDraw.class));
                    globalfinalise = true;
                    Toast.makeText(Payment.this, "Payment Successful!!!!", Toast.LENGTH_SHORT).show();

                } else {
                    //databaseReference=databaseReference.child("Dine");
                    sendToDatabaseDine();
                    startActivity(new Intent(Payment.this, NaviDraw.class));
                    globalfinalise = true;
                    Toast.makeText(Payment.this, "Payment Successful!!!!", Toast.LENGTH_SHORT).show();

                }
                new Database(getBaseContext()).cleanCart();
            }
        });
    }

    private void sendToDatabaseDine() {
        RequestDine obj = new RequestDine(FoodDetail.globalFoodDetail, TwoSeaterTimings.globalTableName, Set_Time.globalTableTiming);
        databaseReference.child("Dine").push().setValue(obj);
    }

    private void sendToDatabaseHomeDelivery() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String currentTime = hour + ":" + minute;
        RequestDine obj = new RequestDine(FoodDetail.globalFoodDetail, CartHD.globaladdress, currentTime);
        databaseReference.child("HomeDelivery").push().setValue(obj);

    }
}
