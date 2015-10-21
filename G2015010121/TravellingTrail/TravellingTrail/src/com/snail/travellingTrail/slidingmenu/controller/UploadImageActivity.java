package com.snail.travellingTrail.slidingmenu.controller;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.ResponseList;

public class UploadImageActivity extends SherlockActivity {

	private ImageView iv_image;

	private Button bt_camera;

	private Bitmap photo;

	private File file;

	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_image);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		iv_image = (ImageView) findViewById(R.id.upload_image_avatar);
		bt_camera = (Button) findViewById(R.id.upload_image_bt);

		bt_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				destoryImage();
				String state = Environment.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					file = new File(saveDir, "tempqq.jpg");
					file.delete();
					if (!file.exists()) {
						try {
							file.createNewFile();
							Log.i("file", file.getAbsolutePath());
						} catch (IOException e) {
							e.printStackTrace();
							Toast.makeText(UploadImageActivity.this, "照片创建失败!",
									Toast.LENGTH_LONG).show();
							return;
						}
					}
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent, 1);
				} else {
					Toast.makeText(UploadImageActivity.this, "sdcard无效或没有插入!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			if (file != null && file.exists()) {
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 2;
				photo = BitmapFactory.decodeFile(file.getPath(), option);
				iv_image.setImageBitmap(photo);
				new Thread(new Runnable() {

					@Override

					public void run() {

						try {
							String a = NetUtil.doPost("http://192.168.1.188:17777/api/users/avatar/"+ResponseList.MyInfoMap.get("Us_Info_Us_Id"), null,file);
							Log.e("onActivityResult", a+"?");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					}).start();
			}
		}
	}

	@Override
	protected void onDestroy() {
		destoryImage();
		super.onDestroy();
	}

	private void destoryImage() {
		if (photo != null) {
			photo.recycle();
			photo = null;
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
