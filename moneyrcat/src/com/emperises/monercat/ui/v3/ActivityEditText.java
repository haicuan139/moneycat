package com.emperises.monercat.ui.v3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.interfaces.EditMyInfoEvent;
import com.tencent.a.b.e;

public class ActivityEditText extends OtherBaseActivity {

	private EditText mEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edittext);
		setCurrentTitle("编辑信息");
	}

	@Override
	protected void initViews() {
		super.initViews();
		mEditText = (EditText) findViewById(R.id.edit_text);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		String defaultValue = getIntent().getStringExtra(INTENT_KEY_EDIT_VALUE);
		mEditText.setText(defaultValue);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.edit_sure:
			// 编辑确定
			int editType = getIntent().getIntExtra(INTENT_KEY_EDIT_TYPE, 0);
			String value = mEditText.getText().toString();
			postEditType(editType, value);
			finish();
			break;
		case R.id.edit_cancel:
			// 编辑取消
			finish();
			break;

		default:
			break;
		}
	}

	private void postEditType(int editType, String value) {
		if (TextUtils.isEmpty(value)) {
			return;
		}
		switch (editType) {
		case R.id.editinfo_address:
			EditMyInfoEvent.getInstance().fireAddressEditEvent(value);
			break;
		case R.id.editinfo_age:
			EditMyInfoEvent.getInstance().fireAgeEditEvent(value);
			break;
		case R.id.editinfo_gender:
			EditMyInfoEvent.getInstance().fireGenderEditEvent(value);
			break;
		case R.id.editinfo_nickname:
			EditMyInfoEvent.getInstance().fireNickNameEditEvent(value);
			break;
		default:
			break;

		}
	}
}