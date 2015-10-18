package com.snail.travellingTrail.common;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.snail.travellingTrail.common.object.User;
import com.snail.travellingTrail.common.persistence.DatabaseManager;
import com.snail.travellingTrail.common.persistence.MySQLiteOpenHelper;

import android.app.Application;


public class TravellingTrailApplication extends Application
{
	public static final String TENCENT_MAP_KEY = "CZWBZ-FAUH3-SJZ3L-3JNJP-UOA3E-JGFET";
	public static User loginUser;
	private static MySQLiteOpenHelper dbOpenHelper;
	private static DatabaseManager dbManager;

	@Override
	public void onCreate()
	{
		Fresco.initialize(TravellingTrailApplication.this);
		
		dbOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
		dbManager = new DatabaseManager(dbOpenHelper.getWritableDatabase());
		
		super.onCreate();
	}

	
	public static MySQLiteOpenHelper getDbOpenHelper()
	{
		return dbOpenHelper;
	}


	public static DatabaseManager getDbManager()
	{
		return dbManager;
	}

	
}
