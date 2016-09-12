package com.example.guandan;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.guandan.UI.ViewCardDeck;
import com.example.guandan.UI.ViewDiscardDeck;
import com.example.guandan.Util.BitmapUtil;
import com.example.guandan.Util.ConstUtil;
import com.example.guandan.customizedUI.ButtonDiscard;
import com.example.guandan.customizedUI.ButtonHint;
import com.example.guandan.customizedUI.ButtonSkip;
import com.example.guandan.customizedUI.ViewClock;
import com.example.guandan.customizedUI.ViewPortrait;
import com.example.guandan.data.Card;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener {
	
	private RelativeLayout rl_background;
	
	private ViewCardDeck vcd;
	
	private ViewDiscardDeck vdd;
	
	private ViewClock vc;
	
	private ButtonSkip btn_skip;
	
	private ButtonHint btn_hint;
	
	private ButtonDiscard btn_discard;
	
	private ViewPortrait vp_right;
	
	private ViewPortrait vp_self;
	
	private ViewPortrait vp_left;
	
	private ViewPortrait vp_opposite;
	
	private Spinner sp;
	
	private int countdown;
	
	private Handler handler=new Handler();
	
	private Runnable runnable;
	
	private boolean firstSelect=true;
	
	private int screenWidth;
	
	private int screenHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		setContentView(R.layout.activity_main);
		
		
		initViews();
		
		initBMPs();
		
		initConstants();
			
		runnable=new Runnable() {
			
			@Override
			public void run() 
			{
				countdown--;
				if(countdown<0)
				{
					countdown=ViewClock.CountDown;
				}
				vc.setTime(countdown);
				
				handler.postDelayed(this, 1000);
			}
		};
		
		handler.postDelayed(runnable, 1000);
	}

	private void initConstants()
	{
		DisplayMetrics metric = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(metric);  
		ConstUtil.ScreenWidth = metric.widthPixels;     // 屏幕宽度（像素）  
		ConstUtil.ScreenHeight = metric.heightPixels;   // 屏幕高度（像素）  
	}

	private void initBMPs()
	{
		TypedValue value=new TypedValue();
		getResources().openRawResource(R.drawable.maingame, value);
		
		BitmapFactory.Options opts=new BitmapFactory.Options();
		
		opts.inTargetDensity=value.density;
		
		BitmapUtil.setBMPMainGame(
				BitmapFactory.decodeResource(getResources(), R.drawable.maingame,opts));
		
		AssetManager asm= getAssets();
		try {
			InputStream is=asm.open("total_smallerblack.jpg");
			BitmapUtil.setBMPCardSurface(BitmapFactory.decodeStream(is));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		BitmapUtil.setBMPCardSurface(
//				BitmapFactory.decodeResource(getResources(), R.drawable.total_smallblack,opts));
	}

	private void initViews() 
	{
		final Random r=new Random();
		List<Card> cards=new ArrayList<Card>()
		{
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			{add(new Card(r.nextInt(108)));}
			
		};
		
		rl_background=(RelativeLayout)findViewById(R.id.rl_background);
		rl_background.setOnClickListener(this);
		rl_background.setBackgroundResource(R.drawable.bg_2);
		
		vcd=(ViewCardDeck)findViewById(R.id.vcd);
		vcd.setCards(cards);
		
		vdd=(ViewDiscardDeck)findViewById(R.id.vdd);
		
		vc=(ViewClock)findViewById(R.id.vc);
		
		btn_skip=(ButtonSkip)findViewById(R.id.btn_skip);
		btn_skip.setOnClickListener(this);
		
		btn_hint=(ButtonHint)findViewById(R.id.btn_hint);
		btn_hint.setOnClickListener(this);
		
		btn_discard=(ButtonDiscard)findViewById(R.id.btn_discard);
		btn_discard.setOnClickListener(this);
		
		sp=(Spinner)findViewById(R.id.sp);
		sp.setOnItemSelectedListener(this);
		
		vp_right=(ViewPortrait)findViewById(R.id.vp_right);
		vp_right.setPortrait(R.drawable.portrait_6);
		
		vp_left=(ViewPortrait)findViewById(R.id.vp_left);
		vp_left.setPortrait(R.drawable.portrait_2);
		
		vp_self=(ViewPortrait)findViewById(R.id.vp_self);
		vp_self.setPortrait(R.drawable.portrait_7);
		
		vp_opposite=(ViewPortrait)findViewById(R.id.vp_opposite);
		vp_opposite.setPortrait(R.drawable.portrait_3);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.btn_skip:
				break;
			case R.id.btn_hint:
				break;
			case R.id.btn_discard:
				vdd.setCards(vcd.DisCard());
				break;
			case R.id.rl_background:
				vcd.clearPickedCards();
			default:
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		if(firstSelect)
		{
			firstSelect=false;
			return;
		}
		Card c=new Card(arg2+System.currentTimeMillis()/2==0?0:1);
		
		vcd.addCards(c);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		
	}
}
