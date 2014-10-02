package com.fragment.fragment.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.util.Log;

import com.fragment.fragment.Chat;
import com.fragment.fragment.R;
import com.fragment.fragment.UserTracker;

import java.util.ArrayList;

/**
  Created by user on 9/15/14.
 */
public class HandlerDatabase {
    //Database Model
    private ModelDatabase model;

    //Database
    private SQLiteDatabase database;

    //All Fields
    private String[] allColumns = {
            ModelDatabase.USER_ID,
            ModelDatabase.USER_NAME,
            ModelDatabase.USER_MESSAGE,
            ModelDatabase.USER_TIME
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){
        model = new ModelDatabase(context);
    }

    /**
     * Add
     */
    public void addMessageToDatabase(String username, String message, String time){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.USER_NAME, username);
        values.put(ModelDatabase.USER_MESSAGE, message);
        values.put(ModelDatabase.USER_TIME, time);
        database.insert(ModelDatabase.TABLE_NAME, null, values);
    }

    public void updateChat(int id, String message, String time){
        ContentValues values = new ContentValues();
            values.put(ModelDatabase.USER_ID, id);
            values.put(ModelDatabase.USER_MESSAGE, message);
            values.put(ModelDatabase.USER_NAME, UserTracker.username);
            values.put(ModelDatabase.USER_TIME, time);
        database.update(ModelDatabase.TABLE_NAME, values, ModelDatabase.USER_ID + " like '%" + id + "%'", null);
    }


    /**
     * Get*/


    public ArrayList<Chat> getAllMessages(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns, null, null, null, null, null));
    }



    public ArrayList<Chat> getChatsByUser(String user){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.USER_NAME + " like '%" + user + "%'",
                null, null, null, null, null
        ));
    }


    public Chat getChatsById(String id){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.USER_ID + " like '%" + id + "%'",
                null, null, null, null
                )).get(0);
    }



     // Delete


    public void deleteChats(){
        database.delete(ModelDatabase.TABLE_NAME, null, null);
    }


     // Additional Helpers



    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<Chat> sweepCursor(Cursor cursor){
        ArrayList<Chat> messages = new ArrayList<Chat>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Kitty
            Chat chat = new Chat(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            //Add the Kitty
            messages.add(chat);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return messages;
    }

    //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}