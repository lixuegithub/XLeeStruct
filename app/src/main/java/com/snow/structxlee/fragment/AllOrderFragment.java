package com.snow.structxlee.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.snow.structxlee.base.BaseFragment;

/**
 * 实现城市选择
 */
public class AllOrderFragment extends BaseFragment {
    public AllOrderFragment() {
    }

    public AllOrderFragment(Context mContext) {
    }

    @Override
    public String initContent() {
        return "我的订单";
    }
}
