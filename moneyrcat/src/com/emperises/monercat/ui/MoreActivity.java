package com.emperises.monercat.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.emperises.monercat.BaseActivity;

public class MoreActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView i = new ImageView(this);
		i.setBackgroundColor(Color.BLUE);
		setContentView(i);
	}
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

}
