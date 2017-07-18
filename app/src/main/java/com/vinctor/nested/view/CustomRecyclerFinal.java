package com.vinctor.nested.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;

/**
 * Created by Vinctor on 2017/7/12.
 */

public class CustomRecyclerFinal extends RecyclerView {
    int mTouchSlop;

    public CustomRecyclerFinal(Context context) {
        super(context);
        init(context);
    }

    public CustomRecyclerFinal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRecyclerFinal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.getTouchSlop();
    }

    float lastX;
    float lastY;
    boolean isFirst = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                requestDisallowIntercept(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowIntercept(false);
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        float x = e.getRawX();
        float y = e.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                requestDisallowIntercept(true);
                lastX = x;
                lastY = y;
                isFirst = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isFirst) {
                    isFirst = false;
                    if (Math.abs(lastY - y) * 2 > Math.abs(lastX - x)) {//垂直滑动,viewpager不拦截
                        requestDisallowIntercept(true);
                        return true;
                    } else {//水平滑动,recyclerview不处理 viewpager处理
                        requestDisallowIntercept(false);
                        return false;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowIntercept(false);
                break;
        }
        return super.onTouchEvent(e);
    }

    private void requestDisallowIntercept(boolean b) {
        requestRecyclerViewDisallowIntercept(b);
    }

    ViewParent recyclerview;

    void requestRecyclerViewDisallowIntercept(boolean intercept) {
        if (recyclerview != null) {
            recyclerview.requestDisallowInterceptTouchEvent(intercept);
            return;
        }
        ViewParent view = getParent();
        while (view != null) {
            if (view instanceof NestedRecyclerViewFinal) {
                view.requestDisallowInterceptTouchEvent(intercept);
                recyclerview = view;
                break;
            }
            view = view.getParent();
        }
    }

    ViewParent viewPager;

    void requestViewPagerDisallowIntercept(boolean intercept) {
        if (viewPager != null) {
            viewPager.requestDisallowInterceptTouchEvent(intercept);
            return;
        }
        ViewParent view = getParent();
        while (view != null) {
            if (view instanceof ViewPager) {
                view.requestDisallowInterceptTouchEvent(intercept);
                viewPager = view;
                break;
            }
            view = view.getParent();
        }
    }
}
