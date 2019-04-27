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

import java.util.Objects;

public class Feedback extends AppCompatActivity {
    //widgets
    RatingBar parking, cordialty, quality, appeal, taste, ambience, comfort, hygiene;
    Button submitFeedback;
    CheckBox check;
    //firebase
    FirebaseDatabase database;
    DatabaseReference databaseRef;
    //variables for the ratings
    float vparking, vcordialty, vquality, vappeal, vtaste, vambience, vcomfort, vhygiene;
    float oldparking, oldcordialty, oldquality, oldappeal, oldtaste, oldambience, oldcomfort, oldhygiene;
    float count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Feedback_Restaurant_Variables();

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference().child("Ratings");

        //When Rating is set by the user
        parking.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vparking = ratingBar.getRating();

            }
        });
        comfort.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vcomfort = ratingBar.getRating();

            }
        });
        cordialty.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vcordialty = ratingBar.getRating();

            }
        });
        hygiene.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vhygiene = ratingBar.getRating();

            }
        });
        quality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vquality = ratingBar.getRating();

            }
        });
        ambience.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vambience = ratingBar.getRating();

            }
        });
        appeal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vappeal = ratingBar.getRating();

            }
        });
        taste.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                vtaste = ratingBar.getRating();

            }
        });

        //To confirm the ratings given by the user and to get the previous count value
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        count = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("count").getValue()).toString());
                        count += 1;//having a counter to see how many times the feedback was given
                        oldambience = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("ambience").getValue()).toString());
                        oldappeal = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("appeal").getValue()).toString());
                        oldcomfort = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("comfort").getValue()).toString());
                        oldcordialty = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("cordialty").getValue()).toString());
                        oldhygiene = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("hygiene").getValue()).toString());
                        oldparking = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("parking").getValue()).toString());
                        oldquality = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("quality").getValue()).toString());
                        oldtaste = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("taste").getValue()).toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                submitFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calculate_Average();//calculates average feedback
                        Send_Feedback();
                        Toast.makeText(Feedback.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(Feedback.this, NaviDraw.class));

                    }
                });

            }
        });

    }

    private void Feedback_Restaurant_Variables() {
        parking = (RatingBar) findViewById(R.id.ratingBarSpeed);
        cordialty = (RatingBar) findViewById(R.id.ratingBarCordial);
        quality = (RatingBar) findViewById(R.id.ratingBarQuality);
        appeal = (RatingBar) findViewById(R.id.ratingBarPacking);
        taste = (RatingBar) findViewById(R.id.ratingBarTaste);
        ambience = (RatingBar) findViewById(R.id.ratingBarAmbience);
        comfort = (RatingBar) findViewById(R.id.ratingBarComfort);
        hygiene = (RatingBar) findViewById(R.id.ratingBarHygiene);
        submitFeedback = (Button) findViewById(R.id.buttonSubmit);
        check = (CheckBox) findViewById(R.id.check);

    }

    private void Calculate_Average() {
        vambience = ((oldambience * (count - 1)) + vambience) / count;
        vappeal = ((oldappeal * (count - 1)) + vappeal) / count;
        vcomfort = ((oldcomfort * (count - 1)) + vcomfort) / count;
        vcordialty = ((oldcordialty * (count - 1)) + vcordialty) / count;
        vhygiene = ((oldhygiene * (count - 1)) + vhygiene) / count;
        vparking = ((oldparking * (count - 1)) + vparking) / count;
        vquality = ((oldquality * (count - 1)) + vquality) / count;
        vtaste = ((oldtaste * (count - 1)) + vtaste) / count;
    }

    private void Send_Feedback() {
        try {
            Data_To_Database ratings = new Data_To_Database(vparking, vcordialty, vquality, vappeal, vtaste, vambience, vcomfort, vhygiene, count);
            databaseRef.setValue(ratings);
        } catch (Exception e) {
            Toast.makeText(this, "Please try again...", Toast.LENGTH_SHORT).show();
        }
    }


}