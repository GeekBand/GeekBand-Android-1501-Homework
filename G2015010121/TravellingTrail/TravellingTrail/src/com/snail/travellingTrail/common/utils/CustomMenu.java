package com.snail.travellingTrail.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.slidingmenu.model.Birthday;
import com.snail.travellingTrail.slidingmenu.view.CityPicker;
import com.snail.travellingTrail.slidingmenu.view.ScrollerNumberPicker;
import com.snail.travellingTrail.slidingmenu.view.ScrollerNumberPicker.OnSelectListener;

public final class CustomMenu implements OnSelectListener {
	 
    private static final String TAG="CustomMenu";
    private static PopupWindow pop=null;
    private final Activity activity;

    private static final int SEX = 0;
    private static final int BIRTHDAY = 1;
    private static final int ADDRESS = 2;
    
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 3;
    
    private static final int MAN = 0;
    private static final int WOMAN = 1;
    
    CityPicker mCityPicker ;
    ScrollerNumberPicker yearnp,monthnp,daynp;
	Map<String,Object> map;
	
	Button mButton;

	TextView manTv,womanTv,cancelTv;
	
    
    public CustomMenu(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
    } 
 
    public  PopupWindow getMenu(OnTouchListener touchListener,OnKeyListener keyListener,int what) {
    	
    	map = new HashMap<String, Object>();
        View view = null ;// layout_custom_menu菜单的布局文件
        switch (what) {
		case SEX:
	        view = activity.getLayoutInflater().inflate(R.layout.menu_useredit_info_sex, null);
	        manTv = (TextView)view.findViewById(R.id.menu_useredit_info_sex_man);
	        womanTv = (TextView)view.findViewById(R.id.menu_useredit_info_sex_women);
	        cancelTv = (TextView)view.findViewById(R.id.menu_useredit_info_sex_cancel);
	        manTv.setOnClickListener(new ButtonClick());
	        womanTv.setOnClickListener(new ButtonClick());
	        cancelTv.setOnClickListener(new ButtonClick());
			break;
		case BIRTHDAY:
	        view = activity.getLayoutInflater().inflate(R.layout.menu_useredit_info_birthday, null);
	        mButton = (Button)view.findViewById(R.id.menu_useredit_info_brithday_btn);
	        mButton.setOnClickListener(new ButtonClick());
	        birthday(view);
			break;
		case ADDRESS:
	        view = activity.getLayoutInflater().inflate(R.layout.menu_useredit_info_address, null);
	        mCityPicker = (CityPicker)view.findViewById(R.id.menu_useredit_info_address_citypicker);
	        mButton = (Button)view.findViewById(R.id.menu_useredit_info_address_btn);
	        mButton.setOnClickListener(new ButtonClick());
			break;

		default:
			break;
		}  
        pop = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setAnimationStyle(R.style.pop_anim_style);
        pop.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.ic_press_normal));// 这句是关键
        pop.setFocusable(true);
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);
        view.setClickable(true);
        view.setFocusableInTouchMode(true);
        pop.setTouchInterceptor(touchListener);
        view.setOnKeyListener(keyListener);
 
        return pop;
 
}
    class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_useredit_info_address_btn:
				submit(ADDRESS, getaddress());
				break;
			case R.id.menu_useredit_info_brithday_btn:
				submit(BIRTHDAY, getbrithday());
				break;
			case R.id.menu_useredit_info_sex_man:
				submit(SEX, "1");
				break;
			case R.id.menu_useredit_info_sex_women:
				submit(SEX, "0");
				break;
			case R.id.menu_useredit_info_sex_cancel:
				pop.dismiss();
				break;
			default:
				break;
			}
			
		}
    	
    }
	private void birthday(View view) {
	    yearnp = (ScrollerNumberPicker)view.findViewById(R.id.yearnp);
	    monthnp = (ScrollerNumberPicker)view.findViewById(R.id.monthnp);
	    daynp = (ScrollerNumberPicker)view.findViewById(R.id.daynp);
	    yearnp.setOnSelectListener(this);
	    monthnp.setOnSelectListener(this);
	    setPickerData();
	}

	public String getbrithday() {
		return Birthday.Year+"-"+Birthday.Month+"-"+Birthday.Day;
		
	}

	public String getaddress() {
		return mCityPicker.getCity_string();
		
	}

	@Override
	public void endSelect(int viewId,int id, String text) {
		Log.i(TAG, ""+viewId+"   "+R.id.monthnp);
		switch (viewId) {
		case R.id.monthnp:
			Log.i(TAG, "R.id.monthnp");
			if(isLeapYear(Integer.valueOf(Birthday.Year))){
				if(text.equals("2")){
					daynp.setData(getData(DAY, 1));
					daynp.setDefault(6);
				}else{
					setDefultDay(text);
				}
			}else{ 
				if(text.equals("2")){
					daynp.setData(getData(DAY, 0));
					daynp.setDefault(6);
				}else{
					setDefultDay(text);
				}
			}
			Birthday.Month = text;
			break;
		case R.id.yearnp:
			Log.i(TAG, "R.id.yearnp");
			if(isLeapYear(Integer.valueOf(text))){
				if(Birthday.Month.equals("2")){
					daynp.setData(getData(DAY, 1));
					daynp.setDefault(6);
				}else{
					setDefultDay(Birthday.Month);
				}
			}else{
				if(Birthday.Month.equals("2")){
					daynp.setData(getData(DAY, 0));
					daynp.setDefault(6);
				}else{
					setDefultDay(Birthday.Month);
				}
			}
			Birthday.Year = text;
			break;
		case R.id.daynp:
			Birthday.Day = text;
			break;
		default:
			break;
		}
	}
	
	private void setDefultDay(String text){
		if(text.equals("4")||text.equals("6")||text.equals("9")||text.equals("11")){
			daynp.setData(getData(DAY, 2));
			daynp.setDefault(6);
		}else{
			daynp.setData(getData(DAY, 3));
			daynp.setDefault(6);
		}
	}
	

	@Override
	public void selecting(int viewId,int id, String text) {
		
	} 
	
	public boolean isLeapYear(int year){
		return (year%4==0&&year%100!=0)||year%400==0;
	}
	
	private void setPickerData(){
		yearnp.setData(getData(YEAR, 0));
		yearnp.setDefault(7);
		monthnp.setData(getData(MONTH, 0));
		monthnp.setDefault(6);
		daynp.setData(getData(DAY, 3));
		daynp.setDefault(6);
	}
	private ArrayList<String> getData(int data,int day){
		ArrayList<String> datas = new ArrayList<String>();
		switch (data) {
		case YEAR:
			for (int i = 0; i < 50; i++) {
				datas.add(String.valueOf(1970+i));
			}
			break;
		case MONTH:
			for (int i = 0; i < 12; i++) {
				datas.add(String.valueOf(1+i));
			}
			break;
		case DAY:
			switch (day) {
			case 0:
				for (int i = 0; i < 28; i++) {
					datas.add(String.valueOf(1+i));
				}
				break;
			case 1:
				for (int i = 0; i < 29; i++) {
					datas.add(String.valueOf(1+i));
				}
				break;
			case 2:
				for (int i = 0; i < 30; i++) {
					datas.add(String.valueOf(1+i));
				}
				break;
			case 3:
				for (int i = 0; i < 31; i++) {
					datas.add(String.valueOf(1+i));
				}
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
		return datas;
	}

	private void submit(int editType, final String content) {
		final HashMap<String, Object> nMap = new HashMap<String, Object>();
		AjaxParams mAjaxParams = new AjaxParams();
		switch (editType) {
		case SEX:
			nMap.put("Us_Birthday", ResponseList.MyInfoMap.get("Us_Birthday"));
			nMap.put("Us_Addresss", ResponseList.MyInfoMap.get("Us_Addresss"));
//			final HttpUtil mHttpUtil = ;
			new Thread(new Runnable() {
				@Override
				public void run() {
					nMap.put("Us_Sex", Integer.valueOf(content));
					String result= "";	
					result = new HttpUtil().queryStringForPost(RequestAddress.MODIFY_DATA+ResponseList.MyInfoMap.get("Us_Info_Us_Id"),nMap);
					if(result.equals("200")){
						map.put("Us_Sex", content);
						map.put("Us_Birthday", ResponseList.MyInfoMap.get("Us_Birthday"));
						map.put("Us_Addresss", ResponseList.MyInfoMap.get("Us_Addresss"));
						analyzeJson();
						ToastHelper.showToast(activity, "修改成功", Toast.LENGTH_SHORT);
					}else {
						ToastHelper.showToast(activity, "修改失败", Toast.LENGTH_SHORT);
					}
				}
				}).start();
			break;
		case BIRTHDAY:
			mAjaxParams.put("Us_Birthday", content);
    		mAjaxParams.put("Us_Sex", ResponseList.MyInfoMap.get("Us_Sex")+"");
			mAjaxParams.put("Us_Addresss", ResponseList.MyInfoMap.get("Us_Addresss")+"");
			map.put("Us_Birthday", content);
			map.put("Us_Sex", ResponseList.MyInfoMap.get("Us_Sex"));
			map.put("Us_Addresss", ResponseList.MyInfoMap.get("Us_Addresss"));
			http(mAjaxParams);
			break;
		case ADDRESS:
			mAjaxParams.put("Us_Addresss", content);
    		mAjaxParams.put("Us_Birthday", ResponseList.MyInfoMap.get("Us_Birthday")+"");
    		mAjaxParams.put("Us_Sex", ResponseList.MyInfoMap.get("Us_Sex")+"");
			map.put("Us_Addresss", content);
			map.put("Us_Birthday", ResponseList.MyInfoMap.get("Us_Birthday"));
			map.put("Us_Sex", ResponseList.MyInfoMap.get("Us_Sex"));
			http(mAjaxParams);
			break;
		default:
			break;

		}
//		
	}

	private void http(AjaxParams mAjaxParams){
		FinalHttp mFinalHttp = new FinalHttp();
//		mAjaxParams.put("Us_Email", ResponseList.MyInfoMap.get("Us_Email"));
		mAjaxParams.put("Us_Info_Us_Id", ResponseList.MyInfoMap.get("Us_Info_Us_Id")+"");
//		mAjaxParams.put("Us_Avatar", ResponseList.MyInfoMap.get("Us_Avatar"));
//		mAjaxParams.put("Us_Info_Snapshot", ResponseList.MyInfoMap.get("Us_Info_Snapshot"));
		mAjaxParams.put("Us_Introduce", ResponseList.MyInfoMap.get("Us_Introduce")+"");
		mAjaxParams.put("Us_Location", ResponseList.MyInfoMap.get("Us_Location")+"");
		mAjaxParams.put("Us_Sinature", ResponseList.MyInfoMap.get("Us_Sinature")+"");
		mAjaxParams.put("Us_Nickname", ResponseList.MyInfoMap.get("Us_Nickname")+"");
	
	
	
		mFinalHttp.post(RequestAddress.MODIFY_DATA+ResponseList.MyInfoMap.get("Us_Info_Us_Id"), mAjaxParams, new AjaxCallBack<Object>() {

		@Override
		public void onSuccess(Object t) {
			Log.i("onSuccess", t.toString());
			DialogTool.cancelProgressDialog();
			ToastHelper.showToast(activity, "修改成功", Toast.LENGTH_SHORT);
			analyzeJson();
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
		map.put("Us_Sinature", ResponseList.MyInfoMap.get("Us_Sinature"));
		map.put("Us_Location", ResponseList.MyInfoMap.get("Us_Location"));
		map.put("Us_Nickname", ResponseList.MyInfoMap.get("Us_Nickname"));
		map.put("Us_Introduce", ResponseList.MyInfoMap.get("Us_Introduce"));
		map.put("Us_Avatar", ResponseList.MyInfoMap.get("Us_Avatar"));
		map.put("Us_Info_Snapshot", ResponseList.MyInfoMap.get("Us_Info_Snapshot"));
		map.put("Us_Email", ResponseList.MyInfoMap.get("Us_Email"));
		ResponseList.MyInfoMap = map;
		sentWhat(0);
	}
	
	private Handler mHandler = new Handler(){
		@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 0:
						pop.dismiss();
						break;
					default:
						break;
				}	
				super.handleMessage(msg);
		}
	};

	private void sentWhat(int what){

		Message msg = Message.obtain();

		msg.what = what;

		mHandler.sendMessage(msg);

	}
}