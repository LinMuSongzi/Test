package com.l.linframwork.framework.data;

import retrofit.Retrofit;

/**
 * Created by lpds on 2017/6/12.
 */
public class RequestEntity<T> {
    private String path;
    private T interfaceEntity;
    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public T getInterfaceEntity() {
        return interfaceEntity;
    }

    public void setInterfaceEntity(T interfaceEntity) {
        this.interfaceEntity = interfaceEntity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
