package com.snail.travellingTrail.trailMap.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.trailMap.model.Footprint;
import com.snail.travellingTrail.trailMap.model.FootprintContent;
import com.snail.travellingTrail.travelNotes.controller.TravelNotesActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SingleFootprintActivity extends Activity implements
		OnClickListener
{
	ZrcListView listView;
	TextView addressTextView, commentTextView,likeTextView;
	SingleFootprintAdapter adapter;
	LinearLayout commentLinearLayout, likeLinearLayout;
	List<FootprintContent> footprintContents;
	Footprint footprint;
	FinalHttp finalHttp;
	long footprintId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_footprint);

		findviews();
		init();
		setListView();
		setListViewListener();
		listView.refresh();
	}

	private void findviews()
	{
		listView = (ZrcListView) findViewById(R.id.act_single_footprint_lv);
		commentLinearLayout = (LinearLayout) findViewById(R.id.act_single_footprint_llyt_comment);
		addressTextView = (TextView) findViewById(R.id.act_single_footprint_tv_address);
		commentTextView = (TextView) findViewById(R.id.act_single_footprint_tv_comment);
		likeLinearLayout = (LinearLayout) findViewById(R.id.act_single_footprint_llyt_like);
		likeTextView =  (TextView) findViewById(R.id.act_single_footprint_tv_like);
	}

	private void init()
	{
		footprintId = getIntent().getExtras().getLong("footprintId");
		footprintContents = new ArrayList<FootprintContent>();
		finalHttp = new FinalHttp();
	}

	private void setListView()
	{
		// 设置下拉刷新的样式
		SimpleHeader header = new SimpleHeader(this);
		header.setTextColor(getResources().getColor(R.color.aurantium));
		header.setCircleColor(getResources().getColor(R.color.aurantium));
		listView.setHeadable(header);

		// 设置加载更多的样式
		SimpleFooter footer = new SimpleFooter(this);
		footer.setCircleColor(getResources().getColor(R.color.aurantium));
		listView.setFootable(footer);

		// 设置列表项出现动画
		listView.setItemAnimForTopIn(R.anim.anim_topitem_in);
		listView.setItemAnimForBottomIn(R.anim.anim_bottomitem_in);
		setAdapter();
	}

	private void setAdapter()
	{
		adapter = new SingleFootprintAdapter(SingleFootprintActivity.this,
				footprintContents);
		listView.setAdapter(adapter);
		likeLinearLayout.setOnClickListener(this);
	}

	private void setListViewListener()
	{
		listView.setOnRefreshStartListener(new OnStartListener()
		{
			@Override
			public void onStart()
			{
				getFootprintData();
			}
		});
	}

	private void getFootprintData()
	{
		System.out.println("SingleFootprintMap:"
				+ RequestAddress.GET_SINGLE_FOOTPRINT + footprintId);
		finalHttp.get(RequestAddress.GET_SINGLE_FOOTPRINT + footprintId,
				new AjaxCallBack<String>()
				{

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg)
					{
						if (strMsg != null)
						{
							Log.v("onFailure", strMsg);
						}
						listView.setRefreshFail();
					}

					@Override
					public void onSuccess(String result)
					{
						Log.v("onSuccess", result);
						Gson gson = new Gson();
						footprint = gson.fromJson(result, Footprint.class);

						footprintContents = footprint.getFootprint_Content();
						addressTextView.setText(footprint.getFtprnt_Address());

						if (footprint.getFtprnt_Comment_Count() != 0)
						{
							commentTextView.setText(String.valueOf(footprint
									.getFtprnt_Comment_Count()));
						}
						adapter.setFootprintContents(footprintContents);
						adapter.notifyDataSetChanged();
						listView.setRefreshSuccess();
						setCommentBtnListener();
					}

				});
	}

	private void setCommentBtnListener()
	{
		commentLinearLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.act_single_footprint_llyt_comment:
			Intent intent = new Intent();
			switch (footprint.getFtprnt_Comment_Count())
			{
			case 0:
				intent.setClass(SingleFootprintActivity.this,
						WriteCommentActivity.class);
				break;

			default:
				intent.setClass(SingleFootprintActivity.this,
						CommentActivity.class);
				break;
			}
			intent.putExtra("footprintId", footprintId);
			startActivity(intent);
			break;

		case R.id.act_single_footprint_llyt_like:
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
			ToastHelper.showToast(SingleFootprintActivity.this, "请先登录", Toast.LENGTH_SHORT);
			return;
		}
//		
//		String jsonString = "{\"Trvl_Likes_Trvls_Id\":" + travelId + 
//								",\"Trvl_Likes_Us_Id\":" + TravellingTrailApplication.loginUser.getUs_Info_Us_Id() + "}";

		
		HttpEntity entity;
//		try
//		{
//			entity = new StringEntity(jsonString);
			
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
//		} catch (UnsupportedEncodingException e1)
//		{
//			e1.printStackTrace();
//		}
	}
}
