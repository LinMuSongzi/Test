package com.l.linframwork.framework.thread;

/**
 * Created by lpds on 2017/5/31.
 */
public abstract class IExecute implements Runnable{

    @Override
    public final void run() {
        try{
            execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public abstract void execute();

}
