package com.lin.haisen.request;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by lpds on 2017/5/31.
 */
public interface WeatherService {

    String PATH = "http://apicloud.mob.com/v1/cook/category/query";

    @GET("?")
    Observable<JsonObject> getAllMsg(@Query("key") String value);

}
