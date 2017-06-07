package com.ifeimo.im.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ifeimo.im.common.util.ConnectUtil;
import com.ifeimo.im.service.LoginService;

/**
 * Created by lpds on 2017/1/11.
 */
public class ConnectReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectUtil.isConnect(context)) {
            context.startService(new Intent(context, LoginService.class));
        }
    }
}
