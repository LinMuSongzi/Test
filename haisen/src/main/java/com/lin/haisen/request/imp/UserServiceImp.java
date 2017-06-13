package com.lin.haisen.request.imp;

import com.google.gson.JsonObject;
import com.l.linframwork.framework.Proxy;
import com.l.linframwork.framework.entity.RequestEntity;
import com.lin.haisen.data.entity.UserEntity;
import com.lin.haisen.request.UserService;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lpds on 2017/6/12.
 */
public class UserServiceImp {

    public static void register(UserEntity userEntity,Subscriber<JsonObject> subscriber){
        Proxy.getRequestManager().
                getBuidler(UserService.class).
                getInterfaceEntity().registerUser(userEntity).
                subscribe(subscriber);
    }

    public static void login1(UserEntity userEntity,Subscriber<JsonObject> subscriber){
        Proxy.getRequestManager().
                getBuidler(UserService.class).
                getInterfaceEntity().loginByUser(userEntity).
                subscribe(subscriber);
    }

}
