package com.snow.structxlee.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.animation.DecelerateInterpolator;

import com.snow.structxlee.R;
import com.snow.structxlee.adapter.OrderActivityAdapter;
import com.snow.structxlee.base.BaseCommWithTopBar;
import com.snow.structxlee.bean.BasicInteractTab;
import com.snow.structxlee.bean.InteractTab;
import com.snow.structxlee.comm.TopBarView;
import com.snow.structxlee.util.Config;
import com.snow.structxlee.viewpager.TabColorPageIndicator;
import com.snow.structxlee.viewpager.ViewPagerScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseCommWithTopBar {
	private TopBarView topBarView;
	private Context context;
	private TabColorPageIndicator indicator;
	private List<InteractTab> mtab;
	private OrderActivity instance=null;
	public  static final String name="q";
	private static ViewPager viewPager;
	public void setTabs(List<InteractTab> tab) {
		if (tab != null && tab.size() > 0) {
			mtab = tab;
		}
	}
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myorder);
		context=OrderActivity.this;
		instance=OrderActivity.this;
		mtab=new ArrayList<InteractTab>();
		initView();
	}
	@Override
	protected void onResume() {
		super.onResume();
		setSelect(Config.MYORDER_TYPE);
	}

	public void initView() {
		initTopBar();
		indicator = (TabColorPageIndicator) findViewById(R.id.indicator);
		viewPager = (ViewPager) findViewById(R.id.interactive_pager);
		try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			ViewPagerScroller mScroller = new ViewPagerScroller(context, new DecelerateInterpolator());
			mScroller.setDurationTime(200);
			mField.set(viewPager, mScroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InteractTab tab_all=new InteractTab();
		tab_all.setAreaType(BasicInteractTab.TabType.TYPE_ALL);
		tab_all.setName("全部");

		InteractTab tab_for_payment=new InteractTab();
		tab_for_payment.setAreaType(BasicInteractTab.TabType.TYPE_FOR_PAYMENT);
		tab_for_payment.setName("待付款");

		InteractTab tab_wait_send_goods=new InteractTab();
		tab_wait_send_goods.setAreaType(BasicInteractTab.TabType.TYPE_WAIT_SEND_GOODS);
		tab_wait_send_goods.setName("待发货");

		InteractTab tab_wait_for_goods=new InteractTab();
		tab_wait_for_goods.setAreaType(BasicInteractTab.TabType.TYPE_WAIT_GOODS);
		tab_wait_for_goods.setName("待收货");

		InteractTab tab_return_of=new InteractTab();
		tab_return_of.setAreaType(BasicInteractTab.TabType.TYPE_RETURN_OF);
		tab_return_of.setName("退款中");
		mtab.add(tab_all);
		mtab.add(tab_for_payment);
		mtab.add(tab_wait_send_goods);
		mtab.add(tab_wait_for_goods);
		mtab.add(tab_return_of);
		OrderActivityAdapter activityAdapter=new OrderActivityAdapter(getSupportFragmentManager(), mtab, instance);
		viewPager.setAdapter(activityAdapter);
		indicator.setViewPager(viewPager);

	}
	public static void setSelect(int position) {
		if (viewPager != null) {
			viewPager.setCurrentItem(position);
		}
	}

	public void initTopBar(){
		topBarView=(TopBarView) findViewById(R.id.topbar);
		topBarView.setTitle("我的订单");
		topBarView.setListener(this);
		topBarView.updateTopBarView();
	}

}
