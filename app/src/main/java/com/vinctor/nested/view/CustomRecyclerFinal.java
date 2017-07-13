package com.vinctor.nested.view;

import android.content.Context;
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
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(lastY - y) > Math.abs(lastX - x)) {
                    requestDisallowIntercept(true);
                } else {
                    requestDisallowIntercept(false);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowIntercept(false);
                break;
        }
        return super.onTouchEvent(e);
    }

    ViewParent nestedRecyclerView;

    void requestDisallowIntercept(boolean intercept) {
        if (nestedRecyclerView != null) {
            nestedRecyclerView.requestDisallowInterceptTouchEvent(intercept);
            return;
        }
        nestedRecyclerView = getParent();
        while (nestedRecyclerView != null) {
            if (nestedRecyclerView instanceof NestedRecyclerViewFinal) {
                nestedRecyclerView.requestDisallowInterceptTouchEvent(intercept);
            }
            nestedRecyclerView = nestedRecyclerView.getParent();
        }
    }
}
