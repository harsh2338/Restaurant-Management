package com.example.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Manager_Feedback extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ListView Lvmnfb;
    String[] menu={"Feedback of Dining Experience","Feedback of Home Delivery"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__feedback);
        Lvmnfb=(ListView)findViewById(R.id.ListViewMNFB);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        Lvmnfb.setAdapter(adapter);
        Lvmnfb.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView temp=(TextView)view;//casting the view(manager's choice) to textView
        if(temp.getText().toString().equals("Feedback of Dining Experience"))
            startActivity(new Intent(Manager_Feedback.this,DineFBMN.class));
        else
            startActivity(new Intent(Manager_Feedback.this,HomeDeliveryFBMN.class));
    }
}
