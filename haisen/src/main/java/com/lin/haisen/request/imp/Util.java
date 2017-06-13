package com.lin.haisen.request.imp;

import com.google.gson.JsonObject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by lpds on 2017/6/13.
 */
class Util {


    public void sub(Observable observer, Subscriber subscriber){
        observer.observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(subscriber);
    }

}
