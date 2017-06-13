package com.lin.haisen.request;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.http.POST;
import rx.Observable;

/**
 * Created by lpds on 2017/6/13.
 */
public interface TestService {

    String PATH = "Http://linhui.lizuolin.com/MrLin.svc/Test?name=1";

    @POST("/")
    Observable<JsonObject> test1();

}
