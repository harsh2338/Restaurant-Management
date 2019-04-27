package com.example.resmanage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoSeaterTimings extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    GridView TwoGrid;
    byte no_of_table;
    public static String globalTableName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        no_of_table = intent.getByteExtra("No_of_tables_in_given_type", (byte) 1);

        setContentView(R.layout.activity_two_seater_timings);
        TwoGrid = findViewById(R.id.TwoGrid);
        TwoGrid.setAdapter(new BookingAdapter(this, no_of_table));

        TwoGrid.setOnItemClickListener(this);

        TwoGrid.setOnItemLongClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(view.getContext(), Set_Time.class);

        Informing_Table_TypeandName obj = new Informing_Table_TypeandName();
        obj.Table_TypeandName(view, intent, no_of_table);


        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), Show_Timings_Dialog.class);

        Informing_Table_TypeandName obj = new Informing_Table_TypeandName();
        obj.Table_TypeandName(view, intent, no_of_table);
        startActivity(intent);
        return true;//if false it will also do the task of single click (given abv) when long press is stopped
    }

}

class Informing_Table_TypeandName {
    public void Table_TypeandName(View view, Intent intent, byte no_of_table) {
        ViewHolderSeat holder = (ViewHolderSeat) view.getTag();
        Table temp = (Table) holder.tableNameHolder.getTag();
        //Intent intent1=new Intent(view.getContext(),Bill.class);//doubt
        //intent1.putExtra("TableNameToBill",temp.tableName);
        TwoSeaterTimings.globalTableName = temp.tableName;

        intent.putExtra("Table_name", temp.tableName);
        if (no_of_table == 6 || no_of_table == 1)
            intent.putExtra("Type_of_Child", "TwoSeater");
        else if (no_of_table == 4)
            intent.putExtra("Type_of_Child", "FourSeater");
        else
            intent.putExtra("Type_of_Child", "EightSeater");
    }

}

class ViewHolderSeat {
    ImageView tableImage;
    TextView tableNameHolder;

    ViewHolderSeat(View v) {
        tableImage = v.findViewById(R.id.imageViewTable);
        tableNameHolder = v.findViewById(R.id.textViewTableName);
    }
}

//we make a class to hold the views (TextView),(ImageView) so that when we refer to a particular grid
//we can directly refer to the object which contains these 2 things to be displayed
class Table {
    int imageId;//image id
    String tableName;//table name

    Table(int imageId, String tableName) {
        this.imageId = imageId;
        this.tableName = tableName;
    }

}

//we are extending to an abstract class called BaseAdapter
class BookingAdapter extends BaseAdapter //BaseAdapter provides rough idea what our app must do
{
    ArrayList<Table> list;
    Context context;

    BookingAdapter(Context context, byte no_of_tables)//context object is required to get our resources
    {
        this.context = context;
        list = new ArrayList<>();//stores Table objects
        Resources res = context.getResources();
        int gettingFromStringsResource;
        if (no_of_tables == 6 || no_of_tables == 1)
            gettingFromStringsResource = R.array.Two_Table_names_strings;
        else if (no_of_tables == 4)
            gettingFromStringsResource = R.array.Four_Table_names_strings;
        else
            gettingFromStringsResource = R.array.Eight_Table_names_strings;


        String[] tableName = res.getStringArray(gettingFromStringsResource);
        int[] tableImage = {R.drawable.twotable};
        for (int i = 0; i < no_of_tables; i++) {
            Table tempTableObject = new Table(tableImage[0], tableName[i]);
            list.add(tempTableObject);
        }

    }

    @Override
    public int getCount() //returns no of elements
    {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //convert view tells if we are creating the object for the 1st time or not
        //if 1st time then it has null
        View grid = convertView;
        ViewHolderSeat holder = null;
        if (grid == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.twotable, parent, false);//take xml file and gives java object
            //grid contains relative layout which is the root of table.xml
            holder = new ViewHolderSeat(grid);//performing findViewById operation

            grid.setTag(holder);//stroring holder so that we dont create it again when it can ve recycled
            //setTag is a special method used to store something in a View object

        } else//recycling ie retrieving the stored holder when recycling
        {
            holder = (ViewHolderSeat) grid.getTag();
        }
        Table temp = list.get(position);
        holder.tableImage.setImageResource(temp.imageId);
        holder.tableNameHolder.setText(temp.tableName);
        holder.tableNameHolder.setTag(temp);
        return grid;
    }
}
