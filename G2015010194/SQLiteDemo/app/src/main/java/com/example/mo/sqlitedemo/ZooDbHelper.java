package com.example.mo.sqlitedemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by mo on 15/10/15.
 */
public class ZooDbHelper extends SQLiteOpenHelper{
    public static final String ID_COLUMN = "_id";
    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String FILE_PATH_COLUMN = "filepath";

    public static final String DATABASE_NAME = "ZooOpenHelper.db";
    public static final String DATABASE_TABLE = "animals";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s text)",
            DATABASE_TABLE, ID_COLUMN, NAME_COLUMN, DESCRIPTION_COLUMN, FILE_PATH_COLUMN);

    public ZooDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists zoo");
        onCreate(db);
    }
}
