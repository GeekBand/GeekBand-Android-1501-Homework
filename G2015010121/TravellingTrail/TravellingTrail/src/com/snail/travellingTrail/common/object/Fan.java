package com.snail.travellingTrail.common.object;

public class Fan
{

	private long Us_Fans_Us_Id;
	private long Us_Fans_Fans_Id;
	private String UsFans_Fans_Name;
	private String Us_Avatar;
	private String Us_Sinature;
	private String Us_Fans_Add_Time;

	

	public Fan(long us_Fans_Us_Id, long us_Fans_Fans_Id,
			String usFans_Fans_Name, String us_Avatar, String us_Sinature,
			String us_Fans_Add_Time)
	{
		super();
		Us_Fans_Us_Id = us_Fans_Us_Id;
		Us_Fans_Fans_Id = us_Fans_Fans_Id;
		UsFans_Fans_Name = usFans_Fans_Name;
		Us_Avatar = us_Avatar;
		Us_Sinature = us_Sinature;
		Us_Fans_Add_Time = us_Fans_Add_Time;
	}

	public long getUs_Fans_Us_Id()
	{
		return Us_Fans_Us_Id;
	}

	public void setUs_Fans_Us_Id(long us_Fans_Us_Id)
	{
		Us_Fans_Us_Id = us_Fans_Us_Id;
	}

	public long getUs_Fans_Fans_Id()
	{
		return Us_Fans_Fans_Id;
	}

	public void setUs_Fans_Fans_Id(long us_Fans_Fans_Id)
	{
		Us_Fans_Fans_Id = us_Fans_Fans_Id;
	}

	public String getUsFans_Fans_Name()
	{
		return UsFans_Fans_Name;
	}

	public void setUsFans_Fans_Name(String usFans_Fans_Name)
	{
		UsFans_Fans_Name = usFans_Fans_Name;
	}

	public String getUs_Fans_Add_Time()
	{
		return Us_Fans_Add_Time;
	}

	public void setUs_Fans_Add_Time(String us_Fans_Add_Time)
	{
		Us_Fans_Add_Time = us_Fans_Add_Time;
	}

	public String getUs_Avatar()
	{
		return Us_Avatar;
	}

	public void setUs_Avatar(String us_Avatar)
	{
		Us_Avatar = us_Avatar;
	}

	public String getUs_Sinature()
	{
		return Us_Sinature;
	}

	public void setUs_Sinature(String us_Sinature)
	{
		Us_Sinature = us_Sinature;
	}

}
