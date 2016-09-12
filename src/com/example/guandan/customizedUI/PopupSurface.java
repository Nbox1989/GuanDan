package com.example.guandan.customizedUI;

import java.io.IOException;
import java.io.InputStream;

import com.example.guandan.R;
import com.example.guandan.Util.BitmapUtil;
import com.example.guandan.Util.ConstUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class PopupSurface extends PopupWindow{
	
	private View parentView;
	
	private ImageView iv;
	
	private RelativeLayout rl;
	
	public PopupSurface(Context context, View parentView, 
			View contentView, int width, int height, boolean focusable, int index) {
		super(contentView, width, height, focusable);
		
		AssetManager asm= context.getAssets();
		
		Bitmap bmp;
		InputStream is = null;
		String srcName = null;
		
		srcName=String.valueOf(index+1)+".jpg";
		
		try {
			is=asm.open(srcName);
			//bmp=BitmapFactory.decodeStream(is);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		setTouchable(true);
		setFocusable(true);
 
		this.parentView=parentView;
		
		iv=(ImageView)contentView.findViewById(R.id.iv_surface);
		iv.setImageDrawable(BitmapDrawable.createFromStream(is, srcName));
		
		rl=(RelativeLayout)contentView.findViewById(R.id.rl_background);
		
		rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PopupSurface.this.dismiss();
			}
		});
		
	}
	
	public void show()
	{
		showAtLocation(parentView, 
        		Gravity.NO_GRAVITY, 
        		0,0);
	}
	
}

