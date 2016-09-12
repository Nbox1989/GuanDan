package com.example.guandan.customizedUI;

import com.example.guandan.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPortrait extends RelativeLayout{

//	Paint p=new Paint();
//	@Override
//	protected void onDraw(Canvas canvas) 
//	{
//		RectF rect=new RectF();
//		rect.right=getWidth();
//		rect.bottom=getHeight();
//		
//		int rx=getWidth()/10;
//		int ry=getHeight()/10;
//		
//		p.setColor(Color.RED);
//		
//		canvas.drawRoundRect(rect, rx, ry, p);
//		
//		
//		super.onDraw(canvas);
//	}

	private TextView tv_name;
	private ImageViewPortrait iv_portrait;
	
	private View contentView;

	public ViewPortrait(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView(context);
	}

	public ViewPortrait(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public ViewPortrait(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ViewPortrait(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) 
	{
		contentView=LayoutInflater.from(context).inflate(R.layout.view_portrait, this, true);
		tv_name=(TextView)contentView.findViewById(R.id.tv_name);
		iv_portrait=(ImageViewPortrait)contentView.findViewById(R.id.iv_portrait);
		
		
		setName("1234567fad;jg");
		setPortrait(R.drawable.portrait_default);
	}
	
	public void setName(String name)
	{
		tv_name.setText(name);
	}
	
	public void setPortrait(int resID)
	{
		iv_portrait.setImageResource(resID);
	}

}
