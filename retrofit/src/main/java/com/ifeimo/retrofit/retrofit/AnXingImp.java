package com.ifeimo.retrofit.retrofit;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.ifeimo.retrofit.App;
import com.ifeimo.retrofit.activity.MainActivity;
import com.ifeimo.retrofit.model.UserEntity;
import com.l.linframwork.framework.entity.JsonEntity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lpds on 2017/6/5.
 */
public class AnXingImp {

    private static <T> T createService(Class<T> tClass,String path){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(path).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(tClass);
    }


    public static void register1(UserEntity userEntity) throws IOException {
        AnXing retrofit =  new Retrofit.Builder().
                baseUrl(AnXing.PATH).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build().create(AnXing.class);
        retrofit.registerUser(userEntity).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted", "onCompleted: ");
//                        App.application.startActivity(new Intent(App.application,MainActivity.class));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.i("onError", "onError: ");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(Object jsonObject) {
                        System.err.println(jsonObject.toString());
                        EventBus.getDefault().post(new JsonEntity(jsonObject.toString()));
                    }
                });
//        Response<JsonObject> o = call.execute();
//        JsonEntity jsonEntity = new JsonEntity();
//        jsonEntity.setJson(String.valueOf(o.body()));
//        EventBus.getDefault().post(jsonEntity);
    }
    static OkHttpClient mOkHttpClient;
    public static void getAsynHttp(UserEntity userEntity) {
        Gson gson  = new Gson();
        String json =gson.toJson(userEntity);
        mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url("http://linhui.lizuolin.com/MrLin.svc/register1");
        //可以省略，默认是GET请求
        requestBuilder.method("POST", RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json));
        com.squareup.okhttp.Call mcall= mOkHttpClient.newCall(requestBuilder.build());
        mcall.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                request.toString();

            }

            @Override
            public void onResponse(final com.squareup.okhttp.Response response) throws IOException {
                final String[] str = new String[1];
                if (null != response.body()) {
                    str[0] = response.body().string();
                }
                JsonEntity jsonEntity = new JsonEntity();
                jsonEntity.setJson(str[0]);
                EventBus.getDefault().post(jsonEntity);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        id_tv.setText(str[0]);
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }

    public static void baidu() throws IOException {
        AnXing retrofit = new Retrofit.Builder().baseUrl("http//www.baidu.com").build().create(AnXing.class);
        Call call = retrofit.getBaiduTest();
        Object o = RequestManager.getInstances().responeConvert(call.execute());
        String json = o.toString();
        JsonEntity jsonEntity = new JsonEntity();
        jsonEntity.setJson(json.toString());
        EventBus.getDefault().post(jsonEntity);
    }

}
