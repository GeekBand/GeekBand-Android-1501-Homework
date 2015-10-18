package com.snail.travellingTrail.travelNotes.controller;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.travelNotes.model.TravelCommentToBeSent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class WriteTravelCommentActivity extends SherlockActivity
{
	EditText editText;
	ActionBar actionBar;
	long travelId;
	String replyTo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_comment);
		editText = (EditText) findViewById(R.id.act_write_comment_edt);
		init();
	}

	private void init()
	{
		travelId = getIntent().getExtras().getLong("travelId");
		replyTo = getIntent().getExtras().getString("reply_to");
		
		if(replyTo != null && !replyTo.equals(""))
		{
			editText.setHint("回复 @" + replyTo + " :");
		}
		else {
			editText.setHint("评论");
		}
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
			DialogTool.showProgressDialog(WriteTravelCommentActivity.this, "发送中..");
			submitComment();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void submitComment()
	{
		String comment = editText.getText().toString();
		
		if (comment == null || comment.equals(""))
		{
			ToastHelper.showToast(WriteTravelCommentActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT);
			return;
		}
		
		TravelCommentToBeSent commentToBeSent = new TravelCommentToBeSent(comment,
				travelId, TravellingTrailApplication.loginUser.getUs_Info_Us_Id());
		Gson gson = new Gson();
		String json = gson.toJson(commentToBeSent);
		
		try
		{
			json = new String(json.getBytes(), "8859_1");
		} catch (UnsupportedEncodingException e2)
		{
			e2.printStackTrace();
		}
		
		
		Log.v("Travel WriteTravelCommentActivity-->submitComment", "--->" + json);

		HttpEntity entity;
		try
		{
			entity = new StringEntity(json);
			
			FinalHttp finalHttp = new FinalHttp();
			finalHttp.post(RequestAddress.SEND_TRAVEL_COMMENT, entity, "application/json",
					new AjaxCallBack<String>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							DialogTool.cancelProgressDialog();
							if (strMsg != null)
							{
								ToastHelper.showToast(WriteTravelCommentActivity.this,
										"发送失败，错误代码：" + errorNo + "\n错误信息：" + strMsg,
										Toast.LENGTH_SHORT);
							}
						}

						@Override
						public void onSuccess(String result)
						{
							DialogTool.cancelProgressDialog();
							Log.v("Footprint WriteCommentActivity-->submitComment", "onSuccess result--->" + result);
//							if (result.equals("\"success\""))
//							{
								ToastHelper.showToast(WriteTravelCommentActivity.this, "发送成功",
										Toast.LENGTH_SHORT);
								setResult(Activity.RESULT_OK); //返回Activity.RESULT_OK，发送成功
								WriteTravelCommentActivity.this.finish();
//							}
//							else {
//								ToastHelper.showToast(WriteTravelCommentActivity.this, 
//										"发送失败，" + 
//										"\n错误信息：" + result,
//										Toast.LENGTH_SHORT);
//							}
						}
						
					});
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		
		
	}
}
