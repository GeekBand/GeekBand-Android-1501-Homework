package com.snail.travellingTrail.common.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper
{

	final static String DATABASE_NAME = "TravellingTrail";
	final static int DATABASE_VERSION = 1;
	final static String CREATE_IMAGE_TABLE = "CREATE TABLE IF NOT EXISTS draft("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT, name TEXT)";
	
	
	public MySQLiteOpenHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_IMAGE_TABLE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
	}

}
