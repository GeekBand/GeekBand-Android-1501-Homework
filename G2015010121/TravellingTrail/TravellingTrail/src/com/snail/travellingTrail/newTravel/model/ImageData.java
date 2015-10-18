package com.snail.travellingTrail.newTravel.model;

import java.io.Serializable;

public class ImageData implements Serializable
{
	
	private long id;     //图片id
	private String path;    //图片路径
	private String name;   //图片名称
	
	
	public ImageData(long id, String path, String name)
	{
		super();
		this.id = id;
		this.path = path;
		this.name = name;
	}

	

	public ImageData(String path, String name)
	{
		super();
		this.path = path;
		this.name = name;
	}



	public long getId()
	{
		return id;
	}


	public void setId(long id)
	{
		this.id = id;
	}


	public String getPath()
	{
		return path;
	}


	public void setPath(String path)
	{
		this.path = path;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
}
