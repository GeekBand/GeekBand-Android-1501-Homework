package com.snail.travellingTrail.socialIntercourse.controller;

import java.util.ArrayList;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.object.Fan;
import com.snail.travellingTrail.user.controller.MyPageActivity;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FansListAdapter extends BaseAdapter implements OnClickListener
{

	ArrayList<Fan> datas;
	Context context;
	LayoutInflater layoutInflater;
	Fan data;
	View itemView;
	FinalBitmap finalBitmap;
	
	
	public FansListAdapter(ArrayList<Fan> datas, Context context)
	{
		this.datas = datas;
		this.context = context;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		finalBitmap = FinalBitmap.create(context);//初始化FinalBitmap模块
		finalBitmap.configLoadingImage(R.drawable.img_default_avatar);
	}

	
	
	public ArrayList<Fan> getDatas()
	{
		return datas;
	}



	public void setDatas(ArrayList<Fan> datas)
	{
		this.datas = datas;
	}



	@Override
	public int getCount()
	{
//		Log.v("getCount", "comments.size:" + comments.size());
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position)
	{
		Log.v("getItem", "position:" + position);
		return datas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Log.v("getView", "position:" + position);
		data = (Fan) getItem(position);
		
		SimpleDraweeView avatarImageView;
		TextView nickNameTextView;
		TextView timeTextView;
		
		if (convertView != null)
		{
			itemView = convertView;
		}
		else {
			itemView = layoutInflater.inflate(R.layout.listitem_fans_list, null);
		}

		avatarImageView = (SimpleDraweeView)itemView.findViewById(R.id.listitem_fans_list_iv_avatar);
		nickNameTextView = (TextView)itemView.findViewById(R.id.listitem_fans_list_tv_nickname);
		timeTextView = (TextView)itemView.findViewById(R.id.listitem_fans_list_tv_add_time);
		
//		finalBitmap.display(avatarImageView, data.getFtprnt_Cmm_Us_Avatar());
		if (data.getUs_Avatar() != null && !data.getUs_Avatar().equals("") 
				&& !data.getUs_Avatar().equals("null") )
		{
			avatarImageView.setImageURI(Uri.parse(data.getUs_Avatar()));
		}
		
		if (data.getUsFans_Fans_Name() != null && !data.getUsFans_Fans_Name().equals("") 
				&& !data.getUsFans_Fans_Name().equals("null") )
		{
			nickNameTextView.setText(data.getUsFans_Fans_Name());
		}
		
		timeTextView.setText("关注时间:" + data.getUs_Fans_Add_Time());
		
		itemView.setTag(Integer.valueOf(position));
		itemView.setOnClickListener(this);
		
		return itemView;
	}

	@Override
	public void onClick(final View v)
	{
		Intent intent = new Intent(context, MyPageActivity.class);
		long fanId = datas.get( ((Integer)v.getTag()).intValue()).getUs_Fans_Fans_Id();
		intent.putExtra("Trvl_Us_Id", fanId);
		context.startActivity(intent);
	}

}
