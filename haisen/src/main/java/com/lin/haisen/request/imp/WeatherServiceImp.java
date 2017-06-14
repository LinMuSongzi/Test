package com.lin.haisen.request.imp;

import com.google.gson.JsonObject;
import com.l.linframwork.framework.Proxy;
import com.l.linframwork.framework.data.RequestEntity;
import com.lin.haisen.MainApplication;
import com.lin.haisen.request.WeatherService;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lpds on 2017/6/13.
 */
public class WeatherServiceImp {

    public static void getAllWeather(Subscriber<JsonObject> subscriber){

        RequestEntity<WeatherService> requestEntity = Proxy.getRequestManager()
                .getBuidler(WeatherService.class);
        Subscription subscribe = requestEntity.getInterfaceEntity().getAllMsg(MainApplication.MOB_API_KEY)
                .subscribe(subscriber);

    }

}
