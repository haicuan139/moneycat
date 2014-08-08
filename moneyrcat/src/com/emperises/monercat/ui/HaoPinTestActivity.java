package com.emperises.monercat.ui;

import com.emperises.monercat.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class HaoPinTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView i = new ImageView(this);
		i.setBackgroundResource(R.drawable.haopingtest);
		setContentView(i);
	}
	
}
