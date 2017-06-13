package com.l.linframwork.framework.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.l.linframwork.framework.topinterface.ILeave;
import com.l.linframwork.framework.topinterface.IThread;

import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lpds on 2017/6/7.
 */
public final class ThreadBody implements ILeave,Handler.Callback{
    private ExecutorService executorService;
    private Handler handler;
    private Handler threadhandler;
    private Set<Integer> mainHandlerRunnables;

    public ThreadBody(String key) {
        this.executorService = Executors.newFixedThreadPool(IThread.MAX_THREAD_COUNT);
        this.handler = new Handler(Looper.getMainLooper(),this);
        this.mainHandlerRunnables = new LinkedHashSet<>();
        HandlerThread handlerThread = new HandlerThread(key);
        handlerThread.start();
        this.threadhandler = new Handler(handlerThread.getLooper());
    }


    @Override
    public void leave() {
            this.executorService.shutdownNow();
            for(Integer what : mainHandlerRunnables){
                this.handler.removeMessages(what);
            }
            this.threadhandler.removeCallbacksAndMessages(null);
            this.executorService = null;
            this.handler = null;
            this.threadhandler = null;
    }

    @Override
    public boolean handleMessage(Message msg) {

        ((Runnable)msg.obj).run();
        mainHandlerRunnables.remove(msg.obj.hashCode());
        return false;
    }


    public void runAotuThread(IExecute runnable){
            executorService.execute(runnable);
    }

    public void runHandlerthread(IExecute runnable){
            threadhandler.post(runnable);
    }

    public void runHandlerthread(IExecute runnable,int time){
            threadhandler.postDelayed(runnable,time);
    }

    public void runOnUiThread(IExecute runnable,int time){
            Message message = new Message();
            message.obj = runnable;
            mainHandlerRunnables.add(runnable.hashCode());
            handler.sendMessageDelayed(message,time);
    }



    public void runOnUiThread(IExecute runnable){
            Message message = new Message();
            message.obj = runnable;
            mainHandlerRunnables.add(runnable.hashCode());
            handler.sendMessage(message);
    }

}
