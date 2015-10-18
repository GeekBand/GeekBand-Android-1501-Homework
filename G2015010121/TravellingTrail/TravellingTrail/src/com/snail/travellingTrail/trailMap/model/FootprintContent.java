package com.snail.travellingTrail.trailMap.model;


public class FootprintContent
{
	
	long Ftprnt_Cntnt_ID;   // 图文ID
	String Ftprnt_Cntnt_Photo;  //图片
	String Ftprnt_Cntnt_Words;  //文字


	public FootprintContent(long ftprnt_Cntnt_ID, String ftprnt_Cntnt_Photo,
			String ftprnt_Cntnt_Words)
	{
		super();
		Ftprnt_Cntnt_ID = ftprnt_Cntnt_ID;
		Ftprnt_Cntnt_Photo = ftprnt_Cntnt_Photo;
		Ftprnt_Cntnt_Words = ftprnt_Cntnt_Words;
	}



	public long getFtprnt_Cntnt_ID()
	{
		return Ftprnt_Cntnt_ID;
	}



	public void setFtprnt_Cntnt_ID(long ftprnt_Cntnt_ID)
	{
		Ftprnt_Cntnt_ID = ftprnt_Cntnt_ID;
	}



	public String getFtprnt_Cntnt_Photo()
	{
		return Ftprnt_Cntnt_Photo;
	}



	public void setFtprnt_Cntnt_Photo(String ftprnt_Cntnt_Photo)
	{
		Ftprnt_Cntnt_Photo = ftprnt_Cntnt_Photo;
	}



	public String getFtprnt_Cntnt_Words()
	{
		return Ftprnt_Cntnt_Words;
	}



	public void setFtprnt_Cntnt_Words(String ftprnt_Cntnt_Words)
	{
		Ftprnt_Cntnt_Words = ftprnt_Cntnt_Words;
	}


	
}
