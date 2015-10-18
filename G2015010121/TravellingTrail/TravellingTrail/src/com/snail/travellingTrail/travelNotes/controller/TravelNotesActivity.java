package com.snail.travellingTrail.travelNotes.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jankey.NoScrollingListView;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.BitmapUtil;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.trailMap.controller.CommentActivity;
import com.snail.travellingTrail.trailMap.controller.TrailMapActivity;
import com.snail.travellingTrail.trailMap.controller.WriteCommentActivity;
import com.snail.travellingTrail.trailMap.model.Footprint;
import com.snail.travellingTrail.trailMap.model.FootprintContent;
import com.snail.travellingTrail.travelNotes.model.MapImage;
import com.snail.travellingTrail.travelNotes.model.TravelInformation;
import com.snail.travellingTrail.travelNotes.model.TravelNotesProcessor;
import com.snail.travellingTrail.user.controller.MyPageActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TravelNotesActivity extends Activity implements OnClickListener
{
	
	NoScrollingListView listView;
	TextView commentTextView, timeIntervalTextView, titleTextView,
		introductionTextView, nicknameTextView, likeTextView;
	ImageView avatarImageView, mapImageView, pressImageView;
	TravelNotesAdapter adapter;
	LinearLayout commentLinearLayout, likeLinearLayout;
	TravelInformation travelInformation;
	List<Footprint> footprints;
	List<FootprintContent> footprintContents;
	FinalHttp finalHttp;
	FinalBitmap mapFinalBitmap, avatarFinalBitmap;
	BitmapDisplayConfig bitmapDisplayConfig;
	long travelId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_notes);
		
		findviews();
		init();
		getMapFootprints();
		getTravelNotesInformation();
		setListener();
//		setListView();
//		setListViewListener();
	}

	private void findviews()
	{
		listView = (NoScrollingListView)findViewById(R.id.act_travel_notes_lv);
		commentLinearLayout = (LinearLayout)findViewById(R.id.act_travel_notes_llyt_comment);
		commentTextView = (TextView) findViewById(R.id.act_travel_notes_tv_comment);
		titleTextView = (TextView) findViewById(R.id.act_travel_notes_tv_title);
		timeIntervalTextView = (TextView) findViewById(R.id.act_travel_notes_tv_time_interval);
		nicknameTextView = (TextView) findViewById(R.id.act_travel_notes_tv_nickname);
		introductionTextView  = (TextView) findViewById(R.id.act_travel_notes_tv_introduction);
		likeTextView = (TextView) findViewById(R.id.act_travel_notes_tv_like);
		likeLinearLayout = (LinearLayout)findViewById(R.id.act_travel_notes_llyt_like);
		avatarImageView = (ImageView) findViewById(R.id.act_travel_notes_iv_avatar);
		mapImageView = (ImageView) findViewById(R.id.act_travel_notes_iv_map);
		pressImageView = (ImageView) findViewById(R.id.act_travel_notes_iv_press);
	}
	
	private void init()
	{
		travelId = Long.valueOf(getIntent().getExtras().getString("travelId"));
//		travelId = 1;
		
		PhoneInfo.loadScreenInfo(TravelNotesActivity.this);
		LayoutParams layoutParams = (LayoutParams) mapImageView.getLayoutParams();
		layoutParams.width = PhoneInfo.screenWidthPx;
		//800为地图图片原始宽度，1000为原始高度
		layoutParams.height = (int) (PhoneInfo.screenWidthPx * ( 800f / 1000f));
		System.out.println("LayoutParams:width x height --- " + 
				layoutParams.width + "x" + layoutParams.height);
		mapImageView.setLayoutParams(layoutParams);
		pressImageView.setLayoutParams(layoutParams);
		
		System.out.println("mapImageView:width x height --- " + 
				mapImageView.getLayoutParams().width + "x" + mapImageView.getLayoutParams().height);
		
		footprintContents = new ArrayList<FootprintContent>();
		mapFinalBitmap = FinalBitmap.create(TravelNotesActivity.this);
		avatarFinalBitmap = FinalBitmap.create(TravelNotesActivity.this);
		finalHttp = new FinalHttp();
	}
	
	private void setListView()
	{
//		// 设置下拉刷新的样式
//		SimpleHeader header = new SimpleHeader(this);
//        header.setTextColor(getResources().getColor(R.color.aurantium));
//        header.setCircleColor(getResources().getColor(R.color.aurantium));
//        listView.setHeadable(header);
//		
//        // 设置加载更多的样式
//		SimpleFooter footer = new SimpleFooter(this);
//        footer.setCircleColor(getResources().getColor(R.color.aurantium));
//        listView.setFootable(footer);
        
//		setAdapter();
	}
	
	private void setAdapter()
	{
		adapter = new TravelNotesAdapter(TravelNotesActivity.this, footprintContents);
		listView.setAdapter(adapter);
	}
	
	private void setListener()
	{
		pressImageView.setOnClickListener(this);
		likeLinearLayout.setOnClickListener(this);
	}
	
	private void setListViewListener()
	{
//		listView.setOnRefreshStartListener(new OnStartListener()
//		{
//			@Override
//			public void onStart()
//			{
//				getTravelNotesData();
//			}
//		});
	}
	
	private void getMapFootprints()
	{
		DialogTool.showProgressDialog(TravelNotesActivity.this, "加载中...客官请稍等哈~");
		finalHttp.get(RequestAddress.GET_TRAVEL_MAP_FOOTPRINTS + travelId, new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				if (strMsg != null)
				{
					Log.v("onFailure", strMsg);
					ToastHelper.showToast(TravelNotesActivity.this, "加载失败，错误代码：" 
							+ errorNo + "\n错误信息：\n" + strMsg, Toast.LENGTH_SHORT);
				}
				else{
					ToastHelper.showToast(TravelNotesActivity.this, "加载失败，错误代码：" 
							+ errorNo, Toast.LENGTH_SHORT);
				}
//				listView.refresh();
			}

			@Override
			public void onSuccess(String result)
			{
				Log.v("onSuccess", result);
				Type type = new TypeToken<ArrayList<Footprint>>(){ }.getType();
				Gson gson = new Gson();
				footprints = gson.fromJson(result, type);
				
				getMapImage();
//				listView.refresh();
			}
			
			
		});
	}
	
	private void getMapImage()
	{
		bitmapDisplayConfig = new BitmapDisplayConfig();
		bitmapDisplayConfig.setBitmapWidth(mapImageView.getLayoutParams().width);
//		800为地图图片原始宽度，1000为原始高度
		bitmapDisplayConfig.setBitmapHeight(mapImageView.getLayoutParams().height);
		
		mapFinalBitmap.configDisplayer(new Displayer()
		{
			
			@Override
			public void loadFailDisplay(View imageView, Bitmap bitmap)
			{
				
			}
			
			@Override
			public void loadCompletedisplay(View view, Bitmap bitmap,
					BitmapDisplayConfig arg2)
			{
				System.out.println("Map Bitmap Origin:width x height --- " + 
						bitmap.getWidth() + "x" + bitmap.getHeight());
				bitmap = BitmapUtil.compressAccordingToWidth(bitmap, PhoneInfo.screenWidthPx);
				
				System.out.println("Map Bitmap Compressed:width x height --- " + 
						bitmap.getWidth() + "x" + bitmap.getHeight());
				
				((ImageView)view).setImageBitmap(bitmap);
				getTravelNotesData();
			}
		});
		
		if (footprints.size() < 1)
		{
			mapFinalBitmap.display(mapImageView,
					MapImage.getNoneMapUrl(), bitmapDisplayConfig);
		} else
		{
			mapFinalBitmap.display(mapImageView,
					MapImage.getSingleTravelMapImgUrl(footprints, TravelNotesActivity.this), bitmapDisplayConfig);
		}
		
	}
	
	

	private void getTravelNotesInformation()
	{
		
		finalHttp.get(RequestAddress.GET_TRAVEL_NOTES_INTRODUCTION + travelId, new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				DialogTool.cancelProgressDialog();
				if (strMsg != null)
				{
					Log.v("onFailure", strMsg);
					ToastHelper.showToast(TravelNotesActivity.this, "加载失败，错误代码：" 
							+ errorNo + "\n错误信息：\n" + strMsg, Toast.LENGTH_SHORT);
				}
				else{
					ToastHelper.showToast(TravelNotesActivity.this, "加载失败，错误代码：" 
							+ errorNo, Toast.LENGTH_SHORT);
				}
			}

			@Override
			public void onSuccess(String request)
			{
				DialogTool.cancelProgressDialog();
				Log.v("onSuccess", request);
				Gson gson = new Gson();
				travelInformation = gson.fromJson(request, TravelInformation.class);
				
				avatarFinalBitmap.display(avatarImageView, travelInformation.getUs_Avatar());
				nicknameTextView.setText( travelInformation.getUs_Nickname());
				titleTextView.setText( travelInformation.getTrvl_Name());
				String endTime = (travelInformation.getTrvl_Time_End() != null 
						&& !travelInformation.getTrvl_Time_End().equals("")
						&& !travelInformation.getTrvl_Time_End().equals("null") ) ? 
								travelInformation.getTrvl_Time_End().substring(0, 10) :
									"未知";
				timeIntervalTextView.setText(travelInformation.getTrvl_Time_Start().substring(0, 10)
						+ " 至 " + endTime );
				introductionTextView.setText(travelInformation.getTrvl_Introduce());
				
				if (travelInformation.getTrvl_Comment_Count() > 0)
				{
					commentTextView.setText(String.valueOf(travelInformation.getTrvl_Comment_Count()));
				}
				
				if (travelInformation.getTrvl_Like_Count() > 0)
				{
					likeTextView.setText(String.valueOf(travelInformation.getTrvl_Like_Count()));
				}
				
				setCommentBtnListener();
			}
			
		});
	}

	private void setCommentBtnListener()
	{
		commentLinearLayout.setOnClickListener(this);
	}
	
	
	
	private void getTravelNotesData()
	{	
		
		finalHttp.get(RequestAddress.GET_TRAVEL_FOOTPRINTS + travelId, new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				if (strMsg != null)
				{
					Log.v("onFailure", strMsg);
				}
//				listView.setRefreshFail();
			}

			@Override
			public void onSuccess(String request)
			{
				Log.v("onSuccess", request);
				Type type = new TypeToken<ArrayList<Footprint>>(){ }.getType();
				Gson gson = new Gson();
				footprints = gson.fromJson(request, type);
				
				footprintContents = TravelNotesProcessor.listTheTravelNotesContents(footprints);
				adapter = new TravelNotesAdapter(TravelNotesActivity.this, footprintContents);
				listView.setAdapter(adapter);
//				adapter.setFootprintContents(footprintContents);
//				adapter.notifyDataSetChanged();
//				listView.setRefreshSuccess();
			}
			
		});
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		switch (v.getId())
		{
		case R.id.act_travel_notes_llyt_comment:
			switch (travelInformation.getTrvl_Comment_Count())
			{
			case 0:
				intent.setClass(TravelNotesActivity.this, WriteTravelCommentActivity.class);
				break;
	
			default:
				intent.setClass(TravelNotesActivity.this, TravelCommentActivity.class);
				break;
			}
			intent.putExtra("travelId", travelId);
			startActivity(intent);
			break;
		case R.id.act_travel_notes_iv_press:
			intent.setClass(TravelNotesActivity.this, TrailMapActivity.class);
			intent.putExtra("travelId", travelId);
			startActivity(intent);
			break;
		case R.id.act_travel_notes_llyt_like:
			sendLike();
			break;
		default:
			break;
		}
	}

	private void sendLike()
	{
		
		if (TravellingTrailApplication.loginUser == null)
		{
			ToastHelper.showToast(TravelNotesActivity.this, "请先登录", Toast.LENGTH_SHORT);
			return;
		}
		
		String jsonString = "{\"Trvl_Likes_Trvls_Id\":" + travelId + 
								",\"Trvl_Likes_Us_Id\":" + TravellingTrailApplication.loginUser.getUs_Info_Us_Id() + "}";

		
		HttpEntity entity;
		try
		{
			entity = new StringEntity(jsonString);
			
			FinalHttp finalHttp = new FinalHttp();
//			finalHttp.post(RequestAddress.SEND_TRAVEL_COMMENT, entity, "application/json",
//					new AjaxCallBack<String>()
//					{
//
//						@Override
//						public void onFailure(Throwable t, int errorNo,
//								String strMsg)
//						{
//							DialogTool.cancelProgressDialog();
//							if (strMsg != null)
//							{
//								ToastHelper.showToast(TravelNotesActivity.this,
//										"失败，错误代码：" + errorNo + "\n错误信息：" + strMsg,
//										Toast.LENGTH_SHORT);
//							}
//						}
//
//						@Override
//						public void onSuccess(String result)
//						{
//							Log.v("TravelNotesActivity-->sendLike", "onSuccess result--->" + result);
//							ToastHelper.showToast(TravelNotesActivity.this, "发送成功",
//										Toast.LENGTH_SHORT);
							if (likeTextView.getText().toString() != null 
									&& !likeTextView.getText().toString().equals(""))
							{
								likeTextView.setText(
										(Integer.valueOf(likeTextView.getText().toString()) + 1) + "");
							}
							else {
								likeTextView.setText("1");
							}
//						}
//						
//					});
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
	}
}
