package com.project.rudy.lekanmovie.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by maoyan on 2018/5/9.
 */

public class UnScrollableViewPager extends ViewPager {

    public UnScrollableViewPager(Context context) {
        super(context);
    }

    public UnScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
