package com.snow.structxlee.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.snow.structxlee.util.SharedPreferencesUtils;

import java.lang.Thread.UncaughtExceptionHandler;

public class BaseApplication extends Application {
	public static  BaseApplication myApplication = null;
	public static Context context;
	SharedPreferences sharedPreferences;

	public static BaseApplication getInstance() {
		if (myApplication == null) {
			synchronized (BaseApplication.class) {
				if (myApplication == null) {
					myApplication = new BaseApplication();
				}
			}
		}
		return myApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		myApplication = this;
		SharedPreferencesUtils.setParam(getApplicationContext(),"user","我是测试用户名的...");
		Log.e("xlee","reader sp "+SharedPreferencesUtils.getParam(getApplicationContext(),"user",""));
	}

	// 创建服务用于捕获崩溃异常
	private UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {
		public void uncaughtException(Thread thread, Throwable ex) {
			restartApp();// 发生崩溃异常时,重启应用
		}
	};

	public void restartApp() {
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid()); // 结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
	}

	public void getDeviceInfo() {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		if (dm.widthPixels < dm.heightPixels) {
			// 竖屏
			// Constants.SCREEN_WIDTH_PORTRAIT = dm.widthPixels;
			// Constants.SCREEN_HEIGHT_PORTRAIT = dm.heightPixels;
			//
			// Constants.SCREEN_HEIGHT_LANDSCAPE = dm.widthPixels;
			// Constants.SCREEN_WIDTH_LANDSCAPE = dm.heightPixels;
		} else {
			// 横屏
			// Constants.SCREEN_WIDTH_PORTRAIT = dm.heightPixels;
			// Constants.SCREEN_HEIGHT_PORTRAIT = dm.widthPixels;
			//
			// Constants.SCREEN_HEIGHT_LANDSCAPE = dm.heightPixels;
			// Constants.SCREEN_WIDTH_LANDSCAPE = dm.widthPixels;
		}
		// Constants.DENSITYDPI = dm.densityDpi;
		// Constants.DENSITY = dm.density;
		// ULog.info(TAG, "setupBaseData, SCREEN_WIDTH = " + dm.widthPixels +
		// ", SCREEN_HEIGHT = " + dm.heightPixels + ", densityDpi = " +
		// dm.densityDpi);
	}

	public static Context getContext() {
		return context;
	}


}
