package com.l.linframwork.framework;

import android.os.Handler;
import android.os.HandlerThread;

import com.l.linframwork.IEmployee;
import com.l.linframwork.framework.topinterface.Request;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by lpds on 2017/6/5.
 */
class RequestManager implements Request{
    private static RequestManager requestManager;
    private static OkHttpClient client;
    private Buidler buidler;
    private Map<String,Object> sets;

//    private
    private RequestManager(){init();}
    static {
        requestManager = new RequestManager();
        client = new OkHttpClient();
    }
    private Map<String,Handler> map = new HashMap<>();
    public static RequestManager getInstances(){
        return requestManager;
    }


    private <T> T create(Class<T> c){
        if(!sets.containsKey(c.getSimpleName())){
            sets.put(c.getSimpleName(),new Retrofit.Builder().baseUrl(buidler.path).
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build().create(c));
        }
        return (T) sets.get(c.getSimpleName());
    }

    @Override
    public boolean hadOfficeholding() {
        return false;
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
        buidler = new Buidler();
        sets = new HashMap<>();
    }


    public static class Buidler{

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }


}
