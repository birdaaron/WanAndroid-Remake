package com.birdaaron.wanandroid.view.main.view.refreshLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import androidx.viewpager2.widget.ViewPager2;

public class MyRefreshLayout extends SmartRefreshLayout
{
    private float lastX;
    private float lastY;
    public MyRefreshLayout(Context context)
    {
        super(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        int action = ev.getActionMasked();
        float x = ev.getX();
        float y = ev.getY();

        if(isLoading()||isRefreshing())
            System.out.println("test"+ev.getAction());
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;


        }

        lastX = x;
        lastY = y;
        return super.onInterceptTouchEvent(ev);
    }
}
