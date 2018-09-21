package com.example.rajivranjan.to_do;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoActivity extends Activity implements OnClickListener {

    private Button addTodoBtn, cancelbtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private DatePicker selecteddate;
    String strdate = "";
    private EditText statEditText;

    private DatabaseHelper dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_layout);
        dbManager = new DatabaseHelper(AddTodoActivity.this);
        subjectEditText = (EditText) findViewById(R.id.title_edittext);
        descEditText = (EditText) findViewById(R.id.description_edittext);
        selecteddate = (DatePicker) findViewById(R.id.datePicker1);
        addTodoBtn = (Button) findViewById(R.id.add_record);
        cancelbtn = (Button) findViewById(R.id.add_cancel);
        addTodoBtn.setOnClickListener(this);
        selecteddate.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:
                final String name = subjectEditText.getText().toString();
                final String desc = descEditText.getText().toString();
                strdate = selecteddate.getDayOfMonth() + "/" + (selecteddate.getMonth() + 1) + "/" + selecteddate.getYear();
                //    final String status = statEditText.getText().toString();
                //validation for not null
                if (name.equalsIgnoreCase("") || desc.equalsIgnoreCase("")) {
                    Toast.makeText(this, "please write something in description or in title", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.insert(name, desc, strdate, null);
                }
                Intent main = new Intent(AddTodoActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
            case R.id.datePicker1:
                strdate = selecteddate.getDayOfMonth() + "/" + (selecteddate.getMonth() + 1) + "/" + selecteddate.getYear();
                break;
            case R.id.add_cancel:
                finish();
                break;

        }
    }

}