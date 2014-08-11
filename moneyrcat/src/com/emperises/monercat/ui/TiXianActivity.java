package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.ui.v3.ActivityTiXianJiLu;

public class TiXianActivity extends OtherBaseActivity {

	private Button mMXButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian);
		setCurrentTitle(getString(R.string.woyaotixian));
	}
	@Override
	protected void initViews() {
		super.initViews();
		mMXButton = (Button) findViewById(R.id.mingxi_button);
		mMXButton.setText("提现记录");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tixian_rel:
			startActivity(new Intent(this , TiXianDialogActivity.class));
			break;
		case R.id.duihuan_rel:
			startActivity(new Intent(this , DuiHuanActivity.class));
			break;
		case R.id.mingxi_button:
			startActivity(new Intent(this , ActivityTiXianJiLu.class));
			break;
		default:
			break;
		}
	}

}
