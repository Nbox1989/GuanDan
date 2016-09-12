package com.example.guandan.customizedUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView{

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int height=MeasureSpec.getSize(heightMeasureSpec);
		
		int width=(int) (573*1.0*height/420);
		
		setMeasuredDimension(width, height);
		
	}

}
