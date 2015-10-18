package com.snail.travellingTrail.travelNotes.controller;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;

import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.utils.BitmapUtil;
import com.snail.travellingTrail.common.utils.PhoneInfo;
import com.snail.travellingTrail.common.utils.UnitConversion;
import com.snail.travellingTrail.trailMap.model.FootprintContent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelNotesAdapter extends BaseAdapter
{
	Context context;
	LayoutInflater layoutInflater;
	List<FootprintContent> footprintContents;
	FootprintContent footprintContent;
//	View itemView;
	FinalBitmap finalBitmap;
	BitmapDisplayConfig bitmapDisplayConfig;
	
	
	public TravelNotesAdapter(Context context, List<FootprintContent> footprintContents)
	{
		super();
		this.context = context;
		this.footprintContents = footprintContents;
		layoutInflater = LayoutInflater.from(context);
		finalBitmap = FinalBitmap.create(context);
		PhoneInfo.loadScreenInfo(context);
		bitmapDisplayConfig = new BitmapDisplayConfig();
		bitmapDisplayConfig.setLoadingBitmap(
				BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_default_photo) );
	}
	
	

	public List<FootprintContent> getFootprintContents()
	{
		return footprintContents;
	}



	public void setFootprintContents(List<FootprintContent> footprintContents)
	{
		this.footprintContents = footprintContents;
	}



	@Override
	public int getCount()
	{
		return footprintContents == null ? 0 : footprintContents.size();
	}

	@Override
	public Object getItem(int position)
	{
		return footprintContents.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return footprintContents.get(position).getFtprnt_Cntnt_ID();
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		footprintContent = footprintContents.get(position);
		
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.listitem_travel_notes, null);
			viewHolder = new ViewHolder();

			viewHolder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.listitem_travel_notes_iv_photo);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.listitem_travel_notes_tv_words);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//只有 有照片的时候才显示imageView控件
		if( footprintContent.getFtprnt_Cntnt_Photo() != null &&
				!(footprintContent.getFtprnt_Cntnt_Photo()).equals("") )
		{
			viewHolder.imageView.setVisibility(View.VISIBLE);
//			finalBitmap.configDisplayer(new Displayer()
//			{
//				
//				@Override
//				public void loadFailDisplay(View imageView, Bitmap bitmap)
//				{
//					
//				}
//				
//				@Override
//				public void loadCompletedisplay(View view, Bitmap bitmap,
//						BitmapDisplayConfig arg2)
//				{
//					System.out.println("Image:width x height --- " + 
//							bitmap.getWidth() + "x" + bitmap.getHeight());
////					bitmap = BitmapUtil.compressAccordingToWidth(bitmap,
////							PhoneInfo.screenWidthPx - UnitConversion.dip2px(context, 63));
//					bitmap = BitmapUtil.compressAccordingToWidth(bitmap,PhoneInfo.screenWidthPx);
//					LayoutParams layoutParams = view.getLayoutParams();
//					layoutParams.width = bitmap.getWidth();
//					layoutParams.height = bitmap.getHeight();
//					((ImageView)view).setLayoutParams(layoutParams);
//					
//					((ImageView)view).setImageBitmap(bitmap);
//				}
//			});
//			finalBitmap.display(viewHolder.imageView, footprintContent.getFtprnt_Cntnt_Photo(), bitmapDisplayConfig);
			
			viewHolder.imageView.setImageURI(Uri.parse(footprintContent.getFtprnt_Cntnt_Photo()));
		}
		else {
			viewHolder.imageView.setVisibility(View.GONE);
		}
		
		//只有 有文字的时候才显示textView控件
		if( footprintContent.getFtprnt_Cntnt_Words() != null &&
				!(footprintContent.getFtprnt_Cntnt_Words()).equals("") )
		{
			viewHolder.textView.setVisibility(View.VISIBLE);
			viewHolder.textView.setText(footprintContent.getFtprnt_Cntnt_Words());
		}
		else {
			viewHolder.textView.setVisibility(View.GONE);
		}
		
		
		return convertView;
	}

	
	static class ViewHolder
	{
		public SimpleDraweeView imageView;
		public TextView textView;
	}
}
