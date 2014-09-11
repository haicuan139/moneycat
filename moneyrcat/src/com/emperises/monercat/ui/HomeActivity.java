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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.adapter.ImagePagerAdapter;
import com.emperises.monercat.domain.ADInfo;
import com.emperises.monercat.domain.MyInfo;
import com.emperises.monercat.ui.v3.ActivityAdDetail_HTML5;
import com.emperises.monercat.utils.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class HomeActivity extends BaseActivity implements OnRefreshListener<ListView> ,OnPageChangeListener ,OnItemClickListener
{
	private PullToRefreshListView mAdListView;
	private MyAdAdapter mAdListAdapter;
	private static final int REFRESH_COMPLETE = 1;
	private static final int START_AUTO_VIEWPAGER = 2;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mAdListView.onRefreshComplete();
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
		mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
		mPullScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//				String label = DateUtils.formatDateTime(HomeActivity.this, System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//				mPullScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						SystemClock.sleep(1000);
						mHandler.sendEmptyMessage(REFRESH_COMPLETE);
					}
				}).start();
			}
		});
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mMXButton.setText(R.string.tixian);
		mAdPager = (AutoScrollViewPager) findViewById(R.id.adPager);
		mAdListView = (PullToRefreshListView) findViewById(R.id.adListView);
		mAdListView.setOnItemClickListener(this);
		mListImage = new ArrayList<View>();
//		ImageView i4 = new ImageView(this);
//		i4.setBackgroundResource(R.drawable.qiaqiaad);
		ImageView i = new ImageView(this);
		i.setBackgroundResource(R.drawable.qiaqiaad);
		ImageView i1 = new ImageView(this);
		i1.setBackgroundResource(R.drawable.testwanglaoji);
		ImageView i2 = new ImageView(this);
		i2.setBackgroundResource(R.drawable.testqirui);
		ImageView i3 = new ImageView(this);
		i3.setBackgroundResource(R.drawable.testbm);
		mListImage.add(i);
		mListImage.add(i1);
		mListImage.add(i2);
		mListImage.add(i3);
		for (int j = 0; j < mListImage.size(); j++) {
			mListImage.get(j).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(HomeActivity.this,ActivityAdDetail_HTML5.class);
					ADInfo itemInfo = (ADInfo) mAdInfos.get(0);
					i.putExtra(INTENT_KEY_ADINFO, itemInfo);
					startActivityWithAnimation(i);	
				}
			});
		}
        mAdListView.setOnRefreshListener(this);
        initTestData();
		 
		mAdListView.setAdapter(mAdListAdapter);
		///////////////////////
		mAdPager.setAdapter(new ImagePagerAdapter(this, mListImage).setInfiniteLoop(true));
		mAdPager.setInterval(2000);
		mAdPager.setStopScrollWhenTouch(true);
		mAdPager.startAutoScroll();
		mAdPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
				% mListImage.size());
        mAdPager.setOnPageChangeListener(this);
        mPagerIndexLayout = (LinearLayout) findViewById(R.id.pagerindex);
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
		
		ADInfo qiaqia = new ADInfo();
		qiaqia.setAdDescription("2014狂欢巴西!抽奖活动点击进入!");
		qiaqia.setAdTtile("更时尚更快乐!就在洽洽瓜子!");
		qiaqia.setAdUrl("http://qiaqia.tmall.com/shop/view_shop.htm?spm=0.0.0.0.fYEF70");
		qiaqia.setAdIconResId(R.drawable.qiaqiaicon);
		mAdInfos.add(qiaqia);
		mAdInfos.add(bmwx4);
		mAdInfos.add(wahaha);
		mAdInfos.add(cflb);
		mAdInfos.add(gxsl);
		mAdInfos.add(qirui);
		mAdInfos.add(wanglaoji);
		mAdInfos.add(zfzx);
		mAdListAdapter = new MyAdAdapter(mAdInfos);
		
	}


	class MyAdAdapter extends BaseAdapter{

		private List<ADInfo> mAdInfos;
		public MyAdAdapter(List<ADInfo> mAdInfos) {
			this.mAdInfos = mAdInfos;
		}
		@Override
		public int getCount() {
			return mAdInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return mAdInfos.get(position - 1);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View v = getLayoutInflater().inflate(R.layout.list_ad_item, null);
			TextView adTitle = (TextView) v.findViewById(R.id.adTitle);
			TextView adDes = (TextView) v.findViewById(R.id.adDescription);
			ImageView adIcon = (ImageView) v.findViewById(R.id.adIcon);
			adTitle.setText(mAdInfos.get(position).getAdTtile());
			adDes.setText(mAdInfos.get(position).getAdDescription());
			adIcon.setBackgroundResource(mAdInfos.get(position).getAdIconResId());
			
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
	private Button mMXButton;
	private List<ADInfo> mAdInfos;
	private PullToRefreshScrollView mPullScrollView;
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
		Intent i = new Intent(this,ActivityAdDetail_HTML5.class);
		ADInfo itemInfo = (ADInfo) mAdListAdapter.getItem(position);
		i.putExtra(INTENT_KEY_ADINFO, itemInfo);
		startActivityWithAnimation(i);
		
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this , TiXianActivity.class));
			break;
		case R.id.wodeyue:
			
			break;

		default:
			break;
		}
	}

}
