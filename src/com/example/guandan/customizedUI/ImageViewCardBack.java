package com.example.guandan.customizedUI;

import com.example.guandan.Util.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewCardBack extends ImageView{
	
	private static final int srcLeft=255;
	
	private static final int srcTop=569;
	
	private static final int srcWidth=124;
	
	private static final int srcHeight=162;

	private Rect src=new Rect(srcLeft, srcTop, srcLeft+srcWidth, srcTop+srcHeight);
	
	private Rect dst=new Rect(0, 0, 0, 0);
	
	public ImageViewCardBack(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	@Override
	protected void onDraw(Canvas canvas)
	{
		dst.right=getWidth();
		dst.bottom=getHeight();
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src, dst, null);
	}

}
