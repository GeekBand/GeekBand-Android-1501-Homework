package com.geekband.luminous.homework.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geekband.luminous.homework.R;

import java.util.List;

/**
 * 一个adapter，会返回图片
 * Created by hisashieki on 15/8/14.
 */
public class MyGridAdapter extends BaseAdapter {
    public static final String TAG = "MyGridAdapter";
    Context context;
    List<Integer> picIds;
    int mWidth;

    public MyGridAdapter(Context context, List<Integer> pictures) {
        this.context = context;
        picIds = pictures;
        mWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public int getCount() {
        return picIds.size();
    }

    @Override
    public Object getItem(int position) {
        return picIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(context, R.layout.item_single_picture, null);
        }
        ImageView ivPic = (ImageView) v.findViewById(R.id.iv_picture);
        ivPic.setLayoutParams(new android.widget.AbsListView.LayoutParams(mWidth / 4, mWidth / 4));
        ivPic.setImageResource(picIds.get(position));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "item onClick " + position);
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "item onLongClick " + position);
                return true;
            }
        });
        return v;
    }
}
