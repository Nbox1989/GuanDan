package com.example.guandan.customizedUI;

import com.example.guandan.Util.BitmapUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.MeasureSpec;

public class ViewClock extends ViewGroup{

	private TextView tv;
	
	private Rect src=new Rect(134,355,214,455);
	
	private Rect dst=new Rect(0,0,0,0);
	
	public ViewClock(Context context, AttributeSet attrs) {
		super(context, attrs);

		tv=new TextView(context);
		tv.setText(String.valueOf(CountDown));
		tv.setTextSize(16f);
		tv.getPaint().setFakeBoldText(true);
		
//		ViewGroup.LayoutParams lp =tv.getLayoutParams();
//		
//		lp.width=ViewGroup.LayoutParams.WRAP_CONTENT;
//		lp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
		
		addView(tv);
		
		setWillNotDraw(false);
	}

	public static final int CountDown=20;
	
	public void setTime(int countdown)
	{
		if(countdown<0)
		{
			tv.setText("00");
			tv.setTextColor(Color.RED);
		}
		
		if(countdown<10)
		{
			tv.setText("0"+countdown);
			tv.setTextColor(Color.RED);
		}
		else
		{
			tv.setText(String.valueOf(countdown));
			tv.setTextColor(Color.BLACK);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int width=MeasureSpec.getSize(widthMeasureSpec);
		int height=MeasureSpec.getSize(heightMeasureSpec);
		
		setMeasuredDimension(width,height);
			
		int widthSpec=MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
		int heightSpec=MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
		
		tv.measure(widthSpec, heightSpec);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		int w=tv.getMeasuredWidth();
		int h=tv.getMeasuredHeight();
		int W=getWidth();
		int H=getHeight();
		
		int left=(int)((W-w)*0.5);
		int top=(int)(0.2*H+(0.8*H-h)*0.5);
		int right=(int)((W+w)*0.5);
		int bottom=(int)(0.2*H+(0.8*H+h)*0.5);
		
		tv.layout(left,top,right,bottom);
		
//		int ChildCount=getChildCount();
//		
//		getChildAt(0).layout(0, 0, 20, 20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		dst.right=getWidth();
		dst.bottom=getHeight();
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src, dst, null);
		int w=tv.getWidth();
		int h=tv.getHeight();
		super.onDraw(canvas);
	}
}
