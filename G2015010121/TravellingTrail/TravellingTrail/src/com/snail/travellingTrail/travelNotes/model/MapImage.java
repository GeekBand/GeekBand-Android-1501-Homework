package com.snail.travellingTrail.travelNotes.model;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.util.Log;

import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.trailMap.model.Footprint;

public class MapImage
{
	public static String getSingleTravelMapImgUrl(List<Footprint> footprints, Context context)
	{

		String points = "";
		
		for (Footprint footprint : footprints)
		{
			points += ("|" + footprint.getFtprnt_Y() + "," + footprint.getFtprnt_X());
		}
		
		String url = "http://apis.map.qq.com/ws/staticmap/v2/?";
		
		String pathString = "color:" + //Integer.toHexString(context.getResources().getColor(R.color.grass_green))
				"0x005bac" + "|weight:8" + points;
		String icon = "http://goteny.com/wp-content/themes/goten/other/ic_location.png";
//		try
//		{
//			icon = URLEncoder.encode(icon, "UTF-8");
//		} catch (UnsupportedEncodingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String markerString	= "icon:" + icon + points;
		
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("size", "1000*800");
		ajaxParams.put("maptype", "roadmap");
		ajaxParams.put("key", TravellingTrailApplication.TENCENT_MAP_KEY);
		ajaxParams.put("path", pathString);
		ajaxParams.put("markers", markerString);
		
		url += ajaxParams.getParamString();
		Log.i("getSingleTravelMapImgUrl", "getSingleTravelMapImgUrl--->"+url);
		
		return url;
	}
	
/*	public static String getAllTravelMapImgUrl(List<Footprint> footprints, Context context)
	{

		String points = "";
		
		for (Footprint footprint : footprints)
		{
			points += ("|" + footprint.getFtprnt_Y() + "," + footprint.getFtprnt_X());
		}
		
		String url = "http://apis.map.qq.com/ws/staticmap/v2/?";
		
		String pathString = "color:" + //Integer.toHexString(context.getResources().getColor(R.color.grass_green))
				"0x005bac" + "|weight:8" + points;
		String markerString	= "icon:http://goteny.com/wp-content/themes/goten/other/ic_location.png" + points;
		
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("size", "1000*800");
		ajaxParams.put("maptype", "roadmap");
		ajaxParams.put("key", TravellingTrailApplication.TENCENT_MAP_KEY);
		ajaxParams.put("path", pathString);
		ajaxParams.put("markers", markerString);
		
		url += ajaxParams.getParamString();
		
		return url;
	}*/
	
	public static String getAllTripMapImgUrl(List<List<Map<String,String>>> footprints, Context context)
	{

		
		Map<String,String> footprint;

		
		String url = "http://apis.map.qq.com/ws/staticmap/v2/?";
		String icon = "icon:http://goteny.com/wp-content/themes/goten/other/ic_location.png" ;
		String markerString	= icon ;
		
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("size", "1000*800");
		ajaxParams.put("maptype", "roadmap");
		ajaxParams.put("key", TravellingTrailApplication.TENCENT_MAP_KEY);
		String path = "" ;
		for(int i = 0;i<footprints.size();i++){
		String points = "";
		for (int j = 0;j<footprints.get(i).size();j++)
		{  
			footprint = footprints.get(i).get(j);
			points += ("|" + footprint.get("Ftprnt_Y") + "," + footprint.get("Ftprnt_X"));
		}
		String pathString = "color:" + //Integer.toHexString(context.getResources().getColor(R.color.grass_green))
				"0x005bac" + "|weight:8" + points;
		markerString += points;
		Log.i("getAllTripMapImgUrl", markerString);
//		ajaxParams.put("path", pathString);
		 path +="&"+"path="+pathString;
		}
		ajaxParams.put("markers", markerString);
		
		url += ajaxParams.getParamString()+path;
		Log.i("getAllTripMapImgUrl", "getAllTripMapImgUrl--->"+url);
		return url;
	}
	
	public static String getNoneMapUrl()
	{
		return "http://apis.map.qq.com/ws/staticmap/v2/?"
				+ "center=34.22,107.09&zoom=4&size=1000*800&maptype=roadmap"
				+ "&markers=size:large|color:0xFFCCFF|label:k|39.8802147,116.415794"
				+ "&key=CZWBZ-FAUH3-SJZ3L-3JNJP-UOA3E-JGFET";
	}
}
