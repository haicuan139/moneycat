package com.emperises.monercat.ui.v3;

import android.os.Bundle;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class ActivityMyInfo extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		setCurrentTitle("我的信息");
	}
	@Override
	protected void initViews() {
		super.initViews();
	}
}
