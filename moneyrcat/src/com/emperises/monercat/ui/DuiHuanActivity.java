package com.emperises.monercat.ui;

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
import com.emperises.monercat.ui.v3.ActivityDuiHuanJiLu;

public class DuiHuanActivity extends OtherBaseActivity implements
		OnItemClickListener {

	private ListView mDuiHuanListView;
	private Button mMXButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan);
		setCurrentTitle(getString(R.string.chaozhiduihuan));
	}

	@Override
	protected void initViews() {
		mDuiHuanListView = (ListView) findViewById(R.id.duihuanListView);
		mDuiHuanListView.setAdapter(new MyAdapter());
		mDuiHuanListView.setOnItemClickListener(this);
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mMXButton.setText("兑换记录");
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 3;
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
			View v = getLayoutInflater().inflate(R.layout.list_duihuan_item,
					null);
			ImageView i = (ImageView) v.findViewById(R.id.duihuan_icon);
			TextView t = (TextView) v.findViewById(R.id.duihuan_title);
			if(position == 1){
				t.setText("东恒服饰金卡");
				i.setBackgroundResource(R.drawable.huiyuanka1);
			}else if(position == 2){
				t.setText("万东国际金卡");
				i.setBackgroundResource(R.drawable.huiyuanka2);
			}
			return v;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(this, DuiHuanDialogActivity.class));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this, ActivityDuiHuanJiLu.class));
			break;

		default:
			break;
		}
	}
}
