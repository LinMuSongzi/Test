package com.l.linframwork.framework.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.l.linframwork.framework.Proxy;
import com.l.linframwork.framework.base.ILife;
import com.l.linframwork.framework.data.EmptyEntity;
import com.l.linframwork.framework.thread.IExecute;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by lpds on 2017/6/5.
 */
public abstract class BaseActivity  extends AppCompatActivity implements IActivity{
    private Toast toast;
    private String key;
    protected final String TAG = "Lib_lin_"+getClass().getSimpleName();
//    protected Handler activityHandler;

    @Override
    public void leave() {
        finish();
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ILife)Proxy.getActivityManager()).onCreate(this);
        beforeInit(savedInstanceState);
        setContentView(getContentView());
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void postUiThread(IExecute runnable) {
        runOnUiThread(runnable);
    }

    @Override
    public void postThread(IExecute runnable) {
        Proxy.getThread().execute(getKey(), runnable);
    }

    @Override
    protected void onResume() {
        ((ILife)Proxy.getActivityManager()).onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        ((ILife)Proxy.getActivityManager()).onPause(this);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        ((ILife)Proxy.getActivityManager()).onNewIntent(this,intent);
        super.onNewIntent(intent);

    }

    @Override
    protected void onDestroy() {
        ((ILife)Proxy.getActivityManager()).onDestrory(this);
        super.onDestroy();
    }

    @Override
    public View getParentView() {
        return getWindow().getDecorView();
    }

    protected abstract int getContentView();

    protected  abstract void initView();

    @Override
    public String getKey() {
        if(isFinishing()){
            Log.i(TAG, "getKey: Thread Name = "+Thread.currentThread().getName()+" the activity is finfish");
//            return null;
        }
        if(key == null){
            return key = String.valueOf(hashCode());
        }
        return key;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmpty(EmptyEntity e){

    }

    @Override
    public void showToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.setText(s);
                toast.show();
            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
