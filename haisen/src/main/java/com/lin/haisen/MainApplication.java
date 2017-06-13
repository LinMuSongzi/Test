package com.lin.haisen;

import android.content.Intent;
import android.util.Log;

import com.l.linframwork.LibApplication;
import com.l.linframwork.framework.Proxy;
import com.lin.haisen.request.TestService;
import com.lin.haisen.request.UserService;
import com.lin.haisen.request.WeatherService;
import com.lin.haisen.ui.activity.UserOperateActivity;

/**
 * Created by lpds on 2017/6/13.
 */
public class MainApplication extends LibApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Proxy.getRequestManager().createImp(UserService.class, UserService.PATH);
        Proxy.getRequestManager().createImp(WeatherService.class, WeatherService.PATH);
        Proxy.getRequestManager().createImp(TestService.class, TestService.PATH);
    }
}
