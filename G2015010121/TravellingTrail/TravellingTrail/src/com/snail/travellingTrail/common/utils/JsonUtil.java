package com.snail.travellingTrail.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * @author fence
 *
 */

public class JsonUtil {

	/**
	 * 解析单个键值对的Json
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String,Object> parseJson(String jsonString) {
		try {
			JSONObject jsonObject;
			jsonObject  = new JSONObject(jsonString);

			Map<String, Object> map  = getMapFromJson(jsonObject);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Map<String,String> parseJson1(String jsonString) {
		try {
			JSONObject jsonObject;
			jsonObject  = new JSONObject(jsonString);

			Map<String, String> map  = getMapFromJson1(jsonObject);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 解析多个键值对的Json
	 * 
	 * @param jsonString
	 * @param tagName
	 * @return
	 */
	public static List<Map<String, Object>> parseJson(String jsonString, String tagName){
		try {
			JSONArray array ;
			if(null == tagName) {
				array = new JSONArray(jsonString);
			}else {
				array = new JSONObject(jsonString).getJSONArray(tagName);
			}

			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<array.length();i++){
				JSONObject jsonObject = array.getJSONObject(i);
				Map<String, Object> map = getMapFromJson(jsonObject);
				list.add(map);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Map<String, String>> parseJson1(String jsonString, String tagName){
		try {
			JSONArray array ;
			if(null == tagName) {
				array = new JSONArray(jsonString);
			}else {
				array = new JSONObject(jsonString).getJSONArray(tagName);
			}

			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			for(int i=0;i<array.length();i++){
				JSONObject jsonObject = array.getJSONObject(i);
				Map<String, String> map = getMapFromJson1(jsonObject);
				list.add(map);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将Json对象转化为Map对象
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private static Map<String, Object> getMapFromJson(JSONObject jsonObject) throws JSONException{
		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keys();		
		Map<String, Object> map  = new HashMap<String, Object>();
		while (keys.hasNext()) {
			String key = keys.next();
			Object value = jsonObject.getString(key);
			map.put(key, value);
		}
		return map;
	}	
	private static Map<String, String> getMapFromJson1(JSONObject jsonObject) throws JSONException{
		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keys();		
		Map<String, String> map  = new HashMap<String, String>();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = jsonObject.getString(key);
			map.put(key, value);
		}
		return map;
	}

	
	/**
	 * 创建Json对象
	 * 
	 * @param object
	 * @return
	 */
	public static String createJson(Object object){
		try {
			Gson gson = new Gson();
			return gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
