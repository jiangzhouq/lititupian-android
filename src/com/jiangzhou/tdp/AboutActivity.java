package com.jiangzhou.tdp;

import com.baidu.mobstat.StatService;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AboutActivity extends Activity implements OnClickListener {
	TranslateAnimation mShowAction;
	TranslateAnimation mHiddenAction;
	boolean showCall = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_activity);
		Button webBtn = (Button) findViewById(R.id.website);
		webBtn.setOnClickListener(this);
		Button phoneBtn = (Button) findViewById(R.id.phone);
		phoneBtn.setOnClickListener(this);
		RelativeLayout total = (RelativeLayout) findViewById(R.id.total);
		total.setOnClickListener(this);
		ImageView about = (ImageView) findViewById(R.id.about);
		about.setOnClickListener(this);
		Button emailBtn = (Button) findViewById(R.id.email);
		emailBtn.setOnClickListener(this);
		Button callBtn = (Button) findViewById(R.id.call);
		callBtn.setOnClickListener(this);
	}
	@Override
	protected void onResume() {
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
	@Override
	public void onClick(View view) {
		final Button phoneBtn = (Button) findViewById(R.id.phone);
		final LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -3.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(300);
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 3.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f);
		mHiddenAction.setDuration(300);
		switch (view.getId()) {
		case R.id.website:
			Uri uri1 = Uri.parse("http://www.china3-d.com");
			Intent it1 = new Intent(Intent.ACTION_VIEW, uri1);
			startActivity(it1);
			break;
		case R.id.phone:
			showCall = true;
			phoneBtn.setAnimation(mHiddenAction);
			mHiddenAction.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation arg0) {
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					phoneBtn.setVisibility(View.GONE);
				}
			});
			linear.setVisibility(View.VISIBLE);
			linear.startAnimation(mShowAction);
			break;
		case R.id.call:
			Uri uri2 = Uri.parse("tel:075586284330");
			Intent it2 = new Intent(Intent.ACTION_CALL, uri2);
			startActivity(it2);
			break;
		case R.id.email:
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.fromParts("mailto", "jack@china3-d.com", null));
//			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
			break;
		case R.id.about:
		case R.id.total:
			if (showCall) {
				showCall = false;
				linear.setAnimation(mHiddenAction);
				mHiddenAction.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation arg0) {
					}

					@Override
					public void onAnimationRepeat(Animation arg0) {
					}

					@Override
					public void onAnimationEnd(Animation arg0) {
						linear.setVisibility(View.GONE);
					}
				});
				phoneBtn.setVisibility(View.VISIBLE);
				phoneBtn.startAnimation(mShowAction);
			}
			break;

		}
	}
}
