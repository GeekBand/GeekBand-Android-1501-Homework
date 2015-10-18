package com.snail.travellingTrail.newTravel.controller;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.utils.BitmapUtil;
import com.snail.travellingTrail.newTravel.model.ImageData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class BigImageActivity extends Activity
{
	
	ImageView imageView;
	ImageData data;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_image);
		
		findView();
		init();
		setImage();
	}

	private void findView()
	{
		imageView = (ImageView) findViewById(R.id.act_big_image_iv);
	}
	
	private void setImage()
	{
		imageView.setImageBitmap(BitmapUtil.getBitmap(data.getPath(), 1));
	}
	
	private void init()
	{
		Intent intent = getIntent();
		data = (ImageData) intent.getExtras().get("ImageData");
	}
	
}
