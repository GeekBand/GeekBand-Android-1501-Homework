package com.snail.travellingTrail.newTravel.controller;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;






import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.AMapUtil;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.trailMap.model.Footprint;

public class LocationMapActivity extends Activity implements AMapLocationListener,
	OnMarkerDragListener, OnGeocodeSearchListener, LocationSource, OnClickListener
{
	public final static String FOOTPRINT_LOCATION = "footprint_location"; //定位信息
	MapView mMapView = null;
	AMap mAMap;
	LocationManagerProxy mLocationManagerProxy;
	TextView mAddressTextView;
	Button mConfirmButton;
	Footprint footprintLocation;
	private GeocodeSearch geocoderSearch;
	private LatLonPoint latLonPoint;
	Marker marker;
	float mZoom;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location_map);
		findView();
		
		mMapView.onCreate(savedInstanceState);// 必须要写

		init();
		initMap();
	}
	
	private void findView()
	{
		mMapView = (MapView) findViewById(R.id.act_trail_map_mapview);
		mAddressTextView = (TextView) findViewById(R.id.act_location_map_tv_address);
		mConfirmButton = (Button) findViewById(R.id.act_location_map_btn_confirm);
	}
	
	
	private void init()
	{
		mConfirmButton.setClickable(false);
		
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		

		DialogTool.showProgressDialog(LocationMapActivity.this, "定位中~");
		
		//此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法     
        //其中如果间隔时间为-1，则定位只定一次
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 1000f, this);
	}

	/**
	 * 初始化AMap对象
	 */
	public void initMap()
	{
		if (mAMap == null) mAMap = mMapView.getMap();

		mAMap.setLocationSource(this);// 设置定位监听
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	}


	private void setListener()
	{
		mConfirmButton.setOnClickListener(this);
	}
	
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
		
        super.onResume();
        mMapView.onResume();
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        stopLocation();
    }
    
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
		DialogTool.cancelProgressDialog();
		
        super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

	
	

	@Override
	public void onLocationChanged(Location location)
	{
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		
	}

	/**
	 * 当前位置发生变化
	 */
	@Override
	public void onLocationChanged(AMapLocation aMapLocation)
	{
		DialogTool.cancelProgressDialog();
		
		//是否定位成功
		if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0)
		{
			ToastHelper.showToast(this, "当前位置：" + aMapLocation.getAddress(), Toast.LENGTH_SHORT);
			
			footprintLocation = new Footprint(aMapLocation.getLongitude(), aMapLocation.getLatitude(),
					aMapLocation.getAddress(), TravellingTrailApplication.loginUser.getIdOfTravelInTravlling());
			
			
			Log.v("NewFootptintActivity--->onLocationChanged", "---经度 & 纬度：" + 
					aMapLocation.getLongitude() + " & " +  aMapLocation.getLatitude());
			LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
			
			mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

			mAMap.clear(); //先清除所有覆盖物（Marker、折线等）
			
			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_location);
			// 构建MarkerOption，用于在地图上添加Marker
			MarkerOptions option = new MarkerOptions().position(latLng).icon(bitmap).draggable(true).visible(true);
			// 在地图上添加Marker，并显示
			marker = mAMap.addMarker(option);
			
			mAddressTextView.setText(aMapLocation.getAddress());
			
			mAMap.setOnMarkerDragListener(this);
			
			setListener();
		}
		
		stopLocation();
	}

	@Override
	public void onClick(View v)
	{
		if (footprintLocation.getFtprnt_X() > 0 && footprintLocation.getFtprnt_Y() > 0)
		{
			Intent intent = new Intent(LocationMapActivity.this, NewFootprintContentListActivity.class);
			intent.putExtra(FOOTPRINT_LOCATION, footprintLocation);
			startActivity(intent);
		}
		else {
			ToastHelper.showToast(this, "还没获取到您所在的位置", Toast.LENGTH_SHORT);
		}
	}
	
	
	/**
	 * 销毁定位资源
	 */
	private void stopLocation()
	{
	    if (mLocationManagerProxy != null) {
	    	mLocationManagerProxy.removeUpdates(this);
	    	mLocationManagerProxy.destory();
	    }
	    mLocationManagerProxy = null;
	}

	/**
	 * Marker拖动
	 */
	@Override
	public void onMarkerDrag(Marker marker)
	{
		
	}

	/**
	 * Marker拖动结束
	 */
	@Override
	public void onMarkerDragEnd(Marker marker)
	{
		mZoom = mAMap.getCameraPosition().zoom;
		latLonPoint = new LatLonPoint(
				marker.getPosition().latitude, marker.getPosition().longitude);
		getAddress(latLonPoint);
	}

	/**
	 * Marker拖动开始
	 */
	@Override
	public void onMarkerDragStart(Marker marker)
	{
		
	}
	
	
	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
		DialogTool.showProgressDialog(LocationMapActivity.this, "获取地址中");
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}


	/**
	 * 逆地理编码回调（经纬度转成地址）
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		DialogTool.cancelProgressDialog();
		if (rCode == 0) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				
				footprintLocation.setFtprnt_Address(
						result.getRegeocodeAddress().getFormatAddress()
						+ "附近");
				footprintLocation.setFtprnt_X(latLonPoint.getLongitude());
				footprintLocation.setFtprnt_Y(latLonPoint.getLatitude());
				
				mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						AMapUtil.convertToLatLng(latLonPoint), mZoom));
				marker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
				
				ToastHelper.showToast(LocationMapActivity.this,
						footprintLocation.getFtprnt_Address(), Toast.LENGTH_SHORT);
				mAddressTextView.setText(footprintLocation.getFtprnt_Address());
			} else {
				ToastHelper.showToast(LocationMapActivity.this,
						getResources().getString(R.string.no_result), Toast.LENGTH_SHORT);
			}
		} else if (rCode == 27) {
			ToastHelper.showToast(LocationMapActivity.this,
					getResources().getString(R.string.error_network), Toast.LENGTH_SHORT);
		} else if (rCode == 32) {
			ToastHelper.showToast(LocationMapActivity.this,
					getResources().getString(R.string.error_key), Toast.LENGTH_SHORT);
		} else {
			ToastHelper.showToast(LocationMapActivity.this,
					getResources().getString(R.string.error_other) + rCode, Toast.LENGTH_SHORT);
		}
	}

	
	/**
	 * 逆地理编码回调（地址转成经纬度）
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		
		if (mLocationManagerProxy == null) {
			mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		}
		
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 1000f, this);
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		stopLocation();
	}
}
