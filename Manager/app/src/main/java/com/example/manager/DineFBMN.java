package com.example.manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DineFBMN extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference databaseRef;
    ListView LvMN_DFB;

    ArrayList<String> Dine_FB;//list for average feedback

    ArrayAdapter<String> arrayAdapter;

    //Data_From_Database ratings;
    float parking,cordialty,quality,appeal,taste,ambience,comfort,hygiene,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_fbmn);

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference().child("Ratings");//refers to Ratings child
        LvMN_DFB=findViewById(R.id.ListViewMN_DineFB);

        Dine_FB=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Dine_FB);


               databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        //getting ratings from firebase database

                        count= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("count").getValue()).toString());
                        ambience= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("ambience").getValue()).toString());
                        appeal= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("appeal").getValue()).toString());
                        comfort= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("comfort").getValue()).toString());
                        cordialty= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("cordialty").getValue()).toString());
                        hygiene= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("hygiene").getValue()).toString());
                        parking= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("parking").getValue()).toString());
                        quality= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("quality").getValue()).toString());
                        taste= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("taste").getValue()).toString());

                        Dine_FB.add("Count:"+count);
                        Dine_FB.add("Ambience:"+ambience);
                        Dine_FB.add("Appeal:"+appeal);
                        Dine_FB.add("Comfort:"+comfort);
                        Dine_FB.add("Cordiality:"+cordialty);
                        Dine_FB.add("Hygiene:"+hygiene);
                        Dine_FB.add("Parking:"+parking);
                        Dine_FB.add("Quality:"+quality);
                        Dine_FB.add("Taste:"+taste);

                        LvMN_DFB.setAdapter(arrayAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

}
