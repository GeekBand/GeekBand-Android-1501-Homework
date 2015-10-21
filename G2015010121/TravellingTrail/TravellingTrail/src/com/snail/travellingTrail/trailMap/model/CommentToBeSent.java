package com.snail.travellingTrail.trailMap.model;

public class CommentToBeSent
{
	
	String Ftprnt_Cmm_Content;
	long Ftprnt_Cmm_Ftprnt_Id;
	long Ftprnt_Cmm_Us_Id;
	
	
	public CommentToBeSent(String ftprnt_Cmm_Content,
			long ftprnt_Cmm_Ftprnt_Id, long ftprnt_Cmm_Us_Id)
	{
		super();
		Ftprnt_Cmm_Content = ftprnt_Cmm_Content;
		Ftprnt_Cmm_Ftprnt_Id = ftprnt_Cmm_Ftprnt_Id;
		Ftprnt_Cmm_Us_Id = ftprnt_Cmm_Us_Id;
	}


	public String getFtprnt_Cmm_Content()
	{
		return Ftprnt_Cmm_Content;
	}


	public void setFtprnt_Cmm_Content(String ftprnt_Cmm_Content)
	{
		Ftprnt_Cmm_Content = ftprnt_Cmm_Content;
	}


	public long getFtprnt_Cmm_Ftprnt_Id()
	{
		return Ftprnt_Cmm_Ftprnt_Id;
	}


	public void setFtprnt_Cmm_Ftprnt_Id(long ftprnt_Cmm_Ftprnt_Id)
	{
		Ftprnt_Cmm_Ftprnt_Id = ftprnt_Cmm_Ftprnt_Id;
	}


	public long getFtprnt_Cmm_Us_Id()
	{
		return Ftprnt_Cmm_Us_Id;
	}


	public void setFtprnt_Cmm_Us_Id(long ftprnt_Cmm_Us_Id)
	{
		Ftprnt_Cmm_Us_Id = ftprnt_Cmm_Us_Id;
	}

	
	
	
}
