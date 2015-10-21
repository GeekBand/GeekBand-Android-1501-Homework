package com.snail.travellingTrail.common;

import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

public class ResponseList {

	//广场旅程列表
	public static List<Map<String, String>> SquareList;
	
	//个人主页-个人信息
	public static Map<String, String> SelfInfoMap;
	
	//个人主页旅程列表
	public static List<Map<String, String>> SelfTripList;
	
	//个人所有旅程迹点
	public static List<Map<String, String>> SelfTripPointList;
	
	//登录-个人信息
	/*
	 * 个人信息
	 * "$id": "1",
     *	"Us_Info_Us_Id":
     *	"Us_Nickname": null,
     *	"Us_Birthday": null,
     *"Us_Location": null,
     *"Us_Addresss": null,
     *"Us_Sinature": 签名,
     *"Us_Introduce": 简介,
     *"Us_Sex": null,
     *"Us_Avatar": null,
     *"Us_Info_Snapshot": null
     *"Us_Email":null
	 */
	public static Map<String,Object> MyInfoMap ;
	
	/*
	 *     "$id": "1",
    "Client_Info_Id": 1,
    "Client_Info_Type": "安卓",
    "Client_Info_Version": "1.0",
    "Client_Info_Download_Url": 
	 */
	
	public static Map<String,String> ClientInfo;
}
