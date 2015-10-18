package com.snail.travellingTrail.trailMap.model;

import java.io.Serializable;
import java.util.List;

public class Footprint implements Serializable
{
	long Ftprnt_Id;   //足迹点ID
	double Ftprnt_X;  //x坐标
	double Ftprnt_Y;  //y坐标
	String Ftprnt_Address;  //地点名
	String Ftprnt_Time;  //时间
	long Ftprnt_Trvl_Id;  //旅程ID
	List<FootprintContent> Footprint_Content;
	int Ftprnt_Comment_Count;
	
	
	public Footprint(double ftprnt_X, double ftprnt_Y, String ftprnt_Address, long ftprnt_Trvl_Id)
	{
		Ftprnt_X = ftprnt_X;
		Ftprnt_Y = ftprnt_Y;
		Ftprnt_Address = ftprnt_Address;
		Ftprnt_Trvl_Id = ftprnt_Trvl_Id;
	}
	
	
	public Footprint(long ftprnt_Id, double ftprnt_X, double ftprnt_Y,
			String ftprnt_Address, String ftprnt_Time, long ftprnt_Trvl_Id,
			List<FootprintContent> footprint_Content, int ftprnt_Comment_Count)
	{
		Ftprnt_Id = ftprnt_Id;
		Ftprnt_X = ftprnt_X;
		Ftprnt_Y = ftprnt_Y;
		Ftprnt_Address = ftprnt_Address;
		Ftprnt_Time = ftprnt_Time;
		Ftprnt_Trvl_Id = ftprnt_Trvl_Id;
		Footprint_Content = footprint_Content;
		Ftprnt_Comment_Count = ftprnt_Comment_Count;
	}


	public long getFtprnt_Id()
	{
		return Ftprnt_Id;
	}


	public void setFtprnt_Id(long ftprnt_Id)
	{
		Ftprnt_Id = ftprnt_Id;
	}


	public double getFtprnt_X()
	{
		return Ftprnt_X;
	}


	public void setFtprnt_X(double ftprnt_X)
	{
		Ftprnt_X = ftprnt_X;
	}


	public double getFtprnt_Y()
	{
		return Ftprnt_Y;
	}


	public void setFtprnt_Y(double ftprnt_Y)
	{
		Ftprnt_Y = ftprnt_Y;
	}


	public String getFtprnt_Address()
	{
		return Ftprnt_Address;
	}


	public void setFtprnt_Address(String ftprnt_Address)
	{
		Ftprnt_Address = ftprnt_Address;
	}


	public String getFtprnt_Time()
	{
		return Ftprnt_Time;
	}


	public void setFtprnt_Time(String ftprnt_Time)
	{
		Ftprnt_Time = ftprnt_Time;
	}


	public long getFtprnt_Trvl_Id()
	{
		return Ftprnt_Trvl_Id;
	}


	public void setFtprnt_Trvl_Id(long ftprnt_Trvl_Id)
	{
		Ftprnt_Trvl_Id = ftprnt_Trvl_Id;
	}


	public List<FootprintContent> getFootprint_Content()
	{
		return Footprint_Content;
	}


	public void setFootprint_Content(List<FootprintContent> footprint_Content)
	{
		Footprint_Content = footprint_Content;
	}


	public int getFtprnt_Comment_Count()
	{
		return Ftprnt_Comment_Count;
	}


	public void setFtprnt_Comment_Count(int ftprnt_Comment_Count)
	{
		Ftprnt_Comment_Count = ftprnt_Comment_Count;
	}
	
	
	
	
	
	
}
