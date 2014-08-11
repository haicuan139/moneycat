package com.emperises.monercat.ui.v3;

import android.os.Bundle;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;


public class ActivityMessageList extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_list);
		setCurrentTitle("消息列表");
	}
	@Override
	protected void initViews() {
		super.initViews();
	}
}
