package com.l.linframwork.framework.topinterface;


import com.l.linframwork.framework.thread.IExecute;

/**
 * Created by lpds on 2017/5/27.
 */
public interface IThread extends IPosition{
    int MAX_THREAD_COUNT = 20;
    void createThreadPoolBykey(String key);

    void leaveThreadByPoolByKey(String key);

    void execute(String key, IExecute runnable, ExecuteMode m);
    void execute(String key, IExecute runnable);
    void executeOnQueue(String key, IExecute runnable);
    void executeOnQueue(String key,IExecute runnable,int time);
    void executeOnMainQueue(String key,IExecute runnable);
    void executeOnMainQueue(String key,IExecute runnable,int time);
    enum ExecuteMode{
        TEMPORARY,
        POLL
    }

}
