package com.example.tomeksz.spiewnik;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Songs.db";
    public static final String TABLE_NAME = "songs_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "AUTHOR";
    public static final String COL_4 = "LEVEL";
    public static final String COL_5 = "LYRICS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, AUTHOR TEXT, LEVEL TEXT, LYRICS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String title, String author, String level, String lyrics){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, author);
        contentValues.put(COL_4, level);
        contentValues.put(COL_5, lyrics);

        long success = db.insert(TABLE_NAME, null, contentValues);

        if(success == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur =db.rawQuery("Select * from " + TABLE_NAME, null);
        return cur;
    }

    public Cursor getData(String title_, String author, String poziom){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] arguments = new String[]{"%"+title_.trim()+"%","%"+author.trim()+"%","%"+poziom.trim()+"%"};
        Cursor cur =db.rawQuery("Select * from " + TABLE_NAME + " where TITLE LIKE ? and AUTHOR LIKE ? and LEVEL LIKE ?", arguments);
        return cur;
    }

    public Cursor getLyrics(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur =db.rawQuery("Select * from " + TABLE_NAME + " where ID = ?", new String[] {id});
        return cur;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
