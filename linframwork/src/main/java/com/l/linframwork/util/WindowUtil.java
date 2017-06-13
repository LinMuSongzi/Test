package com.l.linframwork.util;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.l.linframwork.R;
import com.l.linframwork.framework.activity.IActivity;

/**
 * Created by lpds on 2017/6/10.
 */
public class WindowUtil {

    private static final int SHADOW_VIEW_ID = 0x998;
    public static final int COLOR_BlACK = Color.BLACK;
    public static final int COLOR_BULE = Color.WHITE;
    public static final int COLOR_RED = Color.RED;
    public static final int COLOR_GRAY = Color.GRAY;
    public static final int COLOR_YELLOW = Color.YELLOW;
    public static final int COLOR_GREEN = Color.GREEN;
    public static final int COLOR_PINK = Color.MAGENTA;

    public static final void addShadowBG(IActivity activity,boolean transmit){
        View view = new View(activity.getActivity());
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        view.setBackgroundColor(Color.parseColor("#77000000"));
        ((ViewGroup)activity.getParentView()).addView(view,layoutParams);
        if(transmit){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        view.setId(SHADOW_VIEW_ID);
    }

    public static final void removeShadowBG(IActivity activity){
        final View v = ((ViewGroup)activity.getParentView()).findViewById(SHADOW_VIEW_ID);
        if(v != null) {
            ((ViewGroup) activity.getParentView()).removeView(v);
        }
    }

}
