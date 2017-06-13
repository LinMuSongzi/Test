package com.lin.haisen.request;

import com.google.gson.JsonObject;
import com.l.linframwork.framework.anno.Path;
import com.lin.haisen.data.entity.UserEntity;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.PATCH;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by lpds on 2017/6/12.
 */
public interface UserService {

    @Path
    String PATH = "https://linhui.lizuolin.com";

    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST("/MrLin.svc/register1")
    Observable<JsonObject> registerUser(@Body UserEntity requestBody);

    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST("/MrLin.svc/login1")
    Observable<JsonObject> loginByUser(@Body UserEntity requestBody);



}
