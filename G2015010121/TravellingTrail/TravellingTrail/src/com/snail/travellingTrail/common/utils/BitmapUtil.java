package com.snail.travellingTrail.common.utils;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;

import org.apache.http.util.ByteArrayBuffer;

import com.amap.api.mapcore2d.el;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.util.Log;

public class BitmapUtil
{
	/**
	 * 按居中正方形裁切图片
	 */
	public static Bitmap imageCrop(Bitmap bitmap)
	{
		int w = bitmap.getWidth(); // 得到图片的宽，高
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

		int retX = w > h ? (w - h) / 2 : 0; // 基于原图，居中取正方形左上角x坐标
		int retY = w > h ? 0 : (h - w) / 2;

		// 裁剪
		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}
	
	
	/**
	 * 压缩或放大图片宽度至和width大小，且高度以相同比例压缩或放大
	 * @param bitmap
	 * @param width
	 * @return
	 */
	public static Bitmap compressAccordingToWidth(Bitmap bitmap, int width)
	{
		
		if (width == bitmap.getWidth())
		{
			return bitmap;
		}
		
		float scale = (float)width / (float)bitmap.getWidth();
		int height = (int) (bitmap.getHeight() * scale);
		
		return ThumbnailUtils.extractThumbnail(bitmap, width,
				height, 0);
	}
	
	
	/**
	 * 裁剪Bitmap图片
	 * @param source 要从中截图的原始位图
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 * @param width 要截的图的宽度
	 * @param height 要截的图的宽度
	 * @return
	 */
	public static Bitmap cutBitmap(Bitmap source, int x, int y, int width, int height)
	{
		source = Bitmap.createBitmap(source, 0, 0, width, height);
		return source;
	}

	
	
	/**
	 * 获取图片并压缩
	 * @param path 图片路径
	 * @param compressScaleToScreen 目标图片宽度占屏幕宽度的比例
	 * @return
	 */
	public static Bitmap getBitmap(String path, int compressScaleToScreen)
	{
		Options options = new Options();
		options.inJustDecodeBounds = true; // 先设true，用以读入图片信息
		BitmapFactory.decodeFile(path, options); //此时bitmap为null,因为仅读入图片信息
		int imgWidth = options.outWidth;
		int imgHeight = options.outHeight;
		Log.v("BitmapUtil--->getBitmap", "imgWidth:" + imgWidth + " ;imgHeight:" + imgHeight);
		
		options.inJustDecodeBounds = false; // 再设false，用以加载bitmap
		options.inSampleSize = calculateCompressScale(compressScaleToScreen, imgWidth);
		
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		SoftReference<Bitmap> reference = new SoftReference<Bitmap>(bitmap);
		return reference.get();
	}
	

	/**
	 * 计算图片压缩比
	 * @param compressScaleToScreen 目标图片宽度占屏幕宽度的比例
	 * @param imgWidth 源图片宽度
	 * @return
	 */
	private static int calculateCompressScale(int compressScaleToScreen, int imgWidth)
	{
		//计算图片的目标宽度
		float imgCompressWidth = PhoneInfo.screenWidthPx / compressScaleToScreen;
		//计算压缩的比例
		int compressScaleToOriginImg = (int) (imgWidth / imgCompressWidth);
		return compressScaleToOriginImg;
	}
	
	
	/**
	 * 将base64编码的图片转成bitmap
	 * @param base64Image base64编码的图片的字符串
	 * @return bitmap图片
	 */
	public static Bitmap base64ToBitmap(String base64Image)
	{
		// 将Base64字符串图片转换成byte[]，再转成bitmap
		byte[] byteImage = Base64.decode(base64Image, Base64.NO_WRAP);
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
		return bitmap;
	}
	
	
	/**
	 * 将bitmap编码的图片转成base64
	 * @param bitmap bitmap图片
	 * @return base64图片字符串
	 */
	public static String bitmapToBase64(Bitmap bitmap)
	{
		// 将bitmap图片转换成byte[]，再转成Base64字符串
		byte[] byteImage = bitmapToBytes(bitmap);
		
		if(!bitmap.isRecycled()) //回收bitmap内存
		{
			bitmap.recycle(); 
			bitmap = null;
			System.gc();
		}

//		String stringImage = new String(byteImage);
//		
//		long remainder = stringImage.length() % 512; //求余数
//		//整除求循环次数,若不为512的整数倍则+1
//		long count = (remainder > 0) ?
//				(stringImage.length() / 512) + 1 : (stringImage.length() / 512) ;
//		
//		String base64String = "";
//		
//		//将过长的字符串分段转换成base64再连接在一起，否则Base64.encodeToString()会内存溢出
//		for (int i = 0; i < count; i++)
//		{
//			String temp = "";
//			if (i == count -1)
//			{
//				temp = stringImage.substring(512 * i, stringImage.length() - 1);
//			}else {				
//				temp = stringImage.substring(512 * i, 512 * (i + 1) - 1);
//			}
//			base64String += Base64.encodeToString(temp.getBytes(), Base64.NO_WRAP);
//		}
		
		String base64String = Base64.encodeToString(byteImage, Base64.NO_WRAP);
		return base64String;
	}
	
	
	/**
	 * 将bitmap编码的图片转成String
	 * @param bitmap bitmap图片
	 * @return base64图片字符串
	 */
	public static String bitmapToString(Bitmap bitmap)
	{
		// 将bitmap图片转换成byte[]，再转成Base64字符串
		byte[] byteImage = bitmapToBytes(bitmap);

		String stringImage = new String(byteImage);
		
		return stringImage;
	}
	
	
	
	/**
	 * bitmap转成byte[]
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bitmap)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}
	
	
	/**
	 * 获取上传图片用到的BitmapFactory.Options设置
	 * @return
	 */
	public static BitmapFactory.Options getUploadingBitmapOptions()
	{
		BitmapFactory.Options options = new BitmapFactory.Options();

		int scale = ((options.outWidth / 1080) > 1) ? (options.outWidth / 1080) : 1;
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
		
		return options;
	}
}
