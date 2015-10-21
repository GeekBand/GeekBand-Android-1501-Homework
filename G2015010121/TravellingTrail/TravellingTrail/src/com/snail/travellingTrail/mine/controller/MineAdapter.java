package com.snail.travellingTrail.mine.controller;

import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.views.CircleImageView;
import com.snail.travellingTrail.travelNotes.controller.TravelNotesActivity;

public class MineAdapter extends BaseAdapter
{

	public MineAdapter(){
		
	}
		LayoutInflater mLayoutInflater;
		Context context;
		List<Map<String,String>> list;
		View view;
		FinalBitmap mFinalBitmap;
		public MineAdapter(Context context,List<Map<String,String>> list,FinalBitmap mFinalBitmap){
			this.context = context;
			this.list = list;
			this.mFinalBitmap = mFinalBitmap;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ImageView tripimage;
			TextView themeTv,timeTv,contentTv;
			CheckBox likeCb,commentCb;
			convertView = mLayoutInflater.inflate(R.layout.listitem_mine_trip_all, null);
			tripimage = (ImageView)convertView.findViewById(R.id.listitem_mine_trip_all_iv_tripimage);
			themeTv = (TextView)convertView.findViewById(R.id.listitem_mine_trip_all_tv_theme);
			timeTv = (TextView)convertView.findViewById(R.id.listitem_mine_trip_all_tv_time);
			likeCb = (CheckBox)convertView.findViewById(R.id.listitem_mine_trip_all_cb_good);
			commentCb = (CheckBox)convertView.findViewById(R.id.listitem_mine_trip_all_cb_comment);
			themeTv.setText(list.get(position).get("Trvl_Name"));
			themeTv.setSelected(true);
			timeTv.setText(list.get(position).get("Trvl_Time_Start").split("T")[0]);
			contentTv = (TextView)convertView.findViewById(R.id.listitem_mine_trip_all_tv_content);
			contentTv.setText(list.get(position).get("Trvl_Content"));
			likeCb.setText(list.get(position).get("Trvl_Like_Count"));
			commentCb.setText(list.get(position).get("Trvl_Comment_Count"));
			
			if(list.get(position).get("Trvl_Cover_Photo") != null 
					&& !list.get(position).get("Trvl_Cover_Photo").equals("")
					&& !list.get(position).get("Trvl_Cover_Photo").endsWith("null"))
			{				
				mFinalBitmap.display(tripimage,list.get(position).get("Trvl_Cover_Photo"));//
			}
			
			LayoutParams laParams=(LayoutParams)tripimage.getLayoutParams();
			laParams.height =(int)(((PhoneInfo.getScreenWidth(this.context))/5)*3);
			Log.i("tag", String.valueOf(laParams.height));
			
			convertView.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent();
					intent.putExtra("travelId", list.get((int)position).get("Trvl_Id"));
					intent.setClass(context, TravelNotesActivity.class);
					context.startActivity(intent);
				}
			});
			
			return convertView;
		}
		
		public void setData(List<Map<String,String>> list)
		{
			this.list = list;
		}

}
