package com.emperises.monercat.ui.v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.MyInfo;
import com.emperises.monercat.interfacesandevents.EditMyInfoEvent;
import com.emperises.monercat.interfacesandevents.EditMyInfoInterface;

public class ActivityEditMyinfo extends OtherBaseActivity implements
		EditMyInfoInterface {
	private TextView mAddressText;
	private TextView mAgeText;
	private TextView mGenderText;
	private TextView mNicknameText;
	private MyInfo mInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editmyinfo);
		setCurrentTitle("编辑信息");

	}

	@Override
	protected void initViews() {
		super.initViews();
		MyInfo info = getMyInfoForDatabase();
		mAddressText = (TextView) findViewById(R.id.editinfo_addresstext);
		mAgeText = (TextView) findViewById(R.id.editinfo_agetext);
		mGenderText = (TextView) findViewById(R.id.editinfo_gendertext);
		mNicknameText = (TextView) findViewById(R.id.editinfo_nicknametext);
		mInfo = getDatabaseInterface().getMyInfo();
		if(info != null){
			mAddressText.setText(info.getAddress());
			mAddressText.setText(info.getAge()+"岁");
			mGenderText.setText(info.getGender());
			mNicknameText.setText(info.getNickName());
		}
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, ActivityEditText.class);
		// mAgeText.setText(age);
		// mNicknameText.setText(nickNmae);
		// mGenderText.setText(gender);
		// mAddressText.setText(address);
		super.onClick(v);
		switch (v.getId()) {
		case R.id.editinfo_address:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_address);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mAddressText.getText().toString());
			startActivity(i);
			break;
		case R.id.editinfo_age:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_age);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mAgeText.getText().toString());
			startActivity(i);
			break;
		case R.id.editinfo_gender:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_gender);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mGenderText.getText().toString());
			startActivity(i);
			break;
		case R.id.editinfo_nickname:
			i.putExtra(INTENT_KEY_EDIT_TYPE, R.id.editinfo_nickname);
			i.putExtra(INTENT_KEY_EDIT_VALUE, mNicknameText.getText()
					.toString());
			startActivity(i);
			break;
		case R.id.editinfo_done_button:
			finish();
			// //修改完成将数据插入到数据库中
			saveMyInfo();
			break;
		default:
			break;
		}

	}

	private void saveMyInfo() {
		getDatabaseInterface().saveMyInfo(mInfo);
	}

	@Override
	public void onInfoEditAfter(MyInfo info) {

	}

	@Override
	public void onAgeEditAfter(String age) {
		mAgeText.setText(age);
		mInfo.setAge(age);
	}

	@Override
	public void onNickNameEditAfter(String nickNmae) {
		mNicknameText.setText(nickNmae);
		mInfo.setNickName(nickNmae);
	}

	@Override
	public void onGenderEditAfter(String gender) {
		mGenderText.setText(gender);
		mInfo.setGender(gender);
	}

	@Override
	public void onAddressEditAfter(String address) {
		mAddressText.setText(address);
		mInfo.setAddress(address);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EditMyInfoEvent.getInstance().removeListener(this);
	}
}
