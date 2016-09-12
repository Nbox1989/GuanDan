package com.example.guandan.customizedUI;

import com.example.guandan.Util.BitmapUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class ButtonHint extends BaseButton{

	
	protected int bgLeft=298;
	
	protected int bgTop=737;
	
	protected int bgShadowLeft=385;
			
	protected int bgShadowTop=737;
	
	protected int bgHalfWidth=82;
	
	protected int bgWidth=bgHalfWidth*2;
	
	protected int bgHeight=88;
	
	protected int txtLeft=604;
	
	protected int txtTop=320;
	
	protected int txtWidth=101;
	
	protected int txtHeight=47;
	
	public ButtonHint(Context context, AttributeSet attrs) {
		super(context, attrs);
		RefreshLocation();
	}

	protected void RefreshLocation()
	{
		src_bg=new Rect(bgLeft,bgTop,bgLeft+bgHalfWidth,bgTop+bgHeight);
		src_shadowbg=new Rect(bgShadowLeft,bgShadowTop,bgShadowLeft+bgHalfWidth,bgShadowTop+bgHeight);
		src_txt=new Rect(txtLeft,txtTop,txtLeft+txtWidth,txtTop+txtHeight);
	
	}

}
