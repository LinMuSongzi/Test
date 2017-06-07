package com.ifeimo.test;

import android.widget.TextView;

/**
 * Created by lpds on 2017/1/3.
 */
public class StringUtil {

    public static boolean isNullTextView(TextView s){
        return s == null || s.getText().toString().trim().equals("");
    }
}
