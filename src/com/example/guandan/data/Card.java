package com.example.guandan.data;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.widget.DialerFilter;

public class Card implements Comparable<Card>
{
	public final static int JOKER=0x00;
	public final static int HEART=0x01;
	public final static int SPADE=0x02;
	public final static int DIAMOND=0x03;
	public final static int FLOWER=0x04;
	
	private int mSuit;		//joker is NONE;
	private int mNumber;	//joker is 14 15;
	
	private int mIndex=-1;	//index in two pairs,start from 0
	
	public Card(int index)
	{
		int indexInOnePair=index/2;
		if(indexInOnePair==0)
		{
			mSuit=JOKER;
			mNumber=15;
		}
		else if(indexInOnePair==1)
		{
			mSuit=JOKER;
			mNumber=14;
		}
		else
		{
			mSuit=(indexInOnePair-2)%4+1;
			mNumber=mCompareIndex.get(12-(indexInOnePair-2)/4);
		}
		mIndex=index;
	}
	
//	public Card(int Suit, int Number)
//	{
//		mSuit=Suit;
//		mNumber=Number;
//	}
	
	public int getSuit()
	{
		return mSuit;
	}
	
	public int getNumber()
	{
		return mNumber;
	}
	
	public int getColor()
	{
		if(mSuit==HEART||mSuit==DIAMOND)
		{
			return Color.RED;
		}
		else if(mSuit==SPADE||mSuit==FLOWER)
		{
			return Color.BLACK;
		}
		else
		{
			return mNumber==14?Color.BLACK:Color.RED;
		}
	}
	
	public int getIndexInHeap()
	{
		return mIndex;
	}
	
	public int getHonrizontalIndexInBMP()
	{
		return getIndexInHeap()%12;
	}
	
	public int getVerticalIndexInBMP()
	{
		return getIndexInHeap()/12;
	}

	@Override
	public int compareTo(Card another) 
	{
		if(getNumber()==another.getNumber())
		{
			return -compare(getSuit(), another.getSuit());
		}
		else 
		{
			return compare(mCompareIndex.lastIndexOf(getNumber()),
					mCompareIndex.lastIndexOf(another.getNumber()));
			
		}
	}
	
	private int compare(int a, int b)
	{
		return a==b?0:(a>b?1:-1);
	}
	
	
	public static List<Integer> mCompareIndex=new ArrayList<Integer>()
	{
		{add(2);}
		{add(3);}
		{add(4);}
		{add(5);}
		{add(6);}
		{add(7);}
		{add(8);}
		{add(9);}
		{add(10);}
		{add(11);}
		{add(12);}
		{add(13);}
		{add(1);}
		{add(0);}
		{add(14);}
		{add(15);}
	};
	
	public static int onTureNumberIndex=13;
	
	public static final String[] cardString=new String[]
			{"A","2","3","4","5","6","7","8","9","10","J","Q","K","JOKER","JOKER"};
	
	public static final String[] cardColor=new String[]{"♣","♠","♦","♥"};
}
