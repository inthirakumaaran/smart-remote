package com.example.inthirakumaaranwso2com.smart_remote.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by inthirakumaaranwso2com on 7/15/17.
 */

public class DBremote extends SQLiteOpenHelper {
    private static final int DBversion =2;
    private static final String DBname= "smartRemote.db";
    private final String remotequery ="CREATE TABLE remotes ( _id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "name TEXT,power TEXT,chanelPrev TEXT,chanelNext TEXT,volDown TEXT,volUp TEXT);";

    public DBremote(Context context) {
        super(context, DBname, null, DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(remotequery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS remotes");
        onCreate(db);
    }
    public void addremote(String name,String power,String chDwn,String chUp,String VolUp,
                          String VolDwn){   //add remote
        ContentValues values =new ContentValues();
        values.put("name",name);
        values.put("power",power);
        values.put("chanelPrev",chDwn);
        values.put("chanelNext",chUp);
        values.put("volDown",VolDwn);
        values.put("volUp",VolUp);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("remotes",null,values);
        db.close();
    }

    public boolean checkremotename(String name){   //check remotename
        SQLiteDatabase db=getWritableDatabase();
        String query ="SELECT * FROM remotes WHERE name ='"+name
                +"';";
        Cursor c =db.rawQuery(query,null);
        c.moveToFirst();
        if (c.getCount()>0){
            return true;
        }
        c.close();
        db.close();
        return false;
    }
    public Cursor getcontent(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor data =db.rawQuery("select * from remotes",null);
        return  data;
    }
    public void deleteRemote(Integer id){
        SQLiteDatabase db=getWritableDatabase();
        String query ="DELETE from remotes WHERE _id ='"+id
                +"';";
        db.execSQL(query);
        db.close();
    }
}
