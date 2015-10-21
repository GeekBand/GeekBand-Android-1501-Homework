package com.snail.travellingTrail.trailMap.controller;

public class CommentToBeSent
{
	String name;
	String url;
	String email;
	String content;
	long microblog_id;
	String reply_to;
	
	public CommentToBeSent(String name, String url, String email,
			String content, long microblog_id)
	{
		super();
		this.name = name;
		this.url = url;
		this.email = email;
		this.content = content;
		this.microblog_id = microblog_id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public long getMicroblog_id()
	{
		return microblog_id;
	}

	public void setMicroblog_id(long microblog_id)
	{
		this.microblog_id = microblog_id;
	}

	public String getReply_to()
	{
		return reply_to;
	}

	public void setReply_to(String reply_to)
	{
		this.reply_to = reply_to;
	}

	
}
