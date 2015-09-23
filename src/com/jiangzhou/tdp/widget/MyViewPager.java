package com.jiangzhou.tdp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager{
	public MyViewPager(Context context) {
		super(context);
	}
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	private Toucher mToucher;
	public interface Toucher{
		public void onTouchUp();
		public void onTouchDown();
	}
	public void setToucher( Toucher toucher){
		mToucher = toucher;
	}
	private float mTouchDownX = 0;
	private float mTouchDownY = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			mTouchDownX = event.getX();
			mTouchDownY = event.getY();
			if (null != mToucher)
				mToucher.onTouchDown();
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			
			if(Math.abs(event.getX() - mTouchDownX) < 20 && Math.abs(event.getY() - mTouchDownY) < 20){
				if(null != mToucher)
					mToucher.onTouchUp();
			}
		}
		return super.onTouchEvent(event);
	}
}
