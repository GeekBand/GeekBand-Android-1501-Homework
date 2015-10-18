package com.snail.travellingTrail.common.persistence;

import java.util.ArrayList;
import java.util.List;

import com.snail.travellingTrail.newTravel.model.ImageData;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager
{
	
	private SQLiteDatabase database;
	
	public DatabaseManager(SQLiteDatabase database)
	{
		this.database = database;
	}
	
	
	/**
	 * 插入图片到数据库
	 * @param data
	 * @return
	 */
	public int insertImage(ImageData data)
	{
		database.beginTransaction();  //开始事务
		int id = -1;
		try
		{
			database.execSQL("INSERT INTO draft VALUES(null, ?, ?)", new Object[]{data.getPath(), data.getName()});
			Cursor cursor = database.rawQuery("SELECT LAST_INSERT_ROWID()", null);
			cursor.moveToFirst();
			id = cursor.getInt(0);
			cursor.close();
			database.setTransactionSuccessful();  //设置事务成功完成
			database.endTransaction();
		} catch (Exception e)
		{
			database.endTransaction();
		}
		return id;
	}
	
	
	/**
	 * 修改图片名
	 * @param data
	 * @return
	 */
	public boolean reivseImageName(ImageData data)
	{
		database.beginTransaction();
		try
		{
			database.execSQL("UPDATE draft SET name = ? WHERE id = ?", new Object[]{data.getName(), data.getId()});
			database.setTransactionSuccessful();
			database.endTransaction();
			return true;
		} catch (Exception e)
		{
			database.endTransaction();
			return false;
		}
	}
	
	
	/**
	 * 查询图片列表
	 * @return
	 */
	public List<ImageData> queryImageList()
	{
		database.beginTransaction();
		List<ImageData> dataList = new ArrayList<ImageData>();
		try
		{
			Cursor cursor = database.rawQuery("SELECT * FROM draft", null);
			while (cursor.moveToNext())
			{
				ImageData imageData = new ImageData(
						cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				dataList.add(imageData);
			};
			cursor.close();
			database.setTransactionSuccessful();
			database.endTransaction();
		} catch (Exception e)
		{
			Log.e("DatabaseManager--->queryImageList", e.toString());
			database.endTransaction();
			return null;
		}
		return dataList;
	}
	
	
	/**
	 * 删除图片
	 * @param data
	 * @return
	 */
	public boolean removeImage(ImageData data)
	{
		database.beginTransaction();
		try
		{
			database.execSQL("DELETE FROM draft WHERE id = ?", new Object[]{data.getId()});
			database.setTransactionSuccessful();
			database.endTransaction();
			return true;
		} catch (Exception e)
		{
			database.endTransaction();
			return false;
		}
	}
}
