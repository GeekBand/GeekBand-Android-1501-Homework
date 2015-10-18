package com.snail.travellingTrail.main.controller;



import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.views.CircleImageView;
import com.snail.travellingTrail.mine.controller.MineAdapter;
import com.snail.travellingTrail.travelNotes.model.MapImage;


public class MineFragment extends Fragment 
{
	View frameView,actionView,header,divider;
	Context context;
	ZrcListView mZrcListView;
	LinearLayout mineLlyt;
	ImageView mapIv;
	CircleImageView avatarIv,avatarIv2;
	TextView nickNameTv,addressTv,sinatureTv,nickNameTv2,addressTv2,sinatureTv2;
	Button followButton, followButton2;
	View include;
	FinalHttp mDataFinalHttp,mTripAllFh,mTripFootPoint;
	MineAdapter mineAdapter;
	FinalBitmap mFinalBitmap,mFinalBitmap2;
	private final static String  TAG = "MineFragment";
	private final static int UPDATE = 1;
	private final static int AGE = 2;
	private static boolean REFRASH = false;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		frameView = inflater.inflate(R.layout.fragment_mine, null);

		findid();
		return frameView;
	}


	public void findid() {
		actionView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_mine_header_action, null);
		header = View.inflate(getActivity(), R.layout.frag_mine_header, null);//头部内容
		divider = View.inflate(getActivity(), R.layout.divider, null);
		
		mZrcListView = (ZrcListView)frameView.findViewById(R.id.frag_mine_lv_travellingTrail);
		include = (View)frameView.findViewById(R.id.frag_mine_include);
		avatarIv = (CircleImageView)actionView.findViewById(R.id.frag_mine_header_action_iv_avatar);
		nickNameTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_nickname);
//		ageTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_age);
		addressTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_address);
		sinatureTv = (TextView)actionView.findViewById(R.id.frag_mine_header_action_sinature);
		mapIv = (ImageView)header.findViewById(R.id.fragment_mine_header_iv_map);
		followButton = (Button) actionView.findViewById(R.id.frag_mine_header_action_btn_follow);
		followButton.setVisibility(View.INVISIBLE);
		setListener();
	}
	private void setListener()
	{
		mZrcListView.setOnRefreshStartListener(new zrcListViewRefresh());
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
		mFinalBitmap = FinalBitmap.create(getActivity());
		mFinalBitmap.configLoadingImage(R.drawable.bg_default_photo);
		mFinalBitmap2 = FinalBitmap.create(getActivity());
		mFinalBitmap2.configLoadingImage(R.drawable.bg_default_photo);
		
		
		
		LayoutParams laParams=(LayoutParams)mapIv.getLayoutParams();
		laParams.height =(int)(PhoneInfo.getScreenWidth(getActivity())/5)*4;
		laParams.width = (int)(PhoneInfo.getScreenWidth(getActivity()));
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
					avatarIv2 = (CircleImageView)frameView.findViewById(R.id.frag_mine_header_action_iv_avatar2);
					nickNameTv2 = (TextView)frameView.findViewById(R.id.frag_mine_header_action_nickname2);
//					ageTv2 = (TextView)frameView.findViewById(R.id.frag_mine_header_action_age2);
					addressTv2 = (TextView)frameView.findViewById(R.id.frag_mine_header_action_address2);
					sinatureTv2 = (TextView)frameView.findViewById(R.id.frag_mine_header_action_sinature2);

					followButton2 = (Button) frameView.findViewById(R.id.frag_mine_header_action_btn_follow2);
//					followButton2.setVisibility(View.INVISIBLE);
					
					mFinalBitmap2.display(avatarIv2, ResponseList.SelfInfoMap.get("Us_Avatar"));
					nickNameTv2.setText(ResponseList.SelfInfoMap.get("Us_Nickname"));
//					ageTv2.setText("23岁");
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
		SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(getResources().getColor(R.color.main_green));
        header.setCircleColor(getResources().getColor(R.color.main_green));
        mZrcListView.setHeadable(header);
		
        // 设置加载更多的样式
		SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(getResources().getColor(R.color.main_green));
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
		long userid;
		if (TravellingTrailApplication.loginUser != null)
		{
			userid = TravellingTrailApplication.loginUser.getUs_Info_Us_Id();
			mTripFootPoint.get(RequestAddress.PERSONAL_TRAVEL + "?UserId=" + userid + "&status=9",
					new AjaxCallBack<Object>()
					{

						@Override
						public void onSuccess(Object t)
						{
							analyzeFootpointjson(t.toString());
							super.onSuccess(t);
						}

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{

							super.onFailure(t, errorNo, strMsg);
						}

					});
			mDataFinalHttp.get(RequestAddress.MINE_DATA + userid,
					new AjaxCallBack<Object>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							Log.i(TAG, strMsg.toString());
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onSuccess(Object t)
						{
							Log.i(TAG, t.toString());
							analyzeInfojson(t.toString(), null, REFRASH);
							super.onSuccess(t);
						}

					});
			
			//获取个人已结束的旅程
			mTripAllFh.get(RequestAddress.PERSONAL_TRAVEL + "?UserId=" + userid,
					new AjaxCallBack<Object>()
					{

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg)
						{
							mZrcListView.setRefreshFail("加载失败");
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onSuccess(Object t)
						{
							analyzejson(t.toString(), null, REFRASH);
							super.onSuccess(t);
						}

					});

		}
	}
	
	protected void analyzeFootpointjson(String jsonString){
		List<Map<String, String>> list = JsonUtil.parseJson1(jsonString, null);
		ResponseList.SelfTripPointList = list;
		List<List<Map<String, String>>> list2 = new ArrayList<List<Map<String,String>>>() ;
		for(int i = 0; i<list.size();i++){ 
			list2.add(JsonUtil.parseJson1(list.get(i).get("FootprintSummary"), null));
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
		getAge(map.get("Us_Birthday"));
		addressTv.setText(map.get("Us_Location"));
		sinatureTv.setText(map.get("Us_Sinature"));
		
//		ResponseList.avatarBitmap = mFinalBitmap;
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
				    // 分别取得时间中的小时，分钟和秒，并输出  
				    Log.i(TAG, date.getYear()+"   is year from network");  
				} catch (MalformedURLException e) {  
				    // TODO Auto-generated catch block  
				    e.printStackTrace();  
				}// 取得资源对象  
				catch (IOException e) {  
				    // TODO Auto-generated catch block  
				    e.printStackTrace();  
				}
//				Log.i(TAG, Integer.parseInt(birthday.split("-")[0])+"");
//				age = String.valueOf(date.getYear()+1900-Integer.parseInt(birthday.split("-")[0]));
//				sendMessage(AGE, age);
			}
		}).start();
	}
	
	protected void analyzejson(String jsonString,String tagName,boolean refrash) {
		List<Map<String, String>> list = JsonUtil.parseJson1(jsonString, tagName);
		ResponseList.SelfTripList = list;
		if(refrash && mineAdapter!=null){
			mineAdapter.setData(list);
		}else{
			mineAdapter = new MineAdapter(getActivity(), list,mFinalBitmap);
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
		handler.sendMessage(msg);
	}
}
