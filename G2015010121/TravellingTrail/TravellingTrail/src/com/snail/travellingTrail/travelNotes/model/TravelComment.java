package com.snail.travellingTrail.travelNotes.model;

public class TravelComment
{
	
	long Trvl_Cmm_Id;       //  评论ID
	String Trvl_Cmm_Content; // 评论内容
	String Trvl_Cmm_Time; // 评论时间
	long Trvl_Cmm_Trvl_Id;  //旅程ID
	long Trvl_Cmm_Us_Id; //用户ID
	String Trvl_Cmm_Us_Nickname;  //用户昵称
	String Trvl_Cmm_Us_Avatar;   //用户头像URL
	
	
	
	
	public TravelComment(long trvl_Cmm_Id, String trvl_Cmm_Content,
			String trvl_Cmm_Time, long trvl_Cmm_Trvl_Id, long trvl_Cmm_Us_Id,
			String trvl_Cmm_Us_Nickname, String trvl_Cmm_Us_Avatar)
	{
		super();
		Trvl_Cmm_Id = trvl_Cmm_Id;
		Trvl_Cmm_Content = trvl_Cmm_Content;
		Trvl_Cmm_Time = trvl_Cmm_Time;
		Trvl_Cmm_Trvl_Id = trvl_Cmm_Trvl_Id;
		Trvl_Cmm_Us_Id = trvl_Cmm_Us_Id;
		Trvl_Cmm_Us_Nickname = trvl_Cmm_Us_Nickname;
		Trvl_Cmm_Us_Avatar = trvl_Cmm_Us_Avatar;
	}
	public long getTrvl_Cmm_Id()
	{
		return Trvl_Cmm_Id;
	}
	public void setTrvl_Cmm_Id(long trvl_Cmm_Id)
	{
		Trvl_Cmm_Id = trvl_Cmm_Id;
	}
	public String getTrvl_Cmm_Content()
	{
		return Trvl_Cmm_Content;
	}
	public void setTrvl_Cmm_Content(String trvl_Cmm_Content)
	{
		Trvl_Cmm_Content = trvl_Cmm_Content;
	}
	public String getTrvl_Cmm_Time()
	{
		return Trvl_Cmm_Time;
	}
	public void setTrvl_Cmm_Time(String trvl_Cmm_Time)
	{
		Trvl_Cmm_Time = trvl_Cmm_Time;
	}
	public long getTrvl_Cmm_Trvl_Id()
	{
		return Trvl_Cmm_Trvl_Id;
	}
	public void setTrvl_Cmm_Trvl_Id(long trvl_Cmm_Trvl_Id)
	{
		Trvl_Cmm_Trvl_Id = trvl_Cmm_Trvl_Id;
	}
	public long getTrvl_Cmm_Us_Id()
	{
		return Trvl_Cmm_Us_Id;
	}
	public void setTrvl_Cmm_Us_Id(long trvl_Cmm_Us_Id)
	{
		Trvl_Cmm_Us_Id = trvl_Cmm_Us_Id;
	}
	public String getTrvl_Cmm_Us_Nickname()
	{
		return Trvl_Cmm_Us_Nickname;
	}
	public void setTrvl_Cmm_Us_Nickname(String trvl_Cmm_Us_Nickname)
	{
		Trvl_Cmm_Us_Nickname = trvl_Cmm_Us_Nickname;
	}
	public String getTrvl_Cmm_Us_Avatar()
	{
		return Trvl_Cmm_Us_Avatar;
	}
	public void setTrvl_Cmm_Us_Avatar(String trvl_Cmm_Us_Avatar)
	{
		Trvl_Cmm_Us_Avatar = trvl_Cmm_Us_Avatar;
	}


}
