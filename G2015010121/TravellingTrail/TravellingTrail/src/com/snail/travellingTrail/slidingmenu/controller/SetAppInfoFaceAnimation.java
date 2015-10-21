package com.snail.travellingTrail.slidingmenu.controller;

import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.main.controller.MainTabActivity;
import com.snail.travellingTrail.socialIntercourse.controller.FansListActivity;
import com.snail.travellingTrail.socialIntercourse.controller.FollowsListActivity;

public class SetAppInfoFaceAnimation extends SlidingFragmentActivity{
	
	TextView loginTv,registerTv,aboutTv, logoutTv, reviseInformationTv,updateTv,nickNameTv,fansTv,followsTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.activity_appinfo);
		
		findId();
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			t.commit();
		}
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setBackgroundColor(getResources().getColor(R.color.slidingmenu_gray));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	private void findId() {
		loginTv = (TextView)findViewById(R.id.act_appinfo_tv_login);
		registerTv = (TextView)findViewById(R.id.act_appinfo_tv_register);
		aboutTv = (TextView)findViewById(R.id.act_appinfo_tv_about);
		reviseInformationTv = (TextView)findViewById(R.id.act_appinfo_tv_revise_informartion);
		logoutTv = (TextView)findViewById(R.id.act_appinfo_tv_logout);
		updateTv = (TextView)findViewById(R.id.act_appinfo_tv_update);
		nickNameTv = (TextView)findViewById(R.id.act_appinfo_tv_nickname);
		fansTv = (TextView)findViewById(R.id.act_appinfo_tv_fans);
		followsTv = (TextView)findViewById(R.id.act_appinfo_tv_follows);
		setListener();
		
	}
	private void setListener() {
		loginTv.setOnClickListener(new ButtonClick());
		registerTv.setOnClickListener(new ButtonClick());
		aboutTv.setOnClickListener(new ButtonClick());
		logoutTv.setOnClickListener(new ButtonClick());
		reviseInformationTv.setOnClickListener(new ButtonClick());
		updateTv.setOnClickListener(new ButtonClick());
		fansTv.setOnClickListener(new ButtonClick());
		followsTv.setOnClickListener(new ButtonClick());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.act_appinfo_tv_login:
				intent.setClass(SetAppInfoFaceAnimation.this, LoginActivity.class);
				startActivity(intent);
				break; 
			case R.id.act_appinfo_tv_register:
				intent.setClass(SetAppInfoFaceAnimation.this,RegisterActivity.class);
				startActivity(intent);
				break;
			case R.id.act_appinfo_tv_about:
				intent.setClass(SetAppInfoFaceAnimation.this,AboutActivity.class);
				startActivity(intent);
				break;
			case R.id.act_appinfo_tv_logout:
				TravellingTrailApplication.loginUser = null;
				ResponseList.MyInfoMap = null;
				loginTv.setVisibility(View.VISIBLE);
				registerTv.setVisibility(View.VISIBLE);
				reviseInformationTv.setVisibility(View.GONE);
				logoutTv.setVisibility(View.GONE);
				nickNameTv.setText("");
//				nickNameTv.setTextColor(SetAppInfoFaceAnimation.this.getResources().getColor(R.color.white));
				MainTabActivity.refresh = true;
				break;
			case R.id.act_appinfo_tv_revise_informartion:
				if(TravellingTrailApplication.loginUser != null)
				{
					intent.setClass(SetAppInfoFaceAnimation.this,UserInfoEditActivity.class);
				}else{
					intent.setClass(SetAppInfoFaceAnimation.this, LoginActivity.class);
				}
				startActivity(intent);
				break;
			case R.id.act_appinfo_tv_update:
				update();
				break;
			case R.id.act_appinfo_tv_fans:
				if(TravellingTrailApplication.loginUser != null)
				{
					intent.setClass(SetAppInfoFaceAnimation.this,FansListActivity.class);
					intent.putExtra("userId", TravellingTrailApplication.loginUser.getUs_Info_Us_Id());
				}else{
					intent.setClass(SetAppInfoFaceAnimation.this, LoginActivity.class);
				}
				startActivity(intent);
				break;
			case R.id.act_appinfo_tv_follows:
				if(TravellingTrailApplication.loginUser != null)
				{
					intent.setClass(SetAppInfoFaceAnimation.this,FollowsListActivity.class);
					intent.putExtra("userId", TravellingTrailApplication.loginUser.getUs_Info_Us_Id());
				}else{
					intent.setClass(SetAppInfoFaceAnimation.this, LoginActivity.class);
				}
				startActivity(intent);
				break;
			default:
				break;
			}
			
		}
		
	}

	@Override
	protected void onResume() {
		if(TravellingTrailApplication.loginUser != null){
			loginTv.setVisibility(View.GONE);
			registerTv.setVisibility(View.GONE);
			logoutTv.setVisibility(View.VISIBLE);
			reviseInformationTv.setVisibility(View.VISIBLE);
			fansTv.setVisibility(View.VISIBLE);
			followsTv.setVisibility(View.VISIBLE);
			if(ResponseList.MyInfoMap.get("Us_Nickname")!=null
					&& !ResponseList.MyInfoMap.get("Us_Nickname").equals("")
					&& !ResponseList.MyInfoMap.get("Us_Nickname").equals("null")){
				nickNameTv.setText(ResponseList.MyInfoMap.get("Us_Nickname").toString());
				nickNameTv.setTextColor(this.getResources().getColor(R.color.white));
			}else {
				nickNameTv.setHint("木有昵称");
			}
		}
		super.onResume();
	}
	public void update() {
		FinalHttp mFinalHttp = new FinalHttp();
		mFinalHttp.get(RequestAddress.UPDATE, new AjaxCallBack<String>(){

			@Override
			public void onSuccess(String t) {
				analyzeJson(t);
				super.onSuccess(t);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				ToastHelper.showToast(SetAppInfoFaceAnimation.this, "请检测网络", Toast.LENGTH_SHORT);
				super.onFailure(t, errorNo, strMsg);
			}
			
		});
		
	}
	private void analyzeJson(String json){
		Map<String,String> map = JsonUtil.parseJson1(json);
		Log.i("getVersionName", map.get("Client_Info_Version"));
		if(map.get("Client_Info_Version").equals(getVersionName())){
			ToastHelper.showToast(this, "已经是最新版本", Toast.LENGTH_SHORT);
		}else{
			ToastHelper.showToast(this, "请到"+map.get("Client_Info_Download_Url")+"下载", Toast.LENGTH_SHORT);
		}
	}

	private String getVersionName(){
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String version = packInfo.versionName;
		Log.i("getVersionName", version);
		return version;
	}
}
