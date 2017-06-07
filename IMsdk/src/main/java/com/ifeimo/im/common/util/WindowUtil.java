package com.ifeimo.im.common.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.ifeimo.im.R;

/**
 * Created by lpds on 2017/1/19.
 */
public class WindowUtil {

    public static int ID = 0x9314;

    public static void addMark(Activity activity){
        final ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View v = viewGroup.findViewById(ID);
        if(v == null){
            v = new View(activity);
            v.setBackgroundColor(Color.parseColor("#cc000000"));
            v.setId(ID);
            viewGroup.addView(v);
        }
    }

    public static void removeMark(Activity activity){
        final ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View v = viewGroup.findViewById(ID);
        if(v != null){
            viewGroup.removeView(v);
        }
    }


}
