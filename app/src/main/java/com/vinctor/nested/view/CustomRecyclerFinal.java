package com.vinctor.nested.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import cn.vinctor.loadingviewfinal.RecyclerViewFinal;

/**
 * Created by Vinctor on 2017/7/12.
 */

public class CustomRecyclerFinal extends RecyclerViewFinal {
    public CustomRecyclerFinal(Context context) {
        super(context);
    }

    public CustomRecyclerFinal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerFinal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float lastX;
    float lastY;
    boolean isFirst = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
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
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFirst) {
                    if (Math.abs(lastY - y) * 2 > Math.abs(lastX - x)) {//垂直滑动,viewpager不拦截
                        requestDisallowIntercept(true);
                    } else {//水平滑动,recyclerview不处理 viewpager处理
                        requestDisallowIntercept(false);
                        return false;
                    }
                    isFirst = false;
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
        ViewParent view = getParent();
        while (view != null) {
            view.requestDisallowInterceptTouchEvent(b);
            view = view.getParent();
        }
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
