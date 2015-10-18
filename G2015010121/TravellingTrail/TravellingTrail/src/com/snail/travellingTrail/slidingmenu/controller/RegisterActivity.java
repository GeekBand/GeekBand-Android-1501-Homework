package com.snail.travellingTrail.slidingmenu.controller;

import java.security.MessageDigest;
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
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.common.utils.ToastHelper;

public class RegisterActivity extends SherlockActivity{

	ActionBar actionBar;
	MenuItem menuItem;
	Context context = this;
	EditText emailEt,passwordEt;
	FinalHttp registerFh;
	static String TAG = "RegisterActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_register);
		findid();
		super.onCreate(savedInstanceState);
	}

	private void findid() {
		emailEt = (EditText)findViewById(R.id.act_register_mail);
		passwordEt = (EditText)findViewById(R.id.act_register_password);
		
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
		actionBar.setTitle("REGISTER");
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
			registerFh = new FinalHttp();
			AjaxParams mAjaxParams = new AjaxParams();
			mAjaxParams.put("Us_Email", emailEt.getText().toString());
			mAjaxParams.put("Us_Password", toMD5(passwordEt.getText().toString()));
			
			DialogTool.showProgressDialog(RegisterActivity.this, "注册中~");
			registerFh.post(RequestAddress.REGISTER,mAjaxParams, new AjaxCallBack<Object>(){

				@Override
				public void onSuccess(Object t) {
					DialogTool.cancelProgressDialog();
					analyzeJson(t.toString());
					Log.i(TAG, "onSuccess....."+t);
					super.onSuccess(t);
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					DialogTool.cancelProgressDialog();
					Log.i(TAG, "onFailure....."+strMsg);
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
		Map<String,String> map = JsonUtil.parseJson1(json);
		for (String key:map.keySet()) {
			if(key.equals("Us_Id")){
				ToastHelper.showToast(context, "注册成功", Toast.LENGTH_SHORT);
				finish();
			}else if(key.equals("Message")){
				ToastHelper.showToast(context, map.get("Message"), Toast.LENGTH_LONG);
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
}
