package com.snail.travellingTrail.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.common.utils.JsonUtil;
import com.snail.travellingTrail.square.SquareAdapter;

public class SquareFragment extends Fragment
{
	FinalHttp mFinalHttp;
	FinalBitmap mFinalBitmap,mAvatarFinalBitmap;
	View frameView;
	ZrcListView mZrcListView;
	SquareAdapter mSquareAdapter;
	ArrayList<HashMap<String,String>> list;
	HashMap<String,String> map;
	final static String TAG = "SquareFragment";
	final static int UPDATE = 1;
	public static boolean REFRASH = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		frameView = inflater.inflate(R.layout.fragment_square, null);
		findid();
		return frameView;
	}

	private void findid() {
		mZrcListView = (ZrcListView)frameView.findViewById(R.id.fragmet_square_lv);
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
			refrash();
			
		}


	}
	private void refrash(){
		REFRASH = true;
		http(REFRASH);
	}
	private void setListView()
	{
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
	}
	private void init() {
		setListView();
		mFinalBitmap = FinalBitmap.create(getActivity());
		mAvatarFinalBitmap = FinalBitmap.create(getActivity());
		mFinalBitmap.configLoadingImage(R.drawable.bg_default_photo);
		mAvatarFinalBitmap.configLoadingImage(R.drawable.ic_default_avatar);
		list = new ArrayList<HashMap<String,String>>();
		http(REFRASH);
	}
	private void http(final boolean REFRASH){
		mFinalHttp = new FinalHttp();
		mFinalHttp.get(RequestAddress.SQUARE_TRIP , new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Log.i(TAG, strMsg.toString());
				mZrcListView.setRefreshFail("加载失败");
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(Object t) {
				Log.i(TAG, t.toString());
				analyzejson(t.toString(),null,REFRASH);
				super.onSuccess(t);
			}
			
		});
	}

	protected void analyzejson(String jsonString,String tagName,boolean refrash) {
		List<Map<String, String>> list = JsonUtil.parseJson1(jsonString, tagName);
		ResponseList.SquareList = list;
		if(refrash && mSquareAdapter!=null){
			mSquareAdapter.setData(list);
		}else{
			mSquareAdapter = new SquareAdapter(getActivity(), list,mFinalBitmap,mAvatarFinalBitmap);
			mZrcListView.setAdapter(mSquareAdapter);
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
				sendMessage(UPDATE);
			}
		}).start();
		
	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE:
				mZrcListView.setRefreshSuccess("加载成功");
				mSquareAdapter.notifyDataSetChanged();
				REFRASH = false;
				
			break;
			}
			super.handleMessage(msg);
		}
		
	};
	private void sendMessage(int what){
		Message msg = Message.obtain();
		msg.what = what;
		handler.sendMessage(msg);
		msg.obj = "qwlejlqe";
	}
}
