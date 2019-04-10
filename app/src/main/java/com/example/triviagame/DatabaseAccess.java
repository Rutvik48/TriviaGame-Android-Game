package com.example.triviagame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor cursor = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }
        return  instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db != null){
            this.db.close();
        }
    }
    public int totalQuestions(){
        cursor = db.rawQuery("SELECT * FROM Trivia",new String[]{});
        //cursor.moveToFirst();
        return cursor.getCount();

        //return Integer.parseInt(cursor.getString(0));
        /*int temp;
        while(cursor.moveToNext()){
            temp = Integer.parseInt(cursor.getString(0));
            //temp[1] = cursor.getString(1);
            //buffer.append(""+address+"");

        }
        return 1500;*/
    }

    public String[] getAddress(int randomID){
        cursor = db.rawQuery("SELECT Question, Answer FROM Trivia WHERE ID = " + randomID,new String[]{});
        String[] temp = new String[]{"", ""};
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            temp[0] = cursor.getString(0);
            temp[1] = cursor.getString(1);
            //buffer.append(""+address+"");
        }

        return temp;
    }
}
