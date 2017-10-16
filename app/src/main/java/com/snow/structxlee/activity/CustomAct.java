package com.snow.structxlee.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.snow.structxlee.R;
import com.snow.structxlee.view.ImgEditText;

/**
 * test 自定义view
 */
public class CustomAct extends Activity {

    boolean isHidden;
    ImgEditText pwdIet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        pwdIet = (ImgEditText) this.findViewById(R.id.pwdIet);
        pwdIet.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                if (isHidden) {
                    //设置EditText文本为可见的
                    pwdIet.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwdIet.setRightDrawable(getResources().getDrawable(R.mipmap.mimaxianshi));
                } else {
                //设置EditText文本为隐藏的
                    pwdIet.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwdIet.setRightDrawable(getResources().getDrawable(R.mipmap.mimaguanbi));
                }
                isHidden = !isHidden;
                pwdIet.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = pwdIet.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
    }
}
