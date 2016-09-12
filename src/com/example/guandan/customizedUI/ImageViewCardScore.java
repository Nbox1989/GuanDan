package com.example.guandan.customizedUI;

import com.example.guandan.Util.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewCardScore extends ImageView{
	
	private static final int srcLeft=0;
	
	private static final int srcTop=131;
	
	private static final int srcWidth=124;
	
	private static final int srcHeight=162;

	private Rect src_bg=new Rect(srcLeft, srcTop, srcLeft+srcWidth, srcTop+srcHeight);
	
	private Rect dst_bg=new Rect(0, 0, 0, 0);
	
	private Rect src_txt=new Rect(0, 0, 0, 0);
	
	private Rect dst_txt=new Rect(0, 0, 0, 0);
	
	public ImageViewCardScore(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setScore(2,false);
	}

	private int score =2;
	
	private boolean onTurn=false;
	
	private static final int txtOffsetX=129;
	
	private static final int txtOffsetY=126;
	
	private static final int txtWidth=28;
	
	private static final int txtHeight=30;
	
	public void setScore(int score,boolean on)
	{
		this.score=score;
		
		this.onTurn=on;
		
		src_txt.left=txtOffsetX+(score-1)*txtWidth;
		
		src_txt.right=src_txt.left+txtWidth;
		
		src_txt.top=on?txtOffsetY+txtHeight:txtOffsetY;
		
		src_txt.bottom=src_txt.top+txtHeight;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		dst_bg.right=getWidth();
		dst_bg.bottom=getHeight();
		Bitmap bmp=BitmapUtil.getBMPMainGame();
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_bg, dst_bg, null);
		
		//dst_txt.left=getWidth()/8;
		//dst_txt.right=getWidth()/8*7;
		dst_txt.right=getWidth();
		dst_txt.top=getHeight()/4;
		dst_txt.bottom=getHeight();
		
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_txt, dst_txt, null);
	}

}
