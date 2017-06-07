package com.ifeimo.retrofit.retrofit.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ifeimo.retrofit.App;
import com.ifeimo.retrofit.model.WeatherBean;
import com.ifeimo.retrofit.retrofit.WeatherService;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by lpds on 2017/5/31.
 */
public class FactoryRetrofit {
    private static FactoryRetrofit factoryRetrofit;
    static {
        factoryRetrofit = new FactoryRetrofit();
    }

    private OkHttpClient httpClient;

    private FactoryRetrofit(){
        httpClient = new OkHttpClient();
    }

   public static FactoryRetrofit getInstances(){
        return factoryRetrofit;
    }

    private <T> T createService(Class<T> tClass,String path){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(path).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(tClass);
    }

    public WeatherBean getAllMsgWeather() throws IOException {
        Call call = createService(WeatherService.class,WeatherService.PATH).getAllMsg(App.MOB_API);
        Response<JsonObject> response = call.execute();
        Gson gson = new GsonBuilder().create();
        WeatherBean weatherBean = gson.fromJson(response.body().toString(), WeatherBean.class);
        return weatherBean;
    }

}
