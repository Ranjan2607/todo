package com.example.rajivranjan.to_do;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbManager;
    ArrayList<Details> Detailslist = null;
    private ListView listView;
    private boolean isChecked = false;
  //  private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//show record from table
        listView = (ListView) findViewById(R.id.list_view);
        dbManager = new DatabaseHelper(MainActivity.this);
        Detailslist = dbManager.getDetails();
        if (Detailslist.size() > 0) {
            listView.setAdapter(new DetailsAdapter(MainActivity.this, Detailslist));

        } else {
            ((TextView) findViewById(R.id.empty)).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    //onclick on menu items

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //switch activity
        if (id == R.id.action_add) {

            Intent add_mem = new Intent(this, AddTodoActivity.class);
            startActivity(add_mem);

        } else if (id == R.id.action_like) {
            isChecked = !item.isChecked();
            item.setChecked(isChecked);
            if (isChecked) {
                item.setIcon(R.drawable.ic_action_like);
                //return arraylist from getdetailson
                Detailslist = dbManager.getDetailson("1");
                if (Detailslist.size() > 0) {
                    listView.setAdapter(new DetailsAdapter(MainActivity.this, Detailslist));

                } else {
                    ((TextView) findViewById(R.id.empty)).setVisibility(View.VISIBLE);
                }
            } else {
                item.setIcon(R.drawable.ic_action_unlike);
                Detailslist = dbManager.getDetailson("0");
                if (Detailslist.size() > 0) {
                    listView.setAdapter(new DetailsAdapter(MainActivity.this, Detailslist));

                } else {
                    ((TextView) findViewById(R.id.empty)).setVisibility(View.VISIBLE);
                }
            }
            Toast.makeText(this, "" + isChecked, Toast.LENGTH_SHORT).show();
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        Detailslist.clear();
        Detailslist = dbManager.getDetails();
        super.onResume();
        if (Detailslist.size() > 0) {
            listView.setAdapter(new DetailsAdapter(MainActivity.this, Detailslist));

        } else {
            ((TextView) findViewById(R.id.empty)).setVisibility(View.VISIBLE);
        }

    }
}
