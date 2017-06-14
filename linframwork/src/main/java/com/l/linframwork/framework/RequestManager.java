package com.l.linframwork.framework;


import com.l.linframwork.framework.data.RequestEntity;
import com.l.linframwork.framework.topinterface.Request;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by lpds on 2017/6/5.
 */
class RequestManager implements Request {
    private static RequestManager requestManager;
    private static OkHttpClient client;
    private Map<String,RequestEntity> sets;

//    private
    private RequestManager(){init();}
    static {
        requestManager = new RequestManager();
        client = new OkHttpClient();
    }
    public static RequestManager getInstances(){
        return requestManager;
    }

    @Override
    public <T> void createImp(Class<T> c,String path){
        if(!sets.containsKey(c.getSimpleName())){
            RequestEntity<T> buidler = new RequestEntity<T>();
            buidler.setPath(path);
            buidler.setRetrofit(new Retrofit.Builder().baseUrl(buidler.getPath()).
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build());
            buidler.setInterfaceEntity(buidler.getRetrofit().create(c));
            sets.put(c.getName(),buidler);
        }
    }

    public <T> RequestEntity<T> getBuidler(Class<T> c) {
        return sets.get(c.getName());
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
        sets = new HashMap<>();
    }


}
