package com.emperises.monercat.ui.v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.MyInfo;
import com.emperises.monercat.interfaces.EditMyInfoEvent;
import com.emperises.monercat.interfaces.EditMyInfoInterface;

public class ActivityEditMyinfo extends OtherBaseActivity implements
		EditMyInfoInterface {
	private TextView mAddressText;
	private TextView mAgeText;
	private TextView mGenderText;
	private TextView mNicknameText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editmyinfo);
		setCurrentTitle("编辑信息");
		EditMyInfoEvent.getInstance().addEditInfoListener(this);
	}

	@Override
	protected void initViews() {
		super.initViews();
		mAddressText = (TextView) findViewById(R.id.editinfo_addresstext);
		mAgeText = (TextView) findViewById(R.id.editinfo_agetext);
		mGenderText = (TextView) findViewById(R.id.editinfo_gendertext);
		mNicknameText = (TextView) findViewById(R.id.editinfo_nicknametext);
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, ActivityEditText.class);
//		mAgeText.setText(age);
//		mNicknameText.setText(nickNmae);
//		mGenderText.setText(gender);
//		mAddressText.setText(address);
		super.onClick(v);
		switch (v.getId()) {
		case R.id.editinfo_address:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_address);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mAddressText.getText().toString());
			break;
		case R.id.editinfo_age:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_age);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mAgeText.getText().toString());
			break;
		case R.id.editinfo_gender:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_gender);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mGenderText.getText().toString());
			break;
		case R.id.editinfo_nickname:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_nickname);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mNicknameText.getText().toString());
			break;
		case R.id.editinfo_done_button:
			finish();
//			//修改完成将数据插入到数据库中
//			try {
//				MyInfo info = (MyInfo) getDatabaseInterface().queryDatabaseForClass(MyInfo.class, null, null);
//				if(info == null){
//					//第一次设置个人信息，插入一条数据
//					getDatabaseInterface().insertDataForObjs(objs);
//				}else{
//					//修改信息
//				}
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
			break;
		default:
			break;
		}
		startActivity(i);
	}

	@Override
	public void onInfoEditAfter(MyInfo info) {

	}

	@Override
	public void onAgeEditAfter(String age) {
		mAgeText.setText(age);
	}

	@Override
	public void onNickNameEditAfter(String nickNmae) {
		mNicknameText.setText(nickNmae);
	}

	@Override
	public void onGenderEditAfter(String gender) {
		mGenderText.setText(gender);
	}

	@Override
	public void onAddressEditAfter(String address) {
		mAddressText.setText(address);
	}
}
