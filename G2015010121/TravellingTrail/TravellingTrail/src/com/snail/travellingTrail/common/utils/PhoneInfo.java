package com.snail.travellingTrail.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class PhoneInfo 
{
	public static int screenWidthPx;  //屏幕宽度像素
	public static int screenHeightPx;
	
	public static int getScreenWidth(Context mContext)
	{
		DisplayMetrics dm = new DisplayMetrics();  
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int srceenWidth = dm.widthPixels;
        Log.i("PhoneInfo", "screenWidthPx: " + srceenWidth);
        screenWidthPx = srceenWidth;
		return srceenWidth;
	}

	public static int getScreenHeight(Context mContext)
	{
		DisplayMetrics dm = new DisplayMetrics();  
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int srceenHeight = dm.heightPixels;
        Log.i("PhoneInfo", "screenHeightPx: " + srceenHeight);
        screenHeightPx = srceenHeight;
		return srceenHeight;
	}
	
	public static void loadScreenInfo(Context mContext)
	{	
		getScreenWidth(mContext);
		getScreenHeight(mContext);
	}
}
