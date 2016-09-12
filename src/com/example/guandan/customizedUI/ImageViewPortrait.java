package com.example.guandan.customizedUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewPortrait extends ImageView{

	private RectF rect=new RectF(0, 0, 0, 0);
	
	private int rx;
	
	private int ry;
	
	private Paint paint_border=new Paint();

	public ImageViewPortrait(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint_border.setARGB(0x55, 0, 0, 0);
		paint_border.setStyle(Paint.Style.STROKE);//充满  
		paint_border.setStrokeWidth(10f);
		paint_border.setAntiAlias(true);// 设置画笔的锯齿效果  
	}

	@Override
	protected void onDraw(Canvas canvas) {

		
		super.onDraw(canvas);
		rect.right=getWidth();
		rect.bottom=getHeight();
		
		rx=getWidth()/4;
		ry=getHeight()/4;
		
		//canvas.drawRoundRect(rect, rx, ry, paint_border);
		canvas.drawRect(rect, paint_border);
	}
}
