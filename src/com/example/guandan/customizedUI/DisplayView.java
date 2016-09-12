package com.example.guandan.customizedUI;

import com.example.guandan.R;
import com.example.guandan.Util.BitmapUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class DisplayView extends ViewGroup{

	private int index;
	
	private ImageView iv_right;
	
	private ImageView iv_left;
	
	private Paint p=new Paint();
	
	private Rect rectBg=new Rect(0,0,0,0);
	
	private Rect src_surface=new Rect(BitmapUtil.surfaceLeft, BitmapUtil.surfaceTop,
					BitmapUtil.surfaceLeft+BitmapUtil.surfaceWidthLong, 
					BitmapUtil.surfaceTop+BitmapUtil.surfaceWidthLong);
	
	private Rect dst_surface= new Rect(0,0,0,0);
	
	public DisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		iv_right=new ImageView(context);
		iv_right.setImageResource(R.drawable.right);
		iv_right.setScaleType(ScaleType.FIT_CENTER);

		iv_left=new ImageView(context);
		iv_left.setImageResource(R.drawable.left);
		iv_left.setScaleType(ScaleType.FIT_CENTER);
		
		
		addView(iv_left);
		addView(iv_right);
		
		p.setARGB(0x55, 0x00, 0x00, 0x00);
		p.setStyle(Style.FILL);
		
		setWillNotDraw(false);
		
	}
	
	private float startX;
	
	private float startY;
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				startX=event.getX();
				startY=event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float offsetX=event.getX()-startX;
				iv_right.setX(getWidth()-offsetX);
				break;
			case MotionEvent.ACTION_UP:
				iv_right.setX(getWidth());
				index++;
				invalidate();
				break;
		}
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		rectBg.right=getWidth();
		rectBg.bottom=getHeight();
		canvas.drawRect(rectBg, p);
		
		float zoomRate=(float) (getHeight()*1.0/BitmapUtil.surfaceHeight);
		
		src_surface.left=BitmapUtil.surfaceLeft+index*BitmapUtil.surfaceWidthLong;
		src_surface.right=src_surface.left+BitmapUtil.surfaceWidthLong;
		
		dst_surface.left=(int) ((getWidth()-BitmapUtil.surfaceWidthLong*zoomRate)/2);
		dst_surface.top=0;
		dst_surface.right=(int) ((getWidth()+BitmapUtil.surfaceWidthLong*zoomRate)/2);
		dst_surface.bottom=getHeight();
		
		canvas.drawBitmap(BitmapUtil.getBMPCardSurface(), src_surface, dst_surface, null);
	
		super.onDraw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		
		iv_right.layout(getWidth(), 
				(getHeight()-iv_right.getMeasuredHeight())/2,
				getWidth()+iv_right.getMeasuredWidth(), 
				(getHeight()+iv_right.getMeasuredHeight())/2);
		
		iv_left.layout(-iv_left.getMeasuredWidth(),
				(getHeight()-iv_left.getMeasuredHeight())/2,
				0,
				(getHeight()+iv_left.getMeasuredHeight())/2);
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width=MeasureSpec.getSize(widthMeasureSpec);
		int height=MeasureSpec.getSize(heightMeasureSpec);
		
		setMeasuredDimension(width, height);
		
		int iv_width=MeasureSpec.makeMeasureSpec(35, MeasureSpec.UNSPECIFIED);
		int iv_height=MeasureSpec.makeMeasureSpec(30, MeasureSpec.UNSPECIFIED);
		
		iv_right.measure(iv_width, iv_height);
		iv_left.measure(iv_width, iv_height);
	}

}
