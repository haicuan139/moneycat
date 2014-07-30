package com.emperises.monercat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OtherBaseActivity extends BaseActivity {

	private TextView titleText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		titleText = (TextView) findViewById(R.id.titleText);
		Button backButton = (Button) findViewById(R.id.backbutton);
		if(backButton != null){
			backButton.setOnClickListener(this);
			RelativeLayout p = (RelativeLayout) backButton.getParent();
			p.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}
	@Override
	protected void initViews() {
	}
	protected void setCurrentTitle(String title) {
		if(titleText != null){
			titleText.setText(title);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backbutton:
			finish();
			break;

		default:
			break;
		}
	}

}
