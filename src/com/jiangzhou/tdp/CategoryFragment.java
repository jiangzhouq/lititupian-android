package com.jiangzhou.tdp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public final class CategoryFragment extends Fragment implements OnClickListener{
	private static final String KEY_CONTENT = "TestFragment:Content";
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageForEmptyUri(R.drawable.ic_empty)
	.showImageOnFail(R.drawable.ic_error)
	.resetViewBeforeLoading(true)
	.cacheOnDisk(true)
	.imageScaleType(ImageScaleType.EXACTLY)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.considerExifParams(true)
	.displayer(new FadeInBitmapDisplayer(300))
	.build();
	
	private Cursor mCursor;
	private int mDirect;
	private int mModeChoice = 0;
	private Context mContext;
	public static CategoryFragment newInstance(Context context,int content , Cursor cur, int direct, int mode_choice) {
		CategoryFragment fragment = new CategoryFragment();
		fragment.mDirect = direct;
		fragment.mCursor = cur;
		fragment.mContent = content;
		fragment.mModeChoice = mode_choice;
		fragment.mContext = context;
		Log.d("qiqi", "cur.getCount():" + cur.getCount());
		return fragment;
	}
	private int  mContent = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getInt(KEY_CONTENT);
		}
		LinearLayout layout = null;
		int[] imageViewIds;
		int[] titleIds;
		if(mDirect == Configuration.ORIENTATION_LANDSCAPE){
			layout = (LinearLayout) inflater.inflate(R.layout.category_pager_landscape, container, false);
			imageViewIds = new int[]{
					R.id.category1,
					R.id.category2,
					R.id.category3,
					R.id.category4,
					R.id.category5};
			titleIds = new int[]{
					R.id.title1,
					R.id.title2,
					R.id.title3,
					R.id.title4,
					R.id.title5,
			};
		}else{
			layout = (LinearLayout) inflater.inflate(R.layout.category_pager_portrait, container, false);
			imageViewIds = new int[]{
					R.id.category1,
					R.id.category2,
					R.id.category3,
					R.id.category4,
					R.id.category5,
					R.id.category6,
					R.id.category7,
					R.id.category8,
					R.id.category9};
			titleIds = new int[]{
					R.id.title1,
					R.id.title2,
					R.id.title3,
					R.id.title4,
					R.id.title5,
					R.id.title6,
					R.id.title7,
					R.id.title8,
					R.id.title9
			};
		}
		for (int i = 0; i < imageViewIds.length; i++){
			if((mContent * imageViewIds.length + i) < mCursor.getCount()){
				ImageView imageView = (ImageView) layout.findViewById(imageViewIds[i]);
				TextView text = (TextView) layout.findViewById(titleIds[i]);
				int cur = mContent*imageViewIds.length + i;
				if (Constants.LOG_ENABLE) {
					Log.d("qiqi", "move to :" + cur);
				}
				mCursor.moveToPosition(mContent*imageViewIds.length + i);
				String cName = mCursor.getString(mCursor.getColumnIndex(Pic.COLUMN_DEFAULT_CATEGORY));
				text.setText(cName);
				Log.d("qiqi", mCursor.getString(mCursor.getColumnIndex(Pic.COLUMN_DEFAULT_CATEGORY)));
				imageView.setTag(cName);
				Cursor categoryCur = mContext.getContentResolver().query(Category.CONTENT_URI, null, Category.COLUMN_DEFAULT_NAME + "=?", new String[]{cName}, null);
				categoryCur.moveToFirst();
				if (mDirect == Configuration.ORIENTATION_LANDSCAPE){					
					imageLoader.displayImage(Constants.CATEGORY_IMAGE_DIR + categoryCur.getString(categoryCur.getColumnIndex(Category.COLUMN_DEFAULT_PORT)), imageView, options);
				}else{
					imageLoader.displayImage(Constants.CATEGORY_IMAGE_DIR + categoryCur.getString(categoryCur.getColumnIndex(Category.COLUMN_DEFAULT_LAND)), imageView, options);
				}
				imageView.setOnClickListener(this);
			}
		}

		return layout;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CONTENT, mContent);
	}

	@Override
	public void onClick(View view) {
		if (Constants.LOG_ENABLE) {
			Log.d("qiqi", view.getTag() + "");
		}
		if(mModeChoice == Constants.MODE_CHOLICE_HONGLAN){
			Intent intent = new Intent(getActivity(), ImageGridActivity.class);
			intent.putExtra(Constants.IMAGES_LIGHT, new String[]{"redblue",(String)view.getTag()});
			getActivity().startActivity(intent);
			
		}else{
			Intent intent = new Intent(getActivity(), ImageGridActivity.class);
			intent.putExtra(Constants.IMAGES_LIGHT,  new String[]{"leftright",(String)view.getTag()});
			getActivity().startActivity(intent);
		}
	}
}
