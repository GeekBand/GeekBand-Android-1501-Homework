package com.zhangbz.horizontallistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class HorizontalListViewActivity extends Activity implements OnGestureListener{

	private HorizontalListViewAdapter horizontalListViewAdapter;
	private HorizontalListView horizontalListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horizontallistview);
		horizontalListView = (HorizontalListView)findViewById(R.id.horizontallistview);
		List<Integer> list = new ArrayList();
		for (int i = 0; i < 20; i++) {
			list.add(R.drawable.ic_launcher);
		}
		horizontalListViewAdapter = new HorizontalListViewAdapter(this, list);
		horizontalListViewAdapter.notifyDataSetChanged();
		horizontalListView.setAdapter(horizontalListViewAdapter);
		
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
