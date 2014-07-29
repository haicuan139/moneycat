package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.AdPagerAdapter;
import com.emperises.monercat.custom.AdViewPager;
import com.emperises.monercat.utils.Logger;

public class WoDeTabActivity extends BaseActivity implements
		OnPageChangeListener {
	private int currentIndexPosition;
	private boolean isContinue = true;
	private Timer timer;
	private static final int initPositon = 0;
	private static int currentPosition = initPositon;
	private AdViewPager mAdPager;
	private AdPagerAdapter mPagerAdapter;
	private static final int VIEWPAGER_SCROLL = 0;
	private List<View> mListImage;
	private LinearLayout mPagerIndexLayout;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case VIEWPAGER_SCROLL:
				Logger.i("PAGER", "current:" + currentPosition);
				mAdPager.setCurrentItem(currentPosition);
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
		mAdPager = (AdViewPager) findViewById(R.id.adPager);
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
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				while (true) {
					if (isContinue) {
						currentIndexPosition++;
						if (currentIndexPosition == 4) {
							currentIndexPosition = 0;
						}
						currentPosition++;
						mHandler.sendEmptyMessage(VIEWPAGER_SCROLL);
						sleep(3000);
					}

				}
			}
		}, 4000);
		// /////////////////////
		mPagerAdapter = new AdPagerAdapter(mListImage);
		mAdPager.setAdapter(mPagerAdapter);
		mAdPager.setCurrentItem(initPositon);
		mAdPager.setOnPageChangeListener(this);
		mAdPager.setOnTouchListener(new MyTouchListener());
		mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
		findViewById(R.id.wodeguanggao).setOnClickListener(this);;
		findViewById(R.id.woyaotixian).setOnClickListener(this);
		findViewById(R.id.shouzhimingxi).setOnClickListener(this);
		findViewById(R.id.chaozhiduihuan).setOnClickListener(this);
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
		currentPosition = position;
		changeIndexBg(currentIndexPosition);
	}

	class MyTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				isContinue = false;
				break;
			case MotionEvent.ACTION_UP:
			default:
				isContinue = true;
				break;
			}
			return false;
		}
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeIndexBg(int currentPosition) {
		for (int i = 0; i < mPagerIndexLayout.getChildCount(); i++) {
			ImageView bg = (ImageView) mPagerIndexLayout.getChildAt(i);
			if (i == currentPosition) {
				bg.setBackgroundResource(R.drawable.circle_selected);
				// bg.setBackgroundColor(Color.parseColor("#c80019"));
			} else {
				// bg.setBackgroundColor(Color.parseColor("#f0f0f0"));
				bg.setBackgroundResource(R.drawable.circle_noraml);
			}
		}
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.wodeguanggao:
			startActivityWithAnimation(new Intent(this,WoDebActivity.class));
			break;
		case R.id.woyaotixian:
			
			break;
		case R.id.shouzhimingxi:
			startActivityWithAnimation(new Intent(this,MingXiActivity.class));
			break;
		case R.id.chaozhiduihuan:
			startActivityWithAnimation(new Intent(this,DuiHuanActivity.class));
			break;

		default:
			break;
		}
	}
}
