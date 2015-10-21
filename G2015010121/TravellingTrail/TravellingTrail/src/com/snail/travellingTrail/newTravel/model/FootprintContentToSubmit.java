package com.snail.travellingTrail.newTravel.model;


public class FootprintContentToSubmit
{
	
	long Ftpnt_Cntnt_Ftprnt_Id;   // 足迹点ID
	String Ftprnt_Cntnt_Photo;  //图片url
	String Ftprnt_Cntnt_Words;  //文字
	String imgData;   //足迹点base64编码形式的图片，如："20921adc-0b0d-414f-8e44-2958e1f5fcc4"
	String imgName;   //足迹点图片文件名名，如："abc.jpg"

	
	


	public FootprintContentToSubmit(long ftpnt_Cntnt_Ftprnt_Id,
			String ftprnt_Cntnt_Words, String imgData, String imgName)
	{
		super();
		Ftpnt_Cntnt_Ftprnt_Id = ftpnt_Cntnt_Ftprnt_Id;
		Ftprnt_Cntnt_Words = ftprnt_Cntnt_Words;
		this.imgData = imgData;
		this.imgName = imgName;
	}




	public FootprintContentToSubmit(long ftpnt_Cntnt_Ftprnt_Id,
			String ftprnt_Cntnt_Photo, String ftprnt_Cntnt_Words,
			String imgData, String imgName)
	{
		super();
		Ftpnt_Cntnt_Ftprnt_Id = ftpnt_Cntnt_Ftprnt_Id;
		Ftprnt_Cntnt_Photo = ftprnt_Cntnt_Photo;
		Ftprnt_Cntnt_Words = ftprnt_Cntnt_Words;
		this.imgData = imgData;
		this.imgName = imgName;
	}




	public long getFtpnt_Cntnt_Ftprnt_Id()
	{
		return Ftpnt_Cntnt_Ftprnt_Id;
	}




	public void setFtpnt_Cntnt_Ftprnt_Id(long ftpnt_Cntnt_Ftprnt_Id)
	{
		Ftpnt_Cntnt_Ftprnt_Id = ftpnt_Cntnt_Ftprnt_Id;
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




	public String getImgData()
	{
		return imgData;
	}




	public void setImgData(String imgData)
	{
		this.imgData = imgData;
	}




	public String getImgName()
	{
		return imgName;
	}




	public void setImgName(String imgName)
	{
		this.imgName = imgName;
	}



	

	
}
