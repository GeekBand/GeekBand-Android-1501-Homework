package com.snail.travellingTrail.common.object;

public class User
{
	private long Us_Info_Us_Id;  //用户ID
	private String Us_Nickname;  //昵称
    private String Us_Birthday;  //生日
    private String Us_Location;  //所在地
    private String Us_Addresss;	//详细地址
    private String Us_Sinature;	//个性签名，单词拼写错了
    private String Us_Introduce; //用户简介
    private int Us_Sex;  //性别
    private String Us_Avatar; //头像URL
    private String Us_Email;  //邮箱地址
    private boolean isInTravlling = true;  //是否有未完成旅程
    private long idOfTravelInTravlling = -1;  //未完成旅程id;
    


	public User()
	{
		super();
	}
    
	public User(long us_Info_Us_Id, String us_Nickname, String us_Birthday,
			String us_Location, String us_Addresss, String us_Sinature,
			String us_Introduce, int us_Sex, String us_Avatar, String us_Email)
	{
		super();
		Us_Info_Us_Id = us_Info_Us_Id;
		Us_Nickname = us_Nickname;
		Us_Birthday = us_Birthday;
		Us_Location = us_Location;
		Us_Addresss = us_Addresss;
		Us_Sinature = us_Sinature;
		Us_Introduce = us_Introduce;
		Us_Sex = us_Sex;
		Us_Avatar = us_Avatar;
		Us_Email = us_Email;
	}

	public long getUs_Info_Us_Id()
	{
		return Us_Info_Us_Id;
	}

	public void setUs_Info_Us_Id(long us_Info_Us_Id)
	{
		Us_Info_Us_Id = us_Info_Us_Id;
	}

	public String getUs_Nickname()
	{
		return Us_Nickname;
	}

	public void setUs_Nickname(String us_Nickname)
	{
		Us_Nickname = us_Nickname;
	}

	public String getUs_Birthday()
	{
		return Us_Birthday;
	}

	public void setUs_Birthday(String us_Birthday)
	{
		Us_Birthday = us_Birthday;
	}

	public String getUs_Location()
	{
		return Us_Location;
	}

	public void setUs_Location(String us_Location)
	{
		Us_Location = us_Location;
	}

	public String getUs_Addresss()
	{
		return Us_Addresss;
	}

	public void setUs_Addresss(String us_Addresss)
	{
		Us_Addresss = us_Addresss;
	}

	public String getUs_Sinature()
	{
		return Us_Sinature;
	}

	public void setUs_Sinature(String us_Sinature)
	{
		Us_Sinature = us_Sinature;
	}

	public String getUs_Introduce()
	{
		return Us_Introduce;
	}

	public void setUs_Introduce(String us_Introduce)
	{
		Us_Introduce = us_Introduce;
	}

	public int getUs_Sex()
	{
		return Us_Sex;
	}

	public void setUs_Sex(int us_Sex)
	{
		Us_Sex = us_Sex;
	}

	public String getUs_Avatar()
	{
		return Us_Avatar;
	}

	public void setUs_Avatar(String us_Avatar)
	{
		Us_Avatar = us_Avatar;
	}

	public String getUs_Email()
	{
		return Us_Email;
	}

	public void setUs_Email(String us_Email)
	{
		Us_Email = us_Email;
	}

	public boolean isInTravlling()
	{
		return isInTravlling;
	}

	public void setInTravlling(boolean isInTravlling)
	{
		this.isInTravlling = isInTravlling;
	}

	public long getIdOfTravelInTravlling()
	{
		return idOfTravelInTravlling;
	}

	public void setIdOfTravelInTravlling(long idOfTravelInTravlling)
	{
		this.idOfTravelInTravlling = idOfTravelInTravlling;
	}


    
    
    
    
}
