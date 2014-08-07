package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.customview.CustomDialog;
import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.customview.DialogManager;

public class WoDeTabActivity extends BaseActivity implements
		OnPageChangeListener {
	private List<View> mListImage;
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;

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
					startActivityWithAnimation(new Intent(WoDeTabActivity.this , AdDetailActivity.class));
				}
			});
		}
		// /////////////////////
		mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
		findViewById(R.id.wodeguanggao).setOnClickListener(this);;
		findViewById(R.id.woyaotixian).setOnClickListener(this);
		findViewById(R.id.shouzhimingxi).setOnClickListener(this);
		findViewById(R.id.chaozhiduihuan).setOnClickListener(this);
		mAdPager.setAdapter(new ImagePagerAdapter(this, mListImage).setInfiniteLoop(true));
		mAdPager.setInterval(2000);
		mAdPager.startAutoScroll();
		mAdPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
				% mListImage.size());
		mAdPager.setOnPageChangeListener(this);
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
		currentIndex ++ ;
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
			startActivityWithAnimation(new Intent(this,WoDebActivity.class));
			break;
		case R.id.woyaotixian:
			startActivityWithAnimation(new Intent(this,TiXianActivity.class));
			break;
		case R.id.shouzhimingxi:
			startActivityWithAnimation(new Intent(this,MingXiActivity.class));
			break;
		case R.id.chaozhiduihuan:
//			startActivityWithAnimation(new Intent(this,DuiHuanActivity.class));
			showDialog("标题","消息", new CustomDialog.DialogClick() {
				@Override
				public void onClick(View v) {
					super.onClick(v);
					showToast("点击了确定");
				}
			}, new DialogClick() {
				@Override
				public void onClick(View v) {
					super.onClick(v);
					showToast("点击了取消");
				}
			});
			break;

		default:
			break;
		}
	}
}
