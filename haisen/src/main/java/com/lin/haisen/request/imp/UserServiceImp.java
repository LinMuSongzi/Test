package com.lin.haisen.request.imp;

import android.util.Log;

import com.google.gson.JsonObject;
import com.l.linframwork.framework.Proxy;
import com.lin.haisen.data.entity.UserEntity;
import com.lin.haisen.data.respone.LoginRespone;
import com.lin.haisen.request.UserService;

import rx.Subscriber;

/**
 * Created by lpds on 2017/6/12.
 */
public class UserServiceImp {

    public static final String TAG = "UserServiceImp";

    public static void register(UserEntity userEntity,Subscriber<JsonObject> subscriber){
        Log.i(TAG, "register: "+userEntity);
        Proxy.getRequestManager().
                getBuidler(UserService.class).
                getInterfaceEntity().registerUser(userEntity).
                subscribe(subscriber);
    }

    public static void login1(UserEntity userEntity,Subscriber<LoginRespone> subscriber){
        Log.i(TAG, "login1: "+userEntity);
        Proxy.getRequestManager().
                getBuidler(UserService.class).
                getInterfaceEntity().loginByUser(userEntity).
                subscribe(subscriber);
    }

}
