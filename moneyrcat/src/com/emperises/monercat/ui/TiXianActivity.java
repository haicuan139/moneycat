package com.emperises.monercat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class TiXianActivity extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian);
		setCurrentTitle("我要提现");
	}
	@Override
	protected void initViews() {
		
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tixian_rel:
			startActivity(new Intent(this , TiXianDialogActivity.class));
			break;
		case R.id.duihuan_rel:
			startActivity(new Intent(this , DuiHuanActivity.class));
			break;

		default:
			break;
		}
	}

}
