package com.snail.travellingTrail.newTravel.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.TravellingTrailApplication;
import com.snail.travellingTrail.common.utils.BitmapUtil;
import com.snail.travellingTrail.common.utils.UnitConversion;
import com.snail.travellingTrail.newTravel.model.ImageData;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView.OnEditorActionListener;

public class ImageListAdapter extends BaseAdapter implements OnClickListener,
	OnFocusChangeListener, OnEditorActionListener, OnScrollListener
{
	
	ImageData data;
	List<ImageData> dataList;
	LayoutInflater layoutInflater;
	Context context;
	View currentEditing;  //当前编辑中的输入框
	LruCache<Long, Bitmap> bitmapCache;
	ExecutorService executorService;
	

	public ImageListAdapter(List<ImageData> dataList, Context context)
	{
		super();
		this.dataList = dataList;
		this.context = context;
		
		layoutInflater = LayoutInflater.from(context);
		executorService = Executors.newFixedThreadPool(5);
		final int memoryClass = 
				((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		final int memorySize = 1024*1024*memoryClass / 8;
		bitmapCache = new LruCache<Long, Bitmap>(memorySize){

			@Override
			protected int sizeOf(Long key, Bitmap value)
			{
				return value.getByteCount();
			}
			
		};
	}


	public void setDataList(List<ImageData> dataList)
	{
		this.dataList = dataList;
	}



	@Override
	public int getCount()
	{
		return dataList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return dataList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		data = dataList.get(position);
		ViewHolder holder;
		
		if (convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.listitem_new_footprint_content_list, null);

			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.listitem_new_footprint_content_list_iv_image);
			holder.name = (TextView) convertView.findViewById(R.id.listitem_image_manager_tv_name);
			holder.remove = (ImageView) convertView.findViewById(R.id.listitem_new_footprint_content_list_iv_remove);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setViewPositionTag(holder.image, position);
		setViewPositionTag(holder.name, position);
		setViewPositionTag(holder.remove, position);
		
		setImage(holder.image);
		holder.name.setText(data.getName());

		holder.image.setOnClickListener(this);
		holder.name.setOnClickListener(this);
		holder.remove.setOnClickListener(this);
		
		return convertView;
	}
	
	Handler handler = new Handler(new Callback()
	{
		
		@Override
		public boolean handleMessage(Message msg)
		{
			ImageView imageView = (ImageView)msg.obj;
			int position = getViewPositionTag(imageView);
			long id = dataList.get(position).getId();
			imageView.setImageBitmap(bitmapCache.get(Long.valueOf(id)));
			return false;
		}
	});
	
	Thread thread;
	
	private void setImage(final View view)
	{	
		final int position = getViewPositionTag(view);
		final long id = dataList.get(position).getId();
		
		if (dataList.get(position).getPath() != null
				&& !dataList.get(position).getPath().equals("")) //非纯文字心情（有图片）
		{

			if (bitmapCache.get(Long.valueOf(id)) == null) // 缓存中没有该图的bitmap对象
			{
				// 线程池里执行
				executorService.submit(new Runnable()
				{

					@Override
					public void run()
					{
						Message msg = new Message();
						bitmapCache.put(Long.valueOf(id), BitmapUtil.getBitmap(
								dataList.get(position).getPath(), 5));
						msg.obj = view;
						handler.sendMessage(msg);
					}
				});

			} else
			{
				Message msg = new Message();
				msg.obj = view;
				handler.sendMessage(msg);
			}
		}
		
		
	}
	
	
	private void setViewPositionTag(View view, int position)
	{
		view.setTag(Integer.valueOf(position));
	}
	
	private int getViewPositionTag(View view)
	{
		return ((Integer)view.getTag()).intValue();
	}
	
	
	static class ViewHolder
	{
		ImageView image;
		TextView name;
		ImageView remove;
	}



	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.listitem_new_footprint_content_list_iv_image:
			Intent intent = new Intent(context, BigImageActivity.class);
			intent.putExtra("ImageData", dataList.get(getViewPositionTag(view)));
			context.startActivity(intent);
			break;
		case R.id.listitem_image_manager_tv_name:
			addEditText(view);
			break;
		case R.id.listitem_new_footprint_content_list_iv_remove:
			showRemoveDialog(view);
			break;
		default:
			break;
		}
	}
	
	
	private void showRemoveDialog(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		AlertDialog dialog = builder.create();
		dialog.setTitle("提示");
		dialog.setMessage("确定删除？");
		dialog.setButton(Dialog.BUTTON_POSITIVE, "确定",
				new OnRemoveListener(getViewPositionTag(view)));
		dialog.setButton(Dialog.BUTTON_NEGATIVE, "取消", 
				new OnRemoveListener(getViewPositionTag(view)));
		dialog.show();
	}
	
	class OnRemoveListener implements android.content.DialogInterface.OnClickListener
	{
		int position; //点击的item位置

		public OnRemoveListener(int position)
		{
			super();
			this.position = position;
		}

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			removeItem(position);
		}
	}
	
	private void removeItem(int position)
	{
		if (TravellingTrailApplication.getDbManager().removeImage(dataList.get(position)))
		{
			bitmapCache.remove(getItemId(position));
			dataList.remove(position);
			notifyDataSetChanged();
			Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();
		}
	}
	
	

	/**
	 * 加入编辑框，隐藏文本框
	 * @param view
	 */
	private void addEditText(View view)
	{	
		//先把尚未移除的输入框移除了
		if (currentEditing != null) removeEditText(currentEditing);
		
		EditText editText = new EditText(context);
		editText.setLayoutParams(view.getLayoutParams());
		RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
		view.setVisibility(View.GONE);
		relativeLayout.addView(editText);
		String name = (String) ((TextView)view).getText();
		
		editText.setPadding(UnitConversion.dip2px(context, 9), 0,
				UnitConversion.dip2px(context, 9), 0);
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.setSingleLine();
		editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		editText.setText(name);
		editText.selectAll();   //全选
		editText.requestFocus();  //获取焦点
		//下面两句为弹出软键盘↓
		InputMethodManager inputManager =
				(InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
		
		setViewPositionTag(editText, getViewPositionTag(view));
		editText.setOnFocusChangeListener(this);
		editText.setOnEditorActionListener(this);
		
		currentEditing = editText;
	}


	@Override
	public void onFocusChange(View view, boolean hasFocus)
	{
		if (!hasFocus) removeEditText(view);
	}

	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	{
		switch (actionId)
		{
		case EditorInfo.IME_ACTION_DONE: //输入确定
			removeEditText(currentEditing);
			break;

		default:
			break;
		}
		return false;
	}
	
	
	/**
	 * 移除输入框
	 * @param view
	 */
	private void removeEditText(final View view)
	{
		if (currentEditing == null)
		{
			return;
		}
		
		final RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
		String newName = ((EditText)view).getText().toString();
		dataList.get(getViewPositionTag(view)).setName(newName);  //获取Tag中的position值来修改list内数据
		TravellingTrailApplication.getDbManager().reivseImageName(dataList.get(getViewPositionTag(view)));
		
		//以下需使用handler才不会报空指针错误
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{	
				relativeLayout.removeView(view);
			}
		});
		currentEditing = null;
		TextView nameTextView = (TextView) relativeLayout.findViewById(R.id.listitem_image_manager_tv_name);
		nameTextView.setText(newName);
		nameTextView.setVisibility(View.VISIBLE);
	}

	public void name()
	{
		
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (currentEditing != null)
		{
			removeEditText(currentEditing);
		}
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		
	}

	
}
