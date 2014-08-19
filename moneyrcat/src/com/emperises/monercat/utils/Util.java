package com.emperises.monercat.utils;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.emperises.monercat.R;
import com.emperises.monercat.customview.CustomDialog.DialogClick;
import com.emperises.monercat.customview.CustomDialogConfig;
import com.emperises.monercat.customview.DialogManager;
import com.emperises.monercat.domain.UpdateInfo;
import com.google.gson.Gson;

public class Util {

    /**
     * Convert from DIP value to Pixel value.
     * @param dip Value in DIP
     * @return Value in Pixel
     */
    public  static int dip2px(float dip ,Context ctx) {
    	
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * Convert from Pixel value to DIP value.
     * @param pixel Value in Pixel
     * @return Value in DIP
     */
    public static  int px2dip(float pixel,Context ctx) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pixel / scale + 0.5f);
    }
	public static String getDeviceId(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
		return tm.getDeviceId();
	}
	public static String getLocalTelNumber(){
		//TODO:获取本地手机号码
		return "";
	}
	/**
	 * 下载APK
	 * @param k
	 */
	private static  String DOWN_ID = "";
	private static final String APK_NAME = "moneycat.apk";
	private static final String APK_PATH = "/moneycat/";
	private static final String CONFIG_FILE_NAME = "config";
	
	@SuppressLint({ "InlinedApi", "NewApi" })
	public static void downloadApkAndInstall(Context context , UpdateInfo updateObj){
			//TODO:没有APK下载地址
			DOWN_ID = System.currentTimeMillis()+"";
			BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent arg1) {
					queryDownloadStatus(context);
				}
			};
			DownloadManager mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			context.registerReceiver(mDownloadReceiver, new IntentFilter(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE));
			SharedPreferences sp = getSharedPreferences(context);
			if (!sp.contains(DOWN_ID)) {
				Uri resource = Uri.parse(updateObj.getDownloadUrl());
				DownloadManager.Request request = new DownloadManager.Request(
						resource);
				request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
						| Request.NETWORK_WIFI);
				request.setAllowedOverRoaming(false);
				MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
				String mimeString = mimeTypeMap
						.getMimeTypeFromExtension(MimeTypeMap
								.getFileExtensionFromUrl(updateObj.getDownloadUrl()));
				request.setMimeType(mimeString);
				// 在通知栏中显示
				request.setShowRunningNotification(true);
				request.setVisibleInDownloadsUi(true);
				// sdcard的目录下的download文件夹
				request.setDestinationInExternalPublicDir(APK_PATH, APK_NAME);
				request.setTitle(context.getString(R.string.updatestartstr));
				long id = mDownloadManager.enqueue(request);
				// 保存id
				sp.edit().putLong(DOWN_ID, id)
						.commit();
			} else {
				// 下载已经开始，检查状态
				queryDownloadStatus(context);
			}
			context.registerReceiver(mDownloadReceiver, new IntentFilter(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE));


	}
	public static SharedPreferences getSharedPreferences(Context context){
		
		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
	}
	@SuppressLint("NewApi")
	private static void queryDownloadStatus(Context context) {
		DownloadManager mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(getSharedPreferences(context).getLong(
				DOWN_ID, 0));
		Cursor c = mDownloadManager.query(query);
		if (c.moveToFirst()) {
			int status = c.getInt(c
					.getColumnIndex(DownloadManager.COLUMN_STATUS));
			switch (status) {
			case DownloadManager.STATUS_SUCCESSFUL:
				// 完成
				installApk(
						new File(Environment.getExternalStorageDirectory(),
								APK_PATH+APK_NAME).getAbsolutePath(), context);
				break;
			case DownloadManager.STATUS_FAILED:
				// 清除已下载的内容，重新下载
				mDownloadManager.remove(getSharedPreferences(context)
						.getLong(DOWN_ID, 0));
				getSharedPreferences(context).edit().remove(DOWN_ID)
						.commit();
				break;
			}
		}
	}

	public static void installApk(String apkPath , Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);  
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),  
                "application/vnd.android.package-archive");  
        context.startActivity(intent);
	}
	public static float getLocalVersionCode(Context context){
		// 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        String version = "";
        PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Logger.i("VERSION", "当前版本Float:"+Float.parseFloat(version));
		return Float.parseFloat(version);
	}
	/**
	 * 检查更新版本
	 * @return
	 */
	public static boolean checkUpdateVersion(final Context context){
		//TODO:没有URL
		new FinalHttp().post("", new AjaxCallBack<String>(){
			@Override
			public void onSuccess(String t) {
				//解析更新json
				if(!TextUtils.isEmpty(t)){
					UpdateInfo update = new Gson().fromJson(t, UpdateInfo.class);
					if(update != null){
						float currentVersionCode = Float.parseFloat(update.getVersionCode());
						if(currentVersionCode > getLocalVersionCode(context)){
							showUpdateDialog(context, update);
						}
					}
				}else{
					//TODO: 如果更新内容为空抛出一个调试异常，
					DebugUtil.throwDebugException("更新内容不能为空");
				}
				super.onSuccess(t);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
		});
		return true;
	}
	public static void showCustomDialog(Context context , String title , String msg , DialogInterface.OnClickListener trueButtonListener , DialogInterface.OnClickListener falseButtonListener){
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.sure, trueButtonListener);
		builder.setNegativeButton(R.string.cancle, falseButtonListener);
		builder.show();
	}
	public static void showUpdateDialog(final Context context ,final UpdateInfo update){
		CustomDialogConfig config = new CustomDialogConfig();
		config.setTitle(context.getString(R.string.update_title));
		config.setSureButtonText(context.getString(R.string.update_button_text));
		config.setMessage(update.getMessage());
		config.setCancelListener(new DialogClick() {
			@Override
			public void onClick(View v) {
				super.onClick(v);
			}
		});
		config.setSureListener(new DialogClick() {
			public void onClick(View v) {
				super.onClick(v);
				downloadApkAndInstall(context, update);
			};
		});
		DialogManager.getInstance(context, config).show();
	}
}
