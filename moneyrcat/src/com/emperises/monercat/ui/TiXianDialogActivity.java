package com.emperises.monercat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;

public class TiXianDialogActivity extends OtherBaseActivity {

	private Button mCloseButton;
	private EditText mTiXianEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian_dialog);
		setTitleHide(View.GONE);
	}
	@Override
	protected void initViews() {
		mCloseButton = (Button) findViewById(R.id.closeButton);
		mTiXianEditText = (EditText) findViewById(R.id.tixian_balance_edittext);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.closeButton:
			finish();
			break;
		case R.id.commit_bt:
			try {
				String txje = mTiXianEditText.getText().toString();
				float balance = Float.parseFloat(txje);
				if(balance > queryBalance()){
					showToast("余额不足!");	
				}else{
					tixian(balance);
				}
			} catch (Exception e) {
				showToast("提现时错误,请检查输入的金额是否正确");
			}
			finish();
			break;
		default:
			break;
		}
	}

}
