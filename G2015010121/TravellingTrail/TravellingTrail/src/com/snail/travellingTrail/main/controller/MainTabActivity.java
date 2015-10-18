package com.snail.travellingTrail.main.controller;

import java.io.UnsupportedEncodingException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.newTravel.controller.CreateNewTravelActivity;
import com.snail.travellingTrail.newTravel.controller.LocationMapActivity;
import com.snail.travellingTrail.slidingmenu.controller.LoginActivity;
import com.snail.travellingTrail.slidingmenu.controller.SetAppInfoFaceAnimation;
import com.snail.travellingTrail.trailMap.controller.WriteCommentActivity;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.utils.TimeUtil;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.common.utils.UnitConversion;
import com.snail.travellingTrail.finishTravel.controller.FinishTravelActivity;


public class MainTabActivity extends SetAppInfoFaceAnimation 
			implements OnCheckedChangeListener, OnClickListener, android.widget.CompoundButton.OnCheckedChangeListener
{
	
	private CanvasTransformer canvasTransformer;

	Fragment fragment = null;
	
	public static boolean refresh = false;

	static int TAB_COUNT = 2;
	ViewPager viewPager;
	FragmentStatePagerAdapter pagerAdapter;
	RadioGroup radioGroup;
	PopupWindow popupWindow;
	ImageView locateImageView, stopImageView;
	View popupView, popupFrameView;
	CheckBox addNewButton;

	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_tab);
		PhoneInfo.loadScreenInfo(MainTabActivity.this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager().beginTransaction().commit();
		
		SlidingMenu sm = getSlidingMenu();
		setSlidingActionBarEnabled(true);
		sm.setBehindWidth( (PhoneInfo.screenWidthPx/5)*3);
		sm.setBehindScrollScale(0.0f);
		canvasTransformer = new CanvasTransformer(){
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen*0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
				
			}
			
		};
		
		sm.setBehindCanvasTransformer(canvasTransformer);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				if(refresh){
					pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());

					viewPager.setAdapter(pagerAdapter);
				}
				
			}
		});
		findView();
		initPopupWindow();
		initView();
	}

	
	private void findView()
	{
		viewPager = (ViewPager) this.findViewById(R.id.act_main_tab_viewpager_content);
		radioGroup = (RadioGroup) this.findViewById(R.id.act_main_tab_rgrp);
		addNewButton = (CheckBox) this.findViewById(R.id.act_main_tab_btn_new);
	}
	

	private void initView()
	{
		pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPageChangeListener());
		radioGroup.setOnCheckedChangeListener(this);
		addNewButton.setOnCheckedChangeListener(this);
	}

	
	class TabFragmentPagerAdapter extends FragmentStatePagerAdapter
	{
		
		public TabFragmentPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public int getCount()
		{
			return TAB_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return null;
		}

		
		@Override
		public Fragment getItem(int position)
		{
			switch (position)
			{
			case 0:
				fragment = new SquareFragment();
				break;
			case 1:
				if(TravellingTrailApplication.loginUser != null){
					fragment = new EmptyFragment();
				}else{
					fragment = new MineFragment();
				}
				break;
			default:
				fragment = new SquareFragment();
				break;
			}

			return fragment;
		}

	}

	class ViewPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int position)
		{
			// 当前Fragment页面被选中时将Tab切换到当前项
			switch (position)
			{
			case 0:
				getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				radioGroup.check(R.id.act_main_tab_rbtn_square);
				break;
			case 1:
				if(TravellingTrailApplication.loginUser != null)
				{
					pagerAdapter.notifyDataSetChanged();
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					radioGroup.check(R.id.act_main_tab_rbtn_mine);
				}else{
					Intent intent = new Intent();
					intent.setClass(MainTabActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{

		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{

		}
	}
	
	
	@Override
	protected void onPause()
	{
		if (popupWindow != null && popupWindow.isShowing())
		{
			popupWindow.dismiss();
		}
		super.onPause();
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		//当RadioButton被选中时，切换ViewPager页面
		switch (checkedId)
		{
		case R.id.act_main_tab_rbtn_square:
			viewPager.setCurrentItem(0);
			break;
		case R.id.act_main_tab_rbtn_mine:
			viewPager.setCurrentItem(1);
			break;
		default:
			break;
		}
	}


	@Override
	protected void onResume() {
		Log.i("onResume", "onResume");
		if(TravellingTrailApplication.loginUser != null){
			viewPager.setCurrentItem(0);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			SquareFragment.REFRASH = false;
		}else{
			viewPager.setCurrentItem(0);

			pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());

			viewPager.setAdapter(pagerAdapter);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			radioGroup.check(R.id.act_main_tab_rbtn_square);
		}
		super.onResume();
	}
	
	
	
	
	
	
	private void getPopupWindwoInstance()
	{
		if (popupWindow == null)
		{
			initPopupWindow();
		}
		else {
			popupWindow.dismiss();
		}
	}
	
	//初始化tab中新旅程("+"号)的弹出窗
	private void initPopupWindow()
	{
		setInTravellingWindow();
		setNoTravelWindow();
		LayoutInflater layoutInflater = LayoutInflater.from(MainTabActivity.this);
		popupView = layoutInflater.inflate(R.layout.popup_main_tab_new, null);
		popupFrameView = popupView.findViewById(R.id.popup_main_tab_new_rlyt_frame);
		locateImageView = (ImageView) popupView.findViewById(R.id.popup_main_tab_new_iv_locate);
		stopImageView = (ImageView) popupView.findViewById(R.id.popup_main_tab_new_iv_stop);
		stopImageView.setOnClickListener(this);
		locateImageView.setOnClickListener(this);
		popupWindow = new PopupWindow( popupView,
				PhoneInfo.screenWidthPx / 3,
				UnitConversion.dip2px(MainTabActivity.this, 133) );
		//设置弹出框的可点击外部消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());//这句一定得有，否则点击外部不起作用
		popupWindow.setOutsideTouchable(true);
		popupWindow.setTouchInterceptor(new View.OnTouchListener()
		{    

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
				{
					popupWindow.dismiss();
					addNewButton.setChecked(false);
                    return true;     
                }
				return false;
			}    
        });
		
		popupWindow.setOnDismissListener(new OnDismissListener()
		{
			@Override
			public void onDismiss()
			{
				popupWindow = null;
				addNewButton.startAnimation(
						AnimationUtils.loadAnimation(MainTabActivity.this, R.anim.anim_tab_new_out) );
			}
		});
		
		popupFrameView.setOnClickListener(this);
	}


	private void setNoTravelWindow()
	{
		
	}


	private void setInTravellingWindow()
	{
		
	}


	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		switch (v.getId())
		{
		case R.id.popup_main_tab_new_iv_stop:
			popupWindow.dismiss();
			addNewButton.setChecked(false);
//			intent.setClass(MainTabActivity.this, FinishTravelActivity.class);
//			startActivity(intent);
			ToastHelper.showToast(MainTabActivity.this, "结束旅程", Toast.LENGTH_SHORT);
			sendFinishTravel();
			break;
		case R.id.popup_main_tab_new_iv_locate:
			popupWindow.dismiss();
			addNewButton.setChecked(false);
//			if (User.Us_Info_Us_Id == null || User.Us_Info_Us_Id.equals(""))
//			{
//				ToastHelper.showToast(MainTabActivity.this, "亲，别激动，请先登录哈", Toast.LENGTH_SHORT);
//				return;
//			}
			intent.setClass(MainTabActivity.this, LocationMapActivity.class);
			startActivity(intent);
			ToastHelper.showToast(MainTabActivity.this, "发表地点心情", Toast.LENGTH_SHORT);
			break;
		case R.id.popup_main_tab_new_rlyt_frame:
			if (popupWindow != null  && popupWindow.isShowing())
			{
				popupWindow.dismiss();
				addNewButton.setChecked(false);
			}
			break;
		default:
			break;
		}
	}


	private void sendFinishTravel()
	{
		String jsonString = "\"Trvl_Time_End\": \"" + TimeUtil.getCurrentDate() + "\"," +
	        "\"Trvl_Conclusion\": \"结束啦\"," +
	   		"\"Trvl_City_Among\":\"三亚\"," +
	   		"\"Trvl_City_Destination\":\"惠州\"," +
	   		"\"Trvls_Status\":9,\"Trvl_Us_Id\":" +  TravellingTrailApplication.loginUser.getUs_Info_Us_Id();
	   
		HttpEntity entity;
		try
		{
			entity = new StringEntity(jsonString);
			
			FinalHttp finalHttp = new FinalHttp();
			finalHttp.post(RequestAddress.FINISH_TRAVEL, entity, "application/json",
					new AjaxCallBack<String>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
//							if (strMsg != null)
//							{
//								ToastHelper.showToast(MainTabActivity.this,
//										"失败，错误代码：" + errorNo + "\n错误信息：" + strMsg,
//										Toast.LENGTH_SHORT);
//							}
							ToastHelper.showToast(MainTabActivity.this, "旅程结束啦！",
									Toast.LENGTH_SHORT);
						}

						@Override
						public void onSuccess(String result)
						{
							Log.v("sendFinishTravel sendFinishTravel-->sendFinishTravel", "onSuccess result--->" + result);
								ToastHelper.showToast(MainTabActivity.this, "旅程结束啦！",
										Toast.LENGTH_SHORT);
						}
						
					});
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		

	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		
		switch (buttonView.getId())
		{
		case R.id.act_main_tab_btn_new:
			if (isChecked)
			{
				addNewButton.startAnimation(
						AnimationUtils.loadAnimation(MainTabActivity.this, R.anim.anim_tab_new_in) );
				
				if (TravellingTrailApplication.loginUser == null) //是否登录
				{
					ToastHelper.showToast(MainTabActivity.this, "亲，别激动，请先登录哈", Toast.LENGTH_SHORT);
					buttonView.setChecked(false);
					addNewButton.startAnimation(
						AnimationUtils.loadAnimation(MainTabActivity.this, R.anim.anim_tab_new_out) );
				}
				else {
					if (TravellingTrailApplication.loginUser.isInTravlling() 
							&& TravellingTrailApplication.loginUser.getIdOfTravelInTravlling() > 0 )
					{	//有正在进行的旅程
						
						getPopupWindwoInstance();
						popupWindow.setAnimationStyle(R.style.PopupAnimation);
						popupWindow.showAtLocation(buttonView, Gravity.BOTTOM, 0, 0);
					}else {
						buttonView.setChecked(false);
						addNewButton.startAnimation(
							AnimationUtils.loadAnimation(MainTabActivity.this, R.anim.anim_tab_new_out) );
						ToastHelper.showToast(MainTabActivity.this, "开始新旅程/结束旅程", Toast.LENGTH_SHORT);
						Intent intent = new Intent(MainTabActivity.this, CreateNewTravelActivity.class);
						startActivity(intent);
					}
					
				}
				
			}
			else {
				if (popupWindow != null  && popupWindow.isShowing())
				{
					popupWindow.dismiss();
				}
			}
			break;

		default:
			break;
		}
	}
	
}
