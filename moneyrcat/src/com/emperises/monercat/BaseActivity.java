package com.emperises.monercat;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.customview.CustomDialogConfig;
import com.emperises.monercat.customview.DialogManager;
import com.emperises.monercat.database.DatabaseImpl;
import com.emperises.monercat.database.DatabaseInterface;
import com.emperises.monercat.domain.MyInfo;
import com.emperises.monercat.interfaces.BalanceEvent;
import com.emperises.monercat.interfaces.BalanceInterface;
import com.emperises.monercat.interfaces.EditMyInfoEvent;
import com.emperises.monercat.interfaces.EditMyInfoInterface;
import com.emperises.monercat.interfaces.HeaderImageChangeInterface;
import com.emperises.monercat.interfaces.HeaderImageEvent;
import com.emperises.monercat.interfaces.HttpInterface;
import com.emperises.monercat.interfaces.HttpRequest;
import com.emperises.monercat.interfaces.LocalConfigKey;
import com.emperises.monercat.ui.MingXiActivity;
import com.emperises.monercat.ui.v3.ActivityMyInfo;
import com.emperises.monercat.utils.Logger;
import com.emperises.monercat.utils.Util;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public abstract class BaseActivity extends Activity implements OnClickListener,
		HttpInterface, LocalConfigKey ,HeaderImageChangeInterface,BalanceInterface,EditMyInfoInterface{

	private FinalHttp mFinalHttp;
	private ProgressDialog mProgressDialog;
	private TextView titleText;
	private SharedPreferences sp;

	@Override
	public void onHeaderImageChange(int resId) {
		ImageView header = (ImageView) findViewById(R.id.myheaderimage);
		ImageView headerInfo = (ImageView) findViewById(R.id.headerImage);
		if(header != null){
			header.setBackgroundResource(resId);
		}
		if(headerInfo != null){
			headerInfo.setBackgroundResource(resId);
		}
		
	}
	//余额改变时
	@Override
	public void onBalanceChange() {
		TextView ye = (TextView) findViewById(R.id.yue_text);
		if(ye != null){
			float currentBalance = getFloatValueForKey(LOCAL_CONFIGKEY_BALANCE);
			ye.setText("余额:"+currentBalance+"元");
		}
	}
	//获得头像资源id
	protected int getHeadImageResId() {
		return getIntValueForKey(LOCAL_CONFIGKEY_HEADER_RESID);
	}
	//查询当前余额
	protected float queryBalance() {
		float currentBalance = getFloatValueForKey(LOCAL_CONFIGKEY_BALANCE);
		return currentBalance;
	}
	//增加余额
	protected void addBalance(float balance) {
		float currentBalance = getFloatValueForKey(LOCAL_CONFIGKEY_BALANCE);
		setFloatForKey(LOCAL_CONFIGKEY_BALANCE,balance + currentBalance);
		BalanceEvent.getInstance().fireBalanceChange();
		showToast("恭喜您获得"+balance+"元钱!");
		Logger.i("BALANCE", "余额增加");
	}
	//提现
	protected void tixian(float money) {
		showToast("成功提现:"+money+"元");
		//余额减少
		decBalance(money);
	}
	//兑换
		protected void duihuan(float money) {
			showToast("成功兑换电信充值卡");
			//余额减少
			decBalance(money);
		}
	//减少余额
	protected void decBalance(float balance) {
		float currentBalance = getFloatValueForKey(LOCAL_CONFIGKEY_BALANCE);
		if(balance > currentBalance){
			//TODO:在显示兑换列表的时候,如果有足够的余额去兑换,才会被点击
			showToast("您的余额不足!完成任务,点击广告都可以获得金钱!");
		}else{
			if(currentBalance != 0){
				setFloatForKey(LOCAL_CONFIGKEY_BALANCE,currentBalance - balance  );
				BalanceEvent.getInstance().fireBalanceChange();
			}
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myheaderimage:
			startActivity(new Intent(this, ActivityMyInfo.class));
			break;
		case R.id.leftItem:
			startActivityWithAnimation(new Intent(this, MingXiActivity.class));
			break;
		case R.id.rightItem:
			showToast(R.string.signtoast);
			break;

		default:
			break;
		}
	}

	/**
	 * 获得屏幕宽高
	 * @return
	 */
	protected int[] getWindowScreenWH(Context context) {
		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		// 获取屏幕信息
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeigh = dm.heightPixels;
		int[] i = new int[]{screenWidth,screenHeigh};
		Logger.i("SCREEN", "宽:"+screenWidth+"--高:"+screenHeigh);
		return i;
	}

	protected SharedPreferences getSP() {
		return getSharedPreferences("config", MODE_PRIVATE);
	}

	protected void showCommitOkToast() {
		showToast(R.string.commitsuccess);
	}

	protected void showNetErrorToast() {
		showToast(R.string.neterror);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		
		titleText = (TextView) findViewById(R.id.titleText);
		if (findViewById(R.id.leftItem) != null
				&& findViewById(R.id.rightItem) != null) {
			Button leftButton = (Button) findViewById(R.id.leftItem);
			leftButton.setOnClickListener(this);
			Button rightButton = (Button) findViewById(R.id.rightItem);
			rightButton.setOnClickListener(this);
		}
		ImageView mHeader = (ImageView) findViewById(R.id.myheaderimage);
		if(mHeader != null){
			HeaderImageEvent.getInstance().addHeaderImageListener(this);
			int resId = getHeadImageResId();
			if(resId == 0){
				resId = R.drawable.test_headimage1;
				setIntForKey(LOCAL_CONFIGKEY_HEADER_RESID, R.drawable.test_headimage1);
			}
			mHeader.setBackgroundResource(resId);
			mHeader.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(BaseActivity.this, ActivityMyInfo.class));
				}
			});
		}
		TextView ye = (TextView) findViewById(R.id.yue_text);
		if(ye != null){
			BalanceEvent.getInstance().addBalanceListener(this);
			ye.setText("余额:"+queryBalance()+"元");
		}
		
		initViews();
	}

	public String getCurrentTitle() {
		String title = "";
		if (titleText != null) {
			title = titleText.getText().toString();
		}
		return title;
	}

	protected void setCurrentTitle(String title) {
		if (titleText != null) {
			titleText.setText(title);
		}
	}

	protected void startActivityWithAnimation(Intent i) {
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	private static final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	private DatabaseInterface mDatabase;

	protected DatabaseInterface getDatabaseInterface() {
		return mDatabase;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 社会化分享初始化
		initShare();
		if(mDatabase == null){
			mDatabase = new DatabaseImpl(this, null);// TODO:创建数据库
		}
		// testDB(d.getDatabase());
		mFinalHttp = new FinalHttp();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		EditMyInfoEvent.getInstance().addEditInfoListener(this);
	}

	private void showDialog(CustomDialogConfig config) {
		DialogManager.getInstance(this, config).show();
	}

	/**
	 * 显示一个标准的自定义对话框
	 * 
	 * @param title
	 * @param message
	 * @param sureClickListener
	 *            “确定”点击事件
	 * @param cancleClickListener
	 *            “取消”点击事件
	 */
	protected void showDialog(String title, String message,
			DialogClick sureClickListener, DialogClick cancleClickListener) {
		CustomDialogConfig config = new CustomDialogConfig();
		config.setCancelListener(cancleClickListener);
		config.setSureListener(sureClickListener);
		config.setMessage(message);
		config.setTitle(title);
		showDialog(config);
	}

	protected void setStringtForKey(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	protected void setBooleanForKey(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	protected void setIntForKey(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	protected void setFloatForKey(String key, float value) {
		sp.edit().putFloat(key, value).commit();
	}

	protected String getStringValueForKey(String key) {
		return sp.getString(key, "");
	}

	protected float getFloatValueForKey(String key) {
		return sp.getFloat(key, 0.0f);
	}

	protected boolean getBoleanValueForKey(String key) {
		return sp.getBoolean(key, false);
	}

	protected int getIntValueForKey(String key) {
		return sp.getInt(key, 0);
	}

	protected SQLiteDatabase getDatabase() {
		return mDatabase.getDatabase();
	}

	// private void testDB(SQLiteDatabase db) {
	// try {
	// Object o = DatabaseUtil.queryDatabaseForClass(ADInfo.class, db, "adId",
	// new String[]{"id"});
	// Logger.i("SQL", o.toString());
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// }
	private void initShare() {
		// 设置分享内容
		String shareContent = "你的好友分享了一个招财喵给你!招财喵，真正可以赚钱的APP!";
		// String picUrl = "http://www.baidu.com/img/baidu_sylogo1.gif";
		String shareUrl = "http://bmw.thefront.com.cn/bmw-x4/?from=groupmessage&isappinstalled=0#/";
		String shareTitle = "招财喵,真正可以赚钱的APP";
		// QQ好友
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101962112",
				"RY1S5XEVSVnjx3B7");
		qqSsoHandler.setTitle(shareTitle);
		qqSsoHandler.setTargetUrl(shareUrl);
		qqSsoHandler.addToSocialSDK();

		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(shareContent);
		qqShareContent.setTitle(shareTitle);
		qqShareContent.setShareImage(new UMImage(this, R.drawable.ic_launcher));
		qqShareContent.setTargetUrl(shareUrl);
		mController.setShareMedia(qqShareContent);
		// QQ空间
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"1101962112", "RY1S5XEVSVnjx3B7");
		QZoneShareContent content = new QZoneShareContent();
		qZoneSsoHandler.setTargetUrl(shareUrl);
		qZoneSsoHandler.addToSocialSDK();
		content.setShareContent(shareContent);
		content.setShareImage(new UMImage(this, R.drawable.ic_launcher));
		content.setTargetUrl(shareUrl);
		content.setTitle(shareTitle);
		mController.setShareMedia(content);
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appID = "wxcec4566d135405e6";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this, appID);
		wxHandler.setTitle(shareTitle);
		wxHandler.setTargetUrl(shareUrl);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this, appID);
		wxCircleHandler.setTitle(shareTitle);
		wxCircleHandler.setTargetUrl(shareUrl);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// 为了保证人人分享成功且能够在PC上正常显示，请设置website
		mController.setAppWebSite(SHARE_MEDIA.RENREN, shareUrl);
		// 设置分享到微信的内容, 图片类型
		UMImage mUMImgBitmap = new UMImage(this, R.drawable.ic_launcher);// TODO:改为网页LOGO图片
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(shareTitle);
		weixinContent.setTargetUrl(shareUrl);
		weixinContent.setShareContent(shareContent);
		weixinContent.setShareImage(mUMImgBitmap);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTitle(shareTitle);
		circleMedia.setTargetUrl(shareUrl);
		circleMedia.setShareContent(shareContent);
		circleMedia.setShareImage(new UMImage(this, R.drawable.ic_launcher));// TODO:改为网页LOGO图片
		mController.setShareMedia(circleMedia);

		TencentWbShareContent tencentContent = new TencentWbShareContent();
		tencentContent.setTitle(shareTitle);
		tencentContent.setTargetUrl(shareUrl);
		tencentContent.setShareContent(shareContent);
		tencentContent.setShareImage(new UMImage(this, R.drawable.ic_launcher));
		tencentContent.setTargetUrl(shareUrl);
		// 设置分享到腾讯微博的文字内容
		tencentContent.setShareContent(shareContent);
		// 设置分享到腾讯微博的多媒体内容
		mController.setShareMedia(tencentContent);
		// 设置分享图片，参数2为图片的url.
		mController.setShareMedia(new UMImage(this, R.drawable.ic_launcher));
	}

	protected void openShare() {
		
		
		mController.openShare(this, new SnsPostListener() {
			
			@Override
			public void onStart() {
				
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
				//增加余额
				addBalance(1.0f);
			}
		});
	}

	protected void startRequest() {
	}

	protected void startRequest(String url) {
		AjaxParams params = new AjaxParams();
		mFinalHttp.post(url, params, new HttpRequest(this));
	}

	@Override
	public void onFinished(String content) {
		mProgressDialog.dismiss();
	}

	public final FinalHttp getHttpClient() {
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

	protected ProgressDialog showBaseProgressDialog() {
		return ProgressDialog.show(this, getString(R.string.hit),
				getString(R.string.wait));
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

	public String getResString(int resId) {
		return getResources().getString(resId);
	}

	/**
	 * Convert from DIP value to Pixel value.
	 * 
	 * @param dip
	 *            Value in DIP
	 * @return Value in Pixel
	 */
	public int dip2px(float dip) {

		return Util.dip2px(dip, this);
	}

	/**
	 * Convert from Pixel value to DIP value.
	 * 
	 * @param pixel
	 *            Value in Pixel
	 * @return Value in DIP
	 */
	public int px2dip(float pixel) {
		return Util.px2dip(pixel, this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		HeaderImageEvent.getInstance().removeListener(this);
		BalanceEvent.getInstance().removeListener(this);
		EditMyInfoEvent.getInstance().removeListener(this);
	}
	@Override
	public void onInfoEditAfter(MyInfo info) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAgeEditAfter(String age) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNickNameEditAfter(String nickNmae) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGenderEditAfter(String gender) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddressEditAfter(String address) {
		// TODO Auto-generated method stub
		
	}	
}