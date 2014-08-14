package com.emperises.monercat.ui.v3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebViewClient;

import com.emperises.monercat.OtherBaseActivity;
import com.emperises.monercat.R;
import com.emperises.monercat.domain.ADInfo;
import com.emperises.monercat.interfaces.BalanceEvent;
import com.emperises.monercat.ui.RecommendDialogActivity;
import com.emperises.monercat.ui.WYCJDialogActivity;

@SuppressLint("NewApi")
public class ActivityAdDetail_HTML5 extends OtherBaseActivity {

	private WebView mAdWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad_detail_v3_html5);
		setCurrentTitle("广告详细");
		
	}
	private void initWebSetting(WebView webview) {
		WebSettings webSettings = webview.getSettings();
		webSettings.setAllowContentAccess(true);
		webSettings.setAppCacheMaxSize(10 * 1024 * 1024);
		webSettings.setAllowFileAccess(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setGeolocationDatabasePath(getDir("database", Context.MODE_PRIVATE).getPath());
		webview.setHorizontalScrollBarEnabled(false);
		webview.setVerticalScrollBarEnabled(false);
	}

	@Override
	protected void initViews() {
		super.initViews();
		mAdWebView = (WebView) findViewById(R.id.adwebView);
		initWebSetting(mAdWebView);
		ADInfo info = (ADInfo) getIntent().getSerializableExtra(INTENT_KEY_ADINFO);
		mAdWebView.loadUrl(info.getAdUrl());
		mAdWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if(newProgress == 100){
					//加载完成，完成点击计费
					BalanceEvent.getInstance().fireBalanceAddEvent(1.0f);
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.woyaocanjia:
			startActivity(new Intent(this , WYCJDialogActivity.class));
			break;
		case R.id.recommend_friend:
			startActivity(new Intent(this , RecommendDialogActivity.class));
			break;
		case R.id.share:
			openShare();
			break;

		default:
			break;
		}
	}
}
