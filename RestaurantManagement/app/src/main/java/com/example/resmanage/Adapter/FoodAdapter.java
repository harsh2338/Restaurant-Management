package com.example.resmanage.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resmanage.FoodDetail;
import com.example.resmanage.Model.Food;
import com.example.resmanage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    String TAG;
    private Context mContext;
    private List<Food> foods;

    public FoodAdapter(Context context, List<Food> foods) {
        this.mContext = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.food_item, viewGroup, false);
        return new FoodViewHolder(v);
    }

    //this method is to display the name and image
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
        final Food ffood = foods.get(i);
        foodViewHolder.txtmenuName.setText(ffood.getName());
        Picasso.with(mContext).load(ffood.getImage())
                .fit()
                .centerCrop()
                .into(foodViewHolder.imgview);

        foodViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick : clicked on : " + ffood.getImage());
                String naem = ffood.getName();
                Toast.makeText(mContext, "" + naem, Toast.LENGTH_SHORT).show();
                Intent foodList = new Intent(mContext, FoodDetail.class);
                foodList.putExtra("Name", naem);
                foodList.putExtra("Image", ffood.getImage());
                mContext.startActivity(foodList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}

class FoodViewHolder extends RecyclerView.ViewHolder {

    public TextView txtmenuName;
    public ImageView imgview;

    RelativeLayout relativeLayout;

    public FoodViewHolder(View itemView) {
        super(itemView);

        txtmenuName = itemView.findViewById(R.id.food_namae);
        imgview = itemView.findViewById(R.id.food_list);

        relativeLayout = itemView.findViewById(R.id.food_layout);
    }


}