package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class DuiHuanActivity extends OtherBaseActivity implements OnItemClickListener{

	private ListView mDuiHuanListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan);
		setCurrentTitle("³¬Öµ¶Ò»»");
	}
	@Override
	protected void initViews() {
		mDuiHuanListView = (ListView) findViewById(R.id.duihuanListView);
		mDuiHuanListView.setAdapter(new MyAdapter());
		mDuiHuanListView.setOnItemClickListener(this);
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
			View v = getLayoutInflater().inflate(R.layout.list_duihuan_item, null);
			return v;
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(this, DuiHuanDialogActivity.class));
	}
}
