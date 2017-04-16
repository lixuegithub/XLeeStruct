package com.snow.structxlee.util;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class DownLoadUtils {
    private OkHttpClient client;

    public  DownLoadUtils(){
        client = new OkHttpClient();
    }

    public Observable<byte[]> downLoadImage(final String path){
        return Observable.create(new Observable.OnSubscribe<byte[]>(){
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                if (!subscriber.isUnsubscribed()){
                    //访问网络操作
                    Request request = new Request.Builder().url(path).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()){
                                byte[] data = response.body().bytes();
                                if (null!=data){
                                    subscriber.onNext(data);
                                }
                            }
                            subscriber.onCompleted();
                        }
                    });
                }
            }
        });
    }
}
