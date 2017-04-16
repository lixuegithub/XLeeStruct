
package com.snow.structxlee.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.snow.structxlee.R;
import com.snow.structxlee.util.RxUtils;


/**
 *RxJava简单使用
 */
public class RxActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    //调用Create方法
    public void createMethod(View view){
        RxUtils.createObservable();
    }


}
