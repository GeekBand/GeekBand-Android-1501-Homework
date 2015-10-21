package com.jankey;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class NoScrollingListView extends LinearLayout
{
	private BaseAdapter adapter;
	private OnItemClickListener onItemClickListener;

	
	/**
	 * 通过 Java代码  实例化
	 * @param context
	 */
	public NoScrollingListView(Context context)
	{
		super(context);
		//设置LinearLayoutForListView为垂直布局，否者默认为水平布局，容易疏忽导致子项显示不全
		NoScrollingListView.this.setOrientation(LinearLayout.VERTICAL);
	}

	
	/**
	 * 此构造函数可以允许我们通过 XML的方式注册 控件
	 * @param context
	 * @param attrs
	 */
	public NoScrollingListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		NoScrollingListView.this.setOrientation(LinearLayout.VERTICAL);
	}
	
	

	/**
	 * 设置适配器
	 * 
	 * @param adpater
	 */
	public void setAdapter(BaseAdapter adpater)
	{
		this.adapter = adpater;
		bindLinearLayout();
	}

	/**
	 * 获取适配器Adapter
	 * 
	 * @return adapter
	 */
	public BaseAdapter getAdpater()
	{
		return adapter;
	}

	
	
	/**
	 * 绑定布局:将每个子项的视图view添加进此线性布局LinearLayout中
	 */
	public void bindLinearLayout()
	{
		int count = adapter.getCount();
		for (int i = 0; i < count; i++)
		{
			View v = adapter.getView(i, null, null);

			if (i != count - 1)
			{	//添加每项item之间的分割线
				 v = setDivider(v);
			}
			addView(v, i);
		}
		setItemClickListener();
		Log.v("countTAG", "" + count);
	}

	/**
	 * 添加每项item之间的分割线
	 * 
	 * @param view
	 * @return
	 */
	public View setDivider(View view)
	{
		//分割线view
		View lineView = new View(view.getContext());

		// 将数据从dip(即dp)转换到px，第一参数为数据原单位（此为DIP），第二参数为要转换的数据值
		float fPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 0.5, view.getResources().getDisplayMetrics());
		int iPx = Math.round(fPx);

		LayoutParams layoutParams = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, iPx);
		lineView.setLayoutParams(layoutParams);
		lineView.setBackgroundColor(view.getSolidColor());

		LinearLayout ly = new LinearLayout(view.getContext());
		ly.setOrientation(LinearLayout.VERTICAL);

		ly.addView(view);
		ly.addView(lineView);

		return ly;
	}

	
	/**
	 * 设置点击子项事件监听对象
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		this.onItemClickListener = onItemClickListener;
		setItemClickListener();
	}
	
	/**
	 * 获取点击子项事件监听对象
	 * @return
	 */
	public OnItemClickListener getOnItemClickListener()
	{
		return onItemClickListener;
	}
	
	
	/**
	 * 设置子项点击事件
	 */
	private void setItemClickListener()
	{
		if (adapter != null)
		{
			for (int i = 0; i < adapter.getCount(); i++)
			{
				View view = NoScrollingListView.this.getChildAt(i);
				if (onItemClickListener != null)
				{
					//设置子项点击事件
					view.setOnClickListener(new ItemClickListener(view, i, adapter.getItemId(i)));
				}
			}
		}
	}
	
	class ItemClickListener implements OnClickListener
	{
		View view;
		int position;
		long id;
		
		public ItemClickListener(View view, int position, long id)
		{
			this.view = view;
			this.position = position;
			this.id = id;
		}

		@Override
		public void onClick(View v)
		{
			//将子项视图的点击事件转发到整个listview的OnItemClick事件中
			//此方法有局限性，第一个参数 AdapterView<?> parent（即当前listView的视图）没传入onItemClick()中
			onItemClickListener.onItemClick(null, view, position, id);
		}
	}
}