package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.emperises.monercat.utils.Logger;

public class WoDeTabActivity extends BaseActivity implements
		OnPageChangeListener {
	private List<View> mListImage;
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;
	private static final int START_AUTO_VIEWPAGER = 2;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case START_AUTO_VIEWPAGER:
				mAdPager.startAutoScroll();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodetable);
	}

	@Override
	protected void initViews() {
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		mListImage = new ArrayList<View>();
		ImageView i = new ImageView(this);
		i.setBackgroundResource(R.drawable.adtest1);
		ImageView i1 = new ImageView(this);
		i1.setBackgroundResource(R.drawable.adtest2);
		ImageView i2 = new ImageView(this);
		i2.setBackgroundResource(R.drawable.adtest3);
		ImageView i3 = new ImageView(this);
		i3.setBackgroundResource(R.drawable.adtest4);
		mListImage.add(i);
		mListImage.add(i1);
		mListImage.add(i2);
		mListImage.add(i3);
		for (View im : mListImage) {
			im.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivityWithAnimation(new Intent(WoDeTabActivity.this,
							AdDetailActivity.class));
				}
			});
		}
		// /////////////////////
		mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
		findViewById(R.id.wodeguanggao).setOnClickListener(this);
		;
		findViewById(R.id.woyaotixian).setOnClickListener(this);
		findViewById(R.id.myinfo).setOnClickListener(this);
		findViewById(R.id.chaozhiduihuan).setOnClickListener(this);
		mAdPager.setAdapter(new ImagePagerAdapter(this, mListImage)
				.setInfiniteLoop(true));
		mAdPager.setInterval(2000);
		mAdPager.setStopScrollWhenTouch(true);
		mAdPager.startAutoScroll();
		mAdPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
				% mListImage.size());
		mAdPager.setOnPageChangeListener(this);
		mAdPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mAdPager.stopAutoScroll();
					Logger.i("AUTOSCROLL", "ACTION_DOWN");
					break;
				case MotionEvent.ACTION_UP:
					new Thread(new Runnable() {
						@Override
						public void run() {
							SystemClock.sleep(1000);
							mHandler.sendEmptyMessage(START_AUTO_VIEWPAGER);
						}
					}).start();
					Logger.i("AUTOSCROLL", "ACTION_UP");
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		currentIndex++;
		if (currentIndex == 4) {
			currentIndex = 0;
		}
		changeIndexBg(currentIndex);
	}

	private int currentIndex = 0;

	private void changeIndexBg(int currentPosition) {
		for (int i = 0; i < mPagerIndexLayout.getChildCount(); i++) {
			ImageView bg = (ImageView) mPagerIndexLayout.getChildAt(i);
			if (i == currentPosition) {
				bg.setBackgroundResource(R.drawable.circle_selected);
			} else {
				bg.setBackgroundResource(R.drawable.circle_noraml);
			}
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.wodeguanggao:
			startActivityWithAnimation(new Intent(this, WoDebActivity.class));
			break;
		case R.id.woyaotixian:
			startActivityWithAnimation(new Intent(this, TiXianActivity.class));
			break;
		case R.id.myinfo:
			startActivityWithAnimation(new Intent(this, ActivityMyInfo.class));
			break;
		case R.id.chaozhiduihuan:
			startActivityWithAnimation(new Intent(this, DuiHuanActivity.class));
			break;

		default:
			break;
		}
	}
}
