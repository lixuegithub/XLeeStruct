package com.snow.structxlee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.snow.structxlee.activity.IntervalActivity;
import com.snow.structxlee.activity.OrderActivity;
import com.snow.structxlee.activity.SelectPhotoActivity;
import com.snow.structxlee.activity.TestSingleTaskActivity;

public class MainActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("xlee","onCreate....");
		setContentView(R.layout.activity_main);
		Button btnTabColor = (Button) findViewById(R.id.btn_tabColorPagerIndicator);
		Button btnViewPagerRx = (Button) findViewById(R.id.btn_viewPagerRx);
		Button btnSelectPhoto = (Button) findViewById(R.id.btn_select_photo);
		Button btnTestSingletask = (Button) findViewById(R.id.btn_test_singletask);
		btnTabColor.setOnClickListener(this);
		btnViewPagerRx.setOnClickListener(this);
		btnSelectPhoto.setOnClickListener(this);
		btnTestSingletask.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent =null;
		switch (v.getId()){
			case  R.id.btn_tabColorPagerIndicator:
				intent = new Intent (MainActivity.this, OrderActivity.class);
				startActivity(intent);
				break;
			case  R.id.btn_viewPagerRx:
				intent = new Intent (MainActivity.this, IntervalActivity.class);
				startActivity(intent);
				break;
			case  R.id.btn_select_photo:
				intent = new Intent (MainActivity.this, SelectPhotoActivity.class);
				startActivity(intent);
				break;
			case  R.id.btn_test_singletask:
				intent = new Intent(MainActivity.this, TestSingleTaskActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e("xlee","onStart....");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("xlee","onResume....");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e("xlee","onPause....");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e("xlee","onRestart....");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e("xlee","onStop....");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("xlee","onDestroy....");
	}

	/**
	 * 默认启动模式下重写该方法是没有效果的，设置相关启动模式除了默认的即可以接收其他Activity传递的值
	 * @param intent
     */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e("xlee","onNewIntent:"+intent.getStringExtra("data"));
	}


}
