package com.l.linframwork.framework.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.l.linframwork.R;

/**
 * Created by lpds on 2017/6/10.
 */
public class ClickDarkLinearLayout extends LinearLayout implements GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;
    public ClickDarkLinearLayout(Context context) {
        super(context);
    }

    public ClickDarkLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, this);
    }

    public ClickDarkLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //在cancel里将滤镜取消，注意不要捕获cacncel事件,mGestureDetector里有对cancel的捕获操作
        //在滑动GridView时，AbsListView会拦截掉Move和UP事件，直接给子控件返回Cancel
        if(event.getActionMasked() == MotionEvent.ACTION_UP){
            removeFilter();
        }
        return gestureDetector.onTouchEvent(event);
    }

    private void setFilter(){
        setBackgroundColor(Color.parseColor("#66bbbcbb"));
    }

    private void removeFilter(){
        setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        setFilter();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        removeFilter();
        performClick();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        performLongClick();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
