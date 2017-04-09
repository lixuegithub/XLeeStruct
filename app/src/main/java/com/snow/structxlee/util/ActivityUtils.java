package com.snow.structxlee.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.snow.structxlee.R;


public class ActivityUtils {

    public static String getActivityLabel(Activity activity) {
        try {
            int labelRes = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(), PackageManager.GET_META_DATA).labelRes;
            return activity.getString(labelRes);
        } catch (Exception e) {
            return activity.getString(R.string.app_name);
        }

    }

    public static String getClassName(Object instance) {
        String activityName = instance.getClass().getSimpleName();
        if (activityName.indexOf(".") >= 0) {
            int index = activityName.lastIndexOf(".");
            activityName = activityName.substring(index + 1);
        }
        return activityName;
    }

}
