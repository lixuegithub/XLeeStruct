package com.snow.structxlee.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.snow.structxlee.R;
import com.snow.structxlee.base.BaseCommWithTopBar;
import com.snow.structxlee.util.DownLoadUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 使用okHttp实现对图片的下载
 */
public class AsyncActivity extends BaseCommWithTopBar {
    private ImageView imageView;
    private String PATH = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";

    private DownLoadUtils utils;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_async);
        initView();
    }

    private void initView() {
        Button but = (Button) findViewById(R.id.button);
        imageView= (ImageView) findViewById(R.id.imageView);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.downLoadImage(PATH).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<byte[]>(){
                    @Override
                    public void onCompleted() {
                        Log.e("snow","onComplete...");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("snow","onError...");
                    }

                    @Override
                    public void onNext(byte[] bytes) {
                        Log.e("snow","onNext...retule="+bytes.toString());
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                    }
                });


            }
        });
    }


}
