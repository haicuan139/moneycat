package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.v3.ActivityMessageList;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TasksActivity extends  BaseActivity implements OnRefreshListener<ListView> , OnItemClickListener{

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
		
	}
	class MyAdAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 2;
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
		if(position == 2){
			startActivity(new Intent(this , ActivityMyInfo.class));	
		}else{
			startActivity(new Intent(this , CustomTaskActivity.class));
		}
	}


}
