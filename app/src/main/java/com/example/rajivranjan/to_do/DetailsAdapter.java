package com.example.rajivranjan.to_do;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;


/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class DetailsAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    int i = 0;

    /*************  CustomAdapter Constructor *****************/
    public DetailsAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data = d;

        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder {

        public TextView text;
        public TextView text1;
        public TextView text2;
        public TextView text3;

        public ToggleButton imgdone;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;



            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
        convertView = inflater.inflate(R.layout.activity_view_record, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.title);
            holder.text1 = (TextView) convertView.findViewById(R.id.desc);
            holder.text2 = (TextView) convertView.findViewById(R.id.date);
            holder.text3 = (TextView) convertView.findViewById(R.id.datetile);
            holder.imgdone = (ToggleButton) convertView.findViewById(R.id.toggle);


        final Details tempValues = (Details) data.get(position);
        holder.text.setText(tempValues.getTitle());
        holder.text1.setText(tempValues.getDescription());
        holder.text2.setText(tempValues.getDate());
        holder.text3.setText(tempValues.getDate());
        if (tempValues.getStatus().equalsIgnoreCase("0")) {
            holder.imgdone.setChecked(false);
        } else {
            holder.imgdone.setChecked(true);

        }
        holder.imgdone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    new DatabaseHelper(activity).updatestatus("1", tempValues.getKey_id());
                } else {
                    new DatabaseHelper(activity).updatestatus("0", tempValues.getKey_id());
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(activity,ModifyTodoActivity.class);
                i.putExtra("object",tempValues);
                activity.startActivity(i);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new DatabaseHelper(activity).delete(tempValues.getKey_id());
                return false;
            }
        });
        return convertView;
    }


}