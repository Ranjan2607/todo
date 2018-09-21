package com.example.rajivranjan.to_do;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.regex.Pattern;

public class ModifyTodoActivity extends Activity implements OnClickListener {

    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private EditText statText;
    private DatePicker selecteddate1;
    String strdate1 = "";

    private long _id;
    Details data;

    private DatabaseHelper dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_record);

        dbManager = new DatabaseHelper(this);


        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);
        selecteddate1 = (DatePicker) findViewById(R.id.edt_datePicker1);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        data = (Details) getIntent().getSerializableExtra("object");


        titleText.setText(data.getTitle());
        descText.setText(data.getDescription());
        String[] value_split = data.getDate().split(Pattern.quote("/"));

        selecteddate1.init(Integer.parseInt(value_split[2]), (Integer.parseInt(value_split[1])-1), Integer.parseInt(value_split[0]), null);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        selecteddate1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String title = titleText.getText().toString();
                String desc = descText.getText().toString();
                strdate1 = selecteddate1.getDayOfMonth() + "/" + (selecteddate1.getMonth() + 1) + "/" + selecteddate1.getYear();
                data.setTitle(title);
                data.setDescription(desc);
                data.setDate(strdate1);
                dbManager.updatedetails(data);
                this.returnHome();
                break;

            case R.id.btn_delete:
               // dbManager.delete(data.getKey_id());
                this.returnHome();
                break;

            case R.id.edt_datePicker1:
                strdate1 = selecteddate1.getDayOfMonth() + "/" + (selecteddate1.getMonth() + 1) + "/" + selecteddate1.getYear();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}