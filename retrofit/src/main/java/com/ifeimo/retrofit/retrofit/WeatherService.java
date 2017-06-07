package com.ifeimo.retrofit.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

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
    Call<JsonObject> getAllMsg(@Query("key") String value);

}
