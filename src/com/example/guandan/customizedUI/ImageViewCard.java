package com.example.guandan.customizedUI;

import com.example.guandan.R;
import com.example.guandan.Util.BitmapUtil;
import com.example.guandan.Util.ConstUtil;
import com.example.guandan.data.Card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class ImageViewCard extends ImageView implements View.OnLongClickListener{
	private Context context;
	
	private static final int bgLeft=0;
	
	private static final int bgTop=131;
	
	private static final int bgWidth=124;
	
	private static final int bgHeight=162;
	
	private static final int numberOffsetX=0;
	
	private static final int numberOffsetY=0;
	
	private static final int numberWidth=38;
	
	private static final int numberHeight=42;
	
	private static final int suitOffsetX=218;
	
	private static final int suitOffsetY=214;
	
	private static final int suitWidth=28;
	
	private static final int suitHeight=30;
	
	private static final int jokerLeft=0;
	
	private static final int jokerTop=465;
	
	private static final int jokerWidth=27;
	
	private static final int jokerHeight=130;
	
	private static final int rx=10;
	
	private static final int ry=10;
	
	private static final int gapX=5;
	
	private static final int gapY=5;
	
	private Rect src_surface=new Rect(BitmapUtil.surfaceLeft, BitmapUtil.surfaceTop,
			BitmapUtil.surfaceLeft+BitmapUtil.surfaceWidth, 
			BitmapUtil.surfaceTop+BitmapUtil.surfaceHeight);

	private Rect src_bg=new Rect(bgLeft, bgTop, bgLeft+bgWidth, bgTop+bgHeight);
	
	private Rect dst_bg=new Rect(0, 0, bgWidth, bgHeight);
	
	private RectF shadow_bg=new RectF(2, 0, bgWidth, bgHeight);
	
	private Rect src_number=new Rect(numberOffsetX,numberOffsetY,
			numberOffsetX+numberWidth, numberOffsetY+numberHeight);
	
	private Rect dst_number=new Rect(rx, ry, rx+numberWidth, ry+numberHeight);
	
	private Rect src_suit=new Rect(suitOffsetX,suitOffsetY,
			suitOffsetX+suitWidth, suitOffsetY+suitHeight);
	
	private Rect dst_suit=new Rect(gapX+rx, ry+numberHeight+gapY, 
			gapX+rx+suitWidth, 
			numberHeight+ry+gapY+suitHeight);
	
	private Rect src_joker=new Rect(jokerLeft,jokerTop,
			jokerLeft+jokerWidth, jokerTop+jokerHeight);
	
	private Rect dst_joker=new Rect(rx, ry, rx+jokerWidth, ry+jokerHeight);
	
	public ImageViewCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView(context);
	}

	public ImageViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public ImageViewCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ImageViewCard(Context context) {
		super(context);
		initView(context);
	}

	private Card mCard;
	
	private Paint paintShadow;
	
	private boolean marked = false;	//set whether card is slid over by finger
	
	private boolean picked = false;	//set whether card is picked to discard	
	
	private float zoomRate=1.0f;
	
	private PopupSurface popsurface;
	
	private void initView(Context context)
	{
		this.context=context;
		
		paintShadow=new Paint();
		paintShadow.setARGB(0x55, 0x00, 0x00, 0x00);
		paintShadow.setStyle(Style.FILL);
		paintShadow.setAntiAlias(true);
		
		cm.setSaturation(0);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        p.setColorFilter(f);
		p.setAlpha(0x55);
		
		setOnLongClickListener(this);
	}
	
	public void setCard(Card card)
	{
		this.mCard=card;
		
		if(mCard.getColor()==Color.RED)
		{
			src_number.top+=numberHeight;
			src_number.bottom+=numberHeight;
		}
		src_number.left+=(mCard.getNumber()-1)*numberWidth;
		src_number.right+=(mCard.getNumber()-1)*numberWidth;
		
		boolean isBlack=mCard.getColor()==Color.BLACK;
		
		src_number=new Rect(numberOffsetX+(mCard.getNumber()-1)*numberWidth,
				isBlack?numberOffsetY:numberOffsetY+numberHeight,
				numberOffsetX+numberWidth+(mCard.getNumber()-1)*numberWidth, 
				isBlack?numberOffsetY+numberHeight:numberOffsetY+numberHeight+numberHeight);

		
		int suitIndex=0;
		if(mCard.getSuit()==Card.FLOWER)
		{
			suitIndex=1;	
		}
		else if(mCard.getSuit()==Card.HEART)
		{
			suitIndex=2;	
		}
		else if(mCard.getSuit()==Card.SPADE)
		{
			suitIndex=3;		
		}
		src_suit=new Rect(suitOffsetX+suitIndex*suitWidth,
				suitOffsetY,
				suitOffsetX+suitWidth+suitIndex*suitWidth, 
				suitOffsetY+suitHeight);
		
		src_joker=new Rect(mCard.getNumber()==14?jokerLeft:jokerLeft+jokerWidth,
				jokerTop,
				mCard.getNumber()==14?jokerLeft+jokerWidth:+jokerLeft+jokerWidth+jokerWidth, 
				jokerTop+jokerHeight);
		
	
	}
	
	public void setMarked(boolean selected)
	{
		marked=selected;
		invalidate();
	}
	
	public boolean isMarked()
	{
		return marked;
	}
	
	public void toggleMarked()
	{
		marked=!marked;
		invalidate();
	}
	
	public void setPicked(boolean b)
	{
		picked=b;
		onPickChanged();
	}
	
	public boolean isPicked()
	{
		return picked;
	}
	
	public void togglePicked()
	{
		picked=!picked;
		onPickChanged();
	}
	
	public Card getCard()
	{
		return mCard;
	}
	
	private void onPickChanged()
	{
		setY((int) (picked?0:0.22f*getHeight()));
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		zoomRate=getWidth()*1.0f/bgWidth;
		
		drawBackGround(canvas);
		
		drawSurface(canvas);
		
		if(mCard.getSuit()!=Card.JOKER)
		{
			//drawWhite(canvas);
			drawNumber(canvas);
			drawSuit(canvas);
			
		}
		else
		{

			//drawWhite_(canvas);
			drawJoker(canvas);
		}
		
		if(marked)
		{
			shadow_bg.left*=zoomRate;
			shadow_bg.top*=zoomRate;
			shadow_bg.right*=zoomRate;
			shadow_bg.bottom*=zoomRate;
			canvas.drawRoundRect(shadow_bg, rx*zoomRate, ry*zoomRate, paintShadow);
		}
    }

	
	private Paint p=new Paint();

    ColorMatrix cm = new ColorMatrix();
    

	
	private void drawSurface(Canvas canvas) {
        
		
		src_surface.left=mCard.getHonrizontalIndexInBMP()*BitmapUtil.surfaceWidth;
		src_surface.top=mCard.getVerticalIndexInBMP()*BitmapUtil.surfaceHeight;
		src_surface.right=src_surface.left+BitmapUtil.surfaceWidth;
		src_surface.bottom=src_surface.top+BitmapUtil.surfaceHeight;
		
		
		canvas.drawBitmap(BitmapUtil.getBMPCardSurface(), src_surface, dst_bg, p);
	}

	private void drawBackGround(Canvas canvas) 
	{
		dst_bg.right=(int) (bgWidth*zoomRate);
		dst_bg.bottom=(int) (bgHeight*zoomRate);
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_bg, dst_bg, null);
	}

	private void drawNumber(Canvas canvas) 
	{
		dst_number.left=(int) (rx*zoomRate);
		dst_number.top=(int) (ry*zoomRate);
		dst_number.right=(int) ((rx+numberWidth)*zoomRate);
		dst_number.bottom=(int) ((ry+numberHeight)*zoomRate);
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_number, dst_number, null);
	}

	private void drawSuit(Canvas canvas) 
	{
		dst_suit.left=(int) ((gapX+rx)*zoomRate);
		dst_suit.top=(int) ((ry+numberHeight+gapY)*zoomRate);
		dst_suit.right=(int) ((gapX+rx+suitWidth)*zoomRate);
		dst_suit.bottom=(int) ((numberHeight+ry+gapY+suitHeight)*zoomRate);
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_suit, dst_suit, null);
	}

	private void drawJoker(Canvas canvas) 
	{
		dst_joker.left=(int) (rx*zoomRate);
		dst_joker.top=(int) (ry*zoomRate);
		dst_joker.right=(int) ((rx+jokerWidth)*zoomRate);
		dst_joker.bottom=(int) ((ry+jokerHeight)*zoomRate);
		canvas.drawBitmap(BitmapUtil.getBMPMainGame(), src_joker, dst_joker, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mIsPressed=true;
				this.setMarked(true);
				postCheckForLongClick();
				break;
			case MotionEvent.ACTION_UP:
				if(!mHasPerformedLongPress)
				{
					if(mPendingCheckForLongPress!=null)
					{
						removeCallbacks(mPendingCheckForLongPress);
					}
					this.setMarked(false);
					this.togglePicked();
				}
				mIsPressed=false;
				break;
			default:
				break;
		}
		return true;
	}

	private boolean mHasPerformedLongPress=false;
	
	private boolean mIsPressed=false;
	
	private CheckForLongPress mPendingCheckForLongPress;
	
	private final static int LongPressTimeout=800;
	
	private void postCheckForLongClick() 
	{
		mHasPerformedLongPress=false;
		if(mPendingCheckForLongPress==null)
		{
			mPendingCheckForLongPress=new CheckForLongPress();
			postDelayed(mPendingCheckForLongPress, LongPressTimeout);
		}
	}
	

	@Override
	public boolean onLongClick(View arg0)
	{
		if(popsurface==null)
		{
			View popView = LayoutInflater.from(context).inflate(
	                R.layout.popsurface, null);
	
	        
			popsurface = new PopupSurface(context,this,popView,
					ConstUtil.ScreenWidth, ConstUtil.ScreenHeight, true, mCard.getIndexInHeap());
		}
		popsurface.show();
		return true;
	}
	
	class CheckForLongPress implements Runnable
	{
		@Override
		public void run() 
		{
			if(mIsPressed)
			{
				if(performLongClick())
				{
					mHasPerformedLongPress=true;
				}
			}
		}
	}

}
