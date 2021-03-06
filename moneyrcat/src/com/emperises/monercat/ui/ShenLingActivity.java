package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class ShenLingActivity extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shenling);
		setCurrentTitle(getString(R.string.guanggaoshenling));
	}
	@Override
	protected void initViews() {
		super.initViews();
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.recommend_bt:
			startActivity(new Intent(this , RecommendDialogActivity.class));
			break;
		case R.id.shenling_pengyouquan:
			openShare();
			break;
		default:
			break;
		}
	}
}
