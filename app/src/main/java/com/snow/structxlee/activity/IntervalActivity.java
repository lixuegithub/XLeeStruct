package com.snow.structxlee.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import com.snow.structxlee.R;
import com.snow.structxlee.base.BaseCommWithTopBar;
import com.trello.rxlifecycle.components.RxFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by XLee 图片轮询
 */

public class IntervalActivity extends BaseCommWithTopBar {
    ViewPager mViewPager;
    Button btn_start_loop,btn_stop_loop;

    private List<ImageView> mCacheViews = new ArrayList<>();

    private static final int[] DATAS = new int[]{R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3};
    private PicLoopAdapter loopAdapter;
    private Subscription subscribe_auto;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_interval);
        initView();
        initEvent();
        initViewPager();
    }
    private void initEvent() {
        btn_start_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoLoop();
            }
        });
        btn_stop_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribe_auto != null && !subscribe_auto.isUnsubscribed()) {
                    subscribe_auto.unsubscribe();
                }
            }
        });
    }

    private void initView() {
        mViewPager  =(ViewPager)findViewById(R.id.viewpager);
        btn_start_loop  =(Button)findViewById(R.id.btn_start_loop);
        btn_stop_loop  =(Button)findViewById(R.id.btn_stop_loop);
    }

    private void autoLoop() {
        if (subscribe_auto == null || subscribe_auto.isUnsubscribed()) {
            subscribe_auto = Observable.interval(3000, 3000, TimeUnit.MILLISECONDS)
                    //延时3000 ，每间隔3000，时间单位
                    //.compose(this.<Long>bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            int currentIndex = mViewPager.getCurrentItem();
                            if (++currentIndex == loopAdapter.getCount()) {
                                mViewPager.setCurrentItem(0);
                            } else {
                                mViewPager.setCurrentItem(currentIndex, true);
                            }
                        }
                    });
        }
    }

    private void initViewPager() {
        loopAdapter = new PicLoopAdapter(DATAS);
        mViewPager.setAdapter(loopAdapter);
        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IntervalActivity.this,"toast.."+mViewPager.getCurrentItem(),Toast.LENGTH_SHORT).show();
            }
        });
        try {
            //自定义滑动速度
            Field mScrollerField = ViewPager.class.getDeclaredField("mScroller");
            mScrollerField.setAccessible(true);
            mScrollerField.set(mViewPager, new ViewPagerScroller(mViewPager.getContext()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private class PicLoopAdapter extends PagerAdapter {

        private final int[] mDatas;

        public PicLoopAdapter(int[] datas) {
            this.mDatas = datas;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            int index = position % mDatas.length;
            ImageView iv;
            if (mCacheViews.size() > 0) {
                iv = mCacheViews.remove(0);
            } else {
                iv = new ImageView(IntervalActivity.this);
                iv.setLayoutParams(new ViewPager.LayoutParams());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            iv.setImageResource(mDatas[index]);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mCacheViews.add((ImageView) object);
        }
    }
    /**
     * 自定义Scroller，用于调节ViewPager滑动速度
     */
    public class ViewPagerScroller extends Scroller {
        private static final int M_SCROLL_DURATION = 1200;// 滑动速度

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, M_SCROLL_DURATION);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, M_SCROLL_DURATION);
        }
    }
}
