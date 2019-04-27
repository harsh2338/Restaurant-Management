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

import com.example.resmanage.FoodList;
import com.example.resmanage.Interface.ItemClickListener;
import com.example.resmanage.Model.Category;
import com.example.resmanage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private static final String TAG = "ImageAdapter";

    private Context mContext;
    private List<Category> menu = new ArrayList<Category>();

    ItemClickListener clickListener;

    public ImageAdapter(Context context, List<Category> menus) {
        mContext = context;
        menu = menus;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder menuViewHolder, final int i) {

        Log.d(TAG, "onBindViewHolder  : called");
        final Category mmenu = menu.get(i);
        menuViewHolder.txtMenu.setText(mmenu.getName());
        Picasso.with(mContext).load(mmenu.getImage())
                .fit()
                .centerCrop()
                .into(menuViewHolder.img);

        menuViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(TAG, "OnClick : clicked on : " + mmenu.getImage());
                String name = mmenu.getName();
                Toast.makeText(mContext, "" + name, Toast.LENGTH_SHORT).show();
                Intent foodList = new Intent(mContext, FoodList.class);
                foodList.putExtra("Name", name);
                Log.d(TAG, "onClick: clicked" + name);
                mContext.startActivity(foodList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menu.size();
    }
}

class MenuViewHolder extends RecyclerView.ViewHolder {

    public TextView txtMenu;
    public ImageView img;
    RelativeLayout relativeLayout;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenu = itemView.findViewById(R.id.menu_namae);
        img = itemView.findViewById(R.id.menu_list);

        relativeLayout = itemView.findViewById(R.id.parent_layout);

    }
}