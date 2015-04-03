package com.codepath.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todolist"; // Database name
    private static final int DATABASE_VERSION = 5; // database version

    private static final String TABLE_NAME = "todo_items"; // table name

    //table columns
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM = "item";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_DATE = "due_date";

    public ItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating a table
        String CREATE_TODO_TABLE = "CREATE TABLE "+ TABLE_NAME
                                    + "( "+KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                    +KEY_ITEM + " TEXT NOT NULL, "
                                    +KEY_PRIORITY + " TEXT NOT NULL, "
                                    + KEY_DATE +" DATE );";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); // drop table if exists
        onCreate(db); // create table again
    }

    //adding item into the database
    public void addItemToDB (ItemModel item ){

        SQLiteDatabase writeDB = this.getWritableDatabase(); // Open database for writing
        // fetch each column value
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItemBody());
        values.put(KEY_PRIORITY, item.getPriority());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        values.put(KEY_DATE, df.format(item.getDueDate()));// Converting date to a string
        writeDB.insert(TABLE_NAME, null, values); // insert record into row
        writeDB.close(); // close sb connection
    }

    //querying selected item from the database
    public ItemModel getSingleItem (int id ){
        SQLiteDatabase readDb = this.getReadableDatabase(); // Open database for reading
        ItemModel item;
        Cursor cursor = readDb.query(TABLE_NAME, //table name
                new String []{KEY_ID, KEY_ITEM, KEY_PRIORITY}, // select column names
                KEY_ID + "=?", new String [] {String.valueOf(id)}, // where
                null, null, "due_date ASC", "100"); // Group by, having, order by and limit
        cursor.moveToFirst();
        item = new ItemModel(cursor.getString(1), cursor.getString(2),new Date(cursor.getLong(3)));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));


        readDb.close();
        return item;
    }

    public String getItemsByDate (String date){
        SQLiteDatabase readDb = this.getReadableDatabase(); // Open database for reading
        String todaysItems = "";
        Cursor cursor = readDb.query(TABLE_NAME, //table name
                                     new String []{KEY_ID, KEY_ITEM, KEY_PRIORITY}, // select column names
                                     KEY_DATE + "=?", new String [] {String.valueOf(date)}, // where
                                     null, null, null, "100"); // Group by, having, order by and limit
        while(cursor.moveToNext()) {
            String s= cursor.getString(cursor.getColumnIndexOrThrow(KEY_ITEM));
            todaysItems += s;
            if(cursor.isLast() == false){
                todaysItems += ", ";
            }
        }
        readDb.close();
        return todaysItems;
    }

    // querying all the items from database

    public ArrayList<ItemModel> getAllItems(){
        ArrayList<ItemModel> arrItems = new ArrayList<ItemModel>();

        String selectAllQuery = "SELECT * FROM "+TABLE_NAME + " ORDER BY "+KEY_DATE; // query to select all rows
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);
      /*  Cursor cursor = db.query(TABLE_NAME, //table name
                new String []{KEY_ID, KEY_ITEM, KEY_PRIORITY, KEY_DATE}, // select column names
                null, null, // where
                null, null, "due_date ASC", null); // Group by, having, order by and limit*/
        //looping through the cursor
       while(cursor.moveToNext()) {
           String s= cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE));
           SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
           Date d=new Date();
           try {
               d =  dateFormat.parse(s);
           } catch (ParseException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           ItemModel item = new ItemModel(cursor.getString(1), cursor.getString(2),d);
           item.setId(cursor.getInt(0));

          arrItems.add(item); // add each item row to the array list
        }
        db.close();
        return arrItems;

    }

    // updating a record
    public int updateItem (ItemModel item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItemBody());
        values.put(KEY_PRIORITY, item.getPriority());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); // Converting date to a string
        String selectedDate = df.format(item.getDueDate());
        values.put(KEY_DATE, selectedDate);

        int result = db.update(TABLE_NAME, values, KEY_ID +" = ? ", new String [] {String.valueOf(item.getId())}); // updating the row
        db.close();
        return result;
    }

    // deleting a record
    public void deleteItem (ItemModel item){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ? ", new String[]{String.valueOf(item.getId())}); // deleting the row
        db.close();
    }
}
