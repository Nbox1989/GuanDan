package com.example.guandan.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.guandan.customizedUI.ImageViewCard;
import com.example.guandan.data.Card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.view.View;

public class ViewCardDeck extends ViewGroup implements View.OnClickListener{
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_DOWN)
		{
			if(ev.getY()<deckHeight-cardHeight)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		
		return super.onInterceptTouchEvent(ev);
	}

	protected List<Card> mCards=new ArrayList<Card>();
	
	private static final int cardWidth=124;
	
	private static final int cardHeight=162;
	
	private static final int deckHeight=198;
	
	private static final int cardSpace=40;
	
	private float startX;
	
	private float startY;
	
	public ViewCardDeck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ViewCardDeck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ViewCardDeck(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewCardDeck(Context context) {
		super(context);
	}
	
	public void setCards(List<Card> cards)
	{
		Collections.sort(cards);
		this.mCards=cards;
		
		showCards();
	}
	
	public void addCards(Card card)
	{
		mCards.add(card);
		Collections.sort(mCards);
		
		showCards();
	}
	
	public void showCards()
	{
		removeAllViews();
		
		for(Card c:mCards)
		{
			ImageViewCard ivc=new ImageViewCard(getContext());
			ivc.setCard(c);
			ivc.setOnClickListener(this);
			addView(ivc);
		}
		invalidate();
	}
		
	public void clearPickedCards()
	{
		int childCount=getChildCount();
		
		for(int i=0;i<childCount;i++)
		{
			try
			{
				ImageViewCard ivc=(ImageViewCard)getChildAt(i);
				ivc.setPicked(false);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int childCount=getChildCount();
		
		setMeasuredDimension((childCount-1)*cardSpace+cardWidth,deckHeight);
		
		for (int i = 0; i < childCount; i++) 
		{
			View child = getChildAt(i);
			
			int widthSpec=MeasureSpec.makeMeasureSpec(cardWidth, MeasureSpec.EXACTLY);
			int heightSpec=MeasureSpec.makeMeasureSpec(cardHeight, MeasureSpec.EXACTLY);
			
			child.measure(widthSpec, heightSpec);
		}
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) 
	{
		int childCount=getChildCount();
		for(int i=0;i<childCount;i++)
		{
			try
			{
				ImageViewCard ivc=(ImageViewCard)getChildAt(i);
				
				ivc.layout(i*cardSpace, deckHeight-cardHeight, i*cardSpace+cardWidth, deckHeight);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	private void markCardByLocation(float begin, float end)
	{
		float minX=Math.min(begin, end);
		float maxX=Math.max(begin, end);
		
		int dis=(int) Math.abs(begin-end);
		
		int childCount=getChildCount();
		
		if(childCount==0)
		{
			return;
		}
		else if(childCount==1)
		{
			((ImageViewCard)getChildAt(0)).setMarked(true);;
		}
		else
		{
			int offset=getChildAt(1).getLeft()-getChildAt(0).getLeft();
			int count=dis/offset+1;
			
			boolean started=false;
			
			for(int i=0;i<childCount;i++)
			{
				ImageViewCard v=(ImageViewCard)getChildAt(i);
				if(!started)
				{
					if(v.getLeft()+offset>minX||(i==childCount-1&&v.getRight()>maxX))
					{
						started=true;
						v.setMarked(true);
						count--;
					}
					else
					{
						v.setMarked(false);
					}
				}
				else
				{
					if(count-->0)
					{
						v.setMarked(true);
					}
					else
					{
						v.setMarked(false);
					}
				}
			}
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mIsPressed=true;
				mMoveOutOfLongPressRange=false;
				mPendingCheckForLongPress=null;
				startX=event.getX();
				startY=event.getY();
				postCheckForLongClick();
				break;
			case MotionEvent.ACTION_MOVE:
				if(!mMoveOutOfLongPressRange)
				{
					if(Math.abs(startX-event.getX())>LongPressRange||
							Math.abs(startY-event.getY())>LongPressRange)
					{
						mMoveOutOfLongPressRange=true;
						if(!mHasPerformedLongPress)
						{
							if(mPendingCheckForLongPress!=null)
							{
								removeCallbacks(mPendingCheckForLongPress);
							}
						}
					}
				}
				else
				{
					markCardByLocation(startX, event.getX());
				}
				break;
			case MotionEvent.ACTION_UP:
				if(!mHasPerformedLongPress||mMoveOutOfLongPressRange)
				{
					if(mPendingCheckForLongPress!=null)
					{
						removeCallbacks(mPendingCheckForLongPress);
					}
					markCardByLocation(startX, event.getX());
					togglePickedCards();
				}
				break;
			default:
				break;
		}
		
		return true;
	}
	

	private boolean mIsPressed=false;

	private boolean mHasPerformedLongPress=false;
		
	private CheckForLongPress mPendingCheckForLongPress;
	
	private final static int LongPressTimeout=800;
	
	private final static int LongPressRange=10;
	
	private boolean mMoveOutOfLongPressRange=false;
	
	private void postCheckForLongClick() 
	{
		mHasPerformedLongPress=false;
		if(mPendingCheckForLongPress==null)
		{
			mPendingCheckForLongPress=new CheckForLongPress();
			postDelayed(mPendingCheckForLongPress, LongPressTimeout);
		}
	}

	private void performLongPress(float x) 
	{
		int childCount=getChildCount();
		
		for(int i=0;i<childCount;i++)
		{
			if(getChildAt(i).getLeft()>x)
			{
				getChildAt(i).performLongClick();
				return;
			}
		}
		getChildAt(childCount-1).performLongClick();
	}
	
	private void togglePickedCards() 
	{
		int childCount=getChildCount();
		
		for(int i=0;i<childCount;i++)
		{
			try
			{
				ImageViewCard ivc=(ImageViewCard)getChildAt(i);
				if(ivc.isMarked())
				{
					ivc.togglePicked();
					ivc.setMarked(false);
				}
				
			}
			catch(Exception e)
			{
				
			}
		}
	}

	public List<Card> DisCard()
	{
		List<Card> discards=new ArrayList<Card>();
		int childCount=getChildCount();
		for(int i=0;i<childCount;i++)
		{
			try
			{
				ImageViewCard ivc=(ImageViewCard)getChildAt(i);
				if(ivc.isPicked())
				{
					Card card=ivc.getCard();
					mCards.remove(card);
					discards.add(card);
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
		showCards();
		
		return discards;
	}

	@Override
	public void onClick(View v) 
	{
		((ImageViewCard)v).togglePicked();
	}

	
	class CheckForLongPress implements Runnable
	{
		@Override
		public void run() 
		{
			if(mIsPressed)
			{
				performLongPress(startX);
				mHasPerformedLongPress=true;
			}
		}
	}
}
