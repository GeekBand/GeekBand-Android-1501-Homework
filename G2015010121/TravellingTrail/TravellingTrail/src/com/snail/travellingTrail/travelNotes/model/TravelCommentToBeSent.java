package com.snail.travellingTrail.travelNotes.model;

public class TravelCommentToBeSent
{
	
	String Trvl_Cmm_Content;
	long Trvl_Cmm_Trvl_Id;
	long Trvl_Cmm_Us_Id;
	
	public TravelCommentToBeSent(String trvl_Cmm_Content,
			long trvl_Cmm_Trvl_Id, long trvl_Cmm_Us_Id)
	{
		super();
		Trvl_Cmm_Content = trvl_Cmm_Content;
		Trvl_Cmm_Trvl_Id = trvl_Cmm_Trvl_Id;
		Trvl_Cmm_Us_Id = trvl_Cmm_Us_Id;
	}

	public String getTrvl_Cmm_Content()
	{
		return Trvl_Cmm_Content;
	}

	public void setTrvl_Cmm_Content(String trvl_Cmm_Content)
	{
		Trvl_Cmm_Content = trvl_Cmm_Content;
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
	
	
	
	
	
}
