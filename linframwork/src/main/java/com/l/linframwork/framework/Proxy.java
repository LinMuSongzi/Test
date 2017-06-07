package com.l.linframwork.framework;

import com.l.linframwork.framework.base.ILife;
import com.l.linframwork.framework.topinterface.IThread;

/**
 * Created by lpds on 2017/6/6.
 */
public class Proxy {


    public static ILife getLifeManager(){
        return ActivityBoss.getInstances();
    }

    public static RequestManager getRequestManager(){
        return RequestManager.getInstances();
    }

    public static IThread getThread(){
        return ThreadManager.getInstances();
    }


}
