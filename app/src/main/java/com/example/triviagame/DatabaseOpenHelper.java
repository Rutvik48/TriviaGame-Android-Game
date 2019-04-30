package com.example.triviagame;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;
    HeaderClass headerClass = new HeaderClass();



    public DatabaseOpenHelper(Context context) {
        super(context, "GameDatabase.db",null, DATABASE_VERSION);
    }
}
