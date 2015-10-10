package com.example.forth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlite extends SQLiteOpenHelper{	
	public MySqlite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	private String createTable(){
		return "create table animals ("
				+ "id  INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name VARCHAR(100), "
				+ "description varchar(1000))";	}	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createTable());				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists zoo");
		onCreate(db);		
	}
}
