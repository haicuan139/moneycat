package com.emperises.monercat.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.ADInfo;
import com.emperises.monercat.ui.HomeActivity.MyAdAdapter;
import com.emperises.monercat.ui.v3.ActivityAdDeatil;
import com.emperises.monercat.ui.v3.ActivityShenLinJiLu;

public class WoDebActivity extends OtherBaseActivity implements
		OnItemClickListener {

	private ListView mAdList;
	private Button mMXBtutton;
	private MyAdapter mAdListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		setCurrentTitle(getString(R.string.myad));
	}
	private void initTestData() {
		//初始化测试数据
		//中福在线
		List<ADInfo> mAdInfos = new ArrayList<ADInfo>();
		
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
		mAdListAdapter = new MyAdapter(mAdInfos);
		mAdList.setAdapter(mAdListAdapter);
	}
	@Override
	protected void initViews() {
		mMXBtutton = (Button) findViewById(R.id.mingxi_button);
		mMXBtutton.setText("申领记录");
		mAdList = (ListView) findViewById(R.id.adListView);
		mAdList.setOnItemClickListener(this);
		initTestData();
	}

	class MyAdapter extends BaseAdapter {
		private List<ADInfo> mAdInfos;

		public MyAdapter(List<ADInfo> mAdInfos) {
			this.mAdInfos = mAdInfos;
		}

		@Override
		public int getCount() {
			return mAdInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
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
			adIcon.setBackgroundResource(mAdInfos.get(position)
					.getAdIconResId());

			return v;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(this, ActivityAdDeatil.class));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this, ActivityShenLinJiLu.class));
			break;
		default:
			break;
		}
	}
}
