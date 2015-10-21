package com.snail.travellingTrail.slidingmenu.controller;

import java.io.FileInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.ResponseList;

public class UploadImage extends Activity{
	Button uploadBt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.upload_image);
		uploadBt = (Button)findViewById(R.id.upload_image_bt);
		uploadBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				AjaxParams mAjaxParams = new AjaxParams();
				new Thread(new Runnable() {

					@Override

					public void run() {
						Looper.prepare();
						httpUpload();
				        Looper.loop();
					}

					}).start();
//				mAjaxParams.
//				mFinalHttp.post("htttp://192.168.1.6:17777/api/users/avatar/53", mAjaxParams, new AjaxCallBack<Object>() {
//
//					@Override
//					public void onSuccess(Object t) {
//						// TODO Auto-generated method stub
//						super.onSuccess(t);
//					}
//
//					@Override
//					public void onFailure(Throwable t, int errorNo,
//							String strMsg) {
//						// TODO Auto-generated method stub
//						super.onFailure(t, errorNo, strMsg);
//					}
//					
//				});
				
			}
		});
		super.onCreate(savedInstanceState);
	}
private void httpUpload() {
		
    	//定义HttpClient对象
		HttpClient client = new DefaultHttpClient();
		//获得HttpPost对象
		HttpPost post = new HttpPost("http://192.168.1.188:17777/api/users/avatar/"+ResponseList.MyInfoMap.get("Us_Info_Us_Id"));
		post.addHeader("charset", HTTP.UTF_8);  
		//实例化
		MultipartEntity me = new MultipartEntity();
		
		try {
			
			me.addPart("content",new StringBody("12cccafasdfasdf"));
			me.addPart("title",new StringBody("csdnliwei"));
			me.addPart("local",new StringBody("beijing"));
			//设置流文件
//			me.addPart("file", new InputStreamBody(new FileInputStream( Environment.getExternalStorageDirectory()
//					.getPath() + "/temp_image/"+"temp.jpg"), "image/pjpeg", "temp.jpg"));
			AssetManager am = getResources().getAssets();  
			me.addPart("file", new InputStreamBody(am.open("jia.jpg"), "image/pjpeg", "jia.jpg"));
			post.setEntity(me);
			//获得响应消息
			HttpResponse resp = client.execute(post);
			
			if(resp.getStatusLine().getStatusCode()==200){
				String result = "";
				result = EntityUtils.toString(resp.getEntity());
				Toast.makeText(this, "文件上传文成！", Toast.LENGTH_SHORT).show();
				Log.e(">>>>>>>>>>>>>>>>>>>>", result+"");
				
			}
			Log.e(">>>>>>>>>>>>>>>>>>>>", resp.getStatusLine().getStatusCode()+"");
		} catch (Exception e) {
			Log.e(">>>>>>>>>>>>>>>>>>>>", e.toString()+"");
			e.printStackTrace();
		}
    	
	}
}
