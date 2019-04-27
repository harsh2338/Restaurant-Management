package com.example.resmanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.resmanage.Adapter.CartAdapter;
import com.example.resmanage.Database.Database;
import com.example.resmanage.Model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartHD extends AppCompatActivity {
    private static final String TAG = "CartHD";
    public static float globaltotal;
    public static String globaladdress;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView xtotal;
    Button checkout;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        xtotal = findViewById(R.id.total);
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final String temp = NaviDraw.globalDineorHD;//intentForHD.getStringExtra("DineorHD");
        checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp.equals("HomeDelivery")) {
                    showAlertDialog();
                } else {
                    startActivity(new Intent(CartHD.this, Bill.class));
                }
            }
        });
        loadListFood();
    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartHD.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address");
        final EditText edtAddress = new EditText(CartHD.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_add_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                globaladdress = edtAddress.getText().toString();
                startActivity(new Intent(CartHD.this, Bill.class));
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);
        globaltotal = 0;
        for (Order order : cart) {

            globaltotal += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        }
        Locale locale = new Locale("en", "IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        xtotal.setText(fmt.format(globaltotal));
    }
}
