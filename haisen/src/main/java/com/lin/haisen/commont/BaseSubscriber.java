package com.lin.haisen.commont;

import android.app.Activity;
import android.util.Log;

import com.l.linframwork.framework.activity.IActivity;

import rx.Subscriber;

/**
 * Created by lpds on 2017/6/13.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    public static final String TAG = "安兴";
    private IActivity activity;

    public BaseSubscriber(IActivity activity) {
        this.activity = activity;
    }

    @Override
    public final void onCompleted() {
        if(activity.getActivity().isFinishing()){
            return;
        }
        Log.i(TAG, "************ onCompleted *************");
        completed();
    }

    @Override
    public final void onError(Throwable throwable) {
        error(throwable);
    }

    protected void error(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public final void onNext(T t) {
        if(activity.getActivity().isFinishing()){
            return;
        }
        Log.i(TAG, "onNext: ************ "+t.toString()+" *************");
        next(t);
    }

    protected abstract void next(T t);

    protected abstract void completed();


}
