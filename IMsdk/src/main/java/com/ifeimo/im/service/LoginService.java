package com.ifeimo.im.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.ifeimo.im.framwork.account.IMAccountManager;
import com.ifeimo.im.framwork.connect.IMConnectManager;

/**
 * Created by lpds on 2017/1/11.
 */
public class LoginService extends Service {

    public static final String RELOGIN_KEY = "relogin";
    public static final int RELOGIN = 0x913;
    private static final String TGA = "XMPP_LoginService";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TGA," ---- 登陆服务开启 ---- ");
        IMConnectManager.getInstance().init(this);
        IMAccountManager.getInstances();


        startService(new Intent(this,MsgService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && intent.getIntExtra(RELOGIN_KEY,-1) == RELOGIN){
            IMConnectManager.getInstance().updateLogin();
        }else{
            IMConnectManager.getInstance().runConnectThread();
        }
        return Service.START_STICKY;
    }
}
