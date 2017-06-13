package com.l.linframwork.framework;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.l.linframwork.framework.thread.ThreadBody;
import com.l.linframwork.framework.topinterface.ILeave;
import com.l.linframwork.framework.topinterface.IThread;
import com.l.linframwork.framework.thread.IExecute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lpds on 2017/5/27.
 */
final class ThreadManager implements IThread {

    private static final String TAG = "ThreadManager";
    private Map<String,ThreadBody> executorServices;
    private static ThreadManager threadManager;
    static{
        threadManager = new ThreadManager();
    }
    public static IThread getInstances(){
            return threadManager;
    }

    public ThreadManager() {
        init();
    }


    @Override
    public boolean hadOfficeholding() {
        return executorServices != null;
    }

    @Override
    public boolean hadDimission() {
        return false;
    }

    @Override
    public void fire() {

    }

    @Override
    public void createThreadPoolBykey(String key) {
        synchronized (this){
            if(key!=null && !executorServices.containsKey(key)){
               // ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
                executorServices.put(key,new ThreadBody(key));
                Log.i(TAG, "createThreadPoolBykey: "+key);
            }
        }
    }

    @Override
    public void leaveThreadByPoolByKey(String key) {
        synchronized (this){
            if(key != null && executorServices.containsKey(key)){
                executorServices.get(key).leave();
                executorServices.remove(key);
            }
        }
    }

    @Override
    public void execute(String key, IExecute runnable, ExecuteMode m) {
        switch (m){
            case POLL:
                executorServices.get(key).runAotuThread(runnable);
                break;
            case TEMPORARY:
//                runHandlerThread(runnable);
                break;
        }
    }

    @Override
    public void execute(String key, IExecute runnable) {
        execute(key,runnable,ExecuteMode.POLL);
    }

    @Override
    public void executeOnQueue(String key, IExecute runnable) {
        if(key != null && executorServices.containsKey(key)){
            executorServices.get(key).runHandlerthread(runnable);
        }
    }

    @Override
    public void executeOnQueue(String key, IExecute runnable, int time) {
        if(key != null && executorServices.containsKey(key)){
            executorServices.get(key).runHandlerthread(runnable,time);
        }
    }

    @Override
    public void executeOnMainQueue(String key, IExecute runnable) {
        if(key != null && executorServices.containsKey(key)){
            executorServices.get(key).runOnUiThread(runnable);
        }
    }

    @Override
    public void executeOnMainQueue(String key, IExecute runnable, int time) {
        if(key != null && executorServices.containsKey(key)){
            executorServices.get(key).runOnUiThread(runnable,time);
        }
    }

    @Override
    public void init() {
        executorServices = new HashMap<>();
    }

}
