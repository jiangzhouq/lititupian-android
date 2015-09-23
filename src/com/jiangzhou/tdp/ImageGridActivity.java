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

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.baidu.mobstat.StatService;
import com.jiangzhou.tdp.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageGridActivity extends AbsListViewBaseActivity {

	ArrayList<String> imageUrls = new ArrayList<String>();
	DisplayImageOptions options;

	private View mDecorView;
	private int numCol = 3;
	static int mCurFirstPosition;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ac_image_grid);
		String[] sArgs = getIntent().getStringArrayExtra(Constants.IMAGES_LIGHT);
		Cursor cur = getContentResolver().query(Pic.CONTENT_URI, null, Pic.COLUMN_DEFAULT_MODE + " = ? and " + Pic.COLUMN_DEFAULT_CATEGORY + " = ?", sArgs, null);
//		Cursor cur = getContentResolver().query(Pic.CONTENT_URI, null, null, null, null);
		if (Constants.LOG_ENABLE) {
			Log.d("qiqi", "sArgs" + sArgs[0] + " " + sArgs[1]);
			Log.d("qiqi", "cur.getCount:" + cur.getCount() + "");
		}
		while(cur.moveToNext()){
			imageUrls.add(cur.getString(cur.getColumnIndex(Pic.COLUMN_DEFAULT_NAME)));
		}
		mDecorView = getWindow().getDecorView();
		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		listView = (GridView) findViewById(R.id.gridview);
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			numCol = 4;
		}else{
			numCol =3;
		}
		((GridView)listView).setNumColumns(numCol);
		((GridView) listView).setAdapter(new ImageAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		listView.setSelection(mCurFirstPosition);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
			}
			
			@Override
			public void onScroll(AbsListView arg0, int firstItem, int visibleCount, int totalCount) {
				mCurFirstPosition = firstItem ;
			}
		});
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}
	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		String[] urls = new String[]{};
		urls = imageUrls.toArray(urls);
		intent.putExtra(Extra.IMAGES, urls);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 20:
			listView = (GridView) findViewById(R.id.gridview);
			listView.setSelection(data.getIntExtra("mPagerPosition", 0));
			break;

		default:
			break;
		}
	}
	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}

	public class ImageAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);
				FrameLayout.LayoutParams imgvwDimens = 
				        new FrameLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/numCol, getWindowManager().getDefaultDisplay().getWidth()/numCol);
				holder.imageView.setLayoutParams(imgvwDimens);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			imageLoader.displayImage("http://106.186.22.172/mode/" + imageUrls.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
										 @Override
										 public void onLoadingStarted(String imageUri, View view) {
											 holder.progressBar.setProgress(0);
											 holder.progressBar.setVisibility(View.VISIBLE);
										 }

										 @Override
										 public void onLoadingFailed(String imageUri, View view,
												 FailReason failReason) {
											 holder.progressBar.setVisibility(View.GONE);
										 }

										 @Override
										 public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
											 holder.progressBar.setVisibility(View.GONE);
										 }
									 }, new ImageLoadingProgressListener() {
										 @Override
										 public void onProgressUpdate(String imageUri, View view, int current,
												 int total) {
											 holder.progressBar.setProgress(Math.round(100.0f * current / total));
										 }
									 }
			);

			return view;
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
			numCol = 4;
			hideSystemUI();
		}else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			numCol = 3;
			showSystemUI();
		}
		((GridView)listView).setNumColumns(numCol);
		((GridView) listView).setAdapter(new ImageAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		listView.setSelection(mCurFirstPosition);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				numCol = arg1;
			}
		});
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
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);}
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
}