package com.snow.structxlee.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.snow.structxlee.util.CropSquareTrans;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装工具类
 */

public class OkManager {
private OkHttpClient client;
    private volatile static OkManager manager;
    private final  String TAG = OkManager.class.getSimpleName();//获得类名
    private Handler handler;
    //提交json数据
    private static  final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串
    private static   final  MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

   private  OkManager(){
       client = new OkHttpClient();
       handler  = new Handler(Looper.getMainLooper());//主线程中
   }
    public  static OkManager getInstance(){
        OkManager instance = null;
        if (manager==null){
            synchronized (OkManager.class){
                if (instance==null){
                    instance = new OkManager();
                    manager = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 请求返回的结果是json字符串
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonStringMethod(final String jsonValue, final Func1 callBack){
        handler.post(new Runnable() {//放入子线程中执行
            @Override
            public void run() {
                if (callBack!=null){
                    try {
                        callBack.onRespose(jsonValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 请求返回的是byte字节数组
     * @param data
     * @param callBack
     */
    private  void onSuccessByteMethod(final byte[] data,final Func2 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    callBack.onRespose(data);
                }
            }
        });
    }

    /**
     * 返回响应的结果是json对象
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonObjectMethod(final String jsonValue,final Func4 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try {
                        callBack.onRespose(new JSONObject(jsonValue));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 以上的方法都是异步的，该方法属于同步请求，在Android开发中不常用，因为会阻塞UI线程
     * @param url
     * @return
     */
    public  String syncGetByURL(String url){
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response= client.newCall(request).execute();//同步请求数据
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 请求指定的url返回的结果是String字符串
     * @param url
     * @param callBack
     */
    public  void asyncJsonStringByURL(String url,final  Func1 callBack){
        final  Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    onSuccessJsonStringMethod(response.body().toString(),callBack);
                }
            }
        });
    }
    /**
     * 请求指定的url返回的结果是JsonObject对象
     * @param url
     * @param callBack
     */
    public void asyncJsonObjectByURL (String url,final  Func4 callBack){
        final  Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    onSuccessJsonObjectMethod(response.body().toString(),callBack);
                }
            }
        });
    }
    /**
     * 请求指定的url返回的结果是byte字节数组
     * @param url
     * @param callBack
     */
    public void asyncGetbyteByURL (String url,final  Func2 callBack){
        final  Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    onSuccessByteMethod(response.body().bytes(),callBack);
                }
            }
        });
    }

    /**
     * 请求返回结果是ImageView类型 bitmap类型
     * @param url
     * @param callBack
     */
    public void asyncDownLoadImageByURL (String url,final  Func3 callBack){
        final  Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    byte[] data = response.body().bytes();
                    Bitmap bitmap = new CropSquareTrans().transform(BitmapFactory.decodeByteArray(data,0,data.length));
                    callBack.onRespose(bitmap);
                }
            }
        });
    }

    /**
     * 表单提交测试/模拟表单提交
     * @param url
     * @param params
     * @param callBack
     */
    public  void sendComplexForm(String url, Map<String,String> params,final Func4 callBack){
        FormBody.Builder formBuilder=new FormBody.Builder();//表单对象，包含以input开始的对象，以html表单为主
        if (params!=null && !params.isEmpty()){
            //循环表单
            for (Map.Entry<String,String> entry:params.entrySet()){
                formBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody body = formBuilder.build();
        Request request = new Request.Builder().url(url).post(body).build();//采用post方式提交
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    onSuccessJsonObjectMethod(response.body().string(),callBack);
                }
            }
        });
    }

    /**
     * 向服务器提交String字符串
     * @param url
     * @param content
     * @param callBack
     */
    public  void sendStringByPostMethod(String url,String content,final Func4 callBack){
        Request request = new Request.Builder().post(RequestBody.create(MEDIA_TYPE_MARKDOWN,content)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null && response.isSuccessful()){
                    onSuccessJsonObjectMethod(response.body().string(),callBack);
                }
            }
        });
    }

    interface Func1{
        void onRespose(String result);
    }
    interface Func2{
        void onRespose(byte[] result);
    }
    interface Func3{
        void onRespose(Bitmap bp);
    }
    interface Func4{
        void onRespose(JSONObject jsonObject);
    }

}
