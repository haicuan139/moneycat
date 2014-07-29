package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.AdPagerAdapter;
import com.emperises.monercat.custom.AdViewPager;
import com.emperises.monercat.utils.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HomeActivity extends BaseActivity implements OnRefreshListener<ListView> ,OnPageChangeListener ,OnItemClickListener
{
	private PullToRefreshListView mAdListView;
	private static final int REFRESH_COMPLETE = 1;
	private int currentIndexPosition;
    private boolean isContinue = true;
    private Timer timer;
    private static final int initPositon = 0;
    private static int currentPosition = initPositon;
	private AdViewPager mAdPager;
	private AdPagerAdapter mPagerAdapter;
	private MyAdAdapter mAdListAdapter;
    private static final int VIEWPAGER_SCROLL = 0;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mAdListView.onRefreshComplete();
				break;
			case VIEWPAGER_SCROLL:
				Logger.i("PAGER", "current:"+currentPosition);
				mAdPager.setCurrentItem(currentPosition);
				break;

			default:
				break;
			}
		}
	};
	private List<View> mListImage;
	private LinearLayout mPagerIndexLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	
	@Override
	protected void initViews() {
		mAdPager = (AdViewPager) findViewById(R.id.adPager);
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
		timer = new Timer();
        timer.schedule(new TimerTask()
        {
			public void run()
            {
                while (true)
                {
                    if (isContinue)
                    {
                    	currentIndexPosition++;
                    	if(currentIndexPosition == 4){
                    		currentIndexPosition = 0;
                    	}
                        currentPosition ++;
                        mHandler.sendEmptyMessage(VIEWPAGER_SCROLL);
                        sleep(3000);
                    }
                    
                }
            }
        }, 4000);
        mAdListView.setOnRefreshListener(this);
		mAdListAdapter = new MyAdAdapter(); 
		mAdListView.setAdapter(mAdListAdapter);
		///////////////////////
		mPagerAdapter = new AdPagerAdapter(mListImage);
		mAdPager.setAdapter(mPagerAdapter);
        mAdPager.setCurrentItem(initPositon);
        mAdPager.setOnPageChangeListener(this);
        mAdPager.setOnTouchListener(new MyTouchListener());
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
	 class MyTouchListener implements OnTouchListener
	    {

	        @Override
	        public boolean onTouch(View v, MotionEvent event)
	        {
	            switch (event.getAction())
	            {
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

	private void sleep(long time){
        try
        {
           Thread.sleep(time); 
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}


	@Override
	public void onPageSelected(int position) {
		Logger.i("PAGER", "onPageSelected:"+position);
		   currentPosition = position;
		   changeIndexBg(currentIndexPosition);
	}
	
	private void changeIndexBg(int currentPosition){
		for (int i = 0; i < mPagerIndexLayout.getChildCount(); i++) {
			ImageView bg = (ImageView) mPagerIndexLayout.getChildAt(i);
			if(i == currentPosition){
				bg.setBackgroundResource(R.drawable.circle_selected);
//				bg.setBackgroundColor(Color.parseColor("#c80019"));
			}else{
//				bg.setBackgroundColor(Color.parseColor("#f0f0f0"));
				bg.setBackgroundResource(R.drawable.circle_noraml);
			}
		}
	}

 
	@Override
	public void onItemClick(AdapterView<?> pView, View itemView, int position, long id) {
		startActivityWithAnimation(new Intent(this,AdDetailActivity.class));
		
	}


}
