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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.domain.ADInfo;
import com.emperises.monercat.ui.v3.ActivityAdDetail_HTML5;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.emperises.monercat.utils.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

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
				mPullScrollView.onRefreshComplete();
				break;
			case START_AUTO_VIEWPAGER:
				mAdPager.startAutoScroll();
				break;
			default:
				break;
			}
		}
	};
	private ArrayList<ADInfo> mAdInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
	}
	@Override
	protected void initViews() {
		mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
		mPullScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						SystemClock.sleep(1000);
						mHandler.sendEmptyMessage(REFRESH_COMPLETE);
					}
				}).start();
			}
		});
		mTaskListView = (PullToRefreshListView) findViewById(R.id.taskListView);
		mTaskListView.setOnRefreshListener(this);
		mTaskListView.setAdapter(new MyAdAdapter());
		mTaskListView.setOnItemClickListener(this);
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		mListImage = new ArrayList<View>();
		ImageView i = new ImageView(this);
		i.setBackgroundResource(R.drawable.qiaqiaad);
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
		for (int j = 0; j < mListImage.size(); j++) {
			mListImage.get(j).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(TasksActivity.this,ActivityAdDetail_HTML5.class);
					ADInfo itemInfo = (ADInfo) mAdInfos.get(0);
					i.putExtra(INTENT_KEY_ADINFO, itemInfo);
					startActivityWithAnimation(i);	
				}
			});
		}
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
		initTestData();
	}
	private void initTestData() {
		mAdInfos = new ArrayList<ADInfo>();
		
		ADInfo bmwx4 = new ADInfo();
		bmwx4.setAdDescription("全新BMW M3和全新BMW M4双门跑车即将上市");
		bmwx4.setAdTtile("荣耀之上 传奇不止");
		bmwx4.setAdUrl("http://bmw.thefront.com.cn/bmw-x4/?from=groupmessage&isappinstalled=0#/");
		bmwx4.setAdIconResId(R.drawable.bmtestbg);
		
		ADInfo zfzx = new ADInfo();
		zfzx.setAdDescription("中福在线连环夺宝！点击进入!");
		zfzx.setAdTtile("中福在线");
		zfzx.setAdUrl("http://m.zolcai.com/wap/touch/buy/preBuy.act?lotteryName=ssq");
		zfzx.setAdIconResId(R.drawable.zfzx);
		//王老吉
		ADInfo wanglaoji = new ADInfo();
		wanglaoji.setAdDescription("王老吉幸运摇摇乐！点击马上抽奖！");
		wanglaoji.setAdTtile("凉茶始祖王老吉");
		wanglaoji.setAdUrl("http://wlj2014.21yod.net/index");
		wanglaoji.setAdIconResId(R.drawable.wanglaoji);
		//奇瑞
		ADInfo qirui = new ADInfo();
		qirui.setAdDescription("奇瑞E5以人性化视角观察家庭用车,众多人性科技配备,超越同级的车身尺寸,亲民价为,为您和您的家人打造一个温馨的城堡。");
		qirui.setAdTtile("奇瑞E5");
		qirui.setAdUrl("http://www.chery.cn/wap.php/product?easyname=E5");
		qirui.setAdIconResId(R.drawable.qirui);
		//广汽三菱
		ADInfo gxsl = new ADInfo();
		gxsl.setAdDescription("够劲就来挑战！畅想省会炫动一夏,广汽三菱挑战赛！马上参加！");
		gxsl.setAdTtile("广汽三菱");
		gxsl.setAdUrl("http://m.baidu.com/from=2001c/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_2003%2Cta%40iphone_1_7.1_1_9.6/baiduid=C1D35B1318B1650D2BD6EC9481989296/w=0_10_%E5%B9%BF%E6%B1%BD%E4%B8%89%E8%8F%B1%E5%AE%98%E7%BD%91/t=iphone/l=3/tc?m=8&srd=1&dict=21&src=http%3A%2F%2Fpajero.gmmc.com.cn%2F");
		gxsl.setAdIconResId(R.drawable.sanling);
		//长丰猎豹
		ADInfo cflb = new ADInfo();
		cflb.setAdDescription("猎豹汽车,驰聘无疆！猎豹Q6傲世登场,驾驭飞腾,领略非凡人生！");
		cflb.setAdTtile("猎豹汽车");
		cflb.setAdUrl("http://m.baidu.com/from=2001c/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_2003%2Cta%40iphone_1_7.1_1_9.6/baiduid=C1D35B1318B1650D2BD6EC9481989296/w=0_10_%E9%95%BF%E4%B8%B0%E7%8C%8E%E8%B1%B9%E5%AE%98%E7%BD%91/t=iphone/l=3/tc?m=8&srd=1&dict=21&src=http%3A%2F%2Fwww.leopaard.com%2Fcarmodel%2Fintro.php%3F7");
		cflb.setAdIconResId(R.drawable.changfengliebao);
		//哇哈哈
		ADInfo wahaha = new ADInfo();
		wahaha.setAdDescription("理理气,顺顺心，找我小陈陈");
		wahaha.setAdTtile("哇哈哈饮品");
		wahaha.setAdUrl("http://www.wahaha.com.cn/product/37");
		wahaha.setAdIconResId(R.drawable.wahaha);
		mAdInfos.add(bmwx4);
		mAdInfos.add(wahaha);
		mAdInfos.add(cflb);
		mAdInfos.add(gxsl);
		mAdInfos.add(qirui);
		mAdInfos.add(wanglaoji);
		mAdInfos.add(zfzx);
		
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
			ImageView i = (ImageView) v.findViewById(R.id.task_icon);
			switch (position) {
			case 0:
				title.setText("签到大转盘");
				break;
			case 1:
				title.setText("完善个人信息");
				i.setBackgroundResource(R.drawable.wode_myinfo);
				break;
			case 2:
				title.setText("推荐招财喵");
				i.setBackgroundResource(R.drawable.wode_recommend);
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
	private PullToRefreshScrollView mPullScrollView;

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
