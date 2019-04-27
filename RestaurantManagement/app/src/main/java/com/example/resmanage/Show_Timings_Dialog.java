package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Show_Timings_Dialog extends AppCompatActivity {

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    //Adapter and list
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> list;
    //Widgets
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__timings__dialog);


        listView = findViewById(R.id.BookedTimings);

        //receiving the name of table(X1...) and type of table (T,F,E)
        Intent intent = getIntent();
        final String tableName = intent.getStringExtra("Table_name");
        String typeOfChild = intent.getStringExtra("Type_of_Child");

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference().child("BookedTimings").child(typeOfChild);

        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);


        databaseRef.child(tableName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String timingsReceived = dataSnapshot.getValue().toString();
                String[] split_timings = timingsReceived.split(",");
                for (int i = 0; i < split_timings.length; i++) {
                    list.add(split_timings[i]);
                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        close = findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // startActivity(new Intent(getApplicationContext(),TwoSeaterTimings.class));
            }
        });


    }


}




