package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;

public class MingXiActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mingxi);
		setTitle("ÊÕÖ§Ã÷Ï¸");
	}
	@Override
	protected void initViews() {
		ListView mMinxiListView = (ListView) findViewById(R.id.mingxiListView);
		mMinxiListView.setAdapter(new MyAdapter());
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
			View v = getLayoutInflater().inflate(R.layout.list_mingxi_item, null);
			return v;
		}
		
	}
}
