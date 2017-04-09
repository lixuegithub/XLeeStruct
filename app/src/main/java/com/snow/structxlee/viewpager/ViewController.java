package com.snow.structxlee.viewpager;

import android.view.View;

public interface ViewController {
    /**
     * 显示加载框
     */
    public void showLoadView();

    /**
     * 隐藏加载框
     */
    public void hideLoadView();

    /**
     * 显示内容
     */
    public void showContentView();

    /**
     * 隐藏内容
     */
    public void hideContentView();

    /**
     * 隐藏空数据提示
     */
    public void hideEmptyView();

    /**
     * 显示空数据提示
     */
    public View showEmptyView(String msg);

    /**
     * 显示异常提示
     */
    public View showErrorView(String msg);

    /**
     * 隐藏异常提示
     */
    public void hideErrorView();

}
