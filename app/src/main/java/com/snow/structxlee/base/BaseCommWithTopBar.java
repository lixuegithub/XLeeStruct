package com.snow.structxlee.base;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.snow.structxlee.R;
import com.snow.structxlee.comm.TopBarView;
import com.snow.structxlee.util.ActivityUtils;

public class BaseCommWithTopBar extends FragmentActivity  implements TopBarView.TopBarViewListener {

	protected String activityTag;
	private TopBarView view = null;
	private LinearLayout rootView = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		activityTag = ActivityUtils.getClassName(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		rootView = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.activity_root_layout, null);
		view = (TopBarView) rootView.findViewById(R.id.topbar);
		initTopbarView();
		LinearLayout container = (LinearLayout) rootView
				.findViewById(R.id.container);
		LayoutInflater.from(this).inflate(layoutResID, container);
		super.setContentView(rootView);

	}

	@Override
	public void setContentView(View view) {
		LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.activity_root_layout, null);
		view = (TopBarView) root.findViewById(R.id.topbar);
		initTopbarView();
		LinearLayout container = (LinearLayout) root
				.findViewById(R.id.container);
		container.addView(view);
		super.setContentView(root);
	}

	private void initTopbarView() {
		if (view == null)
			return;
		List<String> item = new ArrayList<String>();
		item.add(ActivityUtils.getActivityLabel(this));
		view.setTitleItem(item);
		view.setListener(this);
		view.setLeftMenuVisiable(true, R.drawable.back);
		view.updateTopBarView();
	}

	public TopBarView getTopBarView() {
		return view;
	}

	@Override
	public void onLeftMenuClick() {
		finish();
	}

	@Override
	public void onRightMenuOneClick() {
	}

	@Override
	public void onRightMenuTwoClick() {
		
	}

	@Override
	public void onCenterMenuItemClick(int position) {
		
	}

	public LinearLayout getRootView() {
		return rootView;
	}

    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    }

}
