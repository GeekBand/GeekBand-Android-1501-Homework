package com.snail.travellingTrail.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastHelper
 * Toast助手类
 * @author ZJJ
 *
 */

public class ToastHelper
{
	private static Toast toast;
	
	
	/**
	 * 使连续显示Toast时不会重复堆叠显示，不会延迟显示
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context context, String text, int duration)
	{
		if (toast == null)
		{
			toast = Toast.makeText(context, text, duration);
		}
		else {
			toast.setText(text);
			toast.setDuration(duration);
		}
		toast.show();
	}
	
	
	/**
	 * 停止显示Toast
	 */
	public static void cencelToast()
	{
		if (toast != null)
		{
			toast.cancel();
		}
	}
}
