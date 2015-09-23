/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jiangzhou.tdp;

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.jiangzhou.tdp.widget.VerticalViewPager;
import com.jiangzhou.tdp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class CategoryActivity extends FragmentActivity {

	private static final String STATE_POSITION = "STATE_POSITION";

	DisplayImageOptions options;
	private ImagePagerAdapter mAdapter;
	private View mDecorView;
	private Cursor mCursor;
	private int mode_choice = 0;
	static int mCurPosition = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mode_choice = getIntent().getExtras().getInt(Constants.MODE_CHOICE_NAME);
		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.resetViewBeforeLoading(true)
			.cacheOnDisk(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			setContentView(R.layout.ac_image_pager_no_actionbar);
			ViewPager pager;
			pager = (ViewPager) findViewById(R.id.pager);
			if(mode_choice == 0){
				mCursor = getContentResolver().query(Pic.CONTENT_URI, null,Pic.COLUMN_DEFAULT_MODE + "=? ) " + " group by " + " ( " + Pic.COLUMN_DEFAULT_CATEGORY, new String[]{"redblue"}, null);
			}else{
				mCursor = getContentResolver().query(Pic.CONTENT_URI, null,Pic.COLUMN_DEFAULT_MODE + "=? ) " + " group by " + " ( " + Pic.COLUMN_DEFAULT_CATEGORY, new String[]{"leftright"}, null);
			}
			mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mCursor,Configuration.ORIENTATION_LANDSCAPE);
			pager.setAdapter(mAdapter);
			pager.setCurrentItem(mCurPosition/5, true);
			Log.d("qiqi", "oncreate land:" + mCurPosition);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int selected) {
					mCurPosition = selected*5;
					Log.d("qiqi", "mCurPositionL:" + mCurPosition);
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}
				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
		}else{
			ViewPager pager;
			setContentView(R.layout.ac_image_pager_no_actionbar);
			pager = (ViewPager) findViewById(R.id.pager);
			if(mode_choice == 0){
				mCursor = getContentResolver().query(Pic.CONTENT_URI, null,Pic.COLUMN_DEFAULT_MODE + "=? ) " + " group by " + " ( " + Pic.COLUMN_DEFAULT_CATEGORY, new String[]{"redblue"}, null);
			}else{
				mCursor = getContentResolver().query(Pic.CONTENT_URI, null,Pic.COLUMN_DEFAULT_MODE + "=? ) " + " group by " + " ( " + Pic.COLUMN_DEFAULT_CATEGORY, new String[]{"leftright"}, null);
			}
			mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mCursor,Configuration.ORIENTATION_PORTRAIT);
			pager.setAdapter(mAdapter);
			pager.setCurrentItem(mCurPosition/9, true);
			Log.d("qiqi", "oncreate port:" + mCurPosition);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int selected) {
					mCurPosition = selected*9;
					Log.d("qiqi", "mCurPositionL:" + mCurPosition);
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}
				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
		}
		mDecorView = getWindow().getDecorView();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
			ViewPager pager;
			setContentView(R.layout.ac_image_pager_no_actionbar);
			pager = (ViewPager) findViewById(R.id.pager);
			mCursor = getContentResolver().query(Pic.CONTENT_URI, null,"1=1 ) " + " group by " + " ( " + Pic.COLUMN_DEFAULT_CATEGORY, null, null);
			mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mCursor,Configuration.ORIENTATION_LANDSCAPE);
			pager.setAdapter(mAdapter);
			pager.setCurrentItem(mCurPosition/5, true);
			Log.d("qiqi", "onchange land:" + mCurPosition);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int selected) {
					mCurPosition = selected*5;
					Log.d("qiqi", "mCurPositionL:" + mCurPosition);
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}
				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
//			hideSystemUI();
		}else{
			ViewPager pager;
			setContentView(R.layout.ac_image_pager_no_actionbar);
			pager = (ViewPager) findViewById(R.id.pager);
			mCursor = getContentResolver().query(Pic.CONTENT_URI, null,"1=1 ) " + " group by " + " ( " + Pic.COLUMN_DEFAULT_CATEGORY, null, null);
			mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mCursor,Configuration.ORIENTATION_PORTRAIT);
			pager.setAdapter(mAdapter);
			pager.setCurrentItem(mCurPosition/9, true);
			Log.d("qiqi", "onchange port:" + mCurPosition);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int selected) {
					mCurPosition = selected*9;
					Log.d("qiqi", "mCurPositionL:" + mCurPosition);
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}
				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
//			showSystemUI();
		}
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
//		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentPagerAdapter {

		private Cursor cursor;
		private int mDirect;
		public ImagePagerAdapter(FragmentManager fm ,Cursor cur, int direct) {
			super(fm);
			cursor = cur;
			mDirect = direct;
			Log.d("qiqi", "new pageadapter");
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			Log.d("qiqi", "mDirect:" + mDirect);
			return CategoryFragment.newInstance(CategoryActivity.this,position , cursor , mDirect, mode_choice);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(mDirect == Configuration.ORIENTATION_LANDSCAPE){
				if(cursor.getCount()%5 == 0)
					return cursor.getCount()/5;
				return cursor.getCount()/5 + 1;
			}else if (mDirect == Configuration.ORIENTATION_PORTRAIT){
				if(cursor.getCount()%9 == 0)
					return cursor.getCount()/9;
				return cursor.getCount()/9 + 1;
			}
			return cursor.getCount()/5 + 1;
		}
	}
	
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//	        super.onWindowFocusChanged(hasFocus);
//	    if (hasFocus) {
//	    	mDecorView.setSystemUiVisibility(
//	                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//	                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//	                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//	                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//	                | View.SYSTEM_UI_FLAG_FULLSCREEN
//	                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
//	}
	// This snippet hides the system bars.
	private void hideSystemUI() {
	    // Set the IMMERSIVE flag.
	    // Set the content to appear under the system bars so that the content
	    // doesn't resize when the system bars hide and show.
	    mDecorView.setSystemUiVisibility(
	            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            | View.SYSTEM_UI_FLAG_IMMERSIVE);
	}

	// This snippet shows the system bars. It does this by removing all the flags
	// except for the ones that make the content appear under the system bars.
	private void showSystemUI() {
	    mDecorView.setSystemUiVisibility(
	            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}
}