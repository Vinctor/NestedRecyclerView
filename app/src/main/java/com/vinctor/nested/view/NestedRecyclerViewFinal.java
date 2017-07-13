package com.vinctor.nested.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import cn.vinctor.loadingviewfinal.RecyclerViewFinal;

/**
 * Created by Vinctor on 2017/7/11.
 */

public class NestedRecyclerViewFinal extends RecyclerViewFinal implements NestedScrollingParent {
    NestedScrollingParentHelper mParentHelper;
    private ScrollerCompat mScroller;
    private RecyclerView recyclerViewChild;

    public NestedRecyclerViewFinal(Context context) {
        super(context);
        init(context);
    }

    public NestedRecyclerViewFinal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NestedRecyclerViewFinal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mParentHelper = new NestedScrollingParentHelper(this);
        mScroller = ScrollerCompat.create(getContext(), null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        SwipeRefreshLayout swip;
        if (parent instanceof SwipeRefreshLayout) {
            swip = (SwipeRefreshLayout) parent;
            swip.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
                //返回false 拦截  返回true 不拦截
                @Override
                public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
                    if (ViewCompat.canScrollVertically(NestedRecyclerViewFinal.this, -1)) {//子view可以向下滑动
                        return true;
                    } else {//不可以向下滑动,再判断内部recyclerview
                        if (recyclerViewChild != null) {
                            if (recyclerViewChild.canScrollVertically(-1)) {//recyclerview可以向下滑动,不拦截
                                return true;
                            } else return false;
                        }
                        return false;
                    }
                }
            });
        }
    }

    public void changeToOtherPage() {
        recyclerViewChild = null;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
        stopNestedScroll();
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        consumed[1] = 0;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        scrollBy(0, dyUnconsumed);
    }


    @Override
    public boolean onNestedFling(View target, float velocityX,
                                 float velocityY, boolean consumed) {
        if (target instanceof RecyclerView) {
            recyclerViewChild = ((RecyclerView) target);
            if ((velocityY > 0 && !recyclerViewChild.canScrollVertically(1))//向上
                    || (velocityY < 0 && !recyclerViewChild.canScrollVertically(-1))) {//向下
                return fling(0, (int) velocityY);
            }
        } else {
            if (!consumed) {
                return fling(0, (int) velocityY);
            }
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }
}
