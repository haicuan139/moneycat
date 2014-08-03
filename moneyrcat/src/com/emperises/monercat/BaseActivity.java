package com.emperises.monercat;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.emperises.monercat.interfaces.HttpInterface;
import com.emperises.monercat.interfaces.HttpRequest;
import com.emperises.monercat.ui.MingXiActivity;

public abstract class BaseActivity extends Activity implements OnClickListener , HttpInterface{

	private FinalHttp mFinalHttp;
	private ProgressDialog mProgressDialog;
	private TextView titleText;
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.leftItem:
			startActivityWithAnimation(new Intent(this , MingXiActivity.class));
			break;
		case R.id.rightItem:
			showToast(R.string.signtoast);
			break;

		default:
			break;
		}
	}
	protected SharedPreferences getSP() {
		return getSharedPreferences("config", MODE_PRIVATE);
	}
	protected void showCommitOkToast(){
		showToast(R.string.commitsuccess);
	}
	protected void 	showNetErrorToast(){
		showToast(R.string.neterror);
	} 

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		titleText = (TextView) findViewById(R.id.titleText);
		if(findViewById(R.id.leftItem) != null && findViewById(R.id.rightItem) != null){
			Button leftButton = (Button)findViewById(R.id.leftItem);
			leftButton.setOnClickListener(this);
			Button rightButton = (Button)findViewById(R.id.rightItem);
			rightButton.setOnClickListener(this);
		}
		initViews();
	}
	public String getCurrentTitle(){
		String title = "";
		if(titleText != null){
			title = titleText.getText().toString();
		}
		return title; 
	}
	protected void setCurrentTitle(String title) {
		if(titleText != null){
			titleText.setText(title);
		}
	}
	protected void startActivityWithAnimation(Intent i){
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFinalHttp = new FinalHttp();
	}
	protected void startRequest() {
	}
	protected void startRequest(String url) {
		AjaxParams params = new AjaxParams();
		mFinalHttp.post(url, params,new HttpRequest(this));
	}
	@Override
	public void onFinished(String content) {
		mProgressDialog.dismiss();
	}
	public final FinalHttp getHttpClient(){
		return mFinalHttp;
	}
	abstract protected void initViews();
	@Override
	public void onFail(Throwable t, int errorNo, String strMsg) {
		mProgressDialog.dismiss();
	}
	@Override
	public void onLoading(long count, long current) {
		
	}
	@Override
	public void onHttpStart() {
		mProgressDialog = showBaseProgressDialog();
	}
	protected ProgressDialog  showBaseProgressDialog() {
		return ProgressDialog.show(this, getString(R.string.hit), getString(R.string.wait));
	}
	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	protected void showToast(int id) {
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
	protected void showErrorToast() {
		showToast(R.string.errortoast);
	}
	public String getResString(int resId){
		return getResources().getString(resId);
	}	
	

}