package com.example.guandan.customizedUI;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

public class ButtonDiscard extends BaseButton{
	
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
	
	public ButtonDiscard(Context context, AttributeSet attrs) {
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
