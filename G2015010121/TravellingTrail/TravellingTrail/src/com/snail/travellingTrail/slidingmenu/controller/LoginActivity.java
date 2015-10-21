package com.snail.travellingTrail.slidingmenu.controller;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
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
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.object.User;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.common.utils.ToastHelper;

public class LoginActivity extends SherlockActivity{
	ActionBar actionBar;
	MenuItem menuItem;
	Context context = this;
	
	EditText mailEt,pwEt;
	
	FinalHttp mFinalHttp;
	
	private static String TAG = "LoginActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		findId();
		mFinalHttp = new FinalHttp();
		super.onCreate(savedInstanceState);
	}



	private void findId() {
		mailEt = (EditText)findViewById(R.id.act_login_et_mail);
		pwEt = (EditText)findViewById(R.id.act_login_et_password);
		
	}

	

	@Override
	protected void onDestroy()
	{
		DialogTool.cancelProgressDialog();
		super.onDestroy();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.mistake);
		actionBar.setTitle("LOGIN");
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.act_login_confirm:
			AjaxParams mAjaxParams = new AjaxParams();
			mAjaxParams.put("Us_Email", mailEt.getText().toString());
			mAjaxParams.put("Us_Password", toMD5(pwEt.getText().toString()));
			
			DialogTool.showProgressDialog(LoginActivity.this, "登录中~");
			mFinalHttp.post(RequestAddress.LOGIN, mAjaxParams,new AjaxCallBack<Object>(){

				@Override
				public void onSuccess(Object t) {
					Log.i(TAG, t+"");
					analyzeJson(t.toString());
					super.onSuccess(t);
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					Log.i(TAG,strMsg);
					DialogTool.cancelProgressDialog();
					analyzeJson(strMsg);
					super.onFailure(t, errorNo, strMsg);
				}
				
			});
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void analyzeJson(String json){
		
		Map<String,Object> map = JsonUtil.parseJson(json);
		for (String key:map.keySet()) {
			if(key.equals("Us_Info_Us_Id")){
				TravellingTrailApplication.loginUser = new User();
				TravellingTrailApplication.loginUser.setUs_Info_Us_Id(Long.valueOf(map.get(key).toString()));
				ResponseList.MyInfoMap = map;
				getIsInTravelling();
			}else if(key.equals("Message")){
				ToastHelper.showToast(context, map.get("Message").toString(), Toast.LENGTH_LONG);
			}
		}
	}
	

	public String toMD5(String args) {
		byte[] source;
    	StringBuffer buf=new StringBuffer(); 
	    try{
	    	source = args.getBytes("UTF-8");
	    	MessageDigest md = MessageDigest.getInstance("MD5");
	    	md.update( source );      	    
	    	for(byte b:md.digest())
	    	buf.append(String.format("%02x", b&0xff) );    	 
	    }catch( Exception e ){
	    		e.printStackTrace(); 
	    }      
    	return buf.toString();
	}
	
	
	//向后台获取判断是否有未结束旅程
	private void getIsInTravelling()
	{
		mFinalHttp.get(RequestAddress.PERSONAL_TRAVEL + "?UserId=" + 
					TravellingTrailApplication.loginUser.getUs_Info_Us_Id() + "&status=1",
				new AjaxCallBack<Object>()
				{

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg)
					{
						DialogTool.cancelProgressDialog();
						ToastHelper.showToast(context, "欢迎回来", Toast.LENGTH_SHORT);
						finish();
						super.onFailure(t, errorNo, strMsg);
					}

					@Override
					public void onSuccess(Object t)
					{
						try
						{
							Log.v("LoginActivity--->getIsInTravelling", "--->" + t.toString());
							List<Map<String, String>> list = JsonUtil.parseJson1(t.toString(), null);
							TravellingTrailApplication.loginUser.setIdOfTravelInTravlling(
									Integer.valueOf(list.get(0).get("Trvl_Id")));
							TravellingTrailApplication.loginUser.setInTravlling(true);
						} catch (Exception e)
						{
							TravellingTrailApplication.loginUser.setInTravlling(false);
						}finally
						{
							DialogTool.cancelProgressDialog();
							ToastHelper.showToast(context, "欢迎回来", Toast.LENGTH_SHORT);
							finish();
						}
						super.onSuccess(t);
					}

				});
		
	}
}
