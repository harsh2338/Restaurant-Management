package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.resmanage.Adapter.ImageAdapter;
import com.example.resmanage.Model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    //Getting reference from databases
    FirebaseDatabase fireData;
    DatabaseReference ref;

    //calling adapter object
    ImageAdapter mAdapter;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    private List<Category> cat;

    Toolbar toolbar;

    FloatingActionButton fab;

    String key, TAG, HDorDine = "\0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d(TAG, "onCreate: started");

        fireData = FirebaseDatabase.getInstance();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String currentTime = hour + ":" + minute;
        Log.d(TAG, "onCreate:Time " + currentTime);

        if (currentTime.compareTo("16:00") > 0) {
            ref = fireData.getReference("ChatsMenu");
            Log.d(TAG, "onCreate:Chats" + ref);
        } else {
            ref = fireData.getReference("Menu");
            Log.d(TAG, "onCreate:Menu" + ref);
        }
        //ref=fireData.getReference("ChatsMenu");

        toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("MENU");


        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        //Creation of array of objects to store the data
        cat = new ArrayList<>();

        fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent putHDorDine = new Intent(MainMenu.this, CartHD.class);
                putHDorDine.putExtra("HDorDine", HDorDine);
                startActivity(putHDorDine);
            }
        });

        //to put the data into the above array from firebase
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Category cate = postSnapshot.getValue(Category.class);
                    cat.add(cate);
                }

                mAdapter = new ImageAdapter(MainMenu.this, cat);
                recycler_menu.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainMenu.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}





