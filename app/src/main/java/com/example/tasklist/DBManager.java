package com.example.tasklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public void insert(String name, String desc, String check, String urgent) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        contentValue.put(DatabaseHelper.DESC, desc);
        contentValue.put(DatabaseHelper.CHECK, check);
        contentValue.put(DatabaseHelper.URGENT, urgent);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC, DatabaseHelper.CHECK, DatabaseHelper.URGENT };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public int update(long id, String title, String desc, String checkBoxChange, String urgent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, title);
        contentValues.put(DatabaseHelper.DESC, desc);
        contentValues.put(DatabaseHelper.CHECK, checkBoxChange);
        contentValues.put(DatabaseHelper.URGENT, urgent);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + id, null);
        return i;
    }

    public Cursor searchByCheck(){
        String s = "SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE "+  DatabaseHelper.CHECK + " = " + "'"+ "Task Done" +"'";
        Cursor cursor = database.rawQuery(s, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor searchByNotCheck(){
        String s = "SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE "+  DatabaseHelper.CHECK + " = " + "'"+ "Task to Perform" +"'";
        Cursor cursor = database.rawQuery(s, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor searchByDate(String date){
        String s = "SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE "+  DatabaseHelper.DESC + " = " + "'"+ date +"'";
        Cursor cursor = database.rawQuery(s, null);
        cursor.moveToFirst();
        return cursor;
    }
}
