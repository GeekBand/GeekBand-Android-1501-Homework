package com.snail.travellingTrail.trailMap.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.slidingmenu.controller.LoginActivity;
import com.snail.travellingTrail.trailMap.model.FootprintComment;


public class CommentActivity extends SherlockActivity implements OnClickListener
{

	ZrcListView commentListView;
	ArrayList<FootprintComment> comments;
	CommentAdapter commentAdapter;
	ActionBar actionBar;
	FinalHttp finalHttp;
	Menu optionsMenu;
	View wirteCommentFrameView, commentsView;
	LayoutInflater layoutInflater;
	long footprintId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		init();
	}

	
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}



	private void init()
	{
		footprintId = getIntent().getExtras().getLong("footprintId");
		finalHttp = new FinalHttp();
		findViews();
		setListener();
		setListView();
		commentListView.refresh();  //执行刷新，会调用OnStartListener的onStart方法
	}
	
	private void findViews()
	{		
		layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		commentsView = layoutInflater.inflate(R.layout.activity_comments, null);
		wirteCommentFrameView = layoutInflater.inflate(R.layout.actionbar_write_comment, null);
		setContentView(commentsView);
		commentListView = (ZrcListView)findViewById(R.id.act_comments_listview);
	}
	
	private void setListView()
	{
		// 设置下拉刷新的样式
		SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(getResources().getColor(R.color.aurantium));
        header.setCircleColor(getResources().getColor(R.color.aurantium));
        commentListView.setHeadable(header);
		
        // 设置加载更多的样式
		SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(getResources().getColor(R.color.aurantium));
        commentListView.setFootable(footer);
        
        // 设置列表项出现动画
        commentListView.setItemAnimForTopIn(R.anim.anim_topitem_in);
        commentListView.setItemAnimForBottomIn(R.anim.anim_bottomitem_in);

		commentAdapter = new CommentAdapter(comments, CommentActivity.this);
		commentListView.setAdapter(commentAdapter);
	}
	
	
	private void setListener()
	{
		(wirteCommentFrameView.findViewById(R.id.actionbar_write_comment))
			.setOnClickListener(this);
		
		// 下拉刷新事件回调
		commentListView.setOnRefreshStartListener(new OnStartListener()
		{
			@Override
			public void onStart()
			{
				getComments();
			}
		});
	}
	
	private void getComments()
	{
//		AjaxParams ajaxParams = new AjaxParams();
//		ajaxParams.put("Footprint_Id", String.valueOf(footprintId));
		finalHttp.get(RequestAddress.GET_SINGLE_FOOTPRINT_COMMENTS + footprintId,
				new AjaxCallBack<String>()
		{
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				commentListView.setRefreshFail();
				if (strMsg != null)
				{
					Log.v("GET_DEFAULT_SHIRT:failure", strMsg);
					ToastHelper.showToast(CommentActivity.this, "加载失败，错误代码：" 
							+ errorNo + "\n错误信息：" + strMsg, Toast.LENGTH_SHORT);
				}
			}

			@Override
			public void onLoading(long count, long current)  //每1秒钟自动被回调一次
			{
				Log.v("GET_DEFAULT_SHIRT:onLoading", current+"/"+count);
			}

			@Override
			public void onSuccess(String result)
			{
				Log.v("GET_DEFAULT_SHIRT:success", result);
				Type type = new TypeToken<ArrayList<FootprintComment>>(){ }.getType();
				Gson gson = new Gson();
//				JsonParser jsonParser = new JsonParser();
//				JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//				if ( !jsonObject.get("result").getAsBoolean())
//				{
//					ToastHelper.showToast(CommentActivity.this, "加载失败，错误信息：\n" + 
//							jsonObject.get("data").getAsString(), Toast.LENGTH_SHORT);
//					commentListView.setRefreshFail();
//					return;
//				}
//				else {
					commentListView.setRefreshSuccess();
					comments = gson.fromJson(result, type);
					commentAdapter.setComments(comments);
					commentAdapter.notifyDataSetChanged();
//				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		optionsMenu = menu;
		getSupportMenuInflater().inflate(R.menu.menu_comment, menu);
		
		actionBar = getSupportActionBar();
		
		// 设置左上角返回按钮
		actionBar.setHomeButtonEnabled(true); // 设置左上角的图标是否可以点击
		actionBar.setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
		actionBar.setDisplayShowHomeEnabled(false); // 不显示左上角程序图标
		
		MenuItem writeCommentItem = optionsMenu.findItem(R.id.menu_write_comment);
		if (writeCommentItem != null) {
			writeCommentItem.setActionView(wirteCommentFrameView);
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home: // 点击了左上角Home按钮
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v)
	{
		Log.v("onClick", v.toString());

		Intent intent = new Intent();
		intent.putExtra("Ftprnt_Id", footprintId);
		switch (v.getId())
		{
		case R.id.actionbar_write_comment:
			
			if (TravellingTrailApplication.loginUser != null)
			{
				intent.setClass(CommentActivity.this, WriteCommentActivity.class);
				intent.putExtra("reply_to", "");
				startActivityForResult(intent, 0);
			}
			else {
				ToastHelper.showToast(CommentActivity.this, "请先登录", Toast.LENGTH_SHORT);
				intent.setClass(CommentActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (resultCode)
		{
		case Activity.RESULT_OK: //发送成功
			commentListView.refresh();
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
