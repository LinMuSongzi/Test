package com.l.linframwork.framework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.l.linframwork.framework.thread.IExecute;

/**
 * Created by lpds on 2017/6/5.
 */
public interface IActivity {
    void leave();
    void beforeInit(Bundle bundle);
    String getKey();
    View getParentView();
    void postUiThread(IExecute runnable);
    void postThread(IExecute runnable);
    Activity getActivity();
    void showToast(String s);
}
