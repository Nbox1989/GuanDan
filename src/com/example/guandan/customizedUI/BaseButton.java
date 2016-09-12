package com.example.guandan.customizedUI;

import com.example.guandan.Util.BitmapUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseButton extends View{
	private float downX;
	
	private float downY;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				downX=event.getX();
				downY=event.getY();
				toucheddown=true;
				invalidate();
				break;
			case MotionEvent.ACTION_CANCEL:
				toucheddown=false;
				invalidate();
				break;

			case MotionEvent.ACTION_UP:
				
				toucheddown=false;
				if(Math.abs(event.getX()-downX)<distanceX &&Math.abs(event.getY()-downY)<distanceY)
				{
					performClick();
				}
				invalidate();
				break;
		}
		return true;
	}

	protected static final int distanceX=10;
	
	protected static final int distanceY=10;

	protected Rect src_bg=new Rect(0, 0, 0, 0);
	
	protected Rect src_shadowbg=new Rect(0,0,0,0);
	
	protected Rect dst_bg=new Rect(0,0,0,0);
	
	protected Rect src_txt=new Rect(0,0,0,0);

	protected Rect dst_txt=new Rect(0,0,0,0);
	
	protected int bgLeft=298;
	
	protected int bgTop=837;
	
	protected int bgShadowLeft=385;
			
	protected int bgShadowTop=837;
	
	protected int bgHalfWidth=82;
	
	protected int bgWidth=bgHalfWidth*2;
	
	protected int bgHeight=88;
	
	protected int txtLeft=714;
	
	protected int txtTop=320;
	
	protected int txtWidth=101;
	
	protected int txtHeight=47;
	
	protected boolean toucheddown=false;
	
	public BaseButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		dst_bg.right=getWidth()/2;
		dst_bg.bottom=getHeight();
		
		if(!toucheddown)
		{
			canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_bg,dst_bg, null);
	
			canvas.translate(getWidth(), 0);
			canvas.scale(-1, 1);
			
			canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_bg,dst_bg, null);
		}
		else
		{
			canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_shadowbg, dst_bg, null);
			
			canvas.translate(getWidth(), 0);
			canvas.scale(-1, 1);
			
			canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_shadowbg, dst_bg, null);
		}
		canvas.scale(-1, 1);
		canvas.translate(-getWidth(), 0);
		
		float zoomRate=(float) (getWidth()*1.0/bgWidth);
		
		dst_txt.left=(int) ((bgWidth-txtWidth)*0.5*zoomRate);
		
		dst_txt.top=(int) ((bgHeight-txtHeight)*0.5*zoomRate);
		
		dst_txt.right=(int) ((bgWidth+txtWidth)*0.5*zoomRate);
		
		dst_txt.bottom=(int) ((bgHeight+txtHeight)*0.5*zoomRate);
		
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_txt,dst_txt, null);
	}
}
