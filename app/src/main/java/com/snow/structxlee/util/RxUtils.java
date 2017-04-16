package com.snow.structxlee.util;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava使用Method,原版,缩减版
 */

public class RxUtils {
    public static final String TAG = RxUtils.class.getSimpleName();

    //TODO method 1
    public static void createObservable() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext("hello");
                    subscriber.onNext("hi");
                    subscriber.onNext(downLoadJson());
                    subscriber.onNext("world");
                    subscriber.onCompleted();

                }
            }
        });
        Subscriber<String> showsub= new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onComplete...");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"onError...");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext...retule="+s);
            }
        };
        observable.subscribe(showsub);//关联被观察者
    }

    public static String downLoadJson() {
        return "json data";
    }

    //TODO method 2 ：缩减版：被观察者直接订阅
    public static void createPrint(){
        Observable.create(new Observable.OnSubscribe<Integer>(){
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()){
                    for (int i = 0; i <=10; i++) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted...");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"onError...");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG,"onNext...integer="+integer);
            }
        });
    }

    /**
     * 使用在被观察者，返回的对象一般都是数值类型
     */
    public static  void from(){
        Integer[] items= {1,2,3,4,5,6,7,8,9};
        Observable observable = Observable.from(items);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.e(TAG,"form method for call Object.toString ="+o.toString());
            }
        });
    }

    /**
     * 指定某一时刻进行数据发送
     */
    public static void Interval(){
        Integer[] items = {1,2,3,4,5};
        Observable observable = Observable.interval(1,2, TimeUnit.SECONDS);//每隔一秒执行一次
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.e(TAG,"Interval method for call Object.toString ="+o.toString());

            }
        });
    }

    /**
     * 处理数组集合
     */
    public static void just(){
        Integer[] items1 = {1,2,3,4,5};
        Integer[] items2 = {6,7,8,9,10};
        Observable observable = Observable.just(items1,items2);
        observable.subscribe(new Subscriber<Integer[]>(){
            @Override
            public void onNext(Integer[] integers) {
                Log.e(TAG,"onNext...");
                for (int i = 0; i <integers.length ; i++) {
                    Log.e(TAG,"next:"+integers[i]);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"onError...");
            }

            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted...");
            }
        });
    }

    /**
     * 使用范围数据，指定输出数据的范围
     */
    public  static void range(){
        Observable observable = Observable.range(1,40);
        observable.subscribe(new Subscriber<Integer>(){
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted...");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"onError...");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG,"onNext...integer="+integer);
            }
        });
    }

    /**
     * 使用过滤功能
     */
    public static void filter(){
        Observable observable = Observable.just(1,2,3,4,5,6,7,8);
        observable.filter(new Func1<Integer,Boolean>(){
            @Override
            public Boolean call(Integer integer) {
                return 0<5;//过滤信息，只返回输出数据小于5的数据
            }
        }).observeOn(Schedulers.io()).subscribe(new Subscriber<Integer>(){
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted...");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG,"onError...");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG,"onNext...integer="+integer);
            }
        } );
    }




}
