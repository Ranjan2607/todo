package com.example.rajivranjan.to_do;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.rajivranjan.to_do.DatabaseHelper.KEY_ID;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc,String date, Integer status) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.KEY_TITLE, name);
        contentValue.put(DatabaseHelper.KEY_DESCRIPTION, desc);
        contentValue.put(DatabaseHelper.KEY_DATE, date);
        contentValue.put(DatabaseHelper.KEY_STATUS, status);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]
                { KEY_ID, DatabaseHelper.KEY_TITLE, DatabaseHelper.KEY_DESCRIPTION,DatabaseHelper.KEY_DATE, DatabaseHelper.KEY_STATUS };
        String mytime="Aug 15, 2018";
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                " dd/MMM/ yyyy");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(DatabaseHelper.KEY_DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null,  myDate+ " ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long KEY_id, String name, String desc,String date, Integer status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_TITLE, name);
        contentValues.put(DatabaseHelper.KEY_DESCRIPTION, desc);
        contentValues.put(DatabaseHelper.KEY_DATE, date);
        contentValues.put(DatabaseHelper.KEY_STATUS, status);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, KEY_ID + " = " + KEY_id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, KEY_ID + "=" + KEY_ID, null);
    }

}
