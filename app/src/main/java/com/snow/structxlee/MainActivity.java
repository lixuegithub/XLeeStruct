package com.snow.structxlee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.snow.structxlee.activity.OrderActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn_TabColor = (Button) findViewById(R.id.btn_tabColorPagerIndicator);
		btn_TabColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent (getApplicationContext(), OrderActivity.class);
				startActivity(intent);
			}
		});

	}

	
}
