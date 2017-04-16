package com.snow.structxlee.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.snow.structxlee.MainActivity;
import com.snow.structxlee.R;
import com.snow.structxlee.base.BaseCommWithTopBar;
import com.snow.structxlee.popwindow.SelectPicPopupWindow;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Test SingleTask 启动模式
 */
public class TestSingleTaskActivity extends BaseCommWithTopBar {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_interval);
        ((Button) findViewById(R.id.btn_start_loop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(TestSingleTaskActivity.this, MainActivity.class);
                intent.putExtra("data","this is data..");
                startActivity(intent);
            }
        });
    }





}
