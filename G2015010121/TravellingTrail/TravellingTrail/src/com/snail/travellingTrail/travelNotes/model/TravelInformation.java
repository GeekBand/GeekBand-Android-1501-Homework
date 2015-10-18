package com.snail.travellingTrail.travelNotes.model;

public class TravelInformation
{
	long Trvl_Id; 			  //旅程ID
	String Trvl_Name;  		  //旅程名
	String Trvl_Cover_Photo;  //旅程封面图
	String Trvl_Time_Start;   //旅程开始时间
	String Trvl_Time_End;    //旅程结束时间
	long Trvl_Like_Count;    //旅程点赞数
	String Trvl_Introduce;   //旅程介绍
	String Trvl_Conclusion;  //旅程总结
	long Trvl_Us_Id;         //用户ID
	String Us_Nickname;		//用户昵称
	String Us_Avatar;		//用户头像
	String Trvl_Snapshot;	//旅程截图
	int Trvl_Comment_Count;  //旅程评论数
	
	
	public TravelInformation(long trvl_Id, String trvl_Name,
			String trvl_Cover_Photo, String trvl_Time_Start,
			String trvl_Time_End, long trvl_Like_Count, String trvl_Introduce,
			String trvl_Conclusion, long trvl_Us_Id, String us_Nickname,
			String us_Avatar, String trvl_Snapshot, int trvl_Comment_Count)
	{
		super();
		Trvl_Id = trvl_Id;
		Trvl_Name = trvl_Name;
		Trvl_Cover_Photo = trvl_Cover_Photo;
		Trvl_Time_Start = trvl_Time_Start.substring(0, 10);
		Trvl_Time_End = trvl_Time_End.substring(0, 10);
		Trvl_Like_Count = trvl_Like_Count;
		Trvl_Introduce = trvl_Introduce;
		Trvl_Conclusion = trvl_Conclusion;
		Trvl_Us_Id = trvl_Us_Id;
		Us_Nickname = us_Nickname;
		Us_Avatar = us_Avatar;
		Trvl_Snapshot = trvl_Snapshot;
		Trvl_Comment_Count = trvl_Comment_Count;
	}


	public long getTrvl_Id()
	{
		return Trvl_Id;
	}


	public void setTrvl_Id(long trvl_Id)
	{
		Trvl_Id = trvl_Id;
	}


	public String getTrvl_Name()
	{
		return Trvl_Name;
	}


	public void setTrvl_Name(String trvl_Name)
	{
		Trvl_Name = trvl_Name;
	}


	public String getTrvl_Cover_Photo()
	{
		return Trvl_Cover_Photo;
	}


	public void setTrvl_Cover_Photo(String trvl_Cover_Photo)
	{
		Trvl_Cover_Photo = trvl_Cover_Photo;
	}


	public String getTrvl_Time_Start()
	{
		return Trvl_Time_Start;
	}


	public void setTrvl_Time_Start(String trvl_Time_Start)
	{
		Trvl_Time_Start = (String) trvl_Time_Start.substring(0, 10);
	}


	public String getTrvl_Time_End()
	{
		return Trvl_Time_End;
	}


	public void setTrvl_Time_End(String trvl_Time_End)
	{
		Trvl_Time_End = trvl_Time_End.substring(0, 10);
	}


	public long getTrvl_Like_Count()
	{
		return Trvl_Like_Count;
	}


	public void setTrvl_Like_Count(long trvl_Like_Count)
	{
		Trvl_Like_Count = trvl_Like_Count;
	}


	public String getTrvl_Introduce()
	{
		return Trvl_Introduce;
	}


	public void setTrvl_Introduce(String trvl_Introduce)
	{
		Trvl_Introduce = trvl_Introduce;
	}


	public String getTrvl_Conclusion()
	{
		return Trvl_Conclusion;
	}


	public void setTrvl_Conclusion(String trvl_Conclusion)
	{
		Trvl_Conclusion = trvl_Conclusion;
	}


	public long getTrvl_Us_Id()
	{
		return Trvl_Us_Id;
	}


	public void setTrvl_Us_Id(long trvl_Us_Id)
	{
		Trvl_Us_Id = trvl_Us_Id;
	}


	public String getUs_Nickname()
	{
		return Us_Nickname;
	}


	public void setUs_Nickname(String us_Nickname)
	{
		Us_Nickname = us_Nickname;
	}


	public String getUs_Avatar()
	{
		return Us_Avatar;
	}


	public void setUs_Avatar(String us_Avatar)
	{
		Us_Avatar = us_Avatar;
	}


	public String getTrvl_Snapshot()
	{
		return Trvl_Snapshot;
	}


	public void setTrvl_Snapshot(String trvl_Snapshot)
	{
		Trvl_Snapshot = trvl_Snapshot;
	}


	public int getTrvl_Comment_Count()
	{
		return Trvl_Comment_Count;
	}


	public void setTrvl_Comment_Count(int trvl_Comment_Count)
	{
		Trvl_Comment_Count = trvl_Comment_Count;
	}
	
	
	
}
