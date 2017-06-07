package com.ifeimo.retrofit.retrofit;

import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by lpds on 2017/1/18.
 */
public interface RetrofitIMP {

    @GET()
    Call<JSONObject> getBaidu();

    @GET("")
    Observable<JSONObject> getBaidu2();
}
