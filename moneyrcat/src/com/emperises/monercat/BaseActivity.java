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

public abstract class BaseActivity extends Activity implements OnClickListener , HttpInterface{

	private FinalHttp mFinalHttp;
	private ProgressDialog mProgressDialog;
	private TextView titleText;
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.leftItem:
			showToast("left item");
			break;
		case R.id.rightItem:
			showToast("right item");
			break;

		default:
			break;
		}
	}
	protected SharedPreferences getSP() {
		return getSharedPreferences("config", MODE_PRIVATE);
	}
	protected void showCommitOkToast(){
		showToast("�����ɹ�");
	}
	protected void 	showNetErrorToast(){
		showToast("����ʧ��");
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
//			List<Integer> ids = new ArrayList<Integer>();
//			ids.add(R.layout.activity_home);
//			ids.add(R.layout.activity_recommend);
//			ids.add(R.layout.activity_wodetable);
//			ids.add(R.layout.activity_more);
//			if(!ids.contains(mCurrentLayoutResId)){
//				
//			}
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
		return ProgressDialog.show(this, "��ʾ", "��ȴ�");
	}
	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	protected void showToast(int id) {
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
	protected void showErrorToast() {
		showToast("���ݽ���ʧ��");
	}
	public String getResString(int resId){
		return getResources().getString(resId);
	}	
	

}