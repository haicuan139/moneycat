package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HomeActivity extends BaseActivity implements OnRefreshListener<ListView> ,OnPageChangeListener ,OnItemClickListener
{
	private PullToRefreshListView mAdListView;
	private static final int REFRESH_COMPLETE = 1;
	private MyAdAdapter mAdListAdapter;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mAdListView.onRefreshComplete();
				break;
			default:
				break;
			}
		}
	};
	private List<View> mListImage;
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	
	@Override
	protected void initViews() {
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		mAdListView = (PullToRefreshListView) findViewById(R.id.adListView);
		mAdListView.setOnItemClickListener(this);
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
        mAdListView.setOnRefreshListener(this);
		mAdListAdapter = new MyAdAdapter(); 
		mAdListView.setAdapter(mAdListAdapter);
		///////////////////////
		mAdPager.setAdapter(new ImagePagerAdapter(this, mListImage).setInfiniteLoop(true));
		mAdPager.setInterval(2000);
		mAdPager.startAutoScroll();
		mAdPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
				% mListImage.size());
        mAdPager.setOnPageChangeListener(this);
        mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
        
	}
	class MyAdAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View v = getLayoutInflater().inflate(R.layout.list_ad_item, null);
			ImageView i = (ImageView) v.findViewById(R.id.adIcon);
			if(position % 2 == 0){
				i.setBackgroundResource(R.drawable.test01);
			}else{
				i.setBackgroundResource(R.drawable.test02);
			}
			return v; 
		}
		
	}
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(this, System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		mAdListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(1000);
				mHandler.sendEmptyMessage(REFRESH_COMPLETE);
			}
		}).start();	
	}



	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
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
	private void changeIndexBg(int currentPosition){
		for (int i = 0; i < mPagerIndexLayout.getChildCount(); i++) {
			ImageView bg = (ImageView) mPagerIndexLayout.getChildAt(i);
			if(i == currentPosition){
				bg.setBackgroundResource(R.drawable.circle_selected);
			}else{
				bg.setBackgroundResource(R.drawable.circle_noraml);
			}
		}
	}

 
	@Override
	public void onItemClick(AdapterView<?> pView, View itemView, int position, long id) {
		startActivityWithAnimation(new Intent(this,AdDetailActivity.class));
		
	}


}
