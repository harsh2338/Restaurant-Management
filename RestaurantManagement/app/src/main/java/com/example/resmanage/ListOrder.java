package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListOrder extends AppCompatActivity {
    private static final String TAG = "ListOrders";
    ListView list;
    String details;
    ArrayAdapter<String> adapter;
    private ArrayList<String> FoodList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        Intent intent = getIntent();
        String type = intent.getStringExtra("Dine/Delivery");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ConfirmedOrders").child(type);

        list = findViewById(R.id.ListOrder);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FoodList);

        list.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Row row = dataSnapshot.getValue(Row.class);
                details = String.valueOf(row);
                adapter.add(details);
                adapter.notifyDataSetChanged();
                //Log.d(TAG, "onChildAdded000000000000000: "+details);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged: 11111111111");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildChanged: 22222222222222");

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged: 333333333333");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onChildChanged: 4444444444444");

            }
        });


    }
}

class Row {
    String identity;
    String time;
    String foodDetail;

    Row() {
    }

    Row(String foodDetail, String identity, String time) {
        this.identity = identity;
        this.time = time.substring(0, 5);
        this.foodDetail = foodDetail;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    @Override
    public String toString() {
        return this.identity + " " + this.time + "\n" + this.foodDetail;
    }
}