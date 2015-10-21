package com.snail.travellingTrail.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.jar.JarOutputStream;









import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.content.Context;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.google.gson.JsonObject;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.common.views.CircleImageView;
import com.snail.travellingTrail.mine.controller.MineAdapter;
import com.snail.travellingTrail.newTravel.controller.NewFootprintContentListActivity;
import com.snail.travellingTrail.travelNotes.model.MapImage;

public class MyPageActivity extends SherlockActivity implements OnClickListener{

	View actionView,header,divider;
	Context context = this;
	ZrcListView mZrcListView;
	LinearLayout mineLlyt;
	ImageView mapIv;
	CircleImageView avatarIv,avatarIv2;
	TextView nickNameTv,addressTv,sinatureTv,nickNameTv2,addressTv2,sinatureTv2;
	Button followButton, followButton2;
	View include;
	FinalHttp mDataFinalHttp,mTripAllFh,mTripFootPoint, mFollowHttp;
	MineAdapter mineAdapter;
	FinalBitmap mFinalBitmap,mFinalBitmap2;
	private final static String  TAG = "MyPageActivity";
	private final static int UPDATE = 1;
	private final static int AGE = 2;
	private static boolean REFRASH = false;
	private static long User_Id;
	private static String age;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		User_Id = getIntent().getExtras().getLong("Trvl_Us_Id");
		
		if (TravellingTrailApplication.loginUser != null &&
				User_Id == TravellingTrailApplication.loginUser.getUs_Info_Us_Id() )
		{
			setContentView(R.layout.fragment_mine);
		}else {
			setContentView(R.layout.activity_user_home);
		}
		
		super.onCreate(savedInstanceState);
		findid();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}


	private void findid() {
		actionView = View.inflate(context, R.layout.frag_mine_header_action, null);
		header = View.inflate(context, R.layout.frag_mine_header, null);//头部内容
		divider = View.inflate(context, R.layout.divider, null);
		
		mZrcListView = (ZrcListView)this.findViewById(R.id.frag_mine_lv_travellingTrail);
		include = (View)this.findViewById(R.id.frag_mine_include);
		avatarIv = (CircleImageView)actionView.findViewById(R.id.frag_mine_header_action_iv_avatar);
		nickNameTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_nickname);
//		ageTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_age);
		addressTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_address);
		sinatureTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_sinature);
		mapIv = (ImageView)header.findViewById(R.id.fragment_mine_header_iv_map);
		followButton = (Button) actionView.findViewById(R.id.frag_mine_header_action_btn_follow);
		
		if (TravellingTrailApplication.loginUser != null 
				&& TravellingTrailApplication.loginUser.getUs_Info_Us_Id() == User_Id)
		{
			followButton.setVisibility(View.INVISIBLE);
		}
		
		setListener();
	}
	private void setListener()
	{
		mZrcListView.setOnRefreshStartListener(new zrcListViewRefresh());
		followButton.setOnClickListener(this);
		init();
	}
	class zrcListViewRefresh implements OnStartListener{

		@Override
		public void onStart() {
			http(true);
			
		}


	}
	private void init() {
		setListView();
		mFinalBitmap = FinalBitmap.create(context);
		mFinalBitmap.configLoadingImage(R.drawable.bg_default_photo);
		mFinalBitmap2 = FinalBitmap.create(context);
		mFinalBitmap2.configLoadingImage(R.drawable.bg_default_photo);
		
		LayoutParams laParams=(LayoutParams)mapIv.getLayoutParams();
		laParams.height =(int)(PhoneInfo.getScreenWidth(context)/5)*4;
		laParams.width = (int)(PhoneInfo.getScreenWidth(context));
		mZrcListView.addHeaderView(header);//添加头部
		mZrcListView.addHeaderView(actionView);//ListView条目中的悬浮部分 添加到头部
		mZrcListView.addHeaderView(divider);
		http(false);
		mZrcListView.setOnScrollListener(new ZrcListView.OnScrollMyListener() {
			

			@Override
			public void onScrollStateChanged(ZrcListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(ZrcListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					include.setVisibility(View.VISIBLE);
					actionView.setVisibility(View.INVISIBLE);
					avatarIv2 = (CircleImageView)MyPageActivity.this.findViewById(R.id.frag_mine_header_action_iv_avatar2);
					nickNameTv2 = (TextView)MyPageActivity.this.findViewById(R.id.frag_mine_header_action_nickname2);
//					ageTv2 = (TextView)MyPageActivity.this.findViewById(R.id.frag_mine_header_action_age2);
					addressTv2 = (TextView)MyPageActivity.this.findViewById(R.id.frag_mine_header_action_address2);
					sinatureTv2 = (TextView)MyPageActivity.this.findViewById(R.id.frag_mine_header_action_sinature2);
					followButton2 = (Button) MyPageActivity.this.findViewById(R.id.frag_mine_header_action_btn_follow2);
					
					if (TravellingTrailApplication.loginUser != null 
							&& TravellingTrailApplication.loginUser.getUs_Info_Us_Id() == User_Id)
					{
						followButton2.setVisibility(View.INVISIBLE);
					}
					
					followButton2.setOnClickListener(MyPageActivity.this);
					mFinalBitmap2.display(avatarIv2, ResponseList.SelfInfoMap.get("Us_Avatar"));
					nickNameTv2.setText(ResponseList.SelfInfoMap.get("Us_Nickname"));
//					ageTv2.setText(age+"岁");
					addressTv2.setText(ResponseList.SelfInfoMap.get("Us_Location"));
					sinatureTv2.setText(ResponseList.SelfInfoMap.get("Us_Sinature"));
					
				} else {
					include.setVisibility(View.GONE);
					actionView.setVisibility(View.VISIBLE);
				}
				
			}
		});
	}

	private void setListView() {
		// 设置下拉刷新的样式
		SimpleHeader header = new SimpleHeader(context);
        header.setTextColor(getResources().getColor(R.color.aurantium));
        header.setCircleColor(getResources().getColor(R.color.aurantium));
        mZrcListView.setHeadable(header);
		
        // 设置加载更多的样式
		SimpleFooter footer = new SimpleFooter(context);
        footer.setCircleColor(getResources().getColor(R.color.aurantium));
        mZrcListView.setFootable(footer);
        
        // 设置列表项出现动画
        mZrcListView.setItemAnimForTopIn(R.anim.anim_topitem_in);
        mZrcListView.setItemAnimForBottomIn(R.anim.anim_bottomitem_in);
        
        mZrcListView.setFooterDividersEnabled(false);
        
		
	}
	private void http(final boolean REFRASH){
		mDataFinalHttp = new FinalHttp();
		mTripAllFh = new FinalHttp();
		mTripFootPoint = new FinalHttp();
		mTripFootPoint.get(RequestAddress.TRIP_FOOT_POINTS+User_Id, new AjaxCallBack<Object>(){

			@Override
			public void onSuccess(Object t) {
				analyzeFootpointjson(t.toString());
				super.onSuccess(t);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				super.onFailure(t, errorNo, strMsg);
			}
			
		});
		mDataFinalHttp.get(RequestAddress.MINE_DATA+User_Id, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Log.i(TAG, strMsg.toString());
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(Object t) {
				Log.i(TAG, t.toString());
				analyzeInfojson(t.toString(),null,REFRASH);
				super.onSuccess(t);
			}
			
		});
		
		
		//获取个人已结束的旅程
		mTripAllFh.get(RequestAddress.PERSONAL_TRAVEL + "?UserId=" + User_Id + "&status=9", new AjaxCallBack<Object>(){

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Log.i(TAG, "mTripAllFh..."+strMsg.toString());
				mZrcListView.setRefreshFail("加载失败");
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(Object t) {
				Log.i(TAG, "mTripAllFh..."+t.toString());
				analyzejson(t.toString(), null, REFRASH);
				super.onSuccess(t);
			}
			
		});
		
	}

	protected void analyzeFootpointjson(String jsonString){
		List<Map<String, String>> list = JsonUtil.parseJson1(jsonString, null);
		ResponseList.SelfTripPointList = list;
		List<List<Map<String, String>>> list2 = new ArrayList<List<Map<String,String>>>();
		for(int i = 0; i<list.size();i++){ 
			String str = list.get(i).get("FootprintSummary");
			
			if (str != null && !str.equals("[]"))
			{
				list2.add(JsonUtil.parseJson1(str, null));
			}
		}
		
		if (list2.size() < 1)
		{
			mFinalBitmap.display(mapIv, MapImage.getNoneMapUrl());
		}else {
			mFinalBitmap.display(mapIv, MapImage.getAllTripMapImgUrl(list2, context));
		}
		
		
		Log.i(TAG, "analyzeFootpointjson       "+list2.toString());
	}
	
	
	protected void analyzeInfojson(String jsonString,String tagName,boolean refrash){
		Map<String, String> map = JsonUtil.parseJson1(jsonString);
		ResponseList.SelfInfoMap = map;
		Log.i(TAG, map.size()+"");
		mFinalBitmap.display(avatarIv, map.get("Us_Avatar"));
		nickNameTv.setText(map.get("Us_Nickname"));
//		getAge(map.get("Us_Birthday"));
		addressTv.setText(map.get("Us_Location"));
		sinatureTv.setText(map.get("Us_Sinature"));
		
	}
	
	//由获取用户年龄
	@SuppressWarnings("deprecation")
	private void getAge(final String birthday){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String age = null;
				URL url;  
				Date date = null ;
				try {  
				    url = new URL("http://www.baidu.com");  
				    URLConnection uc = url.openConnection();// 生成连接对象  
				    uc.connect(); // 发出连接  
				    long ld = uc.getDate(); // 取得网站日期时间 
				    date = new Date(ld); // 转换为标准时间对象  
				} catch (MalformedURLException e) {  
				    // TODO Auto-generated catch block  
				    e.printStackTrace();  
				}// 取得资源对象  
				catch (IOException e) {  
				    // TODO Auto-generated catch block  
				    e.printStackTrace();  
				}
				Log.i(TAG, Integer.parseInt(birthday.split("-")[0])+"");
				age = String.valueOf(date.getYear()+1900-Integer.parseInt(birthday.split("-")[0]));
				MyPageActivity.this.age = age;
				sendMessage(AGE, age);
			}
		}).start();
	}
	
	protected void analyzejson(String jsonString,String tagName,boolean refrash) {
		List<Map<String, String>> list = JsonUtil.parseJson1(jsonString, tagName);
		ResponseList.SelfTripList = list;
		if(refrash && mineAdapter!=null){
			mineAdapter.setData(list);
		}else{
			mineAdapter = new MineAdapter(context, list,mFinalBitmap);
			mZrcListView.setAdapter(mineAdapter);
		}
		run();
	}

	private void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendMessage(UPDATE,"");
			}
		}).start();
		
	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE:
				mZrcListView.setRefreshSuccess("加载成功");
				mineAdapter.notifyDataSetChanged();
				break;
//			case AGE:
//				ageTv.setText(msg.obj.toString()+"岁");
//				break;
			}
			super.handleMessage(msg);
		}
		
	};
	private void sendMessage(int what,Object obj){
		Message msg = Message.obtain();
		msg.what = what;
		msg.obj = obj;
		handler.sendMessage(msg);;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.frag_mine_header_action_btn_follow:
			sendFollow();
			break;
		case R.id.frag_mine_header_action_btn_follow2:
			sendFollow();
			break;
		default:
			break;
		}
		
	}

	private void sendFollow()
	{
		mFollowHttp = new FinalHttp();
		
		if (TravellingTrailApplication.loginUser == null)
		{
			ToastHelper.showToast(MyPageActivity.this, "请先登录", Toast.LENGTH_SHORT);
			return;
		}
		
		String jsonString = "{\"us_fans_us_id\":" + User_Id + 
								",\"us_fans_fans_id\":" + TravellingTrailApplication.loginUser.getUs_Info_Us_Id() + "}";
		
		
		HttpEntity entity;
		try
		{
			entity = new StringEntity(jsonString);
			
			mFollowHttp.post(RequestAddress.FOLLOW_SOMEONE, entity, "application/json",
					new AjaxCallBack<Object>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							super.onFailure(t, errorNo, strMsg);
							if (errorNo == 400)
							{
								ToastHelper.showToast(
										MyPageActivity.this,"已关注",
										Toast.LENGTH_SHORT);
							}else {

								ToastHelper.showToast(
										MyPageActivity.this,
										"失败！错误代码：" + errorNo +  "；错误信息：" + strMsg,
										Toast.LENGTH_SHORT);
							}
						}

						@Override
						public void onSuccess(Object result)
						{
							super.onSuccess(result);
							ToastHelper.showToast(
									MyPageActivity.this,"关注成功",
									Toast.LENGTH_SHORT);
						}
					});
			
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
	}
}
