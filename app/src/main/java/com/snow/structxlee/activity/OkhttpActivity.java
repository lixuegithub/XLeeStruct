
package com.snow.structxlee.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.snow.structxlee.R;
import com.snow.structxlee.util.CropSquareTrans;
import com.snow.structxlee.util.OkManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp框架详解
 * TODO 1、简单的使用handler实现下载图片的功能
 * TODO 2、对下载的图片实现裁剪功能
 */
public class OkhttpActivity extends Activity {
    private static final int SUCCESS_STATUS = 1;
    private static final int FAIL_STATUS = 0;
    Button btn, btn2, btn3;
    ImageView iv;
    private String IMAGE_PATH = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    //需要返回一个json数据
    private String JSON_PATH = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    //服务器端地址
    private String LOGIN_PATH = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";

    private OkHttpClient client;

    private OkManager okManager;
    //handler本身是运行在ui主线程中
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_STATUS:
                    byte[] result = (byte[]) msg.obj;
                    //TODO 1 download图片
//                    Bitmap bp = BitmapFactory.decodeByteArray(result,0, result.length);
                    //TODO 2 裁剪图片
                    Bitmap bp = new CropSquareTrans().transform(BitmapFactory.decodeByteArray(result, 0, result.length));
                    iv.setImageBitmap(bp);
                    break;
                case FAIL_STATUS:
                    Log.e("snow", "download failed....");
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        iv = (ImageView) findViewById(R.id.imageView);
        client = new OkHttpClient();
        //构建一个request请求
        final Request request = new Request.Builder().get().url(IMAGE_PATH).build();//get请求
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //返回下载结果
                        Message msg = handler.obtainMessage();
                        if (response.isSuccessful()) {
                            msg.what = SUCCESS_STATUS;
                            msg.obj = response.body().bytes();//返回字节数组
                            handler.sendMessage(msg);
                        } else {
                            handler.sendEmptyMessage(FAIL_STATUS);
                        }
                    }
                });
            }
        });

        /////////////////////华丽的分割线////////////////////
        okManager = OkManager.getInstance();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okManager.asyncJsonStringByURL(JSON_PATH, new OkManager.Func1() {
                    @Override
                    public void onRespose(String result) {
                        Log.e("snow", "okManager result=" + result);//json字符串
                    }
                });
            }
        });
        /////////////////////华丽的分割线(服务器端使用)////////////////////
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("username", "admin");
                map.put("password", "12345");
                okManager.sendComplexForm(LOGIN_PATH, map, new OkManager.Func4() {
                    @Override
                    public void onRespose(JSONObject jsonObject) {
                        Log.e("snow", "JsonObjectToString..." + jsonObject.toString());
                    }
                });
            }
        });


    }


}
