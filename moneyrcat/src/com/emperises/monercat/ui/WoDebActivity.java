package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class WoDebActivity extends OtherBaseActivity {

	private ListView mAdList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		setCurrentTitle("我的广告");
	}
	@Override
	protected void initViews() {
		mAdList = (ListView) findViewById(R.id.adListView);
		mAdList.setAdapter(new MyAdapter());
	}

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
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
			ImageView i = (ImageView) v.findViewById(R.id.adIcon);
			if(position % 2 == 0){
				i.setBackgroundResource(R.drawable.test01);
			}else{
				i.setBackgroundResource(R.drawable.test02);
			}
			return v;
		}
		
	}
}
