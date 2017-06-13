package com.l.linframwork.framework;

import com.l.linframwork.framework.base.ILife;
import com.l.linframwork.framework.topinterface.IThread;
import com.l.linframwork.framework.topinterface.Link;
import com.l.linframwork.framework.topinterface.Request;

/**
 * Created by lpds on 2017/6/6.
 */
public class Proxy {


    public static Link getActivityManager(){
        return ActivityBoss.getInstances();
    }

    public static Request getRequestManager(){
        return RequestManager.getInstances();
    }

    public static IThread getThread(){
        return ThreadManager.getInstances();
    }


}
