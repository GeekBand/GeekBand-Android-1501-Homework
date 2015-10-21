package com.travis.q7sqlimplement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class SQLHelper extends SQLiteOpenHelper{

    private Context context;
    public static final String DB_NAME = "zooOpenHelper.db";
    public static final String TABLE_NAME = "animals";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            "_id integer primary key autoincrement, " +
            "name text, " +
            "decription text);";


    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
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
}
