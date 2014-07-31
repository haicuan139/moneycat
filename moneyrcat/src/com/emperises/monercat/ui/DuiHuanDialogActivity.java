package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class DuiHuanDialogActivity extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan_dialog);
	}
	@Override
	protected void initViews() {
		super.initViews();
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.sure_bt:
			showToast("¶Ò»»³É¹¦");
			finish();
			break;

		default:
			break;
		}
	}
}
