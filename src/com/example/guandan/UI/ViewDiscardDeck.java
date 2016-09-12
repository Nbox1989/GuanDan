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

public class ViewDiscardDeck extends ViewGroup{
	
	protected List<Card> mCards=new ArrayList<Card>();
	
	private static final int cardWidth=75;
	
	private static final int cardHeight=98;
	
	private static final int cardSpace=24;
	
	public ViewDiscardDeck(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ViewDiscardDeck(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ViewDiscardDeck(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewDiscardDeck(Context context) {
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
			addView(ivc);
		}
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int childCount=getChildCount();
		
		setMeasuredDimension((childCount-1)*cardSpace+cardWidth, cardHeight);
		
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
				ivc.layout(i*cardSpace, 0, i*cardSpace+cardWidth, cardHeight);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	//intercept touch event so that image-view-card will not trigger on-click event
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

}
