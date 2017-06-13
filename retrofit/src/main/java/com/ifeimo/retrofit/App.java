package com.ifeimo.retrofit;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.LoginFilter;
import android.util.Log;

/**
 * Created by lpds on 2017/5/31.
 */
public class App extends Application implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "App";
    public static String MOB_API;
    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationInfo appInfo = null;
        application = this;
        try {
            appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            MOB_API = appInfo.metaData.getString("mob_api");
            Log.i(TAG, "onCreate: MOB_API = "+MOB_API);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.print(t.getContextClassLoader());
    }
}
