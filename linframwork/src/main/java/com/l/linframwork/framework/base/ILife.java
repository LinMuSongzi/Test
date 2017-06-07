package com.l.linframwork.framework.base;


import android.content.Intent;

import com.l.linframwork.framework.activity.IActivity;

/**
 * Created by lpds on 2017/6/5.
 */
public interface ILife{

    void onCreate(IActivity activity);

    void onResume(IActivity activity);

    void onStart(IActivity activity);

    void onPause(IActivity activity);

    void onDestrory(IActivity activity);

    void onNewIntent(IActivity activity, Intent intent);

}
