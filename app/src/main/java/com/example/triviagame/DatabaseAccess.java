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
    HeaderClass headerClass = new HeaderClass();

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
        cursor = db.rawQuery("SELECT * FROM "+ HeaderClass.TABLE_NAME,new String[]{});
        //cursor.moveToFirst();
        return cursor.getCount();

    }
    private int totalCategory(){
        cursor = db.rawQuery("SELECT Category From trivia_questions GROUP BY Category"+ HeaderClass.TABLE_NAME,new String[]{});
        //cursor.moveToFirst();
        return cursor.getCount();

    }



    public String[] getCategories(){
        int number = totalCategory();
        String categoryArray[] = new String[number];

        cursor = db.rawQuery("SELECT Category From trivia_questions GROUP BY Category",new String[]{});

        return categoryArray;
    }

    public String[] getData(int randomID){
        cursor = db.rawQuery("SELECT "
                        +headerClass.QUESTION_COL+", "
                        +headerClass.OPTION1_COL+", "
                        +headerClass.OPTION2_COL+", "
                        +headerClass.OPTION3_COL+", "
                        +headerClass.OPTION4_COL+", "
                        +headerClass.CORRECT_ANS_COL+", "
                        +headerClass.CATEGORY_COL+
                        " FROM "+ HeaderClass.TABLE_NAME +
                         " WHERE "+headerClass.ID_COL+" = " +
                            randomID,new String[]{});

        String[] temp = new String[]{"", "","","", "","",""};

        while(cursor.moveToNext()){

            for (int i = 0; i <7; i++){
                temp[i] = cursor.getString(i);
            }
            /*
            temp[0] = cursor.getString(0); //Question
            temp[1] = cursor.getString(1); //Option1
            temp[2] = cursor.getString(2); //Option2
            temp[3] = cursor.getString(3); //Option3
            temp[4] = cursor.getString(4); //Option4
            temp[5] = cursor.getString(5); //correct_answer
            temp[6] = cursor.getString(6); //category
            //buffer.append(""+address+"");*/
        }

        return temp;
    }
}
