package com.snail.travellingTrail.newTravel.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.amap.api.mapcore2d.da;
import com.google.gson.Gson;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.BitmapHelper;
import com.snail.travellingTrail.common.utils.BitmapUtil;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.utils.TimeUtil;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.newTravel.model.FootprintContentToSubmit;
import com.snail.travellingTrail.newTravel.model.ImageData;
import com.snail.travellingTrail.trailMap.model.Footprint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewFootprintContentListActivity extends SherlockActivity implements OnClickListener
{

	ListView listView;
	ImageListAdapter adapter;
	List<ImageData> dataList;
	TextView addImgTextView;
	ActionBar actionBar;
	FinalHttp finalHttp;
	
	Footprint footprintLocation; //定位信息

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_footprint_content_list);

		findView();
		init();
		setAdapter();
		setListener();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSupportMenuInflater().inflate(R.menu.menu_write_comment, menu);
		actionBar = getSupportActionBar();
		
		// 设置左上角返回按钮
		actionBar.setHomeButtonEnabled(true); // 设置左上角的图标是否可以点击
		actionBar.setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
		actionBar.setDisplayShowHomeEnabled(false); // 不显示左上角程序图标
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home: // 点击了左上角Home按钮
			finish();
			break;
		case R.id.menu_submit_comment:
//			DialogTool.showProgressDialog(WriteFootprintContentActivity.this, "发送中..");
			submitFootprint();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}



	private void findView()
	{
		listView = (ListView) findViewById(R.id.act_main_lv);
		addImgTextView = (TextView) findViewById(R.id.act_main_tv_add_img);
	}

	private void init()
	{
		footprintLocation = (Footprint) getIntent().getSerializableExtra(LocationMapActivity.FOOTPRINT_LOCATION);
		
		PhoneInfo.loadScreenInfo(NewFootprintContentListActivity.this);
		dataList = TravellingTrailApplication.getDbManager().queryImageList();
		if (dataList == null)
		{
			dataList = new ArrayList<ImageData>();
			ToastHelper.showToast(NewFootprintContentListActivity.this, "暂无内容", Toast.LENGTH_SHORT);
		}
		
		finalHttp = new FinalHttp();
	}

	private void setAdapter()
	{
		adapter = new ImageListAdapter(dataList, NewFootprintContentListActivity.this);
		listView.setAdapter(adapter);
	}

	private void setListener()
	{
		addImgTextView.setOnClickListener(this);
		listView.setOnScrollListener(adapter);
	}

	
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		intent.setClass(NewFootprintContentListActivity.this, WriteFootprintContentActivity.class);
		startActivityForResult(intent, 0);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		switch (resultCode)
		{
		case Activity.RESULT_OK:
			String imagePath = data.getExtras().getString(WriteFootprintContentActivity.IMAGE_PATH);
			String contentText = data.getExtras().getString(WriteFootprintContentActivity.CONTENT_TEXT);

			addNewImageData(imagePath, contentText);
			break;

		default:
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	/**
	 * 添加图片数据
	 * @param path
	 * @param name
	 */
	private void addNewImageData(String path, String name)
	{
		ImageData imageData = new ImageData(path, name);
		int id = TravellingTrailApplication.getDbManager().insertImage(imageData);
		Log.v("MainActivity--->onActivityResult--->new_id", String.valueOf(id));
		
		if (id == -1)
		{
			ToastHelper.showToast(NewFootprintContentListActivity.this, "添加失败", Toast.LENGTH_SHORT);
		}else {
			imageData.setId(id);
			dataList.add(imageData);
			adapter.notifyDataSetChanged();
			ToastHelper.showToast(NewFootprintContentListActivity.this, "添加成功", Toast.LENGTH_SHORT);
		}
	}
	
	
	
	/**
	 * 提交足迹点
	 */
	private void submitFootprint()
	{
		DialogTool.showProgressDialog(NewFootprintContentListActivity.this, "提交中，速度较慢，稍等~");
		createFootprint(); //先创建足迹点
	}

	private void createFootprint()
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(footprintLocation);
		try
		{
			jsonString = new String(jsonString.getBytes(), "8859_1");
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		
		HttpEntity entity;
		try
		{
			entity = new StringEntity(jsonString);
			
			finalHttp.post(RequestAddress.CREATE_FOOTPRINT, entity, "application/json",
					new AjaxCallBack<Object>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							super.onFailure(t, errorNo, strMsg);
							DialogTool.cancelProgressDialog();
							ToastHelper.showToast(
									NewFootprintContentListActivity.this,
									"创建足迹点失败！错误代码：" + errorNo +  "；错误信息：" + strMsg,
									Toast.LENGTH_SHORT);
						}

						@Override
						public void onSuccess(Object result)
						{
							super.onSuccess(result);
							footprintLocation.setFtprnt_Id(Integer.valueOf(result.toString()));
							submitContent();
						}
					});
			
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
	}

	private void submitContent()
	{
		for (ImageData data : dataList)
		{
			String imageName = "";
			String base64Image = "";
			
			if (data.getPath() != null && !data.getPath().equals(""))
			{
				File file = new File(data.getPath());
				Uri uri = Uri.fromFile(file);
//				Bitmap bitmap = BitmapHelper.compressBitmap(uriToBytes(uri), 480, 800);
//				Options options = BitmapUtil.getUploadingBitmapOptions();
//				Bitmap bitmap = BitmapFactory.decodeFile(data.getPath(), options);
				base64Image = BitmapUtil.bitmapToBase64(BitmapHelper.compressBitmap(uriToBytes(uri), 480, 800));
				
//				if(!bitmap.isRecycled()) //回收bitmap内存
//				{
//					bitmap.recycle(); 
//					bitmap = null;
//					System.gc();
//				}
				
				imageName = "IMG_" + 
					    TravellingTrailApplication.loginUser.getUs_Info_Us_Id() + 
						"_" + TimeUtil.getCurrentDateAndTime() + ".jpg";
				
			}
			
			FootprintContentToSubmit submit = new FootprintContentToSubmit(
					footprintLocation.getFtprnt_Id(), data.getName(), base64Image, imageName);
			
			Gson gson = new Gson();
			String jsonString = gson.toJson(submit);
			try
			{
				jsonString = new String(jsonString.getBytes(), "8859_1");
			} catch (UnsupportedEncodingException e1)
			{
				e1.printStackTrace();
			}
			
			HttpEntity entity;
			try
			{
				entity = new StringEntity(jsonString);
			
				finalHttp.post(RequestAddress.SEND_FOOTPRINT_CONTENT, entity, "application/json",
					new AjaxCallBack<Object>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							DialogTool.cancelProgressDialog();
							ToastHelper.showToast(
									NewFootprintContentListActivity.this,
									"上传图文失败！错误代码：" + errorNo +  "；错误信息：" + strMsg,
									Toast.LENGTH_SHORT);
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onSuccess(Object t)
						{
							DialogTool.cancelProgressDialog();
							ToastHelper.showToast(
									NewFootprintContentListActivity.this, "上传成功！",
									Toast.LENGTH_SHORT);
							super.onSuccess(t);
						}
						
					});
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
	}

	
	// uri 转 byte[]
	private byte[] uriToBytes(Uri uri)
	{
		byte[] bts = null;
		try
		{
			InputStream input = getContentResolver().openInputStream(uri);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ReadableByteChannel channel = Channels.newChannel(input);
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			while (channel.read(buffer) != -1)
			{
				buffer.flip();
				while (buffer.hasRemaining())
					baos.write(buffer.get());
				buffer.clear();
			}
			bts = baos.toByteArray();
			input.close();
			channel.close();
			baos.close();
			return bts;
		} catch (IOException e)
		{
			Log.e("IOException", "" + e.toString());
			e.printStackTrace();
		}
		return bts;
	}


}
