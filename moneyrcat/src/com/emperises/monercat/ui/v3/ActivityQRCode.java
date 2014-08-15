package com.emperises.monercat.ui.v3;

import android.os.Bundle;
import android.widget.ImageView;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class ActivityQRCode extends OtherBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		setCurrentTitle("我的二维码");
	}
	@Override
	protected void initViews() {
		super.initViews();
		ImageView headerImage = (ImageView) findViewById(R.id.headerImage);
		headerImage.setBackgroundResource(getHeadImageResId());
	}
	
}
