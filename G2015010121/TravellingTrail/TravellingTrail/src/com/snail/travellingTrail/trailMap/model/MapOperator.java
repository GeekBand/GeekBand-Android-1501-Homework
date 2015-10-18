package com.snail.travellingTrail.trailMap.model;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.LatLngBounds.Builder;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.utils.BitmapUtil;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.trailMap.controller.SingleFootprintActivity;

public class MapOperator implements InfoWindowAdapter, OnMarkerClickListener,
	OnMapClickListener, OnInfoWindowClickListener
{
	Context context;
	MapView mapView = null;
	AMap aMap = null;
	List<Footprint> footprints;
	List<Marker> markers;
	FinalBitmap finalBitmap;

	public MapOperator(Context context, MapView mapView, AMap aMap)
	{
		this.context = context;
		this.mapView = mapView;
		this.aMap = aMap;
		finalBitmap = FinalBitmap.create(context);
		markers = new ArrayList<Marker>();
	}

	public void drawFootprintsLine(ArrayList<Footprint> footprints)
	{
		this.footprints = footprints;
		createPolyline();
	}

	private void createPolyline()
	{
		ArrayList<LatLng> points = new ArrayList<LatLng>();
		for (Footprint footprint : footprints)
		{
			LatLng point = new LatLng(footprint.getFtprnt_Y(),
					footprint.getFtprnt_X());
			points.add(point);
			createMaker(footprint, point);
		}
		// 构建折线的Option对象
		PolylineOptions polylineOptions = new PolylineOptions().addAll(points)
				.color(context.getResources().getColor(R.color.main_green)).visible(true);
		// 在地图上添加折线Option，用于显示
		aMap.addPolyline(polylineOptions);
		Builder builder = new Builder();
		builder.include(points.get(0));
		LatLngBounds bounds = builder.build();
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
	}

	private void createMaker(Footprint footprint, LatLng point)
	{
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_location_photo);
		// 构建MarkerOption，用于在地图上添加Marker
		MarkerOptions option = new MarkerOptions().position(point)
				.icon(bitmap).draggable(false).visible(true);
		// 在地图上添加Marker，并显示
		Marker marker = aMap.addMarker(option);
		markers.add(marker);
		marker.setObject(footprint);
		marker.setTitle("Ftprnt_Id:" + footprint.getFtprnt_Id());
		marker.setSnippet(footprint.getFtprnt_Address());
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
		aMap.setOnInfoWindowClickListener(this);
		aMap.setOnMapClickListener(this);
	}
	
	@Override
	public boolean onMarkerClick(Marker marker)
	{
		Toast.makeText(context, "Marker", Toast.LENGTH_SHORT).show();
		Log.v("MapOperator--->onMarkerClick", "--->getFtprnt_Id():" + ((Footprint)marker.getObject()).getFtprnt_Id());
		marker.showInfoWindow();
		return false;
	}

	@Override
	public View getInfoContents(Marker marker)
	{
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker)
	{
		Footprint footprint = (Footprint)marker.getObject();
		
		Log.v("MapOperator--->getInfoWindow", "--->getFtprnt_Id():" + footprint.getFtprnt_Id());
		View view = LayoutInflater.from(context).inflate(R.layout.map_infowindow_photo, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.map_infowindow_photo_iv_img);
		
		// 若有心情内容
		if (footprint.getFootprint_Content() != null
				&& footprint.getFootprint_Content().size() != 0)
		{
			// 显示有图片的心情icon
			if (footprint.getFootprint_Content().get(0).getFtprnt_Cntnt_Photo() != null
					&& !footprint.getFootprint_Content().get(0)
							.getFtprnt_Cntnt_Photo().equals(""))
			{
				finalBitmap.display(imageView, footprint.getFootprint_Content()
						.get(0).getFtprnt_Cntnt_Photo());
			}
			// 显示纯文字心情icon
			else if ((footprint.getFootprint_Content().get(0)
					.getFtprnt_Cntnt_Photo() == null || footprint
					.getFootprint_Content().get(0).getFtprnt_Cntnt_Photo()
					.equals(""))
					&& footprint.getFootprint_Content().get(0)
							.getFtprnt_Cntnt_Words() != null
					&& !footprint.getFootprint_Content().get(0)
							.getFtprnt_Cntnt_Words().equals(""))
			{
				Bitmap popupTextBitmap = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.bg_popup_text);
				popupTextBitmap = BitmapUtil.compressAccordingToWidth(
						popupTextBitmap,
						(int) (PhoneInfo.screenWidthPx / 10) * 3);
				imageView.setImageBitmap(popupTextBitmap);
			}
		}
		
		return view;
	}

	@Override
	public void onMapClick(LatLng latLng)
	{
		Log.v("MapOperator--->onMapClick",
				"--->markers.size():" + markers.size());
		for (Marker marker : markers)
		{
			if (marker.isInfoWindowShown()) 
			{
				Log.v("MapOperator--->onMapClick",
						"--->isInfoWindowShown():" + ((Footprint)marker.getObject()).getFtprnt_Id());
				marker.hideInfoWindow();
			}
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker)
	{
		Footprint footprint = (Footprint)marker.getObject();
		
		Log.v("MapOperator--->onInfoWindowClick", "--->getFtprnt_Id():" + footprint.getFtprnt_Id());
		Intent intent = new Intent(context, SingleFootprintActivity.class);
    	intent.putExtra("footprintId", footprint.getFtprnt_Id());
    	context.startActivity(intent);
	}
	
	
}
