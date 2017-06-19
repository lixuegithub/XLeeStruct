package com.snow.structxlee.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.snow.structxlee.R;


/**
 * Created by asus on 2016/11/24
 */
public class PwdEditText extends EditText implements View.OnFocusChangeListener,
        TextWatcher {

    private Drawable eysOpenDrawable;
    private Drawable eyesCloseDrawable;

    private int length = 0;//记录字符的长度

    private Boolean isShowPwd = true;

    public PwdEditText(Context context) {
        this(context, null);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        eysOpenDrawable = getCompoundDrawables()[2];
        if (eysOpenDrawable == null) {
            eysOpenDrawable = getResources().getDrawable(R.mipmap.icon_yanjingkai);
        }
        eyesCloseDrawable = getResources().getDrawable(R.mipmap.icon_yanjing);

        eysOpenDrawable.setBounds(0, 0, eysOpenDrawable.getIntrinsicWidth()+15,
                eysOpenDrawable.getIntrinsicHeight()+15);
        eyesCloseDrawable.setBounds(0, 0, eyesCloseDrawable.getIntrinsicWidth()+15,
                eyesCloseDrawable.getIntrinsicHeight()+15);

        setClearIconVisible(false);
        setOnFocusChangeListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth()
                        - getPaddingRight() - eysOpenDrawable
                        .getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
//                    this.setText("");
                    Log.i("is_useful", isShowPwd + "");
                    if (isShowPwd) {

                        isShowPwd = false;
                        setCompoundDrawables(getCompoundDrawables()[0],
                                getCompoundDrawables()[1], eyesCloseDrawable, getCompoundDrawables()[3]);
                        this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        isShowPwd = true;

                        setCompoundDrawables(getCompoundDrawables()[0],
                                getCompoundDrawables()[1], eysOpenDrawable, getCompoundDrawables()[3]);
                        //设置密码为隐藏的
                        this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
//        Drawable right = visible ? eysOpenDrawable : null;
        Drawable right = visible ? eysOpenDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    private boolean is_useful = true;

    public void setIs_useful(boolean is_useful) {
        this.is_useful = is_useful;
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

        if (length != s.length()) {
            length = s.length();
            if (is_useful) {
                setClearIconVisible(s.length() > 0);
            } else {
                setClearIconVisible(false);
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}

