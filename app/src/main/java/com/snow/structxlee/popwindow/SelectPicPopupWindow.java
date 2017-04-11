package com.snow.structxlee.popwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.snow.structxlee.R;

public class SelectPicPopupWindow extends PopupWindow {

    private Button takePhotoBtn;//拍照
    private Button pickPhotoBtn;//图库
    private Button cancelBtn;//取消

    @SuppressLint("InflateParams")
    public SelectPicPopupWindow(Context context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.popuwindow_pic, null);
        takePhotoBtn = (Button) mView.findViewById(R.id.takePhotoBtn);
        pickPhotoBtn = (Button) mView.findViewById(R.id.pickPhotoBtn);
        cancelBtn = (Button) mView.findViewById(R.id.cancelBtn);
        // 设置按钮监听
        cancelBtn.setOnClickListener(itemsOnClick);
        pickPhotoBtn.setOnClickListener(itemsOnClick);
        takePhotoBtn.setOnClickListener(itemsOnClick);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽/高
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable(0x80000000));
        this.setOutsideTouchable(true);//设置点击屏幕其他地方弹出框消失
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
    }

}
