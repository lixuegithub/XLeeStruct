package com.snow.structxlee.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.snow.structxlee.R;

/**
 * Created by LS on 2017/5/24 0024.
 * 工具操作类 跳转Activity 工具类
 */

public class UIUtils {

    /**
     * 开启Activity，处理了非Activit上下文打开方式
     *
     * @param clazz 目标Activity的Class对象
     */
    public static void startActivity(Context context, Class<? extends Activity> clazz) {
        startActivity(context, clazz, null);
    }

    /**
     * 开启Activity，处理了非Activit上下文打开方式
     *
     * @param clazz 目标Activity的Class对象
     * @param extra 携带的参数
     */
    public static void startActivity(Context context, Class<? extends Activity> clazz, Bundle extra) {
        Intent intent = new Intent(context, clazz);
        if (extra != null) intent.putExtras(extra);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    public static void startActivityForResult(Activity context, Class<? extends Activity> clazz, Bundle extra, int requestCode) {
        Intent intent = new Intent(context, clazz);
        if (extra != null) intent.putExtras(extra);
        context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
        context.startActivityForResult(intent, requestCode);
    }

}
