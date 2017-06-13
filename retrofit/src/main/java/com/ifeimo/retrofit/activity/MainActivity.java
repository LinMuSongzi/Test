package com.ifeimo.retrofit.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifeimo.retrofit.App;
import com.ifeimo.retrofit.R;
import com.ifeimo.retrofit.model.WeatherBean;
import com.ifeimo.retrofit.retrofit.WeatherService;
import com.ifeimo.retrofit.retrofit.impl.FactoryRetrofit;
import com.l.linframwork.framework.Proxy;
import com.l.linframwork.framework.activity.BaseActivity;
import com.l.linframwork.framework.thread.IExecute;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import rx.Observable;

public class MainActivity extends BaseActivity {

    Snackbar snackbar ;
    Toolbar toolbar;

    private TextView id_tv;

    @Override
    protected void initView() {
//        setContentView(R.layout.activity_main);
        id_tv = (TextView) findViewById(R.id.id_tv);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("retrofit");
        toolbar.setNavigationIcon(R.mipmap.ab_goback_gray);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"goBack",Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Proxy.getThread().execute(getKey(), new IExecute() {
                    @Override
                    public void execute() {
//                        getAsynHttp();
                        getre();
                    }
                });
                if(snackbar == null) {
                    snackbar = Snackbar.make(view, "FAB", Snackbar.LENGTH_LONG)
                            .setAction("cancel", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //这里的单击事件代表点击消除Action后的响应事件

                                }
                            });
                }
                if(snackbar.isShown()){
                    snackbar.dismiss();
                }else{
                    snackbar.show();
                }
            }
        });
        tesetthread();
    }

    private void tesetthread() {
        Proxy.getThread().execute(getKey(), new IExecute() {

            Object o = this;

            @Override
            public void execute() {
                System.err.println("************* execute 线程 run *********** ");

                synchronized (o) {
                    notify();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (o){
                                notify();
                                try {
                                    wait(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();

                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                execute();
            }
        });
    }



    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    private OkHttpClient mOkHttpClient;

    private void getre(){
        try {
            final WeatherBean w = FactoryRetrofit.getInstances().getAllMsgWeather();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    id_tv.setText(w.getResult().getCategoryInfo().getName());
                    Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getAsynHttp() {
        mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(WeatherService.PATH+"?key="+App.MOB_API);
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        final Request request = requestBuilder.build();
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String[] str = new String[1];
                if (null != response.body()) {
                    str[0] = response.body().string();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        id_tv.setText(str[0]);
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public void beforeInit(Bundle bundle) {

    }
}
