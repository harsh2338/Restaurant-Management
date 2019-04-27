package com.example.manager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Manager_Parking_List extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ListView ParkedList;
    ArrayList<String> list;
    ArrayAdapter<String>arrayAdapter;
    private static final String TAG = "Manager_Parking_List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__parking__list);
        list=new ArrayList<String>();

        ParkedList=findViewById(R.id.listParked);
        arrayAdapter=new ArrayAdapter <>(this,android.R.layout.simple_list_item_1,list);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("BookedParking");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: "+ds.getKey());
                    String temp=ds.getKey()+"\t"+ds.getValue();
                    list.add(temp);

                }
                ParkedList.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
