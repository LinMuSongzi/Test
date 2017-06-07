package com.l.linframwork.framework.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.l.linframwork.framework.Proxy;
import com.l.linframwork.framework.entity.EmptyEntity;
import com.l.linframwork.framework.entity.JsonEntity;
import com.l.linframwork.framework.thread.IExecute;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by lpds on 2017/6/5.
 */
public abstract class BaseActivity  extends AppCompatActivity implements IActivity{

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
        Proxy.getLifeManager().onCreate(this);
        setContentView(getContentView());
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
        Proxy.getLifeManager().onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Proxy.getLifeManager().onPause(this);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Proxy.getLifeManager().onNewIntent(this,intent);
        super.onNewIntent(intent);

    }

    @Override
    protected void onDestroy() {
        Proxy.getLifeManager().onDestrory(this);
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
        if(key == null){
            return key = String.valueOf(hashCode());
        }
        return key;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmpty(EmptyEntity e){

    }
}
