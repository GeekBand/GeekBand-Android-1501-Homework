package com.snail.travellingTrail.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;


/*
 * HttpEntity entity =  response.getEntity();
   InputStream is = entity.getContent();
 * */
public class HttpUtil {
	
	private static HttpGet getHttpGet(String uri){
		HttpGet request = new HttpGet(uri);
		return request;
	}
	
	private  HttpPost getHttpPost(String uri){
		HttpPost request = new HttpPost(uri);
		return request;
	}
	
	private  HttpResponse getHttpResponse(HttpGet request) throws Exception{
		HttpClient  client  = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 6*1000);
		HttpConnectionParams.setSoTimeout(params, 6*1000);
		HttpResponse response = client.execute(request);
		Log.i("MAIN", response.getStatusLine().getStatusCode()+"FAN");
		return response;
	}
	
	
	private  HttpResponse getHttpResponse(HttpPost request) throws Exception{
		HttpClient  client  = new DefaultHttpClient();
//		HttpParams params = client.getParams();
//		HttpConnectionParams.setConnectionTimeout(params, 6*1000);
//		HttpConnectionParams.setSoTimeout(params, 6*1000);

		HttpResponse response = client.execute(request);
		
		return response;
	}
	
	//post��ʽ��ȡ�ַ�
	public  String queryStringForPost(String uri){
		HttpPost request = getHttpPost(uri);
		String result = null;
		try {
			HttpResponse response  = getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "error";
			return result;
		}
		return result;
	}
	
	//get��ʽ��ȡ�ַ�
	public  String queryStringForGet(String uri){
		HttpGet request = getHttpGet(uri);
		String result = null;
		try {
			HttpResponse response  = getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (Exception e) {
		
			
			e.printStackTrace();			
			result = "error";
			return result;
		}
		return result;
	}
	
	//post��ʽ�ύ���
	public  String queryStringForPost(String uri,HashMap<String, Object> params){
		String result = null;
		HttpPost request = getHttpPost(uri);
		request.addHeader("Content-Type", "application/json");
		Gson gson = new Gson();
		String jsonString = gson.toJson(params);
		try {
	        StringEntity jsonEntity =new StringEntity(jsonString,"UTF-8");
	        Log.i("jsonString", jsonString);
			request.setEntity(jsonEntity);
			HttpResponse response = getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				Log.i("response", "200");
				return "200";
			}else{
				Log.i("getStatusCode", response.getStatusLine().getStatusCode()+"");
				result = "error";
			}
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			Log.v("queryStringForPost", "error"+e.toString());
			result = "error";
			return result;
		}
		return result;
		
	} 
	
	
	//��get��ʽ��ȡ�ַ������룬�ø÷���
	public String  getStringByGet(String url){
		HttpGet request = getHttpGet(url);
		HttpResponse response;
		InputStream is=null;
		try {
			response = getHttpResponse(request);
			StringBuffer sb  = new StringBuffer();
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			//�Ƕ�ȡҪ�ı����Դ��Դ�ĸ�ʽ��GB2312�ģ���Դ��ʽ��������Ȼ���ٶ�Դ��ת������Ҫ�ı������
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String data = "";
			while((data=br.readLine())!=null){
				sb.append(data);
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String result = "error"; 
			return result;
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	

}
