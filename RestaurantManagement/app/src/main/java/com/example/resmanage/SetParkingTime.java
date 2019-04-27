//fix the data to database
package com.example.resmanage;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class SetParkingTime extends AppCompatActivity {
    private static final String TAG = "SetParkingTime";
    TimePickerDialog timePickerDialog;
    FirebaseDatabase databasePark = FirebaseDatabase.getInstance();
    DatabaseReference databaseRefPark, refPass;
    Button STbutton, ETbutton, Availabilitybutton, Confirm;
    int hour, minute, position;
    boolean anstemp;
    String key;
    String ST = "\0", ET = "\0";
    String random = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_parking_time);

        Confirm = findViewById(R.id.ConfirmParking);
        Availabilitybutton = findViewById(R.id.Availability_button);

        //from parking class
        Intent intent = getIntent();
        String typeOfVehicle = intent.getStringExtra("TypeWheeler");
        //getting reference from database
        refPass = databasePark.getReference().child("BookedParking");
        databaseRefPark = databasePark.getReference().child("PARKING").child(typeOfVehicle);

        Enter_Time();

        Availabilitybutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (!(ST.equals("\0") || ET.equals("\0"))) {

                    databaseRefPark.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (int i = 1; i <= 3; i++) {
                                String timingsReceived = Objects.requireNonNull(dataSnapshot.child("P" + i).getValue()).toString();
                                //Toast.makeText(getApplicationContext(),""+timingsReceived,Toast.LENGTH_SHORT).show();
                                boolean ans = SplitAndCheck(timingsReceived);
                                if (ans) {
                                    anstemp = ans;
                                    position = i;
                                    break;
                                }
                            }
                            if (anstemp) {
                                Toast.makeText(getApplicationContext(), "Available", Toast.LENGTH_SHORT).show();
                                Confirm.setEnabled(true);
                            } else
                                Toast.makeText(getApplicationContext(), "Not Available", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anstemp) {


                    databaseRefPark.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //temp = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                            String temp = String.valueOf(dataSnapshot.child("P" + position).getValue());
                            String addtime = temp + ST.replace(":", "") + "-" + ET.replace(":", "") + ",";
                            databaseRefPark.child("P" + position).setValue(addtime);


                            //key=dataSnapshot.child("P" + position).getKey();
                            //new Data_To_Database_Parking(key,true);

                            //AddTiming(temp);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                Random r = new Random();

                String alphabet = "123xyz";
                random = "";
                for (int i = 0; i <= 5; i++) {
                    random += alphabet.charAt(r.nextInt(alphabet.length()));
                }
                refPass.child(random).setValue(ST.replace(":", "") + "-" + ET.replace(":", ""));
                anstemp = false;
                openDialog();

            }
        });

        //Toast.makeText(getApplicationContext(),""+temp,Toast.LENGTH_SHORT).show();

        //databaseRefPark.child("P" + position).setValue(temp+ST.replace(":","") + "-" + ET.replace(":","")+",");//

    }

    private void openDialog() {


        // Toast.makeText(getContext(),""+random,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(SetParkingTime.this);
        builder.setTitle("YOUR TICKET")
                .setMessage(random + "\n" + "Please take a screenshot of the key you get after the parking process.")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        TextView txtView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        txtView.setLayoutParams(lp);
        builder.setView(txtView);

        builder.show();
    }

    public void Enter_Time() {

        Confirm.setEnabled(false);
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        ETbutton = findViewById(R.id.ETbutton);
        STbutton = findViewById(R.id.STbutton);

        STbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET = "\0";
                TextView displayET = findViewById(R.id.displayET);
                displayET.setText("Ending Time");
                timePickerDialog = new TimePickerDialog(SetParkingTime.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 19 && hourOfDay <= 23) {
                            if (minute < 10) {
                                TextView displayST = findViewById(R.id.displayST);
                                ST = hourOfDay + ":0" + minute;
                                displayST.setText(ST);
                            } else {
                                TextView displayST = findViewById(R.id.displayST);
                                ST = hourOfDay + ":" + minute;
                                displayST.setText(hourOfDay + ":" + minute);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "The restaurant Timings are from 7:00pm to 11:59pm", Toast.LENGTH_LONG).show();
                        }
                    }

                }, hour, minute, true);
                timePickerDialog.show();


            }

        });

        ETbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ST != "\0") {
                    Toast.makeText(getApplicationContext(), "" + ST, Toast.LENGTH_SHORT).show();
                    timePickerDialog = new TimePickerDialog(SetParkingTime.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (hourOfDay == Integer.parseInt(ST.substring(0, 2)) && minute > Integer.parseInt(ST.substring(3, 5))
                                    || hourOfDay > Integer.parseInt(ST.substring(0, 2))) {
                                if (minute < 10) {
                                    TextView displayET = findViewById(R.id.displayET);
                                    ET = hourOfDay + ":0" + minute;
                                    displayET.setText(ET);
                                } else {
                                    TextView displayET = findViewById(R.id.displayET);
                                    ET = hourOfDay + ":" + minute;
                                    displayET.setText(ET);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Please Enter a valid time", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, hour, minute, true);
                    timePickerDialog.show();
                }
            }
        });


    }

    protected boolean SplitAndCheck(String timingsReceived)//eg:  1900-2000,2030-2100,2200-2300
    {
        boolean ans = true;
        int countSt = 0, countEt = 0;//to get the position of the set time
        int tempSt, tempEt;
        String[] split_timings = timingsReceived.split(",");//1900-2000    2030-2100   2200-2300
        int[] st = new int[split_timings.length];
        int[] et = new int[split_timings.length];
        Log.d(TAG, "SplitAndCheck: " + split_timings.length);


        for (int i = 0; i < split_timings.length; i++) {
            st[i] = Integer.parseInt(split_timings[i].substring(0, 4));//1900 2030 2200
            et[i] = Integer.parseInt(split_timings[i].substring(5, 9));//2000 2100 2300
        }
        Arrays.sort(st);
        Arrays.sort(et);
        tempSt = Integer.parseInt(ET.replace(":", ""));//because the entered time will be in the form of hh:mm
        tempEt = Integer.parseInt(ST.replace(":", ""));

        for (int i = 0; i < split_timings.length; i++) {
            if (tempSt > st[i])
                countSt++;
            if (tempEt > et[i])
                countEt++;
        }

        if (countEt == countSt)
            return true;
        else
            return false;
    }
}
