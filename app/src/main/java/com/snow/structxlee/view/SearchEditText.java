package com.snow.structxlee.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snow.structxlee.R;

/**
 * 自定义搜索框
 * Created by Administrator on 2016/7/12.
 * 自定义view如果是布局引入，在引入类中也可以使用fbc_id找到改控件
 */
public class SearchEditText extends RelativeLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private Context context;
    private EditText et_search;
    private TextView tv_hint, tv_search;
    private LinearLayout ll_hint;
    private ImageView clear;//清空文本

    public interface searchCallBack {
        void search(String searchContent);
    }

    private searchCallBack searchCallBack;

    public void setSearchCallBack(SearchEditText.searchCallBack searchCallBack) {
        this.searchCallBack = searchCallBack;
    }

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View viewGroup = LayoutInflater.from(context).inflate(R.layout.view_search, this);
        et_search = (EditText) viewGroup.findViewById(R.id.et_search);
        ll_hint = (LinearLayout) viewGroup.findViewById(R.id.ll_hint);
        tv_hint = (TextView) viewGroup.findViewById(R.id.tv_hint);
        tv_search = (TextView) viewGroup.findViewById(R.id.tv_search);
        clear = (ImageView) viewGroup.findViewById(R.id.clear);
        setListener();
    }

    private void setListener() {
        et_search.addTextChangedListener(this);
        et_search.setOnFocusChangeListener(this);
        tv_search.setOnClickListener(this);
        ll_hint.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                searchCallBack.search(TextUtils.isEmpty(et_search.getText()) ? "" : et_search.getText().toString());
                break;
            case R.id.ll_hint:
                break;
            case R.id.clear:
                et_search.setText("");
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()) {
            // 当 输入 字符 长度 不为0 时 显示 删除 按钮
            if (s.length() > 0) {
                ll_hint.setVisibility(GONE);
                clear.setVisibility(VISIBLE);
            } else {
                ll_hint.setVisibility(VISIBLE);
                clear.setVisibility(GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // 如果 EditText的 焦点 改变了  则相应的 隐藏 显示 功能 按钮
        if (!hasFocus) {
            ll_hint.setVisibility(VISIBLE);
            clear.setVisibility(VISIBLE);
        } else if (et_search.getText().length() > 0) {
            ll_hint.setVisibility(GONE);
            clear.setVisibility(GONE);
        }
    }
}
