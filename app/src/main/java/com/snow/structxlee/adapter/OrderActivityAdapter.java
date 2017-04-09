package com.snow.structxlee.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.snow.structxlee.bean.BasicInteractTab;
import com.snow.structxlee.fragment.AllOrderFragment;

public class OrderActivityAdapter extends FragmentPagerAdapter {
	private List<? extends BasicInteractTab> mTitles = new ArrayList<BasicInteractTab>();
	private Context mContext;
	private Fragment all = null;
	private Fragment for_payment = null;
	private Fragment wait_send_goods = null;
	private Fragment wait_for_goods = null;
	private Fragment return_of = null;

	public OrderActivityAdapter(FragmentManager fm) {
		super(fm);

	}

	public OrderActivityAdapter(FragmentManager fm,
								List<? extends BasicInteractTab> titles, Context context) {
		super(fm);
		this.mTitles = titles;
		this.mContext = context;
		all = new AllOrderFragment(mContext);
		for_payment = new AllOrderFragment(mContext);
		wait_send_goods = new AllOrderFragment(mContext);
		wait_for_goods = new AllOrderFragment(mContext);
		return_of = new AllOrderFragment(mContext);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		if (mTitles != null && mTitles.size() > 0) {
			BasicInteractTab tab = mTitles.get(position);
			if (tab != null) {
				switch (tab.getAreaType()) {
				case BasicInteractTab.TabType.TYPE_ALL:
					fragment=all;
					break;
				case BasicInteractTab.TabType.TYPE_FOR_PAYMENT:
					fragment=for_payment;
					break;
				case BasicInteractTab.TabType.TYPE_WAIT_SEND_GOODS:
					fragment=wait_send_goods;
					break;
				case BasicInteractTab.TabType.TYPE_WAIT_GOODS:
					fragment=wait_for_goods;
					break;
				case BasicInteractTab.TabType.TYPE_RETURN_OF:
					fragment=return_of;
					break;

				default:
					break;
				}
			}
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return mTitles == null ? 0 : mTitles.size();
	}

	public CharSequence getPageTitle(int position) {
		if (mTitles != null && mTitles.size() > 0
				&& mTitles.get(position) != null) {
			return mTitles.get(position).getName();
		}
		return "";
	}

}
