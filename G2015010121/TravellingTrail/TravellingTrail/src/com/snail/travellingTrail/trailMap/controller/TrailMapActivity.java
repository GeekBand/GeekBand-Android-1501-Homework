package com.snail.travellingTrail.trailMap.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;



import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.RequestAddress;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.trailMap.model.Footprint;
//import com.snail.travellingTrail.trailMap.model.MapOperator;
import com.snail.travellingTrail.trailMap.model.MapOperator;

public class TrailMapActivity extends Activity
{

	MapView mapView = null;
	AMap aMap;
	ArrayList<Footprint> footprints;
	MapOperator mapOperator;
	long travelId;
	long userId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trail_map);
		findView();
		
		mapView.onCreate(savedInstanceState);// 必须要写
		init();
		initMap();
		getFootprintData();
	}
	
	private void findView()
	{
		mapView = (MapView) findViewById(R.id.act_trail_map_mapview);
	}
	
	
	private void init()
	{
		travelId = getIntent().getExtras().getLong("travelId");
//		userId = getIntent().getExtras().getLong("userId");
		userId = 1;
	}

	/**
	 * 初始化AMap对象
	 */
	public void initMap()
	{
		if (aMap == null) aMap = mapView.getMap();
		
//		LatLng centerPointLatLng = new LatLng(35.436, 105.203); // 宝鸡 //纬度、经度
		
		mapOperator = new MapOperator(TrailMapActivity.this, mapView, aMap);

//		aMap.moveCamera(CameraUpdateFactory.zoomTo(9f));
	}


	
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
     
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

	
	private void getFootprintData()
	{	
		FinalHttp finalHttp = new FinalHttp();
		
		DialogTool.showProgressDialog(TrailMapActivity.this, "加载足迹中..");
		finalHttp.get(RequestAddress.GET_TRAVEL_MAP_FOOTPRINTS + travelId, new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg)
			{
				DialogTool.cancelProgressDialog();
				if (strMsg != null)
				{
					Log.v("onFailure", strMsg);
					ToastHelper.showToast(TrailMapActivity.this,
							"加载失败，错误代码：" + errorNo +
							"\n错误信息：\n" + strMsg, Toast.LENGTH_SHORT);
				}
				else {
					ToastHelper.showToast(TrailMapActivity.this,
							"加载失败，错误代码：" + errorNo, Toast.LENGTH_SHORT);
				}
			}

			@Override
			public void onSuccess(String result)
			{
				DialogTool.cancelProgressDialog();
				Log.v("onSuccess", result);
				Type type = new TypeToken<ArrayList<Footprint>>(){ }.getType();
				Gson gson = new Gson();
				footprints = gson.fromJson(result, type);
				
				mapOperator.drawFootprintsLine(footprints);
			}
			
		});
	}
}
