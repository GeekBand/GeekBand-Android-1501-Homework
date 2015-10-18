package com.snail.travellingTrail.trailMap.model;

public class FootprintComment
{
	long Ftprnt_Cmm_Id;       //  评论ID
	String Ftprnt_Cmm_Content; // 评论内容
	String Ftprnt_Cmm_Time; // 评论时间
	long Ftprnt_Cmm_Us_Id;  //用户ID
	String Ftprnt_Cmm_Us_Nickname;  //用户昵称
	String Ftprnt_Cmm_Us_Avatar;   //用户头像URL
	long Ftprnt_Cmm_Ftprnt_Id; //足迹点id
	





	public FootprintComment(long ftprnt_Cmm_Id, String ftprnt_Cmm_Content,
			String ftprnt_Cmm_Time, long ftprnt_Cmm_Us_Id,
			String ftprnt_Cmm_Us_Nickname, String ftprnt_Cmm_Us_Avatar,
			long ftprnt_Cmm_Ftprnt_Id)
	{
		super();
		Ftprnt_Cmm_Id = ftprnt_Cmm_Id;
		Ftprnt_Cmm_Content = ftprnt_Cmm_Content;
		Ftprnt_Cmm_Time = ftprnt_Cmm_Time;
		Ftprnt_Cmm_Us_Id = ftprnt_Cmm_Us_Id;
		Ftprnt_Cmm_Us_Nickname = ftprnt_Cmm_Us_Nickname;
		Ftprnt_Cmm_Us_Avatar = ftprnt_Cmm_Us_Avatar;
		Ftprnt_Cmm_Ftprnt_Id = ftprnt_Cmm_Ftprnt_Id;
	}


	public String getFtprnt_Cmm_Us_Avatar()
	{
		return Ftprnt_Cmm_Us_Avatar;
	}


	public void setFtprnt_Cmm_Us_Avatar(String ftprnt_Cmm_Us_Avatar)
	{
		Ftprnt_Cmm_Us_Avatar = ftprnt_Cmm_Us_Avatar;
	}


	public long getFtprnt_Cmm_Id()
	{
		return Ftprnt_Cmm_Id;
	}


	public void setFtprnt_Cmm_Id(long ftprnt_Cmm_Id)
	{
		Ftprnt_Cmm_Id = ftprnt_Cmm_Id;
	}


	public String getFtprnt_Cmm_Content()
	{
		return Ftprnt_Cmm_Content;
	}


	public void setFtprnt_Cmm_Content(String ftprnt_Cmm_Content)
	{
		Ftprnt_Cmm_Content = ftprnt_Cmm_Content;
	}


	public String getFtprnt_Cmm_Time()
	{
		return Ftprnt_Cmm_Time;
	}


	public void setFtprnt_Cmm_Time(String ftprnt_Cmm_Time)
	{
		Ftprnt_Cmm_Time = ftprnt_Cmm_Time;
	}


	public long getFtprnt_Cmm_Us_Id()
	{
		return Ftprnt_Cmm_Us_Id;
	}


	public void setFtprnt_Cmm_Us_Id(long ftprnt_Cmm_Us_Id)
	{
		Ftprnt_Cmm_Us_Id = ftprnt_Cmm_Us_Id;
	}


	public long getFtprnt_Cmm_Ftprnt_Id()
	{
		return Ftprnt_Cmm_Ftprnt_Id;
	}


	public void setFtprnt_Cmm_Ftprnt_Id(long ftprnt_Cmm_Ftprnt_Id)
	{
		Ftprnt_Cmm_Ftprnt_Id = ftprnt_Cmm_Ftprnt_Id;
	}


	public String getFtprnt_Cmm_Us_Nickname()
	{
		return Ftprnt_Cmm_Us_Nickname;
	}


	public void setFtprnt_Cmm_Us_Nickname(String ftprnt_Cmm_Us_Nickname)
	{
		Ftprnt_Cmm_Us_Nickname = ftprnt_Cmm_Us_Nickname;
	}
	
	
	
}
