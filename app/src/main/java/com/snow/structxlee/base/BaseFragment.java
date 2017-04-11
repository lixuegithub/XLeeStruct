package com.snow.structxlee.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snow.structxlee.R;


/**
 * Created by XLee
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        TextView tv = (TextView) view.findViewById(R.id.tv_fragment);
        tv.setText(initContent());
        return view;

    }

    public abstract String initContent();
}
