package com.example.rajivranjan.to_do;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "TODO";

    // Table columns
    public static final String KEY_ID = "Keyid";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_DATE = "Date";
    public static final String KEY_STATUS = "status";

    // Database Information
    static final String DB_NAME = "TODOAPP.DB";

    // database version
    static final int DB_VERSION = 1;


    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT NOT NULL, " + KEY_DESCRIPTION + " TEXT," + KEY_DATE + " TEXT," + KEY_STATUS + " INTEGER " + ")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insert(String name, String desc,String date, Integer status) {
        SQLiteDatabase db=null;
        Long i;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValue = new ContentValues();
            contentValue.put(KEY_TITLE, name);
            contentValue.put(KEY_DESCRIPTION, desc);
            contentValue.put(KEY_DATE, date);
            contentValue.put(KEY_STATUS, 0);
           i= db.insert(TABLE_NAME, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            this.getWritableDatabase().close();
        }
    }
    //end insert
    public ArrayList<Details> getDetails() {

        ArrayList<Details> dataArrayList = new ArrayList<Details>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from "+ TABLE_NAME +" ORDER BY " + KEY_DATE + " ASC",null );

      //      int x = cur.getCount();

            while (cur.moveToNext()) {

                Details details = new Details();
                details.setKey_id(Integer.toString(cur.getInt(cur.getColumnIndex(KEY_ID))));
                details.setTitle(cur.getString(cur.getColumnIndex(KEY_TITLE)));
                details.setDescription(cur.getString(cur.getColumnIndex(KEY_DESCRIPTION)));
                details.setDate(cur.getString(cur.getColumnIndex(KEY_DATE)));
                details.setStatus(Integer.toString(cur.getInt(cur.getColumnIndex(KEY_STATUS))));
                dataArrayList.add(details);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataArrayList;
    }
    public ArrayList<Details> getDetailson(String status) {

        ArrayList<Details> dataArrayList = new ArrayList<Details>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from "+ TABLE_NAME +" WHERE "+ KEY_STATUS + " = " + status +" ORDER BY " + KEY_STATUS + " ASC",null );

         //   int x = cur.getCount();

            while (cur.moveToNext()) {

                Details details = new Details();
                details.setKey_id(Integer.toString(cur.getInt(cur.getColumnIndex(KEY_ID))));
                details.setTitle(cur.getString(cur.getColumnIndex(KEY_TITLE)));
                details.setDescription(cur.getString(cur.getColumnIndex(KEY_DESCRIPTION)));
                details.setDate(cur.getString(cur.getColumnIndex(KEY_DATE)));
                details.setStatus(Integer.toString(cur.getInt(cur.getColumnIndex(KEY_STATUS))));
                dataArrayList.add(details);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataArrayList;
    }

    public long updatestatus(String status,String id) {
        long c = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_STATUS, Integer.parseInt(status.trim()));
                String[] whereArgs = new String[]{id};
                c = db.update(TABLE_NAME, values, "Keyid=?", whereArgs);
            db.close();
            this.getWritableDatabase().close();

        } catch (Exception e) {
            Log.e("ERROR 1",e.getLocalizedMessage());
            return 0;

        }
      return c;
    }
    public long updatedetails(Details data) {
        long c = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, (data.getTitle().trim()));
            values.put(KEY_DESCRIPTION,(data.getDescription().trim()));
            values.put(KEY_DATE, (data.getDate().trim()));
            String[] whereArgs = new String[]{data.getKey_id().trim()};
            c = db.update(TABLE_NAME, values, "Keyid=?", whereArgs);
            db.close();
            this.getWritableDatabase().close();

        } catch (Exception e) {
            Log.e("ERROR 1",e.getLocalizedMessage());
            return 0;

        }
        return c;
    }
    public void delete(String _id) {
     try {
         SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{_id});
     }catch (Exception e){
         e.printStackTrace();
     }
    }
}