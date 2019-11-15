package com.example.kefeng.withyou;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kefeng on 7/26/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static  final String dataBaseName = "User.db";
    public static  final String tableName = "User_db";
    public static  final String column1 = "UseName";

    public static  final String column2 = "Email";




    public DatabaseHelper(Context context) {
        super(context, dataBaseName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + tableName + "(EMAIL TEXT PRIMARY KAY, USERNAME TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ){
        db.execSQL("Drop table if exists" + tableName);
        onCreate(db);

    }
    public boolean insertData(String email, String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1,email);
        contentValues.put(column2,userName);
        long result = db.insert(tableName,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }
}
