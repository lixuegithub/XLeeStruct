package com.snow.structxlee.base;

import android.os.Bundle;
import android.os.Handler;

public class CommonActivity extends MyActivity {

	private Handler handler;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		handler = new Handler();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}


	public Handler getHandler() {
		return handler;
	}


	public void post(final Runnable action) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (CommonActivity.this.isFinishing()) {
					return;
				}
				action.run();
			}
		});
	}

	/**
	 * 统一 post 接口
	 */
	public void post(final Runnable action, int delayMillis) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (CommonActivity.this.isFinishing()) {
					return;
				}
				action.run();
			}
		}, delayMillis);
	}
}
