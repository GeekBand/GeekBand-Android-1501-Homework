package com.snail.travellingTrail.socialIntercourse.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnItemClickListener;
import zrc.widget.ZrcListView.OnStartListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.object.Fan;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.travelNotes.controller.TravelNotesActivity;
import com.snail.travellingTrail.user.controller.MyPageActivity;


public class FansListActivity extends SherlockActivity
{

	ZrcListView fanListView;
	ArrayList<Fan> fans;
	FansListAdapter fanAdapter;
	ActionBar actionBar;
	FinalHttp finalHttp;
	Menu optionsMenu;
	View wirtefanFrameView, fansView;
	LayoutInflater layoutInflater;
	long userId;

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
		userId = getIntent().getExtras().getLong("userId", -1);
		
		if (userId == -1)
		{
			if (TravellingTrailApplication.loginUser != null)
			{
				userId = TravellingTrailApplication.loginUser.getUs_Info_Us_Id();
			}
			else {
				ToastHelper.showToast(this, "咦？！你怎么跑到这个页面来的？有点问题哦", Toast.LENGTH_SHORT);
				finish();
			}
		}
		
		finalHttp = new FinalHttp();
		findViews();
		setListener();
		setListView();
		fanListView.refresh();  //执行刷新，会调用OnStartListener的onStart方法
	}
	
	private void findViews()
	{
		layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		fansView = layoutInflater.inflate(R.layout.activity_fans_list, null);
		setContentView(fansView);
		fanListView = (ZrcListView)findViewById(R.id.act_fans_list_lv);
	}
	
	private void setListView()
	{
		// 设置下拉刷新的样式
		SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(getResources().getColor(R.color.aurantium));
        header.setCircleColor(getResources().getColor(R.color.aurantium));
        fanListView.setHeadable(header);
		
        // 设置加载更多的样式
		SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(getResources().getColor(R.color.aurantium));
        fanListView.setFootable(footer);
        
        // 设置列表项出现动画
        fanListView.setItemAnimForTopIn(R.anim.anim_topitem_in);
        fanListView.setItemAnimForBottomIn(R.anim.anim_bottomitem_in);

		fanAdapter = new FansListAdapter(fans, FansListActivity.this);
		fanListView.setAdapter(fanAdapter);
	}
	
	
	private void setListener()
	{
		
		// 下拉刷新事件回调
		fanListView.setOnRefreshStartListener(new OnStartListener()
		{
			@Override
			public void onStart()
			{
				getFans();
			}
		});
		
	}
	
	private void getFans()
	{
//		AjaxParams ajaxParams = new AjaxParams();
//		ajaxParams.put("Footprint_Id", String.valueOf(footprintId));
		finalHttp.get(RequestAddress.GET_FANS_LIST + userId,
				new AjaxCallBack<String>()
		{
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				fanListView.setRefreshFail();
				if (strMsg != null)
				{
					Log.v("GET_DEFAULT_SHIRT:failure", strMsg);
					ToastHelper.showToast(FansListActivity.this, "加载失败，错误代码：" 
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
				Type type = new TypeToken<ArrayList<Fan>>(){ }.getType();
				Gson gson = new Gson();
//				JsonParser jsonParser = new JsonParser();
//				JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//				if ( !jsonObject.get("result").getAsBoolean())
//				{
//					ToastHelper.showToast(fanActivity.this, "加载失败，错误信息：\n" + 
//							jsonObject.get("data").getAsString(), Toast.LENGTH_SHORT);
//					fanListView.setRefreshFail();
//					return;
//				}
//				else {
					fanListView.setRefreshSuccess();
					fans = gson.fromJson(result, type);
					fanAdapter.setDatas(fans);
					fanAdapter.notifyDataSetChanged();
					if (fanAdapter.getCount() < 1)
					{
						ToastHelper.showToast(FansListActivity.this, "暂时还没有粉丝喔", Toast.LENGTH_SHORT);
						finish();
					}
//				}
			}
			
		});
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

	
}
