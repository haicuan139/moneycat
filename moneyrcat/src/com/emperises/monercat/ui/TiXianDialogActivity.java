package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class TiXianDialogActivity extends OtherBaseActivity {

	private Button mCloseButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian_dialog);
		setTitleHide(View.GONE);
	}
	@Override
	protected void initViews() {
		mCloseButton = (Button) findViewById(R.id.closeButton);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.closeButton:
			finish();
			break;
		case R.id.commit_bt:
			tixian(queryBalance());
			finish();
			break;
		default:
			break;
		}
	}

}
