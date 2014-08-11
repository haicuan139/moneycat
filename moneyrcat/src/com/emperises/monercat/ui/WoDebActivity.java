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

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.v3.ActivityAdDeatil;
import com.emperises.monercat.ui.v3.ActivityShenLinJiLu;

public class WoDebActivity extends OtherBaseActivity implements OnItemClickListener{

	private ListView mAdList;
	private Button mMXBtutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		setCurrentTitle(getString(R.string.myad));
	}
	@Override
	protected void initViews() {
		mMXBtutton = (Button) findViewById(R.id.mingxi_button);
		mMXBtutton.setText("申领记录");
		mAdList = (ListView) findViewById(R.id.adListView);
		mAdList.setOnItemClickListener(this);
		mAdList.setAdapter(new MyAdapter());
	}

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 4;
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
			if(position == 2){
				v.findViewById(R.id.aditem_content).setVisibility(View.GONE);
				v.setBackgroundResource(R.drawable.testaditemhengfu);
			}else if(position % 2 == 0){
				i.setBackgroundResource(R.drawable.bmtestbg);
			}else{
				i.setBackgroundResource(R.drawable.testbenchi);
			}
			return v; 
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(this , ActivityAdDeatil.class));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mingxi_button:
			startActivity(new Intent(this , ActivityShenLinJiLu.class));
			break;
		default:
			break;
		}
	}
}
