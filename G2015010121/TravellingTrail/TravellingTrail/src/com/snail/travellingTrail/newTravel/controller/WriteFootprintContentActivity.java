package com.snail.travellingTrail.newTravel.controller;

import java.io.File;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.travellingTrail.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WriteFootprintContentActivity extends SherlockActivity implements OnClickListener
{
	public static final String IMAGE_PATH = "image_path";
	public static final String CONTENT_TEXT = "content_text";
	
	
	EditText editText;
	SimpleDraweeView imageView;
	ActionBar actionBar;
	long footprintId;
	String replyTo;
	final int SELECT_PIC = 0, SELECT_PIC_KITKAT = 1;
	String imagePath;
	String contentText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_footprint_content);
		editText = (EditText) findViewById(R.id.act_write_footprint_content_edt);
		imageView = (SimpleDraweeView) findViewById(R.id.act_write_footprint_content_iv);
		imageView.setOnClickListener(this);
		//init();
	}

	private void init()
	{
		footprintId = getIntent().getExtras().getLong("footprintId");
		replyTo = getIntent().getExtras().getString("reply_to");
		
		if(replyTo != null && !replyTo.equals(""))
		{
			editText.setHint("回复 @" + replyTo + " :");
		}
		else {
			editText.setHint("评论");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getSupportMenuInflater().inflate(R.menu.menu_write_comment, menu);
		actionBar = getSupportActionBar();
		
		// 设置左上角返回按钮
		actionBar.setHomeButtonEnabled(true); // 设置左上角的图标是否可以点击
		actionBar.setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
		actionBar.setDisplayShowHomeEnabled(false); // 不显示左上角程序图标
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home: // 点击了左上角Home按钮
			cencelAndReturn();
			break;
		case R.id.menu_submit_comment:
//			DialogTool.showProgressDialog(WriteFootprintContentActivity.this, "发送中..");
			submitComment();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void submitComment()
	{
		contentText = editText.getText().toString();
		
		
		if ( (contentText == null || contentText.equals("")) && 
				(imagePath == null || imagePath.equals("")) )
		{
			Toast.makeText(this, "文字、图片至少写一种哦", Toast.LENGTH_SHORT).show();
		}else {
			saveAndReturn();
		}
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);

		//判断是否4.4系统
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
		{
			startActivityForResult(intent, SELECT_PIC_KITKAT);
		} else
		{
			startActivityForResult(intent, SELECT_PIC);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{	
		
		switch (resultCode)
		{
		case Activity.RESULT_OK:
			Uri uri = data.getData();
			Log.v("MainActivity--->onActivityResult--->uri.getPath()", uri.getPath());
			
			if (requestCode == SELECT_PIC_KITKAT)
			{
				imagePath = getRealFilePath(uri);  //若系统为4.4，则需转换成真实路径地址，否则路径不正确
			}
			else {
				imagePath = uri.getPath();
			}
			
			Log.v("MainActivity--->onActivityResult--->realPath", imagePath);
			
			File file = new File(imagePath);
			
			uri = Uri.fromFile(file);
			
			imageView.setImageURI(uri);
			
			break;

		default:
			break;
		}
	
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	
	/**
	 * 将uri转换成文件真实地址
	 * @param context
	 * @param uri
	 * @return
	 */
	public String getRealFilePath(final Uri uri)
	{
		if (null == uri) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme))
		{
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
		{
			Cursor cursor = getContentResolver().query(uri,
					new String[]
					{ ImageColumns.DATA }, null, null, null);
			if (null != cursor)
			{
				if (cursor.moveToFirst())
				{
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1)
					{
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
	
	
	/**
	 * 保存编写图文内容并返回
	 */
	private void saveAndReturn()
	{
		Intent intent = new Intent();
		intent.putExtra(WriteFootprintContentActivity.IMAGE_PATH, imagePath);
		intent.putExtra(WriteFootprintContentActivity.CONTENT_TEXT, contentText);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	
	/**
	 * 取消编写图文内容并返回
	 */
	private void cencelAndReturn()
	{
		Intent intent = new Intent();
		setResult(Activity.RESULT_CANCELED, intent);
		finish();
	}
	
	
	
	/**
	 * 监听返回键点击
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			cencelAndReturn();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
