package com.birdaaron.wanandroid.view.main.view.tagview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView
{
    public MyHorizontalScrollView(Context context)
    {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    float mLastX = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        int actionMasked = event.getActionMasked();
        int maxScrollX = getChildAt(0).getMeasuredWidth() - getMeasuredWidth();
        int curScrollX = getScrollX();
        switch (actionMasked)
        {
            case MotionEvent.ACTION_DOWN :
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE :
                float delta = event.getX() - mLastX;
                if( (curScrollX==0&&delta>0) || (curScrollX==maxScrollX&&delta<0) )
                {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;
        }
        mLastX = event.getX();
        return super.onInterceptTouchEvent(event);
    }

}
