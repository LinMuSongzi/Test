package com.ifeimo.retrofit.retrofit;


import com.google.gson.JsonObject;
import com.ifeimo.retrofit.model.UserEntity;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by lpds on 2017/6/5.
 */
public interface AnXing {

   String PATH = "https://linhui.lizuolin.com";

    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST("/MrLin.svc/register1")
    Observable<JsonObject> registerUser(@Body UserEntity requestBody);

    @FormUrlEncoded
    @POST("/login1")
    Call<JsonObject> loginByUser(@Field("secretKey") String secreKey, @Field("account") String account, @Field("password") String password);


    @GET("/")
    Call getBaiduTest();

}
