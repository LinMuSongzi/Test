package com.l.linframwork.framework;


import android.content.Intent;

import com.l.linframwork.IEmployee;
import com.l.linframwork.framework.activity.IActivity;
import com.l.linframwork.framework.base.ILife;
import com.l.linframwork.framework.topinterface.Link;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;

/**
 * Created by lpds on 2017/6/5.
 */
final class ActivityBoss implements ILife,IEmployee,Link {

    private ActivityBoss(){
        iActivitys = new LinkedList<>();
    };
    private static ActivityBoss activityBoss;
    static{
        activityBoss  = new ActivityBoss();
    }

    public static Link getInstances(){
        return activityBoss;
    }

    private LinkedList<IActivity> iActivitys;

    @Override
    public void onCreate(IActivity activity) {
        iActivitys.add(activity);
        EventBus.getDefault().register(activity);
        ThreadManager.getInstances().createThreadPoolBykey(activity.getKey());
    }

    @Override
    public void onResume(IActivity activity) {
    }

    @Override
    public void onStart(IActivity activity) {

    }

    @Override
    public void onPause(IActivity activity) {

    }

    @Override
    public void onDestrory(IActivity activity) {
        iActivitys.remove(activity);
        EventBus.getDefault().unregister(activity);
        ThreadManager.getInstances().leaveThreadByPoolByKey(activity.getKey());
    }

    @Override
    public void onNewIntent(IActivity activity, Intent intent) {

    }

    @Override
    public boolean hadOfficeholding() {
        return true;
    }

    @Override
    public boolean hadDimission() {
        return false;
    }

    @Override
    public void fire() {

    }

    @Override
    public void init() {
    }

    @Override
    public IActivity getLast() {
        return iActivitys.getLast();
    }
}
