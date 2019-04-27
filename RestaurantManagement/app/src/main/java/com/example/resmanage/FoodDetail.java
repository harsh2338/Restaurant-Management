package com.example.resmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.resmanage.Database.Database;
import com.example.resmanage.Model.Food;
import com.example.resmanage.Model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    private static final String TAG = "FoodDetail";
    String food_name, food_id, food_image;
    TextView food, price;
    ImageView img;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    ElegantNumberButton enb;
    public static String globalFoodDetail = "";
    DatabaseReference ref;

    FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

    Food foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        ref = FirebaseDatabase.getInstance().getReference("Food");
        //food_id= ref.orderByChild(food_name).toString();

        getIncomingIntent();
        Log.d(TAG, "getIncomingIntent: found " + food_name);


        if (!food_name.isEmpty())
            getFoodDetail(food_name);

        food = findViewById(R.id.food_name);
        price = findViewById(R.id.food_price);
        img = findViewById(R.id.img_food);
        enb = findViewById(R.id.number_button);
        fab = findViewById(R.id.btnCart);
        try{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Database(getBaseContext()).addToCart(new Order(
                            food_id,
                            foods.getName(),
                            enb.getNumber(),
                            foods.getPrice()
                    ));
                    Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    globalFoodDetail += foods.getName() + " " + enb.getNumber() + ",";
                }

            });
        }catch(Exception e){
            Log.d(TAG, "onCreate: " + e.getMessage());
            startActivity(new Intent(FoodDetail.this, FoodList.class));
            Toast.makeText(this, "Please wait for the content to load..", Toast.LENGTH_SHORT).show();
        }

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);


    }

    private void getIncomingIntent() {

        Log.d(TAG, "getIncomingIntent: gotten");
        if (getIntent().hasExtra("Name") && getIntent().hasExtra("Image")) {

            food_name = getIntent().getStringExtra("Name");
            food_image = getIntent().getStringExtra("Image");
        }
    }

    private void getFoodDetail(final String ID) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                food_id = "";
                //  Log.d(TAG, "onDataChange: " + dataSnapshot.getKey()+foods.getPrice());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    food_id = child.getKey();
                    if (dataSnapshot.child(food_id).child("Name").getValue().equals(food_name)) {

                        Log.d(TAG, "onDataChange: " + dataSnapshot.child(food_id).getValue());

                        break;
                    }
                    Log.d(TAG, "onDataChange: " + child.getKey() + "," + dataSnapshot.child(food_id).child("Name").getValue().equals(food_name));
                }
                //  Toast.makeText(FoodDetail.this, ""+child.getKey(), Toast.LENGTH_SHORT).show();
                foods = dataSnapshot.child(food_id).getValue(Food.class);

                Picasso.with(FoodDetail.this).load(foods.getImage())
                        .fit()
                        .centerCrop()
                        .into(img);
                collapsingToolbarLayout.setTitle(foods.getName());

                price.setText(foods.getPrice());
                Log.d(TAG, "onDataChange: setting price" + foods.getPrice());

                food.setText(foods.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
