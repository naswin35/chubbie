package com.cgtin.admin.sherazipetshopkimo.CommonClasses;

/**
 * Created by Admin on 10-03-2018.
 */


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class newViewpager extends android.support.v4.view.ViewPager {

    public newViewpager(Context context) {
        super(context);
    }

    public newViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
