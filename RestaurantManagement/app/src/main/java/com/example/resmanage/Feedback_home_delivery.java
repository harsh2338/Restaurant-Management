package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback_home_delivery extends AppCompatActivity {
    RatingBar quickness, cordialtyHD, qualityHD, packing, tasteHD;
    Button submitFeedback;
    CheckBox check;
    FirebaseDatabase database;
    DatabaseReference databaseRefHD;
    float vquickness, vcordialtyHD, vqualityHD, vpacking, vtasteHD;
    float oldquickness, oldcordialtyHD, oldqualityHD, oldpacking, oldtasteHD;
    float countHD;
    float[] vFeedback, oldFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_home_delivery);
        Feedback_HD_Variables();
        database = FirebaseDatabase.getInstance();
        databaseRefHD = database.getReference().child("Ratings_HD");

        vFeedback = new float[5];
        oldFeedback = new float[5];
        //When Rating is set by the user
        quickness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vquickness = ratingBar.getRating();

            }
        });
        packing.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vpacking = ratingBar.getRating();

            }
        });
        cordialtyHD.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vcordialtyHD = ratingBar.getRating();

            }
        });
        qualityHD.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vqualityHD = ratingBar.getRating();

            }
        });
        tasteHD.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vtasteHD = ratingBar.getRating();

            }
        });
        //To confirm the ratings given by the user and to get the previous countHD value
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRefHD.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        countHD = Float.parseFloat(dataSnapshot.child("countHD").getValue().toString());
                        countHD += 1;
                        oldquickness = Float.parseFloat(dataSnapshot.child("quickness").getValue().toString());
                        oldpacking = Float.parseFloat(dataSnapshot.child("packing").getValue().toString());
                        oldcordialtyHD = Float.parseFloat(dataSnapshot.child("cordialtyHD").getValue().toString());
                        oldqualityHD = Float.parseFloat(dataSnapshot.child("qualityHD").getValue().toString());
                        oldtasteHD = Float.parseFloat(dataSnapshot.child("tasteHD").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                submitFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calculate_Average();
                        Send_Feedback();
                        Toast.makeText(Feedback_home_delivery.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(Feedback_home_delivery.this, NaviDraw.class));

                    }
                });
            }
        });


    }

    private void Feedback_HD_Variables() {
        quickness = (RatingBar) findViewById(R.id.ratingBarSpeed);
        cordialtyHD = (RatingBar) findViewById(R.id.ratingBarCordial);
        qualityHD = (RatingBar) findViewById(R.id.ratingBarQuality);
        packing = (RatingBar) findViewById(R.id.ratingBarPacking);
        tasteHD = (RatingBar) findViewById(R.id.ratingBarTaste);
        submitFeedback = (Button) findViewById(R.id.buttonSubmit);
        check = (CheckBox) findViewById(R.id.check);


    }

    private void Calculate_Average() {
        vquickness = ((oldquickness * (countHD - 1)) + vquickness) / countHD;
        vpacking = ((oldpacking * (countHD - 1)) + vpacking) / countHD;
        vcordialtyHD = ((oldcordialtyHD * (countHD - 1)) + vcordialtyHD) / countHD;
        vqualityHD = ((oldqualityHD * (countHD - 1)) + vqualityHD) / countHD;
        vtasteHD = ((oldtasteHD * (countHD - 1)) + vtasteHD) / countHD;
    }

    private void Send_Feedback() {
        try {
            Data_To_Database_HD ratingsHD = new Data_To_Database_HD(vquickness, vcordialtyHD, vpacking, vtasteHD, countHD, vqualityHD);
            databaseRefHD.setValue(ratingsHD);
        } catch (Exception e) {
            Toast.makeText(this, "Please try again...", Toast.LENGTH_SHORT).show();
        }
    }
}