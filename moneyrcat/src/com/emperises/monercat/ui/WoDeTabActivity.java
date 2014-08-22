package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emperises.monercat.BaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.MyInfo;
import com.emperises.monercat.interfacesandevents.HeaderImageEvent;
import com.emperises.monercat.ui.v3.ActivityMyInfo;

public class WoDeTabActivity extends BaseActivity {

	private ImageView mHeaderImage;
	private TextView genderAgeAddr;
	private TextView mNicknameText;
	private TextView mTelText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wodetable);
	}

	@Override
	protected void initViews() {
		HeaderImageEvent.getInstance().addHeaderImageListener(this);
		mHeaderImage = (ImageView) findViewById(R.id.headerImage);
		mHeaderImage.setBackgroundResource(getHeadImageResId());
		genderAgeAddr = (TextView) findViewById(R.id.mytab_genderageaddr);
		mNicknameText = (TextView) findViewById(R.id.mytab_nickname);
		mTelText = (TextView) findViewById(R.id.mytab_tel);
		setMyInfo();
	}

	private void setMyInfo() {
		MyInfo info = getMyInfoForDatabase();
		if(info != null){
			String gender = info.getGender();
			String age = info.getAge();
			String addr = info.getAddress();
			genderAgeAddr.setText(gender+age+"岁 "+addr);
			mNicknameText.setText(info.getNickName());
			mTelText.setText(info.getTelNumber());
		}
	}

	@Override
	public void onMyInfoChange(MyInfo info) {
		super.onMyInfoChange(info);
		setMyInfo();
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
