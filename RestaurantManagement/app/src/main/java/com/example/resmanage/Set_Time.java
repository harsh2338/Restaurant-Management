package com.example.resmanage;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Set_Time extends AppCompatActivity {
    private static final String TAG = "Set_Time";
    TimePickerDialog timePickerDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    public static String globalTableTiming;

    Button STbutton, ETbutton, Availabilitybutton, ShowBooked, Confirm;
    int hour, minute, position;
    boolean anstemp;
    String ST = "\0", ET = "\0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__time);


        Confirm = findViewById(R.id.ConfirmTable);
        Availabilitybutton = findViewById(R.id.Availability_button);


        final Intent intent = getIntent();
        final int no_of_table = intent.getByteExtra("No_of_tables_in_given_type", (byte) 1);
        final String tableName = intent.getStringExtra("Table_name");
        final String typeOfChild = intent.getStringExtra("Type_of_Child");
        databaseRef = database.getReference().child("BookedTimings").child(typeOfChild);
        Availabilitybutton = findViewById(R.id.Availability_button);

        Enter_Time();
        Availabilitybutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (ST != "\0" && ET != "\0")
                    if (!(ST.equals("\0") && ET.equals("\0"))) {

                        databaseRef.child(tableName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String timingsReceived = dataSnapshot.getValue().toString();
                                boolean ans = SplitAndCheck(timingsReceived);

                                if (ans) {
                                    Toast.makeText(getApplicationContext(), "Available", Toast.LENGTH_SHORT).show();

                                    anstemp = ans;
                                    Log.d(TAG, "onDataChange: " + ST);
                                    Confirm.setEnabled(true);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Not Available", Toast.LENGTH_SHORT).show();
                                }


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
                    databaseRef.child(tableName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp = String.valueOf(dataSnapshot.getValue());
                            String addtime = temp + ST.replace(":", "") + "-" + ET.replace(":", "") + ",";
                            if (Payment.globalfinalise) {
                                databaseRef.child(tableName).setValue(addtime);
                            }
                            globalTableTiming = ST.replace(":", "") + "-" + ET.replace(":", "") + ",";

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                Intent time = new Intent(Set_Time.this, CartHD.class);
                time.putExtra("time", ST);
                anstemp = false;
                startActivity(new Intent(Set_Time.this, MainMenu.class));

            }
        });
        ShowBooked = findViewById(R.id.Show_Prev_Time);
        ShowBooked.setEnabled(false);

        ShowBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Show_Timings_Dialog.class);
                intent.putExtra("Table_name", tableName);
                intent.putExtra("Type_of_Child", typeOfChild);
                // intent.putExtra("STNo_of_tables",no_of_table);
                startActivity(intent);
            }
        });
        //startActivity(new Intent(Set_Time.this,Home.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

        Button ShowBooked = (Button) findViewById(R.id.Show_Prev_Time);
        ShowBooked.setEnabled(true);
    }

    protected boolean SplitAndCheck(String timingsReceived)//eg:  1900-2000,2030-2100,2200-2300
    {
        boolean ans = true;
        int countSt = 0, countEt = 0, tempSt, tempEt;
        String[] split_timings = timingsReceived.split(",");//1900-2000    2030-2100   2200-2300
        int[] st = new int[split_timings.length];
        int[] et = new int[split_timings.length];


        for (int i = 0; i < split_timings.length; i++) {
            st[i] = Integer.parseInt(split_timings[i].substring(0, 4));
            et[i] = Integer.parseInt(split_timings[i].substring(5, 9));
        }
        Arrays.sort(st);
        Arrays.sort(et);
        tempSt = Integer.parseInt(ET.replace(":", ""));
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
                timePickerDialog = new TimePickerDialog(Set_Time.this, new TimePickerDialog.OnTimeSetListener() {
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
                    timePickerDialog = new TimePickerDialog(Set_Time.this, new TimePickerDialog.OnTimeSetListener() {
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


}