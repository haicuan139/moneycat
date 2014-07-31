package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.MainActivity;
import com.emperises.monercat.R;

public class SplashActivity extends BaseActivity {

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			startActivity(new Intent(SplashActivity.this,MainActivity.class));
			finish();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(1000);
				mHandler.sendEmptyMessage(0);
			}
		}).start();;
	}
	@Override
	protected void initViews() {
		
	}

}
