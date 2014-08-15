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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.emperises.monercat.utils.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TasksActivity extends  OtherBaseActivity implements OnRefreshListener<ListView> , OnItemClickListener,OnPageChangeListener{
	private List<View> mListImage;
	private LinearLayout mPagerIndexLayout;
	private AutoScrollViewPager mAdPager;
	private static final int START_AUTO_VIEWPAGER = 2;

	private PullToRefreshListView mTaskListView;
	private static final int REFRESH_COMPLETE = 1;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mTaskListView.onRefreshComplete();
				break;
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
		setContentView(R.layout.activity_tasks);
		mTaskListView = (PullToRefreshListView) findViewById(R.id.taskListView);
		mTaskListView.setOnRefreshListener(this);
		mTaskListView.setAdapter(new MyAdAdapter());
		mTaskListView.setOnItemClickListener(this);
	}
	@Override
	protected void initViews() {
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		mListImage = new ArrayList<View>();
		ImageView i = new ImageView(this);
		i.setBackgroundResource(R.drawable.test_task_wahaha);
		ImageView i1 = new ImageView(this);
		i1.setBackgroundResource(R.drawable.test_task_wanglaoji);
		ImageView i2 = new ImageView(this);
		i2.setBackgroundResource(R.drawable.test_task_qirui);
		ImageView i3 = new ImageView(this);
		i3.setBackgroundResource(R.drawable.test_task_bm);
		mListImage.add(i);
		mListImage.add(i1);
		mListImage.add(i2);
		mListImage.add(i3);
		// /////////////////////
		mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
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
	class MyAdAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 4;
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
			View v = getLayoutInflater().inflate(R.layout.list_task_item, null);
			TextView title = (TextView) v.findViewById(R.id.taskTitle);
			switch (position) {
			case 0:
				title.setText("签到大转盘");
				break;
			case 1:
				title.setText("完善个人信息");
				break;
			case 2:
				title.setText("推荐招财喵");
				break;

			default:
				break;
			}
			return v; 
		}
		
	}
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		String label = DateUtils.formatDateTime(TasksActivity.this, System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		mTaskListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(1000);
				mHandler.sendEmptyMessage(REFRESH_COMPLETE);
			}
		}).start();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		switch (position) {
		case 1:
			startActivity(new Intent(this , CustomTaskActivity.class));
			break;
		case 2:
			startActivity(new Intent(this , ActivityMyInfo.class));	
			break;
		case 3:
			openShare();
			break;

		default:
			break;
		}
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
	public void onPageSelected(int arg0) {
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



}
