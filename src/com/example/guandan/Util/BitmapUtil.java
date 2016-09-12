package com.example.guandan.Util;

import android.graphics.Bitmap;

public class BitmapUtil
{
	private static Bitmap bmp_maingame;
	
	private static Bitmap bmp_cardSurface;
	
	public static void setBMPMainGame(Bitmap bmp)
	{
		bmp_maingame=bmp;
	}
	
	public static Bitmap getBMPMainGame()
	{
		return bmp_maingame;
	}
	
	public static void setBMPCardSurface(Bitmap bmp)
	{
		bmp_cardSurface=bmp;
	}
	
	public static Bitmap getBMPCardSurface()
	{
		return bmp_cardSurface;
	}
	
	public static final int surfaceLeft=0;
	
	public static final int surfaceTop=0;
	
	public static final int surfaceWidth=86;
	
	public static final int surfaceHeight=146;
	
	public static final int surfaceWidthLong=557;
	
	
	
	
}
