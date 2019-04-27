package com.example.resmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.resmanage.Adapter.FoodAdapter;
import com.example.resmanage.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FoodList extends AppCompatActivity {

    private static final String TAG = "FoodList";

    FirebaseDatabase database;
    DatabaseReference ref;

    FoodAdapter mAdapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private List<Food> foods;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Log.d(TAG, "onCreate: started");

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String currentTime = hour + ":" + minute;

        database = FirebaseDatabase.getInstance();
        if (currentTime.compareTo("16:00") > 0) {
            ref = database.getReference("Food");
            Log.d(TAG, "onCreate: " + ref);
        } else {
            ref = database.getReference("Food");
            Log.d(TAG, "onCreate: " + ref);

        }
        //ref = database.getReference("Chats");


        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Array for holding food items
        foods = new ArrayList<>();

        getIncomingIntent();
        Log.d(TAG, "getIncomingIntent: found " + name);


        if (!name.isEmpty()) {
            Query q = ref.orderByChild("CategoryID").equalTo(name);

            q.addValueEventListener(new ValueEventListener() {
                private static final String TAG = "FoodList";

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    foods.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: in the loop");
                        final Food food = postSnapshot.getValue(Food.class);
                        foods.add(food);
                    }

                    mAdapter = new FoodAdapter(FoodList.this, foods);
                    recyclerView.setAdapter(mAdapter);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(FoodList.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: gotten");
        if (getIntent().hasExtra("Name")) {

            name = getIntent().getStringExtra("Name");

        }
    }

}
