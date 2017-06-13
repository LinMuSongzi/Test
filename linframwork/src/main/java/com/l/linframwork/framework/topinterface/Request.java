package com.l.linframwork.framework.topinterface;

import com.l.linframwork.IEmployee;
import com.l.linframwork.framework.entity.RequestEntity;

/**
 * Created by lpds on 2017/6/6.
 */
public interface Request extends IEmployee {
    <T> RequestEntity<T> getBuidler(Class<T> c);
    <T> void createImp(Class<T> c,String path);
}
