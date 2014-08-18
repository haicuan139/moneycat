package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.interfaces.HeaderImageEvent;
import com.emperises.monercat.ui.v3.ActivityMyInfo;

public class WoDeTabActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodetable);
	}

	@Override
	protected void initViews() {
		HeaderImageEvent.getInstance().addHeaderImageListener(this);
		ImageView mHeaderImage = (ImageView) findViewById(R.id.headerImage);//我的
		mHeaderImage.setBackgroundResource(getHeadImageResId());
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.wodeshenling:
			startActivityWithAnimation(new Intent(this, WoDebActivity.class));
			break;
		case R.id.woyaotixian:
			startActivityWithAnimation(new Intent(this, TiXianActivity.class));
			break;
		case R.id.wodeinfo:
			startActivityWithAnimation(new Intent(this, ActivityMyInfo.class));
			break;
		case R.id.chaozhiduihuan:
			startActivityWithAnimation(new Intent(this, DuiHuanActivity.class));
			break;

		default:
			break;
		}
	}
}
