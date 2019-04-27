package com.example.manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerRefresh extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseRef;
    Button RefTim,RefFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_refresh);
        RefFeed = findViewById(R.id.Refresh_Ratings);
        RefTim=findViewById(R.id.Refresh_Timing);

        database=FirebaseDatabase.getInstance();
        databaseRef=database.getReference();
        RefFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef.child("Ratings").child("count").setValue(0);
                databaseRef.child("Ratings_HD").child("countHD").setValue(0);
            }
        });

        RefTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data_To_Database_Timings T=new Data_To_Database_Timings("0000-0000,","0000-0000,","0000-0000,","0000-0000,","0000-0000,","0000-0000,");
                databaseRef.child("BookedTimings").child("TwoSeater").setValue(T);

                Data_To_Database_Timings F=new Data_To_Database_Timings("0000-0000,","0000-0000,","0000-0000,","0000-0000,");
                databaseRef.child("BookedTimings").child("FourSeater").setValue(F);

                Data_To_Database_Timings E=new Data_To_Database_Timings("0000-0000,","0000-0000,");
                databaseRef.child("BookedTimings").child("EightSeater").setValue(E);

                Data_To_Database_Timings P2=new Data_To_Database_Timings("0000-0000,","0000-0000,","0000-0000,");
                databaseRef.child("PARKING").child("FourWheeler").setValue(P2);

                Data_To_Database_Timings P4=new Data_To_Database_Timings("0000-0000,","0000-0000,","0000-0000,");
                databaseRef.child("PARKING").child("TwoWheeler").setValue(P4);
                
                
            }
        });


    }
}
