package com.lin.haisen.util;

import android.content.Context;
import android.content.Intent;

import com.l.linframwork.framework.Proxy;
import com.l.linframwork.framework.activity.IActivity;
import com.lin.haisen.ui.activity.UserOperateActivity;

/**
 * Created by lpds on 2017/6/12.
 */
public class ActivityToggle {

    public static final void startRegisterActivity(Context activity){
        Intent intent = new Intent(activity, UserOperateActivity.class);
        intent.putExtra(UserOperateActivity.ACTION,UserOperateActivity.REGISTER);
        activity.startActivity(intent);
    }

    public static final void startLoginActivity(Context activity){
        Intent intent = new Intent(activity, UserOperateActivity.class);
        intent.putExtra(UserOperateActivity.ACTION,UserOperateActivity.LOGIN);
        activity.startActivity(intent);
    }

}
