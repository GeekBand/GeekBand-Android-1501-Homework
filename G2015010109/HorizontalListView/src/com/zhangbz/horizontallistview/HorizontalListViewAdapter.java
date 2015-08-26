package com.zhangbz.horizontallistview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalListViewAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Integer> list;
	public HorizontalListViewAdapter(Context con, List<Integer> list) {
		mInflater = LayoutInflater.from(con);
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	private ViewHolder viewHolder = new ViewHolder();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.horizontallistviewitem, null);
			viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		


		viewHolder.pic.setImageResource(list.get(position));;
		return convertView;
	}
	
	private static class ViewHolder {
		private ImageView pic;
	}

}
