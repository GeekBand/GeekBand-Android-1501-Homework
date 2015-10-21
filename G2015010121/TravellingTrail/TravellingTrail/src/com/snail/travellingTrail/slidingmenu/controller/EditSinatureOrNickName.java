package com.snail.travellingTrail.slidingmenu.controller;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.common.utils.ToastHelper;

public class EditSinatureOrNickName extends SherlockActivity
{
	EditText editText;
	ActionBar actionBar;
	long Us_Info_Us_Id;
	int editType;
	private final static int sinature = 0;
	private final static int nickname = 1;
	private final static int introduce = 2;
	Map<String,Object> map;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smenu_minfo_sinature);
		editText = (EditText) findViewById(R.id.act_smenu_minfo_sinature);
		map = new HashMap<String, Object>();
		init();
	}

	private void init()
	{
		Us_Info_Us_Id = Integer.valueOf(ResponseList.MyInfoMap.get("Us_Info_Us_Id").toString());
		editType = getIntent().getExtras().getInt("editType");
		
		if(editType==sinature)
		{
			editText.setHint("编辑个性签名");
		}
		else if(editType==introduce) {
			editText.setHint("编辑个人简介");
		}else{
			editText.setHint("编辑昵称");
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
			DialogTool.showProgressDialog(EditSinatureOrNickName.this, "发送中..");
			submit(editType,editText.getText().toString());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void submit(int editType, String content) {
		FinalHttp mFinalHttp = new FinalHttp();
		AjaxParams mAjaxParams = new AjaxParams();
		switch (editType) {
		case sinature:
			mAjaxParams.put("Us_Sinature", content);
			mAjaxParams.put("Us_Nickname", ResponseList.MyInfoMap.get("Us_Nickname")+"");
			mAjaxParams.put("Us_Introduce", ResponseList.MyInfoMap.get("Us_Introduce")+"");
			map.put("Us_Sinature", content);
			map.put("Us_Nickname", ResponseList.MyInfoMap.get("Us_Nickname")+"");
			map.put("Us_Introduce", ResponseList.MyInfoMap.get("Us_Introduce")+"");
			break;
		case nickname:
			mAjaxParams.put("Us_Nickname", content);
			mAjaxParams.put("Us_Sinature", ResponseList.MyInfoMap.get("Us_Sinature")+"");
			mAjaxParams.put("Us_Introduce", ResponseList.MyInfoMap.get("Us_Introduce")+"");
			map.put("Us_Nickname", content);
			map.put("Us_Sinature", ResponseList.MyInfoMap.get("Us_Sinature"));
			map.put("Us_Introduce", ResponseList.MyInfoMap.get("Us_Introduce"));
			break;
		case introduce:
			mAjaxParams.put("Us_Introduce", content);
			mAjaxParams.put("Us_Sinature", ResponseList.MyInfoMap.get("Us_Sinature")+"");
			mAjaxParams.put("Us_Nickname", ResponseList.MyInfoMap.get("Us_Nickname")+"");
			map.put("Us_Introduce", content);
			map.put("Us_Sinature", ResponseList.MyInfoMap.get("Us_Sinature"));
			map.put("Us_Nickname", ResponseList.MyInfoMap.get("Us_Nickname"));
			break;
		default:
			break;

		}
		mAjaxParams.put("Us_Sex", ResponseList.MyInfoMap.get("Us_Sex")+"");
		mAjaxParams.put("Us_Birthday", ResponseList.MyInfoMap.get("Us_Birthday")+"");
		mAjaxParams.put("Us_Addresss", ResponseList.MyInfoMap.get("Us_Addresss")+"");
//		mAjaxParams.put("Us_Email", ResponseList.MyInfoMap.get("Us_Email"));
//		mAjaxParams.put("Us_Info_Us_Id", ResponseList.MyInfoMap.get("Us_Info_Us_Id"));
//		mAjaxParams.put("Us_Avatar", ResponseList.MyInfoMap.get("Us_Avatar"));
//		mAjaxParams.put("Us_Info_Snapshot", ResponseList.MyInfoMap.get("Us_Info_Snapshot"));
		mAjaxParams.put("Us_Location", ResponseList.MyInfoMap.get("Us_Location")+"");
		
		
		
		mFinalHttp.post(RequestAddress.MODIFY_DATA+Us_Info_Us_Id, mAjaxParams, new AjaxCallBack<Object>() {

			@Override
			public void onSuccess(Object t) {
				Log.i("onSuccess", t.toString());
				DialogTool.cancelProgressDialog();
				ToastHelper.showToast(EditSinatureOrNickName.this, "修改成功", Toast.LENGTH_SHORT);
				analyzeJson();
				finish();
				super.onSuccess(t);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				DialogTool.cancelProgressDialog();
				Log.i("onFailure", errorNo + "   " + strMsg);
				super.onFailure(t, errorNo, strMsg);
			}
			
		});
		
	}

	private void analyzeJson(){    
		map.put("Us_Info_Us_Id", ResponseList.MyInfoMap.get("Us_Info_Us_Id"));
		map.put("Us_Birthday", ResponseList.MyInfoMap.get("Us_Birthday"));
		map.put("Us_Location", ResponseList.MyInfoMap.get("Us_Location"));
		map.put("Us_Addresss", ResponseList.MyInfoMap.get("Us_Addresss"));
		map.put("Us_Sex", ResponseList.MyInfoMap.get("Us_Sex"));
		map.put("Us_Avatar", ResponseList.MyInfoMap.get("Us_Avatar"));
		map.put("Us_Info_Snapshot", ResponseList.MyInfoMap.get("Us_Info_Snapshot"));
		map.put("Us_Email", ResponseList.MyInfoMap.get("Us_Email"));
		ResponseList.MyInfoMap = map;
	}
	
}
