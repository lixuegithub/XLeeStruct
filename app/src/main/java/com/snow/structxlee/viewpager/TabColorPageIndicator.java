package com.snow.structxlee.viewpager;

import java.util.ArrayList;
import java.util.List;


import com.snow.structxlee.R;
import com.snow.structxlee.viewpager.TabPageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class TabColorPageIndicator extends TabPageIndicator {

	String TAG = TabColorPageIndicator.class.getName();

	List<TabView> tabs = new ArrayList<TabView>();

	Context context;

	int tabPadding = 24;

	//线在字的上面
	private boolean isLineOnTop = true;

	public TabColorPageIndicator(Context context) {
		super(context);
		this.context = context;
	}

	public TabColorPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void addTab(int index, CharSequence text, int iconResId) {
		final TabView tabView = new TabView(getContext());
		tabView.setGravity(Gravity.CENTER);
		tabView.mIndex = index;
		tabView.setPadding(tabPadding, tabPadding, tabPadding, tabPadding);
		tabView.setFocusable(true);
		tabView.setOnClickListener(mTabClickListener);
		tabView.setTextSize(14);
		tabView.setTextColor(getResources().getColor(R.color.text_main));
		tabView.setText(text);
		if (iconResId != 0) {
			tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
		}
		mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
		tabs.add(tabView);
	}

	@Override
	public void onPageSelected(int position) {
		Log.i(TAG, "position:" + position);
		setCurrentItem(position);
		resetTabs(tabs.get(position));
		if (mListener != null) {
			mListener.onPageSelected(position);
		}
	}

	@Override
	public void setViewPager(ViewPager view) {
		super.setViewPager(view);
		Log.i(TAG, "mSelectedTabIndex:" + mSelectedTabIndex);
		resetTabs(tabs.get(mSelectedTabIndex));
	}

	public void resetTabs(TabView tabView) {
		for (TabView tb : tabs) {
			tb.setTextColor(Color.BLACK);
			tb.setBackgroundColor(Color.WHITE);
			tb.setPadding(tabPadding, tabPadding, tabPadding, tabPadding);
		}
		tabView.setTextColor(Color.RED);
		if(isLineOnTop){
			tabView.setBackgroundResource(R.drawable.tab_top_line);
		}else{
			tabView.setBackgroundResource(R.drawable.tab_bottom_line);
		}
	}

	public boolean isLineOnTop() {
		return isLineOnTop;
	}

	public void setLineOnTop(boolean isLineOnTop) {
		this.isLineOnTop = isLineOnTop;
	}

	final OnClickListener mTabClickListener = new OnClickListener() {
		public void onClick(View view) {
			TabView tabView = (TabView) view;
			resetTabs(tabView);
			final int oldSelected = mViewPager.getCurrentItem();
			final int newSelected = tabView.getIndex();
			mViewPager.setCurrentItem(newSelected);
			if (oldSelected == newSelected && mTabReselectedListener != null) {
				mTabReselectedListener.onTabReselected(newSelected);
			}
		}
	};

}
