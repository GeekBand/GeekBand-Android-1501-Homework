package com.snail.travellingTrail.square;

import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.views.CircleImageView;
import com.snail.travellingTrail.travelNotes.controller.TravelNotesActivity;
import com.snail.travellingTrail.user.controller.MyPageActivity;

public class SquareAdapter extends BaseAdapter{

	LayoutInflater mLayoutInflater;
	Context context;
	List<Map<String,String>> list;
	View view;
	FinalBitmap mFinalBitmap;
	FinalBitmap mAvatarFinalBitmap;
	
	public SquareAdapter(Context context,List<Map<String,String>> list,FinalBitmap mFinalBitmap,FinalBitmap mAvatarFinalBitmap){
		this.context = context;
		this.list = list;
		this.mFinalBitmap = mFinalBitmap;
		this.mAvatarFinalBitmap = mAvatarFinalBitmap;
		this.mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SimpleDraweeView tripimage;
		CircleImageView avatar;
		TextView nicknameTv,themeTv,timeTv;
		CheckBox likeCb,commentCb;
//		LinearLayout mLinearLayout;
		convertView = mLayoutInflater.inflate(R.layout.listitem_square, null);
		tripimage = (SimpleDraweeView)convertView.findViewById(R.id.listitem_square_iv_tripimage);
		avatar = (CircleImageView)convertView.findViewById(R.id.listitem_square_iv_avatar);
		nicknameTv = (TextView)convertView.findViewById(R.id.listitem_square_tv_nickname);
		themeTv = (TextView)convertView.findViewById(R.id.listitem_square_tv_theme);
		timeTv = (TextView)convertView.findViewById(R.id.listitem_square_tv_time);
		likeCb = (CheckBox)convertView.findViewById(R.id.listitem_square_cb_good);
		commentCb = (CheckBox)convertView.findViewById(R.id.listitem_square_cb_comment);
		likeCb.setText(list.get(position).get("Trvl_Like_Count"));
		commentCb.setText(list.get(position).get("Trvl_Comment_Count"));
		nicknameTv.setText(list.get(position).get("Us_Nickname"));
		themeTv.setText(list.get(position).get("Trvl_Name"));
		themeTv.setSelected(true);
		timeTv.setText(list.get(position).get("Trvl_Time_Start").split("T")[0]);
//		mFinalBitmap.display(tripimage,list.get(position).get("Trvl_Cover_Photo"));//
		
		if (list.get(position).get("Trvl_Cover_Photo") != null &&
				!list.get(position).get("Trvl_Cover_Photo").equals("") && 
				!list.get(position).get("Trvl_Cover_Photo").equals("null"))
		{			
			tripimage.setImageURI(Uri.parse(list.get(position).get("Trvl_Cover_Photo")));	
		}
		
		mAvatarFinalBitmap.display(avatar, list.get(position).get("Us_Avatar"));//
		Log.i("tag", list.get(position).get("Us_Avatar"));

		LayoutParams laParams=(LayoutParams)tripimage.getLayoutParams();
		laParams.height =(int)(((PhoneInfo.getScreenWidth(this.context))/16)*9);
		tripimage.setBackgroundResource(R.drawable.share_corners);
		tripimage.setOnClickListener(new ImageClickListener(position));
		avatar.setOnClickListener(new ImageClickListener(position));
		return convertView;
	}
	
	public void setData(List<Map<String,String>> list)
	{
		this.list = list;
	}
	
	class ImageClickListener implements OnClickListener{
		Intent intent = new Intent();
		long position;
		public ImageClickListener(long position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.listitem_square_iv_tripimage:
				intent.putExtra("travelId", list.get((int)position).get("Trvl_Id"));
				intent.setClass(context, TravelNotesActivity.class);
				((Activity)context).startActivity(intent);
				break;
			case R.id.listitem_square_iv_avatar:
				intent.putExtra("Trvl_Us_Id", Long.valueOf(list.get((int)position).get("Trvl_Us_Id")));
				intent.setClass(context, MyPageActivity.class);
				((Activity)context).startActivity(intent);
				break;
			default:
				break;
			}
			
		}
		
	}
}
