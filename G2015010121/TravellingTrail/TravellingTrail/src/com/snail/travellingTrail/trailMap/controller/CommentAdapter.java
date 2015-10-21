package com.snail.travellingTrail.trailMap.controller;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.utils.DialogTool;
import com.snail.travellingTrail.common.utils.ToastHelper;
import com.snail.travellingTrail.trailMap.model.FootprintComment;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentAdapter extends BaseAdapter implements OnClickListener
{

	ArrayList<FootprintComment> comments;
	Context context;
	LayoutInflater layoutInflater;
	FootprintComment comment;
	View itemView;
	FinalBitmap finalBitmap;
	
	
	public CommentAdapter(ArrayList<FootprintComment> comments, Context context)
	{
		this.comments = comments;
		this.context = context;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		finalBitmap = FinalBitmap.create(context);//初始化FinalBitmap模块
		finalBitmap.configLoadingImage(R.drawable.img_default_avatar);
	}

	
	
	public ArrayList<FootprintComment> getComments()
	{
		return comments;
	}



	public void setComments(ArrayList<FootprintComment> comments)
	{
		this.comments = comments;
	}



	@Override
	public int getCount()
	{
//		Log.v("getCount", "comments.size:" + comments.size());
		return comments == null ? 0 : comments.size();
	}

	@Override
	public Object getItem(int position)
	{
		Log.v("getItem", "position:" + position);
		return comments.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return comments.get(position).getFtprnt_Cmm_Id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Log.v("getView", "position:" + position);
		comment = (FootprintComment) getItem(position);
		
		ImageView avatarImageView;
		TextView nickNameTextView;
		TextView timeTextView;
		TextView contentTextView;
		
		if (convertView != null)
		{
			itemView = convertView;
		}
		else {
			itemView = layoutInflater.inflate(R.layout.listitem_comment, null);
		}

		avatarImageView = (ImageView)itemView.findViewById(R.id.listitem_comment_iv_avatar);
		nickNameTextView = (TextView)itemView.findViewById(R.id.listitem_comment_tv_nickname);
		timeTextView = (TextView)itemView.findViewById(R.id.listitem_comment_tv_time);
		contentTextView = (TextView)itemView.findViewById(R.id.listitem_comment_tv_content);
		
		if (comment.getFtprnt_Cmm_Us_Avatar() != null 
				&& !comment.getFtprnt_Cmm_Us_Avatar().trim().equals("")
				&& !comment.getFtprnt_Cmm_Us_Avatar().equals("null") )
		{
			finalBitmap.display(avatarImageView, comment.getFtprnt_Cmm_Us_Avatar());
		}
		else {
			avatarImageView.setImageResource(R.drawable.ic_default_avatar);
		}
		
		
		if (comment.getFtprnt_Cmm_Us_Nickname() != null 
				&& !comment.getFtprnt_Cmm_Us_Nickname().trim().equals("")
				&& !comment.getFtprnt_Cmm_Us_Nickname().equals("null") )
		{
			nickNameTextView.setText(comment.getFtprnt_Cmm_Us_Nickname());
		}
		else {
			nickNameTextView.setText("未设置昵称");
		}
		
		timeTextView.setText(comment.getFtprnt_Cmm_Time());
		contentTextView.setText(comment.getFtprnt_Cmm_Content());
		
		itemView.setTag(position);
		itemView.setOnClickListener(this);
		
		return itemView;
	}

	@Override
	public void onClick(final View v)
	{
//		//ToastHelper.showToast(context, v.getTag().toString(), Toast.LENGTH_SHORT);
//		
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setItems(context.getResources().getStringArray(R.array.comment_clicked_array),
//				new DialogInterface.OnClickListener()
//				{
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which)
//					{
//						switch (which)
//						{
//						case 0:
//							Intent intent = new Intent(context, WriteCommentActivity.class);
//							intent.putExtra("microblog_id",
//									(long)((FootprintComment)(getItem(Integer.parseInt(v.getTag().toString())))).getMicroblog_id() );
//							intent.putExtra("reply_to",
//									((FootprintComment)(getItem(Integer.parseInt(v.getTag().toString())))).getName() );
//							((Activity)context).startActivityForResult(intent, 0);
//							break;
//						case 1:
//							Dialog alertDialog = DialogTool.createConfirmDialog(context, "提示", "真的要删除这条微博吗？", "是的，删！", "还是算了",
//									new android.content.DialogInterface.OnClickListener(){
//
//										@Override
//										public void onClick(DialogInterface dialog, int which)
//										{
//											DialogTool.showProgressDialog(context, "删除中..");
//											AjaxParams ajaxParams = new AjaxParams();
//											ajaxParams.put("id", 
//													String.valueOf(((FootprintComment)(getItem(
//															Integer.parseInt(v.getTag().toString())))).getId()) );
//											FinalHttp finalHttp = new FinalHttp();
//											finalHttp.post("http://www.goteny.com/wp-content/themes/goten/weibo/delete_comment.php",
//													ajaxParams, new AjaxCallBack<String>()
//													{
//														@Override
//														public void onFailure(Throwable t,
//																int errorNo, String strMsg)
//														{
//															DialogTool.cancelProgressDialog();
//															if (strMsg != null)
//															{
//																Log.v("GET_DEFAULT_SHIRT:failure", strMsg);
//																ToastHelper.showToast(context, "加载失败，错误代码：" 
//																		+ errorNo + "\n错误信息：" + strMsg, Toast.LENGTH_SHORT);
//															}
//															super.onFailure(t, errorNo, strMsg);
//														}
//				
//														@Override
//														public void onSuccess(String result)
//														{
//															DialogTool.cancelProgressDialog();
//															JsonParser jsonParser = new JsonParser();
//															JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
//															if ( !jsonObject.get("result").getAsBoolean())
//															{
//																ToastHelper.showToast(context, "删除失败，错误信息：\n" + 
//																		jsonObject.get("msg").getAsString(), Toast.LENGTH_SHORT);
//																return;
//															}
//															else {
//																ToastHelper.showToast(context, "删除成功！", Toast.LENGTH_SHORT);
//																comments.remove(Integer.parseInt(v.getTag().toString()));
//																notifyDataSetChanged();
//															}
//															super.onSuccess(result);
//														}
//														
//													});
//										}
//							} , null, DialogTool.NO_ICON);
////							alertDialog.setContentView(R.layout.listitem_microblog);
//							alertDialog.show();
//							break;
//						default:
//							break;
//						}
//					}
//				});
//		builder.show();
	}

}
