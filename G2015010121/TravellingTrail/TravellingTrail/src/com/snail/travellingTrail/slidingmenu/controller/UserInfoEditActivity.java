package com.snail.travellingTrail.slidingmenu.controller;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.ResponseList;
import com.snail.travellingTrail.common.utils.CustomMenu;
import com.snail.travellingTrail.common.views.CircleImageView;

public class UserInfoEditActivity extends SherlockActivity{
    private PopupWindow pop;
    RelativeLayout sexRlyt,birthdayRlyt,addressRlyt,nicknameRlyt,sinatureRlyt,introduceRlyt;
    private TextView sinatureTv,nickNameTv,sexTv,brithdayTv,addressTv,introduceTv,accountTv;
    private final static int SEX = 0;
    private final static int BIRTHDAY = 1;
    private final static int ADDRESS = 2;
	Map<String,String> map;
	CircleImageView mCircleImageView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_slidingmenu_modify_info);  
		findid();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
	}
	private void findid() {
		sexRlyt = (RelativeLayout)findViewById(R.id.act_slidingmenu_rlyt_sex);
		birthdayRlyt = (RelativeLayout)findViewById(R.id.act_slidingmenu_rlyt_birthday);
		addressRlyt = (RelativeLayout)findViewById(R.id.act_slidingmenu_rlyt_address);
		nicknameRlyt = (RelativeLayout)findViewById(R.id.act_slidingmenu_rlyt_nickname);
		sinatureRlyt = (RelativeLayout)findViewById(R.id.act_slidingmenu_rlyt_signature);
		introduceRlyt = (RelativeLayout)findViewById(R.id.act_slidingmenu_rlyt_introduce);
		sinatureTv = (TextView)findViewById(R.id.act_slidingmenu_tv_signature_value);
		nickNameTv = (TextView)findViewById(R.id.act_slidingmenu_tv_nickname_value);
		sexTv = (TextView)findViewById(R.id.act_slidingmenu_tv_sex_value);
		brithdayTv = (TextView)findViewById(R.id.act_slidingmenu_tv_birthday_value);
		addressTv = (TextView)findViewById(R.id.act_slidingmenu_tv_address_value);
		introduceTv = (TextView)findViewById(R.id.act_slidingmenu_tv_introduce_value);
		accountTv = (TextView)findViewById(R.id.act_slidingmenu_tv_email_value);
		mCircleImageView = (CircleImageView)findViewById(R.id.act_slidingmenu_modify_info_avatar);
		setListener();
	}
	private void setListener() {
		sexRlyt.setOnClickListener(new LayoutListener());
		birthdayRlyt.setOnClickListener(new LayoutListener());
		addressRlyt.setOnClickListener(new LayoutListener());
		nicknameRlyt.setOnClickListener(new LayoutListener());
		sinatureRlyt.setOnClickListener(new LayoutListener());
		introduceRlyt.setOnClickListener(new LayoutListener());
		mCircleImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoEditActivity.this, UploadImageActivity.class);
				startActivity(intent);
			}
		});
		init();
	}

	

	@Override
	protected void onResume() {
		findid();
		super.onResume();
	}
	private void init() {
		if(ResponseList.MyInfoMap.get("Us_Sinature")!=null){
			sinatureTv.setText(ResponseList.MyInfoMap.get("Us_Sinature").toString().trim());
		}
		if(ResponseList.MyInfoMap.get("Us_Nickname")!=null){
			nickNameTv.setText(ResponseList.MyInfoMap.get("Us_Nickname").toString().trim());
		}
		if(!ResponseList.MyInfoMap.get("Us_Sex").equals(null)&&!ResponseList.MyInfoMap.get("Us_Sex").equals("null")&&!ResponseList.MyInfoMap.get("Us_Sex").equals("")){
			switch (Integer.valueOf(ResponseList.MyInfoMap.get("Us_Sex").toString().trim())) {
			case 0:
				sexTv.setText("女");
				break;
			case 1:
				sexTv.setText("男");
				break;

			default:
				break;
			}
		}
		if(ResponseList.MyInfoMap.get("Us_Birthday")!=null){
			brithdayTv.setText(ResponseList.MyInfoMap.get("Us_Birthday").toString().trim().split("T")[0]);
		}
		if(ResponseList.MyInfoMap.get("Us_Addresss")!=null){
			addressTv.setText(ResponseList.MyInfoMap.get("Us_Addresss").toString().trim());
		}
		if(ResponseList.MyInfoMap.get("Us_Introduce")!=null){
			introduceTv.setText(ResponseList.MyInfoMap.get("Us_Introduce").toString().trim());
		}
		if(ResponseList.MyInfoMap.get("Us_Email")!=null){
			accountTv.setText(ResponseList.MyInfoMap.get("Us_Email").toString().trim());
		}
		
	}

	class LayoutListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			CustomMenu cm = null;
			switch (v.getId()) {
			case R.id.act_slidingmenu_rlyt_sex:
				cm = new CustomMenu(UserInfoEditActivity.this);
		        pop = cm.getMenu(touchListener, keyListener,SEX);
		        showAmissPop();
		        pop.setOnDismissListener(new onDismissListener());
				break;
			case R.id.act_slidingmenu_rlyt_birthday:
				cm = new CustomMenu(UserInfoEditActivity.this);
		        pop = cm.getMenu(touchListener, keyListener,BIRTHDAY);
		        showAmissPop();
		        pop.setOnDismissListener(new onDismissListener());
				break;
			case R.id.act_slidingmenu_rlyt_address:
				cm = new CustomMenu(UserInfoEditActivity.this);
		        pop = cm.getMenu(touchListener, keyListener,ADDRESS);
		        showAmissPop();
		        pop.setOnDismissListener(new onDismissListener());
				break;
			case R.id.act_slidingmenu_rlyt_signature:
				Intent intent1 = new Intent();
				Bundle mBundle1 = new Bundle();
				mBundle1.putInt("editType", 0);
				intent1.putExtras(mBundle1);
				intent1.setClass(UserInfoEditActivity.this, EditSinatureOrNickName.class);
				startActivity(intent1);
				break;
			case R.id.act_slidingmenu_rlyt_nickname:
				Intent intent = new Intent();
				Bundle mBundle = new Bundle();
				mBundle.putInt("editType", 1);
				intent.putExtras(mBundle);
				intent.setClass(UserInfoEditActivity.this, EditSinatureOrNickName.class);
				startActivity(intent);
				break;
			case R.id.act_slidingmenu_rlyt_introduce:
				Intent intent2 = new Intent();
				Bundle mBundle2 = new Bundle();
				mBundle2.putInt("editType", 2);
				intent2.putExtras(mBundle2);
				intent2.setClass(UserInfoEditActivity.this, EditSinatureOrNickName.class);
				startActivity(intent2);
				break;
			default:
				break;
			}
			cm = null;
		}
		private void showAmissPop(){
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
                pop.showAtLocation(findViewById(R.id.act_slidingmenu_tv_introduce),Gravity.BOTTOM, 0, 0);
 
            }
		}
	}
	
	class onDismissListener implements OnDismissListener{

		@Override
		public void onDismiss() {
			init();
			Log.i("onDismiss", "onDismiss");
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportMenuInflater().inflate(R.menu.menu_userinfoedit, menu);
		return true;
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
 
     /**
     * 处理键盘事件
     */
 
        private OnTouchListener touchListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pop.dismiss();
                }

            	Log.e("touchListener", "touchListener");
                return false;
            }
        };
        private OnKeyListener keyListener = new OnKeyListener() {
 
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode== KeyEvent.KEYCODE_BACK ) {
                    pop.dismiss();
                    return true;
                }
                return false;
            }
        };
}
