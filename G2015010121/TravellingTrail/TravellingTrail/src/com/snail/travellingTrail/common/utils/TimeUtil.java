package com.snail.travellingTrail.common.utils;

import java.text.SimpleDateFormat;

public class TimeUtil
{

	
	/**
	 * 获取当前日期和时间
	 * @return
	 */
	public static String getCurrentDateAndTime()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
	
	/**
	 * 获取当前日期和时间
	 * @return
	 */
	public static String getCurrentDate()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
}
