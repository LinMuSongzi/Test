package com.l.linframwork;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by lpds on 2017/6/6.
 */
public class LibApplication extends Application implements Thread.UncaughtExceptionHandler {

    public static final String MOB_API_KEY = "1e47c1e7361fe";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.print(t.getContextClassLoader());
    }
}
