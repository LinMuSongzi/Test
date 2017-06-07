package com.ifeimo.retrofit.retrofit;

import android.os.Handler;
import android.os.HandlerThread;


import com.ifeimo.retrofit.model.UserEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Response;

/**
 * Created by lpds on 2017/6/5.
 */
public class RequestManager {

    Map<String,Handler> map = new HashMap<>();

    private RequestManager(){

    }
    private static RequestManager requestManager;
    static {
        requestManager = new RequestManager();
    }

    public static RequestManager getInstances(){
        return requestManager;
    }


    public void addActivity(String key){
        HandlerThread handlerThread = new HandlerThread(key);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        map.put(key,handler);
    }

    public void register(String key, final String acount, final String password){
        if( map.get(key) == null){
            addActivity(key);
        }
        map.get(key).post(new Runnable() {
            @Override
            public void run() {
                UserEntity userEntity = new UserEntity();
                userEntity.setAccount(acount);
                userEntity.setPassword(password);
                try {
                    AnXingImp.register1(userEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void baidu(String key){
        if( map.get(key) == null){
            addActivity(key);
        }
        map.get(key).post(new Runnable() {
            @Override
            public void run() {
                try {
                    AnXingImp.baidu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T> T responeConvert(Response<T> response){

        if(response.isSuccess() && response.body()!=null){
                return response.body();
        }else {
            return null;
        }
    }


}
