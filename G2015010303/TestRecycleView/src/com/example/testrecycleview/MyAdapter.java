package com.example.testrecycleview;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
	private LayoutInflater inflater;
	private Context mContext;
	private List<String> mDatas;
	
	
	
	public MyAdapter(Context context,List<String> datas) {
		this.mContext=context;
		this.mDatas=datas;
		inflater=LayoutInflater.from(context);
	}
	
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int pos) {
		holder.tv.setText(mDatas.get(pos));
		
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = inflater.inflate(R.layout.item, arg0,false);
		MyViewHolder myHolder = new MyViewHolder(view);
		return myHolder;
	}

}



class MyViewHolder extends ViewHolder{
	TextView tv;
	ImageView iv;
	
	public MyViewHolder(View itemView) {
		super(itemView);
		tv = (TextView) itemView.findViewById(R.id.tv);
		iv = (ImageView) itemView.findViewById(R.id.iv);
	}
	
	
	
}